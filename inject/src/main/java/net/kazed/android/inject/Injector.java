package net.kazed.android.inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Injector {
	
    private ApplicationContext applicationContext;
	private Map<Class<? extends Object>, Object> singletons;
	private Map<String, Object> singletonsByName;
    
    /**
     * Constructor.
     * @param applicationContext Context to retrieve beans from.
     */
	public Injector(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		singletons = new HashMap<Class<? extends Object>, Object>();
		singletonsByName = new HashMap<String, Object>();
	}
	
	/**
	 * Inject dependencies into target.
	 * @param target Target with dependencies.
	 */
	public void inject(Object target) {
		Class<? extends Object> targetClass = target.getClass();
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				Object[] parameters = null;
				Autowired autowired = method.getAnnotation(Autowired.class);
				if (autowired != null) {
					parameters = getAutowireParameters(target, method);
				} else {
					Resource resource = method.getAnnotation(Resource.class);
					if (resource != null) {
						parameters = getResourceParameters(resource, target, method);
					}
				}
				if (parameters != null) {
					try {
						method.invoke(target, parameters);
					} catch (IllegalArgumentException e) {
						throw new InjectException("Failed to inject setter: " + method.getName() + ", target: " + target.getClass(), e);
					} catch (IllegalAccessException e) {
						throw new InjectException(e);
					} catch (InvocationTargetException e) {
						throw new InjectException(e);
					}
				}
			}
		}
	}
	
	/**
	 * Inject application context beans into parameters.
	 * @param target Dependency injection target.
	 * @param method Method to be invoked.
	 */
	private Object[] getAutowireParameters(Object target, Method method) {
		Class<? extends Object>[] parameterTypes = method.getParameterTypes();
		Object[] parameters = new Object[parameterTypes.length];
		int parameterIndex = 0;
		boolean allParametersInstantiated = true;
		for (Class<? extends Object> parameterType : parameterTypes) {
			if (applicationContext.containsType(parameterType)) {
				parameters[parameterIndex] = getBean(parameterType);
			} else {
				allParametersInstantiated = false;
			}
			parameterIndex++;
		}
		if (!allParametersInstantiated) {
			parameters = null;
		}
		return parameters;
	}

	/**
	 * Inject application context beans into parameters.
	 * @param target Dependency injection target.
	 * @param method Method to be invoked.
	 */
	private Object[] getResourceParameters(Resource resource, Object target, Method method) {
		Object[] parameters = null;
		Class<? extends Object>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length == 1) {
			parameters = new Object[parameterTypes.length];
			parameters[0] = getBean(resource.value());
		}
		
		return parameters;
	}

	@SuppressWarnings("unchecked")
    private <T> T getBean(Class<T> interfaceClass) {
	    T bean = null;
		if (singletons.containsKey(interfaceClass)) {
			bean = (T) singletons.get(interfaceClass);
		} else {
		    bean = (T) applicationContext.getBean(interfaceClass);
		    Class<? extends Object> implementationClass = bean.getClass();
	        Component component = implementationClass.getAnnotation(Component.class);
	        if (component != null) {
	            inject(bean);
	        }
			singletons.put(interfaceClass, bean);
		}
	    
	    return bean;
	}
	
    private Object getBean(String name) {
	    Object bean = null;
		if (singletonsByName.containsKey(name)) {
			bean = singletonsByName.get(name);
		} else {
			bean = applicationContext.getBean(name);
		    Class<? extends Object> implementationClass = bean.getClass();
	        Component component = implementationClass.getAnnotation(Component.class);
	        if (component != null) {
	            inject(bean);
	        }
			singletonsByName.put(name, bean);
			Class<?>[] interfaces = implementationClass.getInterfaces();
			for (Class<?> interfaceClass : interfaces) {
				singletons.put(interfaceClass, bean);
			}
		}
	    
	    return bean;
	}
}
