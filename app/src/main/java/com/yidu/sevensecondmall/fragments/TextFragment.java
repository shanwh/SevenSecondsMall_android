package com.yidu.sevensecondmall.fragments;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.GoodInfoBean;
import com.yidu.sevensecondmall.i.IWebViewTop;
import com.zzhoujay.richtext.RichText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/11.
 */
public class TextFragment extends BaseFragment {
    private static final String TAG = "TextFragment";

    //    @BindView(R.id.richtext)
//    TextView richtext;
    @BindView(R.id.wb)
    WebView wb;
    //    @BindView(R.id.text_scroll)
//    ScrollView textScroll;
    private GoodInfoBean bean;
    private IWebViewTop iWebViewTop;
    static boolean istop = true;


    @Override
    protected int setViewId() {
        return R.layout.text_fragment_layout;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    protected void init() {
//        String content = getFragmentManager()
//                .findFragmentByTag("tfragment")
//                .getArguments()
//                .getString("content");
//        if (content != null && !content.equals("")) {
////            Log.e(TAG, "init: " + content);
////            wb.set
////            RichText.from(content)
////                    .async(true)
////                    .into(richtext);
//            initWebView(wb, content);
//        }
    }


    private void initWebView(WebView webView, String content) {
        Log.e(TAG, "initWebView: ------");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); //取消滚动条白边效果
//        webView.setWebChromeClient(webChromeClient);
//        webView.setWebViewClient(webViewClient);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);  //注意安卓5.0以上的权限
        }
        webView.loadDataWithBaseURL("http://avatar.csdn.net", getNewContent(content), "text/html", "UTF-8", null);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new MyWebViewClient());
    }

    private String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        return doc.toString();
    }

    @Override
    protected void loadData() {

    }

    public void setIWebViewTop(IWebViewTop istop) {
        iWebViewTop = istop;
    }

    @Override
    protected void initEvent() {
//        richtext.setMovementMethod(ScrollingMovementMethod.getInstance());
        wb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (v.getScrollY() <= 0) {
                            iWebViewTop.isTop(true);
                            istop = true;
//                            Log.d("scroll view", "top");
                        } else
//                        if (scollview.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY())
                        {
//                            Log.d("scroll view", "bottom");
//                            Log.d("scroll view", "view.getMeasuredHeight() = " + view.getMeasuredHeight()
//                                    + ", v.getHeight() = " + v.getHeight()
//                                    + ", v.getScrollY() = " + v.getScrollY()
//                                    + ", view.getChildAt(0).getMeasuredHeight() = " + view.getChildAt(0).getMeasuredHeight());
                            iWebViewTop.isTop(false);
                            istop = false;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void setData(){
        String content = SystemUtil.getSharedString("Goods_content");
        initWebView(wb, content);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (istop) {
            iWebViewTop.isTop(istop);
        } else {
            iWebViewTop.isTop(!istop);
        }
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

            Log.e(TAG, "onPageFinished: onPageFinished" );
            String javascript = "javascript:function hideOther() {" +
//                    "document.getElementById('widget_sub_navs').style.display='none';" +
//                    "document.getElementById('widget_tabbar').style.display='none';" +
//                    "document.getElementsByTagName('body')[0].style.padding='0px';" +
                    "document.getElementsByTagName('p')[0].style.padding='0px';" +
                    "document.getElementsByTagName('p')[1].style.padding='0px';" +
                    "document.getElementsByTagName('p')[2].style.padding='0px';" +

//                    "alert(111);"+
//                    "var p = document.getElementsByTagName('p');" +
//                    "for(var i = 0; i < p.length; i++) {" +
//                    "p[i].style.marginTop = '-5px';" +
//                    " }"+
                    "}";



            //创建方法
            view.loadUrl(javascript);
            //加载方法
            view.loadUrl("javascript:hideOther();");
        }
    }


}
