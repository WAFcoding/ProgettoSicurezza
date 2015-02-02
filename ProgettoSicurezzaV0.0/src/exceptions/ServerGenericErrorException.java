package exceptions;

public class ServerGenericErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1303079576947409710L;

	public ServerGenericErrorException() {
	}

	public ServerGenericErrorException(String message) {
		super(message);
	}

}
