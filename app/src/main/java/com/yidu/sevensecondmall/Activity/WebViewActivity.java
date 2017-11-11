package com.yidu.sevensecondmall.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.widget.JavaScriptinterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/26.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    String url;

    @Override
    protected int setViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.addJavascriptInterface(new JavaScriptinterface(this), "android");
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient());
        url = getIntent().getStringExtra("url");


         Log.e("WebView===userAgent",webView.getSettings().getUserAgentString());

    }

    @Override
    protected void init() {
        if (url != null && !url.equals("")) {
            webView.loadUrl(url);
        } else {
            webView.loadUrl("https://www.baidu.com/");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CookieSyncManager.createInstance(this);
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        webView.setWebViewClient(null);
        webView.setWebChromeClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
    }

    @Override
    protected void loadData() {

    }

}
