package com.yidu.sevensecondmall.views.widget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yidu.sevensecondmall.R;

/**
 * Created by Administrator on 2017/8/22.
 */
public class CircleInterCircleView extends View{
    private float outSize;
    private int outColor;
    private int interColor;
    private float interSize;
    private  Paint paint;

    public CircleInterCircleView(Context context) {
        super(context);
    }
    public CircleInterCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleInterCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CircleInterCircleView);

        //获取自定义属性和默认值
        outColor = mTypedArray.getColor(R.styleable.CircleInterCircleView_OutCircleColor,Color.RED);
        interColor = mTypedArray.getColor(R.styleable.CircleInterCircleView_InterCircleColor, Color.GREEN);
        outSize = mTypedArray.getDimension(R.styleable.CircleInterCircleView_OutCircleWidth, 15);
        interSize = mTypedArray.getDimension(R.styleable.CircleInterCircleView_InterCircleWidth, 5);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth()/2;
        int interRadius = (int)interSize/2;
        int outRadius = (int)outSize/2;
        paint.setColor(interColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(centre,centre,interRadius,paint);
        paint.setColor(outColor);
        canvas.drawCircle(centre,centre,outRadius,paint);

    }

    public  void setOutIntColor(int outColor,int interColor){
        this.outColor = outColor;
        this.interColor =interColor;
        invalidate();
    }
}
