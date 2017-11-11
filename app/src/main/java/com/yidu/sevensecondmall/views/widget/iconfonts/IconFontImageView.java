package com.yidu.sevensecondmall.views.widget.iconfonts;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/23.
 */
public class IconFontImageView extends ImageView{

    private Context mContext;
    private Paint p;

    public IconFontImageView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public IconFontImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public IconFontImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }


    private void initView()
    {
        Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), "fonts/iconfont.ttf");
        p = new Paint();
        p.setTypeface(iconfont);
    }
}
