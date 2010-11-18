package net.kazed.android.inject;

import android.app.Activity;
import android.os.Bundle;

public abstract class InjectedActivity extends Activity {

	private InjectedApplication application;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	InjectedApplication application = getInjectedApplication();
        application.injectInto(this);
        super.onCreate(savedInstanceState);
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