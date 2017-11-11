package com.yidu.sevensecondmall.views.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/6/12.
 */
public class dispatchViewPager extends ViewPager{

    public dispatchViewPager(Context context) {
        super(context);
    }

    public dispatchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        Log.e("viewpager",ev.getAction()+"");
        return false;
    }
}
