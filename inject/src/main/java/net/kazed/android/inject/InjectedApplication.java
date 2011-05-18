package net.kazed.android.inject;

import android.app.Application;
import android.content.Context;

/**
 * Application class.
 * @author Koert Zeilstra
 */
public abstract class InjectedApplication extends Application {

    
    @Override
   public void onCreate() {
      super.onCreate();
      ApplicationContext applicationContext = ApplicationSingleton.getInstance().getApplicationContext();
      applicationContext.bindInstance(Context.class, this);
      initializeContext(applicationContext);
   }

   public void injectInto(Object target) {
    	ApplicationSingleton.getInstance().getInjector().inject(target);
    }
    
    /**
     * Initialize application context, create your bindings in your subclass
     * and override this method. Always call super.
     * @param applicationContext Application context.
     */
    protected void initializeContext(final ApplicationContext applicationContext) {
    }
    
}
