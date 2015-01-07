package com.bj.fdhelper;

import android.webkit.WebViewClient;

/**
 * Created by J on 2014-12-24.
 */
public class FDHWebViewClient extends WebViewClient {

    /*
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().endsWith("html5rocks.com")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
    */
}
