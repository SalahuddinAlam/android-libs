package net.kazed.android.help;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
      Log.d("help", "onCreate " + itemUri + " - " + currentHelpFile);

      assetManager = getAssets();

      setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
      requestWindowFeature(Window.FEATURE_PROGRESS);
      setContentView(getContentViewId());

      webView = (WebView) findViewById(getWebkitViewId());
      webView.setWebViewClient(new WebViewClient() {
         private String failedUrl;
         
         @Override
         public void onPageFinished(final WebView view, final String url) {
            Log.d("help", "onPageFinished " + url);
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
            Log.d("help", "onReceivedError " + failingUrl);
            failedUrl = failingUrl;

//            failedUrls.add(failingUrl);
//            if (visitedUrls.size() > 0) {
//               String lastUrl = visitedUrls.get(visitedUrls.size() - 1);
//               if (failingUrl.equals(lastUrl)) {
//                  visitedUrls.remove(visitedUrls.size() - 1);
//                  Log.d("help", "onReceivedError removed " + visitedUrls);
//               }
//            }
//            if (failingUrl.startsWith(FILE_URL_PREFIX)) {
//               int lastSlashIndex = failingUrl.lastIndexOf('/');
//               String helpFile = failingUrl.substring(lastSlashIndex+1);
//               boolean containsLanguage = (lastSlashIndex > FILE_URL_PREFIX.length());
//               if (containsLanguage) {
//                  webView.goBack();
//                  displayDefaultFile(helpFile);
//               }
//            } else {
//               // create intent for web browser
////               webView.goBack();
//               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(failingUrl));
//               startActivity(intent);
//            }
            
//            if (failingUrl.endsWith(currentHelpFile)) {
//               if (useLanguage) {
//                  displayDefaultFile(currentHelpFile);
//               }
//            } else {
//               if (failingUrl.startsWith(FILE_URL_PREFIX)) {
//                  int index = failingUrl.lastIndexOf('/');
//                  if (index >= 0) {
//                     currentHelpFile = failingUrl.substring(index+1);
//                     webView.loadUrl(failingUrl);
//                  }
//               } else {
//                  // create intent for web browser
//               }
//            }
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

//      Log.d("help", "onKeyDown " + keyCode + " - " + visitedUrls);
//      if (KeyEvent.KEYCODE_BACK == keyCode && visitedUrls.size() > 1) {
//         String lastUrl = visitedUrls.get(visitedUrls.size() - 2);
//         int lastSlashIndex = lastUrl.lastIndexOf('/');
//         currentHelpFile = lastUrl.substring(lastSlashIndex+1);
//         visitedUrls.remove(visitedUrls.size() - 2);
//         displayFile(getLanguage(), currentHelpFile);
//      } else {
//         result = super.onKeyDown(keyCode, event);
//      }
      return result;
   }

   @Override
   public boolean onKeyUp(int keyCode, KeyEvent event) {
      boolean result = true;
//      if (KeyEvent.KEYCODE_BACK == keyCode && webView.canGoBack()) {
//         webView.goBack();
//         String url = webView.getOriginalUrl();
//         int lastSlashIndex = url.lastIndexOf('/');
//         currentHelpFile = url.substring(lastSlashIndex+1);
//      } else {
      if (KeyEvent.KEYCODE_BACK != keyCode) {
         result = super.onKeyUp(keyCode, event);
      }
      return result;
   }

}
