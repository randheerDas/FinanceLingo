package analytics.creator;


import java.util.EnumSet;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeParseException;

import actions.tools.FinanceInterpreter;
import actions.tools.exception.SymbolNotFoundException;
import analytics.exception.FIFActoryException;
import app.util.DateUtilityFunctions;

import com.opengamma.analytics.financial.instrument.InstrumentDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondFixedSecurityDefinition;
import com.opengamma.analytics.financial.instrument.bond.BondSecurityDefinition;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondSecurity;
import com.opengamma.analytics.financial.interestrate.swap.derivative.Swap;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventions;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCounts;
import com.opengamma.financial.convention.yield.SimpleYieldConvention;
import com.opengamma.financial.convention.yield.YieldConvention;
import com.opengamma.financial.convention.yield.YieldConventionFactory;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.DateUtils;

/**
 * 
 * @author randheerDas
 * This class abstracts the creation of various Financial instruments 
 * across a family of Asset Classes.
 * Inspired from the Abstract Factory Design pattern
 * http://en.wikipedia.org/wiki/Abstract_factory_pattern
 *
 */
public abstract class AnalyticsAbstractFactory {
	
	/** Abstract Method declarations implemented by concrete factory implementations 
	 * */
	
	 public abstract InstrumentDefinition<?>  createBond(String args) throws SymbolNotFoundException, FIFActoryException;
	 public abstract Swap<?,?> 			 	  createCDS(String args);
	 public abstract Swap<?,?> 			 	  createIRS(String args);
	 
	 /** Singleton instances of concrete factory classes */
	 private static final FixedIncomeFactory fixedIncomeFactory 			= new FixedIncomeFactory();
	 private static final OTCDerivativeFactory otcDerivativeFactory 		= new OTCDerivativeFactory();
	 private static final ListedDerivativeFactory listedDerivativeFactory 	= new ListedDerivativeFactory();
	 //TODO : Need to define other product family factories
	 
	 // Reference to the FinanceInterpretor
	 private static FinanceInterpreter finInterp;
	 
	/***
	 *   Enum defining the supported family of Product Families
	 * */
	
	public enum  ProductFamilies {
	 
		FIXED_INCOME("FIXED_INCOME"), 
		OTC_DERIVATIVES("OTC_DERIVATIVES"), 
		LISTED_DERIVATIVES("LISTED_DERIVATIVES"), 
		FOREIGN_EXCHANGE("FOREIGN_EXCHANGE"),		// Cannot c
		EQUITIES("EQUITIES"),
		FUNDS("FUNDS");
		
		private final String value;

		private ProductFamilies(final String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
	}
	
	/** Returns a concrete factory object that is an instance of the
	  * concrete factory class appropriate for the given Product Family. 
	 * @param financeInterpreter */
	public static AnalyticsAbstractFactory getFactory(ProductFamilies prodFamily) {
		
		AnalyticsAbstractFactory currentFactoryInstance = null;
		try{
			switch (prodFamily) {
				case FIXED_INCOME: {
					currentFactoryInstance   =  fixedIncomeFactory;
					break;
				}case OTC_DERIVATIVES: {
					currentFactoryInstance   =  otcDerivativeFactory;
					break;
				}case LISTED_DERIVATIVES: {
					currentFactoryInstance	 = listedDerivativeFactory;
					break;
				}
				// TODO: Need to add more switch case for other product families
			}// End of Switch
		}catch (IllegalArgumentException iae) {
			// Unsupported function, log and report the error
			System.err.println("Unsupported Product Family, " + prodFamily);
		}
		return currentFactoryInstance;
	}
	
	public static FinanceInterpreter getFinInterp() {
		return finInterp;
	}
	public static void setFinInterp(FinanceInterpreter finInterp) {
		AnalyticsAbstractFactory.finInterp = finInterp;
	}

	/**
	 * Concrete Class factories
	 */
	private static class FixedIncomeFactory extends AnalyticsAbstractFactory
	{
		/** Currently supported set of DayCountConventions */
		enum DayCountTypes {
			
