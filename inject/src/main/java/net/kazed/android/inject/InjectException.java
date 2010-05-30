package net.kazed.android.inject;

public class InjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InjectException(String message) {
		super(message);
	}

	public InjectException(Throwable cause) {
		super(cause);
	}

	public InjectException(String message, Throwable cause) {
		super(message, cause);
	}

}
