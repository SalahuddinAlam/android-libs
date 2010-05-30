package net.kazed.android.inject;

import android.app.Service;

public abstract class InjectedService extends Service {

	private InjectedApplication application;
	
    @Override
    public void onCreate() {
    	InjectedApplication application = getInjectedApplication();
        application.injectInto(this);
        super.onCreate();
    }
    
    /**
     * @return
     */
    private InjectedApplication getInjectedApplication() {
    	if (application == null) {
            application = (InjectedApplication) getApplication();
    	}
    	return application;
    }

}