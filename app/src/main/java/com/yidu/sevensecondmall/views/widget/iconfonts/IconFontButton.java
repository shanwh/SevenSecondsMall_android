package com.yidu.sevensecondmall.views.widget.iconfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;

/**
 * Created by Administrator on 2016/6/22.
 */
public class IconFontButton extends Button {
    private Context mContext;

    public IconFontButton(Context context){
        super(context);
        mContext = context;
        initView();
    }

    public IconFontButton(Context context, android.util.AttributeSet attrs) {
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
