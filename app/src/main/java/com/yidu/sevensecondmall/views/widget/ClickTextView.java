package com.yidu.sevensecondmall.views.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class ClickTextView extends TextView {
    private int maxLength;

    private float radiu;

    private float centerX, centerY;

    private Paint paint;

    private float FACTOR=0.1F;

    public ClickTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#55ffffff"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当手指抬起表示确认才执行操作
        if (event.getAction() == MotionEvent.ACTION_UP) {
            radiu = 0;
            maxLength = (int) Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2));
            centerX = event.getX();
            centerY = event.getY();
            startScaleAnimation();
            invalidate();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    performClick();
                }
            },300);
        }
        return true;
    }

    private void startScaleAnimation() {
        ObjectAnimator animator=ObjectAnimator.ofFloat(this,"xxx",0,1).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress= (float) animation.getAnimatedValue();
                updateView(progress);
            }
        });
        animator.start();
    }

    private void updateView(float progress) {
        setPivotX(getWidth()/2);
        setPivotY(getHeight()/2);
        if(progress>=0&&progress<=0.5f){
            setScaleX(1+FACTOR*progress);
            setScaleY(1+FACTOR*progress);
        }else{
            setScaleX(1+FACTOR*(1-progress));
            setScaleY(1+FACTOR*(1-progress));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (radiu > maxLength) {
            return;
        }
        canvas.drawCircle(centerX, centerY, radiu, paint);
        radiu = radiu + 30;
        //防止第一次进入onDraw
        invalidate();
    }
}
