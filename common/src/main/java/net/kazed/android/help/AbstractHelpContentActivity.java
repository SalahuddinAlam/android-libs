package net.kazed.android.help;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public abstract class AbstractHelpContentActivity extends Activity {

   private static final String FILE_URL_PREFIX = "file:///android_asset/";
   
   private WebView webView;
   private Uri itemUri;
   private String currentHelpFile;

   private AssetManager assetManager;

   protected abstract int getContentViewId();

   protected abstract int getWebkitViewId();

   @Override
   protected void onCreate(Bundle icicle) {
      super.onCreate(icicle);
      itemUri = getIntent().getData();
      if (itemUri == null) {
         currentHelpFile = "welcome.html";
      } else { 
         currentHelpFile = itemUri.getLastPathSegment();
      }

      assetManager = getAssets();

      setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
      requestWindowFeature(Window.FEATURE_PROGRESS);
      setContentView(getContentViewId());

      webView = (WebView) findViewById(getWebkitViewId());
      webView.setWebViewClient(new WebViewClient() {
         private String failedUrl;
         
         @Override
         public void onPageFinished(final WebView view, final String url) {
            super.onPageFinished(view, url);
            if (failedUrl != null) {
               if (!failedUrl.startsWith(FILE_URL_PREFIX)) {
                  // create intent for web browser
                  webView.goBack();
                  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(failedUrl));
                  startActivity(intent);
               }
               failedUrl = null;
            }

         }

         public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            failedUrl = failingUrl;
         }
       });

      String language = getLanguage();
      displayFile(language, currentHelpFile);
   }

   @Override
   protected void onResume() {
      super.onResume();
      String language = getLanguage();
      displayFile(language, currentHelpFile);
   }

   private String getLanguage() {
      String language = getResources().getConfiguration().locale.getLanguage();
      return language;
   }

   private void displayFile(final String language, final String helpFile) {
      boolean useLanguage = false;
      if (language != null) {
         try {
            assetManager.open(language + "/" + helpFile);
            useLanguage = true;
         } catch (IOException e) {
            useLanguage = false;
         }
         
      }
      if (useLanguage) {
         webView.loadUrl(FILE_URL_PREFIX + language + "/" + helpFile);
      } else {
         webView.loadUrl(FILE_URL_PREFIX + helpFile);
      }
      currentHelpFile = helpFile;
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
      if (KeyEvent.KEYCODE_BACK != keyCode) {
         result = super.onKeyUp(keyCode, event);
      }
      return result;
   }

}
