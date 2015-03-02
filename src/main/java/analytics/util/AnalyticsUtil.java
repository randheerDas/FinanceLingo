package analytics.util;

import org.threeten.bp.ZonedDateTime;

import actions.tools.FinanceInterpreter;
import actions.tools.exception.SymbolNotFoundException;
import analytics.creator.AnalyticsAbstractFactory;
import analytics.exception.FIFActoryException;
import app.util.DateUtilityFunctions;

import com.opengamma.analytics.financial.instrument.InstrumentDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondFixedSecurityDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondSecurityDefinition;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivative;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondFixedSecurity;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondSecurity;
import com.opengamma.analytics.financial.interestrate.bond.provider.BondSecurityDiscountingMethod;


/**
 * 
 * @author randheerDas
 * A Utility class providing Analytics specific utility functions
 *
 */

public final class AnalyticsUtil {
	
	 /** CREATOR FUNCTIONS 
	 * @param financeInterpreter 
	 * @return 
	 * @throws SymbolNotFoundException 
	 * @throws FIFActoryException */
	public static InstrumentDefinition<?> createBondInstrument(FinanceInterpreter financeInterpreter, String funcArgs)
			throws SymbolNotFoundException, FIFActoryException {
			
		//Setup the reference to the interpretor, as it contains the symbol table information
		 AnalyticsAbstractFactory.setFinInterp(financeInterpreter);
		 
		 AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.ProductFamilies.FIXED_INCOME);
		 InstrumentDefinition<?> bondInstrument 	= af.createBond(funcArgs);
		 return bondInstrument;
	}
	
	public static void createCDSContract(FinanceInterpreter financeInterpreter,String funcArgs) {
		 
		//Setup the reference to the interpreter, as it contains the symbol table information
		 AnalyticsAbstractFactory.setFinInterp(financeInterpreter);
		 
		 AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.ProductFamilies.OTC_DERIVATIVES);
		 InstrumentDerivative cdsInstrument 		= af.createCDS(funcArgs);
		
	}
	
	public static void createIRSContract(FinanceInterpreter financeInterpreter,String funcArgs) {
		 
		 //Setup the reference to the interpretor, as it contains the symbol table information
		 AnalyticsAbstractFactory.setFinInterp(financeInterpreter);
		 
		 AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.ProductFamilies.LISTED_DERIVATIVES);
		 InstrumentDerivative irsInstrument 		= af.createIRS(funcArgs);
	}

	public static Object createBondContract(FinanceInterpreter financeInterpreter, String funcArgs) throws SymbolNotFoundException,
																										   FIFActoryException {
		 BondSecurity<?,?> bondContract = null;
		
		/** Function arguments contain the Instrument Definition and Trade Date.
		 *  Each argument is the symbol name. Need to fetch the value using 
		 *  finInterp.getVarValue();
		 */
		String[] argArray 	=	funcArgs.split(",");
		
		// First arg is the Instrument Definition. Fetch the constructed instrument from the symbol table
		Object instrument = financeInterpreter.getVarValue(argArray[0]);
				
		if(instrument instanceof BondFixedSecurityDefinition){
		
			BondFixedSecurityDefinition bondFixedDef = (BondFixedSecurityDefinition)instrument;
			// Second arg is the Trade Date. Fetch the Trade Date value from the symbol table
			Double tradeDateValue				=  (Double) financeInterpreter.getVarValue(argArray[1]);
			final ZonedDateTime	tradeDate 	    =  DateUtilityFunctions.covertToDate(tradeDateValue,"TradeDate");
			
			bondContract 						= bondFixedDef.toDerivative(tradeDate);
		}else{
			/* Other type of Bonds not supported yet */
			throw new FIFActoryException("Currently only Fixed Bonds supported. " 
											+ instrument.getClass().getName() + " Not Supported");
		}
		
		return bondContract;
	}

		
	/***
	 * ##################--------------------TODO-----------------###############################
	 * public static void createCommoditiesInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.OTC_DERIVATIVES);
	 *  . ................
	 *   
	 * }
	 * 
	 * public static void createEquityOptionsInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.LISTED_DERIVATIVES);
	 *  . ................
	 *   
	 * }
	 * 
	 * public static void createCommoditiesInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.OTC_DERIVATIVES);
	 *  . ................
	 *   
	 * }
	 *
	 * public static void createFXSwapsInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.FOREIGN_EXCHANGE);
	 *  . ................
	 *   
	 * }
	 * 
	 * public static void createFXForwardsInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.FOREIGN_EXCHANGE);
	 *  . ................
	 *   
	 * }
	 * 
	 *  public static void createStocksInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.EQUITIES);
	 *  . ................
	 *   
	 * }
	 * 
	 *  public static void createFundsInstrument(String funcArgs) {
	 * 	  AnalyticsAbstractFactory af 				= AnalyticsAbstractFactory.getFactory(AnalyticsAbstractFactory.FUNDS);
	 *  . ................
	 *   
	 * }
	 */
	
	/** ############ ANALYTIC FUNCTIONS ########### *****/ 
	 
	/**
	 * * @throws SymbolNotFoundException **/
	public static void printCashFlows(FinanceInterpreter financeInterpreter, String instrumentSymbol) throws SymbolNotFoundException {
		
		// Fetch the constructed instrument from the symbol table
		Object instrument = financeInterpreter.getVarValue(instrumentSymbol);
		
		if(instrument instanceof BondSecurityDefinition){
			
			BondSecurityDefinition<?,?> bondDef = (BondSecurityDefinition<?,?>)instrument;
			String couponPayments  = bondDef.getCoupons().toString();
			System.out.println("Cashflow Details ................");
			System.out.println(couponPayments);
			
		}// TODO  else if(){
			// Checks for other Instruments.
		//}
		
	}

	public static Object calculateAccruedInterest(FinanceInterpreter financeInterpreter, String contractSymbol) 
			throws SymbolNotFoundException {
	
		double accruedInterest = 0;
		// Fetch the constructed security or contract from the symbol table
		Object security = financeInterpreter.getVarValue(contractSymbol);
		
		if(security instanceof BondFixedSecurity){
			BondFixedSecurity bondContract	= ((BondFixedSecurity)security);
			accruedInterest					=  bondContract.getAccruedInterest();
		}
		return accruedInterest;
	}

	public static Object calculateYieldFromCleanPrice(
			FinanceInterpreter financeInterpreter, String funcArgs) throws SymbolNotFoundException {
		
		double yield = 0;
		
		/** Function arguments contain the Security and Price.
		 *  Each argument is the symbol name. Need to fetch the value using 
		 *  finInterp.getVarValue();
		 */
		String[] argArray 	=	funcArgs.split(",");
		
		// First arg is the Security. Fetch the constructed instrument from the symbol table
		Object security = financeInterpreter.getVarValue(argArray[0]);
		
		if(security instanceof BondFixedSecurity){
		   
			//Create a Discounting calculator for the Fixed Coupon Bond Type
			BondSecurityDiscountingMethod discountingCalculator = BondSecurityDiscountingMethod.getInstance();
			BondFixedSecurity bondFixedSecurity					= (BondFixedSecurity)security;
			
			// Second arg is the Clean Price. Fetch the Clean Price value from the symbol table
			Double cleanPrice									= (Double)financeInterpreter.getVarValue(argArray[1]);
			yield 												= discountingCalculator.yieldFromCleanPrice(
																  bondFixedSecurity, cleanPrice);
		} 
		
		// TODO create the respective discounting calculator for other bond types else if {}
		
		return yield;
	}

	public static Object calcCleanPriceFromYie1d(FinanceInterpreter financeInterpreter, String funcArgs) throws SymbolNotFoundException {
				
		double cleanPrice = 0;
		
		/** Function arguments contain the Security and Yield.
		 *  Each argument is the symbol name. Need to fetch the value using 
		 *  finInterp.getVarValue();
		 */
		String[] argArray 	=	funcArgs.split(",");
		
		// First arg is the Security. Fetch the constructed instrument from the symbol table
		Object security = financeInterpreter.getVarValue(argArray[0]);
		
		if(security instanceof BondFixedSecurity){
		   
			//Create a Discounting calculator for the Fixed Coupon Bond Type
			BondSecurityDiscountingMethod discountingCalculator = BondSecurityDiscountingMethod.getInstance();
			BondFixedSecurity bondFixedSecurity					= (BondFixedSecurity)security;
			
			// Second arg is the Yield. Fetch the yield value from the symbol table
			Double yield									= (Double)financeInterpreter.getVarValue(argArray[1]);
			cleanPrice										= discountingCalculator.cleanPriceFromYield(bondFixedSecurity,yield);
		} 
		
		// TODO create the respective discounting calculator for other bond types else if {}
		
		return cleanPrice;
	}

	
}
