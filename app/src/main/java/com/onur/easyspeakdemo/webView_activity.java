package com.onur.easyspeakdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webView_activity extends AppCompatActivity {
    WebView webView;
//    final String url="http://sinav.online-sts.com/easyspeak";
    final String url="http://www.facebook.com";
    final Boolean found=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        webView= findViewById(R.id.webview);
        webView.getSettings().getJavaScriptCanOpenWindowsAutomatically();
        webView.getSettings().getAllowFileAccess();
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //webView.setWebViewClient(webViewClient);

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);



    }
}
