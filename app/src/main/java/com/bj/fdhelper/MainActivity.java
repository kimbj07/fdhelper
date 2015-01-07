package com.bj.fdhelper;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends ActionBarActivity {
    private WebView mWebView;
    private static String JS_ITEM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDefaultTextEncodingName("UTF-8");

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.loadUrl(FDHConstants.ULR_MAIN + FDHConstants.USE_DEBUG);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.submenu_use_item_condition:
            case R.id.submenu_use_item_stamina:
            case R.id.submenu_use_item_condition_and_stamina:
            case R.id.submenu_use_item_yellow_card:
            case R.id.submenu_use_item_taurine:
                return useItem(item);
            case R.id.submenu_combine_item_low:
            case R.id.submenu_combine_item_max_condition:
            case R.id.submenu_combine_item_max_stamina:
                return combineItem(item);
            case R.id.menu_challenge_friend:
                return challengeFriend();
            case R.id.menu_make_lunch:
                return makeLunch();
            case R.id.menu_help:
                return showHelp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param item
     * @return
     */

    private boolean useItem(MenuItem item) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                injectScriptFile("jquery-1.8.3.min.js");
                injectScriptFile("fdh.item.js");
                mWebView.loadUrl("javascript:$(document).ready(function() {fdh.item.useItem();});");
                mWebView.setWebViewClient(new WebViewClient());
            }
        });

        mWebView.loadUrl(FDHConstants.ULR_ITEM +  FDHConstants.USE_DEBUG);

        return true;
    }

    /**
     * @param item
     * @return
     */
    private boolean combineItem(MenuItem item) {
        mWebView.loadUrl(FDHConstants.ULR_ITEM);
        return true;
    }

    /**
     * @return
     */
    private boolean makeLunch() {
        return true;
    }

    /**
     * @return
     */
    private boolean challengeFriend() {
        return true;
    }

    /**
     * @return
     */
    private boolean showHelp() {
        return false;
    }


    private void injectScriptFile(String scriptFile) {
        String script = FDHScriptManager.getScript(this, scriptFile);
        mWebView.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var script = document.createElement('script');" +
                "script.type = 'text/javascript';" +
                "script.innerHTML = window.atob('" + script + "');" +
                "parent.appendChild(script)" +
                "})()");
    }

    /*
    final class JavaScriptInterface {
        JavaScriptInterface() {
        }

        @JavascriptInterface
        public void windowLoaded() {
            mWebView.loadUrl("javascript:loadScript('fdh.item.js', 'fdh.item.init')");
        }
    }
    */
}

