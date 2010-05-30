package net.kazed.android.inject;

public class BeanInstantiationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BeanInstantiationException(String message) {
		super(message);
	}

	public BeanInstantiationException(Throwable cause) {
		super(cause);
	}

	public BeanInstantiationException(String message, Throwable cause) {
		super(message, cause);
	}

}
