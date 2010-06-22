package net.kazed.android.inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DirectApplicationContext implements ApplicationContext {
	
	private Map<Class<? extends Object>, Class<? extends Object>> binding;
	private Map<String, Class<? extends Object>> bindingByName;
	private Map<Class<? extends Object>, Object> singletons;
	private Map<String, Object> singletonByName;
	private Map<Class<? extends Object>, BindingFactory> factories;

	public DirectApplicationContext() {
		binding = new HashMap<Class<? extends Object>, Class<? extends Object>>();
		bindingByName = new HashMap<String, Class<? extends Object>>();
		singletons = new HashMap<Class<? extends Object>, Object>();
		singletonByName = new HashMap<String, Object>();
		factories = new HashMap<Class<? extends Object>, BindingFactory>();
	}
	
	public void bind(Class<? extends Object> interfaceClass, Class<? extends Object> implementationClass) {
		binding.put(interfaceClass, implementationClass);
	}
	
	public void bind(Class<? extends Object> implementationClass) {
		Component component = implementationClass.getAnnotation(Component.class);
		if (component != null) {
			if (component.value() != null) {
				bindingByName.put(component.value(), implementationClass);
			}
		}
		binding.put(implementationClass, implementationClass);
	}
	
	public void bindInstance(Class<? extends Object> interfaceClass, Object singleton) {
		singletons.put(interfaceClass, singleton);
	}

	public void bindFactory(Class<? extends Object> interfaceClass, BindingFactory factory) {
		factories.put(interfaceClass, factory);
	}

	public void inject(Object target) {
		Class<? extends Object> targetClass = target.getClass();
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				Autowired inject = method.getAnnotation(Autowired.class);
				if (inject != null) {
					injectParameters(target, method);
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> interfaceClass) {
	    T bean = (T) getSingleton(interfaceClass);
	    return bean;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(String name) {
	    T bean = (T) getSingleton(name);
	    return bean;
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> boolean containsType(Class<T> interfaceClass) {
		boolean exists = (singletons.containsKey(interfaceClass) || binding.containsKey(interfaceClass)
				|| factories.containsKey(interfaceClass));
		return exists;
	}

	private void injectParameters(Object target, Method method) {
		Class<? extends Object>[] parameterTypes = method.getParameterTypes();
		Object[] parameters = new Object[parameterTypes.length];
		int parameterIndex = 0;
		boolean allParametersInstantiated = true;
		for (Class<? extends Object> parameterType : parameterTypes) {
			if (singletons.containsKey(parameterType) || binding.containsKey(parameterType) || factories.containsKey(parameterType)) {
				parameters[parameterIndex] = getSingleton(parameterType);
			} else {
				allParametersInstantiated = false;
			}
			parameterIndex++;
		}
		if (allParametersInstantiated) {
			try {
				method.invoke(target, parameters);
			} catch (IllegalArgumentException e) {
				throw new InjectException(e);
			} catch (IllegalAccessException e) {
				throw new InjectException(e);
			} catch (InvocationTargetException e) {
				throw new InjectException(e);
			}
		}
	}

	private synchronized Object getSingleton(Class<? extends Object> parameterType) {
		Object singleton = null;
		if (singletons.containsKey(parameterType)) {
			singleton = singletons.get(parameterType);
		} else {
			if (factories.containsKey(parameterType)) {
				BindingFactory factory = factories.get(parameterType);
				singleton = factory.getBinding();
			} else if (binding.containsKey(parameterType)) {
				Class<? extends Object> implementationClass = binding.get(parameterType);
				if (implementationClass == null) {
					throw new BeanNotFoundException("no binding found for type: " + parameterType.toString());
				}
				singleton = createSingleton(implementationClass);
			} else {
				throw new InjectException("Failed to find binding for type: " + parameterType.getCanonicalName());
			}
			singletons.put(parameterType, singleton);
		}
		return singleton;
	}

	private synchronized Object getSingleton(String name) {
		Object singleton = null;
		if (singletonByName.containsKey(name)) {
			singleton = singletonByName.get(name);
		} else {
			Class<? extends Object> implementationClass = bindingByName.get(name);
			if (implementationClass == null) {
				throw new BeanNotFoundException("no binding found for name: " + name);
			}
			singleton = createSingleton(implementationClass);
			singletonByName.put(name, singleton);
		}
		return singleton;
	}

	private Object createSingleton(Class<? extends Object> implementationClass) {
		Object singleton = null;
		try {
			singleton = implementationClass.newInstance();
		    Component component = implementationClass.getAnnotation(Component.class);
		    if (component != null) {
		        inject(singleton);
		    }
		} catch (InstantiationException e) {
			throw new BeanInstantiationException(e);
		} catch (IllegalAccessException e) {
			throw new BeanInstantiationException(e);
		}
		return singleton;
	}

}
