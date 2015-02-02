package exceptions;

public class ClientUnAuthorizedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1264530578178347213L;

	public ClientUnAuthorizedException() {
	}

	public ClientUnAuthorizedException(String message) {
		super(message);
	}

}
