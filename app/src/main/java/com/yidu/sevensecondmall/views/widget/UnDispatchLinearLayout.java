package com.yidu.sevensecondmall.views.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/6/7.
 */
public class UnDispatchLinearLayout extends LinearLayout implements View.OnTouchListener{

    public UnDispatchLinearLayout(Context context) {
        super(context);
    }

    public UnDispatchLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnDispatchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                Log.e("unlinear",ev.getAction()+"");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e("unlinearm",ev.getAction()+"");
                return super.onInterceptTouchEvent(ev);
            default:
                return true;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v instanceof RecyclerView){
            Log.e("unlinear","RecyclerView");
        }
        return false;
    }
}