			ACT_ACT_ICMA("actual_actual_icma"), ACT_360("actual/360"),
			ACT_365("actual/365"), ACT_ACT_AFB("actual_actual_afb"),
			ACT_ACT_ISDA("actual_actual_isda"), ACT_36525("actual/36525"),
			THIRTY_U_360("30U/360"), THIRTY_E_360("30E/360"),
			THIRTY_E_360_ISDA("30E/360 isda"), TWENTY_EIGHT_360("28/360"),
			BUSINESS_252("Business/252"), THIRTY_360("30/360");
					
			private final String value;

			private DayCountTypes(final String value) {
				this.value = value;
			}

			public String toString() {
				return value;
			}

			/* The below method obtains a Valid Enum based on the String value */
			public static DayCountTypes getByValue(String value) {
				for (final DayCountTypes element : EnumSet.allOf(DayCountTypes.class)) {
					if (element.toString().equals(value)) {
						return element;
					}
				}
				throw new IllegalArgumentException("A Valid DayCount Convention not defined for the input value: " + value);
			}
		} // End of DayCountTypes
		
		/** Currently supported set of BusinessDayConventionTypes */
		enum BusinessDayConventionTypes {
			
			MODIFIED_FOLLOWING("Modified_Following"),
			MODIFIED_PRECEDING("Modified_Preceding"),
			FOLLOWING("Following"),
			PRECEDING("Preceding"),
			NONE("None");
		
			private final String value;

			private BusinessDayConventionTypes(final String value) {
				this.value = value;
			}

			public String toString() {
				return value;
			}
			
			/* The below method obtains a Valid Enum based on the String value */
			public static BusinessDayConventionTypes getByValue(String value) {
				for (final BusinessDayConventionTypes element : EnumSet.allOf(BusinessDayConventionTypes.class)) {
					if (element.toString().equals(value)) {
						return element;
					}
				}
				throw new IllegalArgumentException("A Valid BusinessDayConvention Type not defined for the input value: " + value);
			}
		
		} // End of BusinessDayConventionTypes
		
		/** Currently supported set of YieldConventionTypes */
		enum YieldConventionTypes {
	
			UK_STRIP_METHOD("uk_strip_method"),
			UK_BUMP_DMO_METHOD("uk_bump_dmo_method"),
			US_IL_REAL("us_il_real"),
			US_STREET("us_street"),
			US_TREASURY_EQUIVALANT("us_treasury_equivalent"),
			MONEY_MARKET("money_market"),
			JGB_SIMPLE("jgb_simple"),
			US_BOND("us_bond");
			 
						
			private final String value;

			private YieldConventionTypes(final String value) {
				this.value = value;
			}

			public String toString() {
				return value;
			}
			
			/* The below method obtains a Valid Enum based on the String value */
			public static YieldConventionTypes getByValue(String value) {
				for (final YieldConventionTypes element : EnumSet.allOf(YieldConventionTypes.class)) {
					if (element.toString().equals(value)) {
						return element;
					}
				}
				throw new IllegalArgumentException("A Valid YieldConvention Type not defined for the input value: " + value);
			}
			
		}// End of YieldConventionTypes
		
		/** Currently supported set of Issuers */
		enum Issuers {
		 
			BEL_ISSUER("belg_govt"),GER_ISSUER("germ_govt"),
			IT_ISSUER("italy_govt"),UK_ISSUER("uk_govt"),
			US_ISSUER("us_govt"), AUS_ISSUER("aust_govt");
		
			private final String value;

			private Issuers(final String value) {
				this.value = value;
			}
	
			public String toString() {
				return value;
			}
			
			/* The below method obtains a Valid Enum based on the String value */
			public static Issuers getByValue(String value) {
				for (final Issuers element : EnumSet.allOf(Issuers.class)) {
					if (element.toString().equals(value)) {
						return element;
					}
				}
				throw new IllegalArgumentException("A Valid Issuer not defined for the input value: " + value);
			}
		}

