package net.kazed.android.inject;


public interface BindContext {
	
	/**
	 * Bind interface with implementation.
	 * @param interfaceClass Interface class.
	 * @param implementationClass Implementation class.
	 */
	void bind(Class<? extends Object> interfaceClass, Class<? extends Object> implementationClass);
	
	/**
	 * Bind implementation with annotations.
	 * @param implementationClass Implementation class.
	 */
	void bind(Class<? extends Object> implementationClass);

	/**
	 * Bind singleton to interfaceClass.
	 * @param interfaceClass Interface class.
	 * @param singleton Singleton.
	 */
	void bindInstance(Class<? extends Object> interfaceClass, Object singleton);
	

}
