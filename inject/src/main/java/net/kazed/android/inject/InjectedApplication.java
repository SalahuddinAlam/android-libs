package net.kazed.android.inject;

import android.app.Application;
import android.content.Context;

/**
 * Application class.
 * @author Koert Zeilstra
 */
public abstract class InjectedApplication extends Application {

    private Injector injector;
    
    public void injectInto(Object target) {
    	getInjector().inject(target);
    }
    
    protected ApplicationContext createApplicationContext() {
		DirectApplicationContext appContext = new DirectApplicationContext();
		return appContext;
    }
    
    /**
     * Initialize application context, create your bindings in your subclass
     * and override this method. Always call super.
     * @param applicationContext Application context.
     */
    protected void initializeContext(ApplicationContext applicationContext) {
    	applicationContext.bindInstance(Context.class, this);
    }
    
    public synchronized Injector getInjector() {
    	if (injector == null) {
    		ApplicationContext applicationContext = createApplicationContext();
    		initializeContext(applicationContext);
    		injector = new Injector(applicationContext);
    	}
    	return injector;
    }
    
}
