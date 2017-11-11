package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class ShopApplyMsgActivity extends BaseActivity {


    @BindView(R.id.wb)
    WebView webView;
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_apply_msg;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        webView.loadUrl("http://www.qimiaolife.cn/home/index/agreement");
        toolbarTitle.setText("商家入驻协议");

        WebSettings settings = webView.getSettings();
        //设置webview属性可以对js进行操作
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void loadData() {

    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //这里进行url拦截


            return super.shouldOverrideUrlLoading(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.getSettings().setJavaScriptEnabled(true);

//            String javascript =  "javascript:function hideOther() {" +
//                    "document.getElementById('widget_sub_navs').style.display='none';"+
//                    "document.getElementById('widget_tabbar').style.display='none';"+
//                    "document.getElementsByTagName('body')[0].style.padding='0px';"+
//                    "}";
//
//            //创建方法
//            view.loadUrl(javascript);
//            //加载方法
//            view.loadUrl("javascript:hideOther();");
        }
    }
}
