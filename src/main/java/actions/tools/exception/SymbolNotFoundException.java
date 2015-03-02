package actions.tools.exception;

/**
 * An exception class to denote an error of an un-defined symbol
 * @author randheerDas
 *
 */
public class SymbolNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cause;
	
	public SymbolNotFoundException(String errorMsg) {
		cause = errorMsg;
	}
	
	@Override
	public String getMessage() {
		return new StringBuffer().append(cause).toString();

	}

}
