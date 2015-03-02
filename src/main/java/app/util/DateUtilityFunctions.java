package app.util;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeParseException;

import actions.tools.exception.SymbolNotFoundException;
import analytics.exception.FIFActoryException;

import com.opengamma.util.time.DateUtils;

/**
 * A Date Utility class providing date conversion functions
 * @author randheerDas
 *
 */
public class DateUtilityFunctions {
   
	/** A generic  Date conversion function.
	 * Date is treated as int in grammar and INT is converted to double for arithmetic calculations
	 * 
	 * @param Double: Contains the numerical date representation
	 * @param The name of the date type
	 * @return 
	 * @throws SymbolNotFoundException 
	 * @throws FIFActoryException 
	 */
	public static ZonedDateTime covertToDate(Double dateVal, String dateType) throws SymbolNotFoundException, FIFActoryException {
		
		final int tradeDateInt 			=  dateVal.intValue();
		LocalDate tradeDateLocal 		= null;
		
		try{
			tradeDateLocal 	=  DateUtils.toLocalDate(tradeDateInt);
		}catch(DateTimeParseException dtpe){
			System.err.println("INCORRECT " + dateType + " = " + tradeDateInt);
			System.err.println("Error CAUSE is  = " + dtpe.getMessage());
			
			/* Cannot proceed further*/
			throw new FIFActoryException("Incorrect date value provided for " + dateType ,dtpe);
		}
		
		ZonedDateTime date			=  DateUtils.getUTCDate(tradeDateLocal.getYear(), tradeDateLocal.getMonthValue(),
										   tradeDateLocal.getDayOfMonth());
		
		return date;
	}
}
