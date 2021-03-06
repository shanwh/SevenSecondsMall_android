package com.yidu.sevensecondmall.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by liuchao on 2015/3/24.
 */
public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private final RecyclerView.OnScrollListener externalListener;

    //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
    boolean isSlidingToLast = false;
    private int mBackTopCount;


    public RecyclerOnScrollListener(int backTopCount) {
        this(null);
        this.mBackTopCount = backTopCount;
    }

    public RecyclerOnScrollListener( ) {
        this(null);
    }

    public RecyclerOnScrollListener(RecyclerView.OnScrollListener customListener) {

        externalListener = customListener;
    }
    public RecyclerOnScrollListener(RecyclerView.OnScrollListener customListener, int backTopCount) {

        externalListener = customListener;
        this.mBackTopCount = backTopCount;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
               // imageLoader.resume();
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof LinearLayoutManager){
                    LinearLayoutManager manager = (LinearLayoutManager)layoutManager;
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int firstVisibleItem = manager.findFirstVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    //int visibleItemCount = manager.getChildCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast ) {
                        //加载更多功能的代码
                        onLoadMore();
                    }

                    if(firstVisibleItem == 0 && !isSlidingToLast){
                        onTopLoadMore();
                    }

                    if(mBackTopCount > 0){
                        if( firstVisibleItem + 1 > mBackTopCount){
                            onShowBackTop();
                        }
                        if(firstVisibleItem + 1 <= mBackTopCount ){
                            onHideBackTop();
                        }
                    }
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:

                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                break;
        }
        if (externalListener != null) {
            externalListener.onScrollStateChanged(recyclerView, newState);
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (externalListener != null) {
            externalListener.onScrolled(recyclerView, dx, dy);
        }
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dx > 0 || dy > 0) {
            //大于0表示，正在向右滚动
            isSlidingToLast = true;
        } else {
            //小于等于0 表示停止或向左滚动
            isSlidingToLast = false;
        }
    }

    public abstract void onLoadMore();//要在实现类中实现
    public void onTopLoadMore(){}
    public void onShowBackTop(){}
    public void onHideBackTop(){}

}
