package com.yidu.sevensecondmall.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yidu.sevensecondmall.R;

/**
 * Created by Administrator on 2017/8/29.
 */
public class GearView extends View {
    private boolean isCenter;
    private int coverPic;
    private int bottomPic;
    private boolean inner2;
    private boolean inner1;
    Context context;
    private Paint mPaint = new Paint();
    private int widofview = 0;
    private boolean isRotate = true;
    private int mSpeed = 0;
    private double mPersent;
    private Bitmap up;
    private Bitmap down;
    private Bitmap b1 = null;
    private Bitmap b2 = null;


    // private  Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GearView(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public GearView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GearView);
        isCenter = ta.getBoolean(R.styleable.GearView_isCenterText, false);
        inner1 = ta.getBoolean(R.styleable.GearView_innerCircle1, false);
        inner2 = ta.getBoolean(R.styleable.GearView_innerCircle2, false);
        bottomPic = ta.getResourceId(R.styleable.GearView_bottomPic, 0);
        coverPic = ta.getResourceId(R.styleable.GearView_coverPic, 0);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        widofview = getWidth();
        Log.e("wid+wid....", widofview + "");
        if (null == b2 || null == up || null == b1 || null == down) {
            init();
        }
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);
        if (bottomPic != 0 && coverPic != 0) {
            canvas.save();
            //底部图片
            canvas.drawBitmap(b2, 0, 0, mPaint);
            getSectorClip(canvas, widofview / 2, widofview / 2, widofview / 2, 90, 90 + (int) (mPersent * 360));
            canvas.drawBitmap(b1, 0, 0, mPaint);
            canvas.restore();

            if (inner1 && inner2) {
                drawInnerCricle(canvas, mSpeed);
            }
        }
    }

    //初始化
    private void init() {
        Bitmap b2old = BitmapFactory.decodeResource(getResources(), bottomPic);
        Bitmap b1old = BitmapFactory.decodeResource(getResources(), coverPic);
        float scaleX = (float) getWidth()/ b2old.getWidth();
        int newW = (int) (b2old.getWidth() * scaleX);
        b2 =Bitmap.createScaledBitmap(b2old,newW,newW,true);
        b1 =Bitmap.createScaledBitmap(b1old,newW,newW,true);
        if (inner1 && inner2) {
            Bitmap upold = BitmapFactory.decodeResource(getResources(), R.drawable.speedupgear);
            Bitmap downold = BitmapFactory.decodeResource(getResources(), R.drawable.speeddowngear);
            int newW2 = (int) (upold.getWidth() * scaleX);
            up = Bitmap.createScaledBitmap(upold,newW2,newW2,true);
            int neww3 =(int) (downold.getWidth() * scaleX);
            down = Bitmap.createScaledBitmap(downold,neww3,neww3,true);
        }
    }


    private void drawInnerCricle(Canvas canvas, final int speed) {
        canvas.save();
        canvas.rotate(speed, widofview / 5 + 10 + up.getHeight() / 2, widofview * 2 / 5 + 10 + (up.getWidth() / 2));//顺时针
        canvas.drawBitmap(up, widofview / 5 + 10, widofview * 2 / 5 + 10, mPaint);//绘制图片，（图片会被旋转）
        canvas.restore();
        canvas.save();
        canvas.rotate(-speed * 3 / 5, widofview * 2 / 5 + down.getWidth() / 2, widofview / 5 + down.getWidth() / 2);
        canvas.drawBitmap(down, widofview * 2 / 5, widofview / 5, mPaint);
        canvas.restore();

        if (isRotate) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSpeed += 10;
                    Log.e("postdelay", mSpeed + "");
                    invalidate();
                }
            }, 6);

        }
    }

    Handler handler = new Handler();

    /**
     * 返回一个扇形的剪裁区
     *
     * @param canvas     //画笔
     * @param center_X   //圆心X坐标
     * @param center_Y   //圆心Y坐标
     * @param r          //半径
     * @param startAngle //起始角度
     * @param sweepAngle //终点角度
     */

    private void getSectorClip(Canvas canvas, float center_X, float center_Y, float r, float startAngle, float sweepAngle)

    {

        Path path = new Path();

        //下面是获得一个三角形的剪裁区

            path.moveTo(center_X, center_Y);  //圆心

            path.lineTo((float) (center_X + r * Math.cos(startAngle * Math.PI / 180)),   //起始点角度在圆上对应的横坐标

                    (float) (center_Y + r * Math.sin(startAngle * Math.PI / 180)));    //起始点角度在圆上对应的纵坐标

            path.lineTo((float) (center_X + r * Math.cos(sweepAngle * Math.PI / 180)),  //终点角度在圆上对应的横坐标

                    (float) (center_Y + r * Math.sin(sweepAngle * Math.PI / 180)));   //终点点角度在圆上对应的纵坐标

            path.close();


//      //设置一个正方形，内切圆

        RectF rectF = new RectF(center_X - r, center_Y - r, center_X + r, center_Y + r);

        //下面是获得弧形剪裁区的方法

        path.addArc(rectF, startAngle, sweepAngle - startAngle);

        canvas.clipPath(path);

    }

    public  Path getPath(Canvas canvas, float center_X, float center_Y, float r, float startAngle, float sweepAngle){
        Path path = new Path();
        path.moveTo(center_X, center_Y);  //圆心

        path.lineTo((float) (center_X + r * Math.cos(startAngle * Math.PI / 180)),   //起始点角度在圆上对应的横坐标

                (float) (center_Y + r * Math.sin(startAngle * Math.PI / 180)));    //起始点角度在圆上对应的纵坐标

        path.lineTo((float) (center_X + r * Math.cos(sweepAngle * Math.PI / 180)),  //终点角度在圆上对应的横坐标

                (float) (center_Y + r * Math.sin(sweepAngle * Math.PI / 180)));   //终点点角度在圆上对应的纵坐标
        path.close();
        return  path;
    }

    public void startRotate(double persent) {//开始旋转
        if (!isRotate) {
            this.isRotate = true;
            this.mPersent = persent;
            invalidate();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRotate();
                }
            }, 2000);
        }
    }

    public void startRotate() {//开始旋转
        if (!isRotate) {
            this.isRotate = true;
            invalidate();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRotate();
                }
            }, 2000);
        }
    }

    public void setProgress(double dou) {
        isRotate = false;
        this.mPersent = dou;
        invalidate();
    }

    public void stopRotate() {//暂停旋转
        isRotate = false;
    }


}