		@Override
		public InstrumentDefinition<?> createBond(String funcArgs) throws SymbolNotFoundException,FIFActoryException {
			 
			/** Function arguments contain the Bond contractual details.
			 *  Each argument is the symbol name. Need to fetch the value using 
			 *  finInterp.getVarValue();
			 */
			String[] argArray 	=	funcArgs.split(",");
			
		    // First arg = Currency
			Currency ccy  = null;
			try{
				String ccyVal = (String) finInterp.getVarValue(argArray[0]);
				ccy 		  = Currency.of(ccyVal);
			}catch(IllegalArgumentException iae){
				// As it is currently a console app, print the error
				System.err.println("Not a Valid Currency	" + argArray[0] + "	Please input a valid ccy code: ");
				/* Cannot proceed further*/
				throw new FIFActoryException("An Invalid currency provided",iae);
			}
			
			/** Second arg = Maturity Date */
			Double matDateVal					=  (Double) finInterp.getVarValue(argArray[1]);
			final ZonedDateTime	maturityDate 	= DateUtilityFunctions.covertToDate(matDateVal,"MaturityDate");
			
			/** Third arg = Accrual Date */
			Double accrualDateVal				=  (Double) finInterp.getVarValue(argArray[2]);
			final ZonedDateTime	accrualDate 	= DateUtilityFunctions.covertToDate(accrualDateVal,"AccrualDate");
		
		    /** Fourth arg = Payment Tenor. Needs to be valid int */
			int months				=    ((Double)finInterp.getVarValue(argArray[3])).intValue();
			Period paymentTenor		=	 Period.ofMonths(months);
			
			/** Fifth arg = Coupon Interest Rate.*/
			double couponRate 		= (Double)finInterp.getVarValue(argArray[4]);
			
			/** Sixth arg = Coupon Interest Rate.*/
			int settlementDays 		= ((Double)finInterp.getVarValue(argArray[5])).intValue();
			
			/** Seventh arg = Notional */
			double notional  		= (Double)finInterp.getVarValue(argArray[6]);
			
			/** Eighth arg = Day count convention */
			DayCount dayCount       = createDayCount(argArray[7]);
			
			/** Ninth arg = Business Day convention */
			BusinessDayConvention busDayConv	=	createBusinessDayConvention(argArray[8]);
			
			/** Tenth arg = Yield Convention */
			YieldConvention yieldConv			=	createYieldConvention(argArray[9]);
			
			/** Eleventh arg = Issuer */
			String issuerName  = createIssuer(argArray[10]);
			
			BondSecurityDefinition<?, ?> bondInstrument = createBondInstrument(ccy,maturityDate,accrualDate, paymentTenor,
					                                          couponRate,settlementDays,notional,dayCount, 
					                                          busDayConv, yieldConv, issuerName);
			
			return bondInstrument;
		} // End of the method createBond(String funcArgs)

		
		private BondSecurityDefinition<?, ?> createBondInstrument(Currency ccy,ZonedDateTime maturityDate, ZonedDateTime accrualDate,
				Period paymentTenor, double couponRate, int settlementDays,	double notional, DayCount dayCount,
				BusinessDayConvention busDayConv, YieldConvention yieldConv, String issuerName) {
			
			/** Set the calendar. Current version supports only US Calendar */
			Calendar calendar = new MondayToFridayCalendar("A");
			boolean isEom     = false;
					
			BondSecurityDefinition<?, ?> bondInstrument	 = 	BondFixedSecurityDefinition.from(ccy, maturityDate,accrualDate,
					paymentTenor,couponRate, settlementDays, notional,calendar, dayCount,
					busDayConv, yieldConv, isEom, issuerName, "RepoType");
			
			
			return bondInstrument;
		}


		private String createIssuer(String issuerNameSymbol) throws SymbolNotFoundException, FIFActoryException {
			
			String issuerName = null;
			String issuerType = (String)finInterp.getVarValue(issuerNameSymbol);
			
			Issuers issuerTypeEnum = Issuers.getByValue(issuerType);
			
			try{
				switch(issuerTypeEnum){
					case BEL_ISSUER:{
						issuerName = "BELGIUM GOVT";
						break;
					}case AUS_ISSUER:{
						issuerName = "AUSTRALIAN GOVT";
						break;
					}case GER_ISSUER:{
						issuerName = "GERMANY GOVT";
						break;
					}case IT_ISSUER:{
						issuerName = "IT GOVT";
						break;
					}case UK_ISSUER:{
						issuerName = "UK GOVT";
						break;
					} 
					case US_ISSUER:{
						issuerName = "US GOVT";
						break;
					} 
			  }//End of Switch
			}catch(IllegalArgumentException iae){
				/* Indicates an incorrect Issuers, need to report this, as a FI [FixedIncome] factory error*/
				
				System.err.println("An unsupported or Incorrect Issuer provided");
				
				/*List the currently supported Issuers */
				StringBuffer supportedIssuerTypes = new StringBuffer().append("\n");
				for (final Issuers element : EnumSet.allOf(Issuers.class)) {
					supportedIssuerTypes.append(element.toString()).append("\n");
				}
				System.out.println("List of Supported DSL Issuers types are:" + supportedIssuerTypes);
				
				/* Cannot proceed further*/
				throw new FIFActoryException("An unsupported or Incorrect YieldConvention provided",iae);
			}
			
			return issuerName;
		}


