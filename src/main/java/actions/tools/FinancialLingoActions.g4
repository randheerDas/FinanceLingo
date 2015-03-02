grammar FinancialLingoActions ; 

@header {
package actions.tools;
}

@parser::members {

 	FinanceInterpreter finInterp;
    public FinancialLingoActionsParser(TokenStream input, FinanceInterpreter finInterp) {
        this(input);
        this.finInterp = finInterp;
    }
}

/** Parser Rules */ 
program: stat NEWLINE ; 

stat:  expr 
       |assignment 
       |funcCall 
       |NEWLINE ;
       
       
assignment
       :ID '=' ( expr  {finInterp.store($ID.text, $expr.v);}
               | funcCall {finInterp.store($ID.text, $funcCall.v);} ) 
        | type ID ( '=' expr)? 	  {finInterp.store($ID.text, $expr.v);} // For Primitives 
       ;

type : 'double' | 'float' | 'int' ;

expr returns [Object v] 
	  : a=expr op=('*'|'/') b=expr  {$v = finInterp.eval((Double)$a.v, $op.type, (Double)$b.v);}
      | a=expr op=('+'|'-') b=expr  {$v = finInterp.eval((Double)$a.v, $op.type, (Double)$b.v);}	
      |funcCall  {$v = $funcCall.v;} 
      |literal   {$v = $literal.v;}
      |ID  
	    {  // variable reference
	      	String id = $ID.text;
	      	$v = finInterp.containsObject(id) ? finInterp.getVarValue(id) : id;
	    }   
      |'('expr')'  {$v = $expr.v;} 
      ;

literal returns [Object v]
		 : FloatingPointLiteral {$v = Double.parseDouble($FloatingPointLiteral.text);}
         |INT {$v = new Integer($INT.int).doubleValue();} 
         ;

funcCall returns [Object v]  // func call like f(), f(x), f(1,2)
	: ID '('exprList?')' {$v = finInterp.evaluateFunctionCall($ID.text,$exprList.text);};

exprList: expr (',' expr)*; // arg list 

/** Lexer Rules */ 

FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix 
    ;

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

//Match identifiers
ID : DAYCOUNT
     |BUSDAYCONV
     |YIELDCONV
     |ISSUER
     |[a-zA-Z|'_']+
     ; 

fragment //Supported Day Counts
DAYCOUNT : ('actual_actual_icma'|'actual/360'|'actual/365'
           |'actual_actual_afb'|'actual_actual_isda'|'30U/360'|'30E/360'
           |'28/360'|'Business/252'|'30/360'|'actual/36525'|'30E/360 isda');	

fragment //Supported Business Day Conventions
BUSDAYCONV : ('Modified_Following'|'Modified_Preceding'|'Following'|'Preceding'|'None');

fragment //Supported Yield Conventions
YIELDCONV : ('us_street'|'money_market'|'uk_bump_dmo_method'
            |'uk_strip_method'|'us_il_real'|'us_treasury_equivalent'
            |'jgb_simple'|'us_bond'); 

fragment //Supported ISSUERS
ISSUER : ('belg_govt'|'germ_govt'|'italy_govt'
          |'uk_govt'|'us_govt'|'aust_govt');

INT : [0-9]+; // match integers
NEWLINE : '\r'? '\n' ; // return newlines to parser (is end- statement signal) 
WS: [ \t]+ -> skip; // toss out whitespace 
 

COMMENT
     : '//' .*? '\r'? '\n' -> skip;