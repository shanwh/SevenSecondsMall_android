package com.yidu.sevensecondmall.views.widget.iconfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/22.
 */
public class IconFontTextView extends TextView {

    private Context mContext;

    public IconFontTextView(Context context){
        super(context);
        mContext = context;
        initView();
    }

    public IconFontTextView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView()
    {
        Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), "fonts/iconfont.ttf");
        setTypeface(iconfont);
    }

}
