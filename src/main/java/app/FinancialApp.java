package app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import actions.tools.FinanceInterpreter;
import actions.tools.FinancialLingoActionsLexer;
import actions.tools.FinancialLingoActionsParser;


/**
 * The main class that drives the Financial DSL application
 * @author randheerDas
 *
 */
public class FinancialApp {

	public static void main(String[] args) throws Exception {
        
		String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        
        InputStream is = System.in;
        if ( inputFile!=null ) {
            is = new FileInputStream(inputFile);
        }

        // Welcome message // TODO: Need to work on the versioning scheme
        System.out.println("Financial DSL interpreter version 1.0:");
        System.out.print(">");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String expr = br.readLine();              // get first expression
        int line = 1;                             // track input expr line numbers

        FinanceInterpreter finInterp	   = new FinanceInterpreter();
        FinancialLingoActionsParser parser = new FinancialLingoActionsParser(null,finInterp); // share single parser instance
  		parser.setBuildParseTree(false);          // don't need trees

        while ( expr!=null ) {             // while we have more expressions
           
        	// create new lexer and token stream for each line (expression)
            ANTLRInputStream input 				= new ANTLRInputStream(expr+"\n");
            FinancialLingoActionsLexer lexer 	= new FinancialLingoActionsLexer(input);
            lexer.setLine(line);           // notify lexer of input position
            lexer.setCharPositionInLine(0);
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            parser.setInputStream(tokens); // notify parser of new token stream
            parser.program();               // start the parser
            System.out.print(">");	    // Input prompt
            expr = br.readLine();          // see if there's another line
            line++;
        }
    } //End of Main
} 
