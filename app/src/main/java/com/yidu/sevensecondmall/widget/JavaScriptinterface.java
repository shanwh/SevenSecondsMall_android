package com.yidu.sevensecondmall.widget;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by dell on 2017/2/23.
 */

public class JavaScriptinterface {
    Activity activity;
    public JavaScriptinterface(Activity activity) {
        this.activity = activity;
    }
    @JavascriptInterface
    public void webViewFinish(){
            activity.finish();
    }

}
