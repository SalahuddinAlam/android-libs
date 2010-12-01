package net.kazed.android.help;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;

public abstract class AbstractHelpContentActivity extends Activity {
	
    private WebView webView;
    private Uri itemUri;

    protected abstract int getContentViewId();
    protected abstract int getWebkitViewId();
    
    @Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        itemUri = getIntent().getData();
		
        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(getContentViewId());
        
        webView= (WebView) findViewById(getWebkitViewId());
	}

    @Override
    protected void onResume() {
        super.onResume();
        if (itemUri == null) {
            webView.loadUrl("file:///android_asset/welcome.html");
        } else {
        	String helpFile = itemUri.getLastPathSegment();
            webView.loadUrl("file:///android_asset/" + helpFile);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = true;
        if (KeyEvent.KEYCODE_BACK == keyCode && webView.canGoBack()) {
            webView.goBack();
        } else {
            result = super.onKeyDown(keyCode, event);
        }
        return result;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean result = true;
        if (KeyEvent.KEYCODE_BACK == keyCode && webView.canGoBack()) {
            webView.goBack();
        } else {
            result = super.onKeyUp(keyCode, event);
        }
        return result;
    }

    
}