		private YieldConvention createYieldConvention(String yieldConvSymbol) throws FIFActoryException, SymbolNotFoundException {
			YieldConvention yieldConv = null;
			
			String yieldConvType = (String)finInterp.getVarValue(yieldConvSymbol);
			
			YieldConventionTypes dslYieldConvTypes = YieldConventionTypes.getByValue(yieldConvType);
			
			try{
				switch(dslYieldConvTypes){
				 case UK_STRIP_METHOD:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("UK STRIP METHOD");
					 break;
				 }
				 case UK_BUMP_DMO_METHOD:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("UK:BUMP/DMO METHOD");
					 break;
				 }
				 case US_IL_REAL:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("US_IL_REAL");
					 break;
				 }
				 case US_STREET:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("STREET CONVENTION");
					 break;
				 }
				 case US_TREASURY_EQUIVALANT:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("US Treasury equivalent");
					 break;
				 }
				 case MONEY_MARKET:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("Money Market");
					 break;
				 }
				 case JGB_SIMPLE:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("JGB simple");
					 break;
				 }
				 case US_BOND:{
					 yieldConv = YieldConventionFactory.INSTANCE.getYieldConvention("US Treasury");
					 break;
				 }
				} // End of Switch
			}catch(IllegalArgumentException iae){
				/* Indicates an incorrect Yield Convention type, need to report this, as a FI [FixedIncome] factory error*/
				
				System.err.println("An unsupported or Incorrect YieldConvention provided");
				
				/*List the currently supported YieldConvention types */
				StringBuffer supportedYieldConvTypes = new StringBuffer().append("\n");
				for (final YieldConventionTypes element : EnumSet.allOf(YieldConventionTypes.class)) {
					supportedYieldConvTypes.append(element.toString()).append("\n");
				}
				System.out.println("List of Supported DSL YieldConvention Convention types are:" + supportedYieldConvTypes);
				
				/* Cannot proceed further*/
				throw new FIFActoryException("An unsupported or Incorrect YieldConvention provided",iae);
			}
			return yieldConv;
		}


		private BusinessDayConvention createBusinessDayConvention(String busDaySymbol) throws SymbolNotFoundException, FIFActoryException {
			BusinessDayConvention busDayConv = null;
			
			String busDayConvType = (String)finInterp.getVarValue(busDaySymbol);
			
			BusinessDayConventionTypes dslBusDayConvTypes = BusinessDayConventionTypes.getByValue(busDayConvType);
			
			try{
				switch(dslBusDayConvTypes){
				
					case MODIFIED_FOLLOWING:{
						busDayConv = BusinessDayConventions.MODIFIED_FOLLOWING;
						break;
					}case MODIFIED_PRECEDING:{
						busDayConv = BusinessDayConventions.MODIFIED_PRECEDING;
						break;
					}case FOLLOWING:{
						busDayConv = BusinessDayConventions.FOLLOWING;
						break;
					}case PRECEDING:{
						busDayConv = BusinessDayConventions.PRECEDING;
						break;
					}case NONE:{
						busDayConv = BusinessDayConventions.NONE;
						break;
					}	
				}
			}catch(IllegalArgumentException iae){
				/* Indicates an incorrect Business Day Convention type, need to report this, as a FI [FixedIncome] factory error*/
				
				System.err.println("An unsupported or Incorrect BusinessDay Convention provided");
				
				/*List the currently supported BusinessDayConvention types */
				StringBuffer supportedBussDayCountTypes = new StringBuffer().append("\n");
				for (final BusinessDayConventionTypes element : EnumSet.allOf(BusinessDayConventionTypes.class)) {
					supportedBussDayCountTypes.append(element.toString()).append("\n");
				}
				System.out.println("List of Supported DSL BusinessDay Convention types are:" + supportedBussDayCountTypes);
				
				/* Cannot proceed further*/
				throw new FIFActoryException("An unsupported or Incorrect BusinessDay Convention provided",iae);
			}
			
			return busDayConv;
		}


		private DayCount createDayCount(String dayCountSymbol) throws FIFActoryException, SymbolNotFoundException {
			DayCount dayCount = null;
			
			String dayCountType = (String)finInterp.getVarValue(dayCountSymbol);
			
			DayCountTypes dslDayCntTypes = DayCountTypes.getByValue(dayCountType);
			
			try{
				switch (dslDayCntTypes) {
					case ACT_ACT_ICMA: {
						dayCount = DayCounts.ACT_ACT_ICMA;
						break;
					}case ACT_360: {
						dayCount = DayCounts.ACT_360;
						break;
					}case ACT_365: {
						dayCount = DayCounts.ACT_365;
						break;
					}case ACT_ACT_AFB: {
						dayCount = DayCounts.ACT_ACT_AFB;
						break;
					}case ACT_ACT_ISDA: {
						dayCount = DayCounts.ACT_ACT_ISDA;
						break;
					}case ACT_36525: {
						dayCount = DayCounts.ACT_36525;
						break;
					}case THIRTY_U_360: {
						dayCount = DayCounts.THIRTY_U_360;
						break;
					}case THIRTY_E_360: {
						dayCount = DayCounts.THIRTY_E_360;
						break;
					}case THIRTY_E_360_ISDA: {
						dayCount = DayCounts.THIRTY_E_360_ISDA;
						break;
					}case TWENTY_EIGHT_360: {
						dayCount = DayCounts.TWENTY_EIGHT_360;
						break;
					}case BUSINESS_252: {
						dayCount = DayCounts.BUSINESS_252;
						break;
					}
			  } //End of Switch
			}catch(IllegalArgumentException iae){
				/* Indicates an incorrect Day Count type, need to report this, as a FI [FixedIncome] factory error*/
				
				System.err.println("An unsupported or Incorrect Day Count provided");
				
				/*List the currently supported DayCount types */
				StringBuffer supportedDayCountTypes = new StringBuffer().append("\n");
				for (final DayCountTypes element : EnumSet.allOf(DayCountTypes.class)) {
					supportedDayCountTypes.append(element.toString()).append("\n");
				}
				System.out.println("List of Supported DSL Day Count types are:" + supportedDayCountTypes);
				
				/* Cannot proceed further*/
				throw new FIFActoryException("An unsupported or Incorrect Day Count provided",iae);
			}
			
			return dayCount;
		}


		@Override
		public Swap<?, ?> createCDS(String funcArgs) {
			throw new RuntimeException("createCDS() is not implemented by FixedIncomeFactory");
		}

		@Override
		public Swap<?, ?> createIRS(String funcArgs) {
			throw new RuntimeException("createIRS() is not implemented by FixedIncomeFactory");
		}
	
	} // End of class FixedIncomeFactory
	
	private static class OTCDerivativeFactory extends AnalyticsAbstractFactory
	{

		@Override
		public InstrumentDefinition<?> createBond(String funcArgs) {
			throw new RuntimeException("createBond() is not implemented by OTCDerivativeFactory");
		}

		@Override
		public Swap<?, ?> createCDS(String funcArgs) {
			return null;
		}

		@Override
		public Swap<?, ?> createIRS(String funcArgs) {
			throw new RuntimeException("createIRS() is not implemented by OTCDerivativeFactory");
		}
		
	} // End of class OTCDerivativeFactory
	
	private static class ListedDerivativeFactory extends AnalyticsAbstractFactory
	{

		@Override
		public InstrumentDefinition<?> createBond(String funcArgs) {
			throw new RuntimeException("createBond() is not implemented by ListedDerivativeFactory");
		}

		@Override
		public Swap<?, ?> createCDS(String funcArgs) {
			throw new RuntimeException("createCDS() is not implemented by ListedDerivativeFactory");
		}

		@Override
		public Swap<?, ?> createIRS(String funcArgs) {
			return null;
		}
	
	} // End of class ListedDerivativeFactory

	
	
}
