package net.kazed.android.inject;


public interface ApplicationContext {
	
	/**
	 * Get application context bean.
	 * @param interfaceClass Class of bean.
	 * @return Configured bean.
	 */
    public <T> T getBean(Class<T> interfaceClass);

	/**
	 * Get application context bean.
	 * @param name Name of bean.
	 * @return Configured bean.
	 */
    public <T> T getBean(String name);

	/**
	 * Check if application context contains bean.
	 * @param interfaceClass Class of bean.
	 * @return True if application context contains bean with type interfaceClass.
	 */
    public <T> boolean containsType(Class<T> interfaceClass);

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
	
	/**
	 * Add factory binding.
	 * @param interfaceClass Interface class.
	 * @param factory Factory to create singleton instance.
	 */
	void bindFactory(Class<? extends Object> interfaceClass, BindingFactory factory);
}
