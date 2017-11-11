package com.yidu.sevensecondmall.views.widget.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class NoScrollViewpager extends ViewPager {
    private boolean isCanScroll = true;
    public NoScrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        if(isCanScroll) {
            super.scrollTo(x, y);
        }
    }
}
