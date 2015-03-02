package actions.tools;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import actions.tools.exception.SymbolNotFoundException;
import analytics.exception.FIFActoryException;
import analytics.util.AnalyticsUtil;



/**
 * Interpreter class to execute actions, during the walk of the financial DSL parse tree
 * @author randheerDas
 *
 */
public class FinanceInterpreter {

	/** "memory" for our financial app ; variable/value pairs go here */ 
	private Map<String, Object> appMemory = new HashMap<String, Object>();
		
	public static final int MUL=5, DIV=9, SUB=6, ADD=4;
	
	/***
	 *  Current set of supported DSL functions 
	 * */
	
	enum DSLFunctions {
		
		createBondInstrument("createBondInstrument"),
		createBondContract("createBondContract"),
		calcYieldFromCleanPrice("calcYieldFromCleanPrice"), 
		calcCleanPriceFromYield("calcCleanPriceFromYield"),
		calcAI("calcAI"),
		printCashFlows("printCashFlows"),
		print("print");
		
		private final String value;

		private DSLFunctions(final String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
	} // End of enum DSLFunctions
	
	public Object eval(Double left, int op, Double right) {
		
		switch ( op ) {
	        case MUL : return left * right;
	        case DIV : return left / right;
	        case ADD : return left + right;
	        case SUB : return left - right;
	    }
	    return 0;
	}
	
	/** Function that populates the app memory*/
	public void store(String name, Object o) { 
		appMemory.put(name, o); 
	}
	
	/**
	 * This function  executes the functions supported by the financial DSL.
	 * It determines the type of the function name and dispatches the call to the 
	 * respective function. 
	 * 
	 * TODO: This is the first version. Various optimizations like:
	 *  1. Configuring the function names in a config file
	 *  2. Grouping these by financial asset class specifics.
	 *  ............
	 * 
	 * @param functionName
	 * @return Returns the execution result as a an Object
	 */
	public Object evaluateFunctionCall(String functionName, String funcArgs){
		Object outputValue = null;
		
	try{
		DSLFunctions dslFunction	=	DSLFunctions.valueOf(functionName);
		
		/* Maps DSL function calls to respective Analytic Functions */
		switch (dslFunction) {
				case createBondInstrument: {
					outputValue		=	AnalyticsUtil.createBondInstrument(this,funcArgs);
					break;
				}
				case createBondContract: {
					outputValue		=	AnalyticsUtil.createBondContract(this,funcArgs);
					break;
				}
				case calcAI: {
					outputValue		=	AnalyticsUtil.calculateAccruedInterest(this,funcArgs);
					break;
				}
				case calcYieldFromCleanPrice: {
					outputValue		=	AnalyticsUtil.calculateYieldFromCleanPrice(this,funcArgs);
					break;
				}
				case calcCleanPriceFromYield: {
					outputValue		=  AnalyticsUtil.calcCleanPriceFromYie1d(this,funcArgs);
					break;
				}
				case printCashFlows: {
					AnalyticsUtil.printCashFlows(this,funcArgs);
					break;
				}
				case print: {
					System.out.println(funcArgs + " is = " + appMemory.get(funcArgs));
					break;
				}
			}// End of Switch
		} catch (IllegalArgumentException iae) {
			// Unsupported function, log and report the error
			System.err.println("Unsupported function --> " + functionName);
			
			// Build the set of supported DSL functions
			StringBuffer supportedFunctions = new StringBuffer().append("\n");
			for (final DSLFunctions element : EnumSet.allOf(DSLFunctions.class)) {
				supportedFunctions.append(element.toString()).append("\n");
			}// End of for
			System.out.println("List of Supported DSL functions are:" + supportedFunctions);
			
		} catch (SymbolNotFoundException snfe) {
			// A SymbolNotFoundException occurred. Can continue only after the symbol is defined.
			System.err.println("SymbolNotFoundException occured " + " The message is = " + snfe.getMessage());
			System.err.println("The Error occured at --> 	");
			snfe.printStackTrace();
			
		} catch (FIFActoryException fifae) {
			System.err.println("Error occured during the creation of a Instrument");
			System.err.println("Error Message is = " + fifae.getCause().getMessage());
		}
	
		return outputValue;
	}
	
	/* Check for the variable */
	public boolean containsObject(String id){
		
		return appMemory.containsKey(id)? true:false;
	}
	
	/* Fetch the variable's value*/
	public Object getVarValue(String id) throws SymbolNotFoundException {
		
		Object value = appMemory.get(id);
		
		if(value != null){
			return value;
		}else{
			String errorMsg  = "Symbol " + id + " is not defined";
			System.err.println(errorMsg);
			System.out.println("Please define the symbol = " + id);
			
			throw new SymbolNotFoundException(errorMsg);
			
		}
		
	}
	
	
}
