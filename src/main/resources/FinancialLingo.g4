grammar FinancialLingo ; 

/** Parser Rules */ 
program: stat NEWLINE ; 

stat:  expr 
       |assignment 
       |funcCall 
       |NEWLINE ;
       
assignment
       :ID '=' ( expr | funcCall ) 
        | type ID ( '=' expr)? // For Primitives 
       ;

type : 'double' | 'float' | 'int' ;

expr : expr ('*'|'/') expr 
      |expr ('+'|'-') expr	
      |funcCall 
      |literal 
      |ID      // variable reference
      |'('expr')'
      ;

literal : FloatingPointLiteral
         |INT
         ;

funcCall : ID '('exprList?')'; // func call like f(), f(x), f(1,2)

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