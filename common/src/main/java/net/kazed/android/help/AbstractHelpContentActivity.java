package net.kazed.android.help;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public abstract class AbstractHelpContentActivity extends Activity {

   private static final String FILE_URL_PREFIX = "file:///android_asset/";
   
   private WebView webView;
   private Uri itemUri;
   private String helpFile;
   private boolean useLanguage;

   protected abstract int getContentViewId();

   protected abstract int getWebkitViewId();

   @Override
   protected void onCreate(Bundle icicle) {
      super.onCreate(icicle);
      itemUri = getIntent().getData();
      if (itemUri == null) {
         helpFile = "welcome.html";
      } else {
         helpFile = itemUri.getLastPathSegment();
      }

      setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
      requestWindowFeature(Window.FEATURE_PROGRESS);
      setContentView(getContentViewId());

      webView = (WebView) findViewById(getWebkitViewId());
      webView.setWebViewClient(new WebViewClient() {
         public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (failingUrl.endsWith(helpFile)) {
               if (useLanguage) {
                  displayDefaultFile(helpFile);
               }
            } else {
               if (failingUrl.startsWith(FILE_URL_PREFIX)) {
                  int index = failingUrl.lastIndexOf('/');
                  if (index >= 0) {
                     helpFile = failingUrl.substring(index+1);
                     webView.loadUrl(failingUrl);
                  }
               } else {
                  // create intent for web browser
               }
            }
         }
       });

   }

   @Override
   protected void onResume() {
      super.onResume();
      String language = getResources().getConfiguration().locale.getLanguage();
      displayFile(language, helpFile);
   }

   private void displayFile(final String language, final String helpFile) {
      if (language == null) {
         useLanguage = false;
         webView.loadUrl(FILE_URL_PREFIX + helpFile);
      } else {
         useLanguage = true;
         webView.loadUrl(FILE_URL_PREFIX + language + "/" + helpFile);
      }
   }

   private void displayDefaultFile(final String helpFile) {
      displayFile(null, helpFile);
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
