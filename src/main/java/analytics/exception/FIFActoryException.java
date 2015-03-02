package analytics.exception;

/**
 * An exception class that wraps exceptions that can occur at runtime while creating a 
 * Fixed Income Instrument.
 * @author randheerDas
 *
 */
public class FIFActoryException extends Exception {

	private static final long serialVersionUID = 1L;
	private String reason;
	
	
	public FIFActoryException(String reason) {
		this.reason = reason;
	}
	
	/**
	 * @param A String specifying the cause
	 * @param The Original exception, being wrapped
	 */
	public FIFActoryException(String reason, Throwable arg1) {
		super(arg1);
		this.reason = reason;
	}
	
	
	@Override
	public String getMessage() {
		return new StringBuffer().append(reason).toString();

	}

}
