package com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.IBottom2;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class CustomRecyclerView extends FrameLayout implements IBottom2 {
    private static final String TAG = "TRecyclerView";
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private int layoutMangerType;
    private List<Visitable> list = new ArrayList<>();
    private int currentPage = 1;
    private int totalPage = 1;
    private boolean isEmpty = false;
    private MultiTypeAdapter adapter;
    private boolean isShowHasNotMoreData = true;
    private RelativeLayout view;
    private Context context;
    private boolean isBottom;
    private boolean isList;
    private int tag;
    private boolean isShowEmpty;
    private boolean isWarpContent;
    private ProgressDialog dialog;
    private View v;
    private boolean isHidtitel;
    private View v2;
    private int move;
    private boolean tag_is_not_update =false;

    public CustomRecyclerView(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    public Boolean isRefreshing() {
        if (swipeRefresh == null) {
            return false;
        } else {
            return swipeRefresh.isRefreshing();
        }
    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public RecyclerView getRv() {
        return rv;
    }

    public boolean isShowHasNotMoreData() {
        return isShowHasNotMoreData;
    }

    public void setShowHasNotMoreData(boolean showHasNotMoreData) {
        isShowHasNotMoreData = showHasNotMoreData;
    }

    public boolean isShowEmpty() {
        return isShowEmpty;
    }

    public void setShowEmpty(boolean showEmpty) {
        isShowEmpty = showEmpty;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public static String getTAG() {
        return TAG;
    }

    public SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }


    public MultiTypeAdapter getAdapter() {
        return adapter;
    }

    public MultiTypeAdapter getAdapter(View v,View v2,boolean boo) {
        this.v =v;
        this.v2 =v2;
        this.isHidtitel =boo;
        return adapter;

    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void init(final Context context, AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TRecyclerView);
        int itemType = ta.getResourceId(R.styleable.TRecyclerView_itemType, 0);
        boolean isRefreshable = ta.getBoolean(R.styleable.TRecyclerView_isRefreshable, true);
        layoutMangerType = ta.getInteger(R.styleable.TRecyclerView_layoutMangerType, 0);
        int layoutMangerCount = ta.getInteger(R.styleable.TRecyclerView_layoutMangerCount, 1);
        int layoutMangerOrientation = ta.getInteger(R.styleable.TRecyclerView_layoutMangerOrientation, 0);
        boolean hasContext = ta.getBoolean(R.styleable.TRecyclerView_hasContext, false);
        isWarpContent = ta.getBoolean(R.styleable.TRecyclerView_isWrapContent, false);
        ta.recycle();

        dialog = new ProgressDialog(context);
        dialog.setMessage("请等待");

        if (isWarpContent) {
            View layout = inflate(context, R.layout.layout_custom_recycle_view_wrap, this);
            rv = (RecyclerView) layout.findViewById(R.id.recycler_view);
            rv.setFocusableInTouchMode(false);//设置不需要焦点
            rv.requestFocus();

            rv.setHasFixedSize(true);
            switch (layoutMangerType) {
                case 0:
                    mLinearLayoutManager = new LinearLayoutManager(context);
                    mLinearLayoutManager.setOrientation(layoutMangerOrientation == 1 ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
                    rv.setLayoutManager(mLinearLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
                    break;
                case 1:
                    mGridLayoutManager = new GridLayoutManager(context, layoutMangerCount);
                    mGridLayoutManager.setOrientation(layoutMangerOrientation == 1 ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
                    rv.setLayoutManager(mGridLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    break;
                case 2:
                    mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(layoutMangerCount, layoutMangerOrientation == 1 ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
                    rv.setLayoutManager(mStaggeredGridLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    break;
                default:
                    mLinearLayoutManager = new LinearLayoutManager(context);
                    rv.setLayoutManager(mLinearLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());

                    break;
            }

            if (hasContext) {
                adapter = new MultiTypeAdapter(list, context);
            } else {
                adapter = new MultiTypeAdapter(list);
            }
            rv.setAdapter(adapter);

        } else {
            View layout = inflate(context, R.layout.layout_custom_recycle_view, this);
            swipeRefresh = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_refresh);
            rv = (RecyclerView) layout.findViewById(R.id.recycler_view);
            rv.setFocusableInTouchMode(false);//设置不需要焦点
            rv.requestFocus();
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (rv.getAdapter() != null
                            && newState == RecyclerView.SCROLL_STATE_IDLE
                            ) {

                        if (isBottom) {
                            if (currentPage < totalPage) {
                                currentPage++;
                                actionLoadData();
                            } else {
                                if (isShowHasNotMoreData)
                                    ToastUtil.showToast(CustomRecyclerView.this.context, "暂无更多数据");
                            }
                        }
                    }

                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int arg0, int arg1) {
                    super.onScrolled(recyclerView, arg0, arg1);
                    if(isHidtitel){
                        int i =getScollYDistance();
                       if(i==0){
                           v.setTag("0");
                           v.getBackground().setAlpha(0);
                           v2.getBackground().setAlpha(0);
                       }
                        if(i<45) {
                            v.setTag("1");
                            v.setBackgroundColor(Color.parseColor("#eb8a2f"));
                            v.getBackground().setAlpha(i* 255 / 45);
                            v2.setBackgroundColor(Color.parseColor("#eb8a2f"));
                            v2.getBackground().setAlpha(i * 255 / 45);
                            tag_is_not_update=false;
                        }

                        Log.e("tag_is_not_update","----------------------------------"+tag_is_not_update);
                        if(i>45&&!tag_is_not_update){
                            v.setTag("2");
                            tag_is_not_update=true;
                            v.setBackgroundColor(Color.parseColor("#eb8a2f"));
                            v2.setBackgroundColor(Color.parseColor("#eb8a2f"));
//                            v2.getBackground().setAlpha(255);
                        }
                    }
                }
            });

            swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    currentPage = 1;
                    swipeRefresh.setRefreshing(true);
                    actionLoadData();
                }
            });
            rv.setHasFixedSize(true);
            switch (layoutMangerType) {
                case 0:
                    mLinearLayoutManager = new LinearLayoutManager(context);
                    mLinearLayoutManager.setOrientation(layoutMangerOrientation == 1 ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
                    rv.setLayoutManager(mLinearLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
                    break;
                case 1:
                    mGridLayoutManager = new GridLayoutManager(context, layoutMangerCount);
                    mGridLayoutManager.setOrientation(layoutMangerOrientation == 1 ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
                    rv.setLayoutManager(mGridLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    break;
                case 2:
                    mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(layoutMangerCount, layoutMangerOrientation == 1 ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
                    rv.setLayoutManager(mStaggeredGridLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    break;

                default:
                    mLinearLayoutManager = new LinearLayoutManager(context);
                    rv.setLayoutManager(mLinearLayoutManager);
                    rv.setItemAnimator(new DefaultItemAnimator());
                    break;
            }

            if (hasContext) {
                adapter = new MultiTypeAdapter(list, context, CustomRecyclerView.this);
            } else {
                adapter = new MultiTypeAdapter(list, CustomRecyclerView.this);
            }
            rv.setAdapter(adapter);

            swipeRefresh.setEnabled(isRefreshable);
        }
    }
    //获取距离
    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }


    public void stopSwipeRefresh() {
        hideDialog();
        if (swipeRefresh != null)
            swipeRefresh.setRefreshing(false);
    }


    @Override
    public void isBottom(boolean bottom, boolean List) {
        isBottom = bottom;
        isList = List;
        if (isList&&isBottom){
            
        }
    }

    public void showEmptyView() {
        rv.setVisibility(GONE);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = (RelativeLayout) inflater.inflate(R.layout.layout_empty_tip, null);
            view.findViewById(R.id.refresh).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPage = 1;
                    dialog.show();
                    actionLoadData();
                }
            });
            this.addView(view);
        } else {
            view.setVisibility(View.VISIBLE);
        }
        isShowEmpty = true;
    }

    public void hideEmptyView() {
        rv.setVisibility(VISIBLE);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        isShowEmpty = false;
    }

    private void actionLoadData() {
        //通知model层刷新数据
        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA, currentPage, tag));
    }

    public void addFindFirstViewListener() {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int position = findFirstViewPosition();
                EventBus.getDefault().post(new LoginEvent(IEventTag.FIND_FIRST_VIEW_POSITION, position));

                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private int findFirstViewPosition() {
        int position = 0;
        switch (layoutMangerType) {
            case 0:
                position = mLinearLayoutManager.findFirstVisibleItemPosition();
                break;
            case 1:
                position = mGridLayoutManager.findFirstVisibleItemPosition();
                break;
            case 2:
                int[] firstVisibleItems = null;
                position = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems)[0];
                break;
        }
        return position;
    }

    public void scrollToTop() {
        rv.scrollToPosition(0);
    }
}