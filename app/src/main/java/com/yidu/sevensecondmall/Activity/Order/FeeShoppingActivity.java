package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.FeeShoppingBean;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.views.adapter.HomePagerAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/1.
 */
public class FeeShoppingActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar1;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_circles)
    ViewPager viewpagerCircles;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

//
//    @BindView(R.id.custom_rv)
//    CustomRecyclerView customRv;

    private int currentPage = 1;

    private int count;

    //    private String[] titles = new String[]{"  全部  ", "可转让", "转让中"};
    private String[] titles = new String[]{"  全部  "};
    private List<View> childViews = new ArrayList<>();
    private ArrayList<CustomRecyclerView> rvList = new ArrayList();
    private ArrayList<Integer> pageList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private HomePagerAdapter adapter;

    private ArrayList<Visitable> assignList = new ArrayList<>();
    private ArrayList<Visitable> assignedList = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.freeshopping_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("赠品进度");
//        toolbarRight.setText("转让区");
//        toolbarRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        mLayoutInflater = LayoutInflater.from(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        for (int i = 0; i < titles.length; i++) {
            childViews.add(mLayoutInflater.inflate(R.layout.layout_default_list, viewpagerCircles, false));
            rvList.add((CustomRecyclerView) childViews.get(i).findViewById(R.id.custom_rv));
            rvList.get(i).setTag(i);
            pageList.add(1);
        }
        adapter = new HomePagerAdapter(childViews);
        initViews();
    }

    private void initViews() {
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }

        //为ViewPager设置FragmentPagerAdapter
        viewpagerCircles.setAdapter(adapter);
        //TabLaout和ViewPager进行关联
        tabLayout.setupWithViewPager(viewpagerCircles);


        //防止tab太多，都拥挤在一起
        if (titles.length > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }

        ////隐藏两个界面  resetWidth();

        for (int i = 0; i < titles.length; i++) {
            tabLayout.getTabAt(i).setText(titles[i]);
        }
    }

    private void resetWidth() {
        try {
            //拿到tabLayout的mTabStrip属性
            Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);

            LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);

//            int dp10 = SM.dip2px(getContext(), 10);
//            int dp10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, DistributionActivity.this.getResources().getDisplayMetrics());

            for (int i = 0; i < titles.length; i++) {

            }


            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                mTextView.getText().length();
//                width = mTextView.getWidth();
//                if (width == 0) {
//                    mTextView.measure(0, 0);
//                    width = mTextView.getMeasuredWidth();
//                }
                String title = titles[0];
                width = getCharacterWidth(title, 15);
                width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, FeeShoppingActivity.this.getResources().getDisplayMetrics());
                int mar = (getScreen() / titles.length - width) / 2;


                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                params.width = width ;
                params.leftMargin = mar;
                params.rightMargin = mar;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCharacterWidth(String text, float size) {
        if (null == text || "".equals(text)) {
            return 0;

        }


        Paint paint = new Paint();
        paint.setTextSize(size);
        int text_width = (int) paint.measureText(text);// 得到总体长度
        // int width = text_width/text.length();//每一个字符的长度
        return text_width;
    }

    private int getScreen() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }


    @Override
    protected void loadData() {
        loadNetData();
    }


    @OnClick({R.id.back, R.id.tv_assignment})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
//            case R.id.tv_assignment:
//                intent = new Intent(this, AssignmentActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(LoadDataEvent event) {
        switch (event.functionTag) {

            case IEventTag.LOAD_DATA:
                currentPage = event.page;
                loadNetData();
                break;

            case IEventTag.SCREEN_DIM:
                WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
                // layoutParams.alpha = 0.5f; //0.0-1.0
                this.getWindow().setAttributes(layoutParams);
                this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);//让窗口背景后面的任何东西变暗
                break;

        }
    }

    public void loadNetData() {
        UserDAO.getUserInfo(
                new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            UserBean bean = (UserBean) data;

                            boolean isVip = bean.getIcon().isVip();
                            boolean isShareHolder = bean.getIcon().isPartner();

                            if (isShareHolder) {
                                SystemUtil.setSharedBoolean("isVip", true);
                            } else if (isVip) {
                                SystemUtil.setSharedBoolean("isVip", true);
                            } else {
                                SystemUtil.setSharedBoolean("isVip", false);
                            }

                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode == 1006 || errorCode == 1005) {

                        } else {
                            showShortToast(data + "");
                        }
                    }
                }

        );


        UserDAO.myFreeOrderList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                ArrayList<Visitable> list = (ArrayList<Visitable>) data;
                assignList.clear();
                assignedList.clear();
                for (int i = 0; i < list.size(); i++) {
                    FeeShoppingBean bean = (FeeShoppingBean) list.get(i);
                    if (bean.getSell_status().equals("0")) {
                        assignList.add(bean);
                    } else {
                        if (!bean.getProgress().equals("100"))
                            assignedList.add(bean);
                    }
                }
//                CustomRecyclerView cR = rvList.get(1);
//                setData(cR, assignList);
//隐藏两个界面
//                CustomRecyclerView cRd = rvList.get(2);
//                setData(cRd, assignedList);

                CustomRecyclerView customRv = rvList.get(0);
                MultiTypeAdapter adapter = customRv.getAdapter();
//                if (currentPage == 1) {
//                    if (list.size() == 0) {
//                        customRv.showEmptyView();
//                    } else {
//                        customRv.hideEmptyView();
//                    }

                adapter.refreshData(list);
                customRv.stopSwipeRefresh();
//                    if (customRv.getSwipeRefresh().isRefreshing()) {
//                        showShortToast("刷新成功");
//                    }
//                    customRv.stopSwipeRefresh();
//                } else {
//                    adapter.addMoreData(list);
//                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });

    }


    private void setData(CustomRecyclerView customRv, ArrayList<Visitable> list) {
        MultiTypeAdapter adapter = customRv.getAdapter();
//                if (currentPage == 1) {
//                    if (list.size() == 0) {
//                        customRv.showEmptyView();
//                    } else {
//                        customRv.hideEmptyView();
//                    }

        adapter.refreshData(list);
        customRv.stopSwipeRefresh();
    }

}
