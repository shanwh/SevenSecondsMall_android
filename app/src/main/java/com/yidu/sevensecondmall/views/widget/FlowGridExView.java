package com.yidu.sevensecondmall.views.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.views.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 * 宽度可变式gridview by yjy
 */
public class FlowGridExView extends GridLayout{

    private GridAdapter adapter;
    private int screenwidth;
    private int screenheight;
    private int size;
    private int column = 1;
    private Context context;
    private int childheight = 0;
    private List<AttrsBean.GoodsSpecListBean> list = new ArrayList<>();
    private List<View>views = new ArrayList<>();
    private List<Integer> widths = new ArrayList<>();
    private LinearLayout contaner;
    private Paint mPaint;
    private int mItemCount;
    private boolean mDataChanged;
    private int mOldItemCount;
    private FlowObservable mDataObservable;
    int paddingtop= 0;
    int paddingBottom=0;
    int paddingLeft = 0;
    int paddingRight = 0;

    public FlowGridExView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FlowGridExView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FlowGridExView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init(){
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        screenwidth = wm.getDefaultDisplay().getWidth();
        screenheight = wm.getDefaultDisplay().getHeight();
        mPaint = new Paint();
    }


    public void setAdapter(GridAdapter adapter){
        if(this.adapter !=null&&mDataObservable!=null){
            adapter.unregisterDataSetObserver(mDataObservable);
        }
        removeAllViewsInLayout();
        if(adapter !=null){
            this.adapter = adapter;
            size = adapter.getCount();
            list = adapter.getList();
            //添加到父布局
            mDataObservable = new FlowObservable();
            this.adapter.registerDataSetObserver(mDataObservable);
            for(int i = 0;i<list.size();i++){
                View child = adapter.getView(i,null,this);
                addView(child);
                views.add(child);
            }
            requestLayout();
        }else {
            throw new NullPointerException("绑定的adapter为空");
        }

    }

    public GridAdapter getAdapter(){
        return adapter;
    }


    //首先获取整个屏幕的宽度，进一步计算子项的宽度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childheight = 0;
        int totalwidth = 0;
        paddingtop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        paddingRight = getPaddingRight();
        paddingLeft = getPaddingLeft();
        for(int i = 0;i<list.size();i++){
            View view = getChildAt(i);
            int childwith = view.getMeasuredWidth();
            childheight = view.getMeasuredHeight();
            totalwidth = totalwidth + childwith;
            //每一次计算到大于屏幕宽度的整数倍时候，换行
            if(totalwidth > screenwidth * (column)){
                column++;
            }
        }
        int realwidth = screenwidth;
        int realheight = childheight*column + 20;
        setMeasuredDimension(realwidth,realheight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = 0;
        int y = 0;
        int count = getChildCount();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float touchx = event.getX();
                float touch = event.getY();

                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //设置位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int totalwidth = 0;
        int column = 0;
        int childleft=0;
        for(int i = 0;i<adapter.getCount();i++){
            //计算父布局和此时的子布局累加的宽度，大于屏幕则换行
            View child = getChildAt(i);
            int childwith = child.getMeasuredWidth();
            int childheight = child.getMeasuredHeight();
            totalwidth = totalwidth + views.get(i).getMeasuredWidth();
            if(totalwidth * column >screenwidth ){
                column++;
            }
            child.layout(childleft,column*childheight,childwith+childleft,childheight *(column+1));
            childleft += childwith+40;
        }
    }


    public class FlowObservable extends DataSetObserver {

        public FlowObservable() {
        }

        private Parcelable mInstanceState = null;

        @Override
        public void onChanged() {
            mDataChanged = true;
            mOldItemCount = mItemCount;
            mItemCount = getAdapter().getCount();
            removeAllViewsInLayout();
            for(int i = 0;i<mItemCount;i++){
                View child = adapter.getView(i,null,FlowGridExView.this);
                addView(child);
                views.add(child);
            }

            // Detect the case where a cursor that was previously invalidated has
            // been repopulated with new data.
//            if (getAdapter().hasStableIds() && mInstanceState != null
//                    && mOldItemCount == 0 && mItemCount > 0) {
//                onRestoreInstanceState(mInstanceState);
//                mInstanceState = null;
//            } else {
//
//            }
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            mDataChanged = true;

//            if (getAdapter().hasStableIds()) {
//                // Remember the current state for the case where our hosting activity is being
//                // stopped and later restarted
//                mInstanceState = onSaveInstanceState();
//            }

            // Data is invalid so we should reset our state
            mOldItemCount = mItemCount;
            mItemCount = 0;
            requestLayout();
        }

        public void clearSavedState() {
            mInstanceState = null;
        }


    }
}
