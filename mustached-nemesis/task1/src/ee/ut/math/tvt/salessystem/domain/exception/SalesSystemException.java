package ee.ut.math.tvt.salessystem.domain.exception;

public class SalesSystemException extends Exception {

	private String message;
	private static final long serialVersionUID = -4211156645500262486L;

	public SalesSystemException() {

	}

	public SalesSystemException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
