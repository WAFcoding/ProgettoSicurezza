package exceptions;

public class MagickImageNullException extends Exception {

	private static final long serialVersionUID = -5158612793724134906L;

	public MagickImageNullException() {
	}

	public MagickImageNullException(String message) {
		super(message);
	}

	public MagickImageNullException(Throwable cause) {
		super(cause);
	}

	public MagickImageNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public MagickImageNullException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
