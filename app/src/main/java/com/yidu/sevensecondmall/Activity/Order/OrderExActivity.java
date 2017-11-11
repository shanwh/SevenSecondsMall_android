package com.yidu.sevensecondmall.Activity.Order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.OrderInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.views.adapter.HomePagerAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 订单activity
 */
public class OrderExActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_circles)
    ViewPager viewpagerCircles;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String[] titles = new String[]{"全部", "待付款", "待发货", "待收货", "待评价", "已退款"};
    private String[] types = new String[]{"", "waitpay", "waitsend", "waitget", "waitevaluate", "return_goods"};
    public ArrayList<String> tabTitles = new ArrayList<>();
    private List<View> childViews = new ArrayList<>();
    private ArrayList<CustomRecyclerView> rvList = new ArrayList();
    private ArrayList<Integer> pageList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private HomePagerAdapter adapter;

    private ArrayList<Boolean> booleanList = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.activity_order_ex;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("个人订单");
        mLayoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < titles.length; i++) {
            tabTitles.add(titles[i]);
            booleanList.add(true);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        for (int i = 0; i < tabTitles.size(); i++) {
            childViews.add(mLayoutInflater.inflate(R.layout.layout_default_list, viewpagerCircles, false));
            rvList.add((CustomRecyclerView) childViews.get(i).findViewById(R.id.custom_rv));
            rvList.get(i).setTag(i);
            pageList.add(1);
        }
        adapter = new HomePagerAdapter(childViews);
        initViews();
        if (getIntent().hasExtra("clickIndex")) {
            viewpagerCircles.setCurrentItem(getIntent().getIntExtra("clickIndex", 0));
        }
    }

    @Override
    protected void loadData() {
        for (int i = 0; i < titles.length; i++) {
            pageList.set(i, 1);
            loadOrderList(1, i);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemUtil.getSharedBoolean("refreshOrder", false)) {
            loadData();
            SystemUtil.setSharedBoolean("refreshOrder", false);
        }
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    private void initViews() {
        for (int i = 0; i < tabTitles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitles.get(i)));
        }

        //为ViewPager设置FragmentPagerAdapter
        viewpagerCircles.setAdapter(adapter);
        //TabLaout和ViewPager进行关联
        tabLayout.setupWithViewPager(viewpagerCircles);

        for (int i = 0; i < tabTitles.size(); i++) {
            tabLayout.getTabAt(i).setText(tabTitles.get(i));
        }
        //防止tab太多，都拥挤在一起
        if (tabTitles.size() > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }
    }

    private static final String TAG = "OrderExActivity";

    public void loadOrderList(int page, final int position) {
        OrderDAO.getOrderList(page, types[position],
                new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        OrderInfo info = (OrderInfo) data;
                        ArrayList<Visitable> list = info.getList();
                        Log.e(TAG, "success: " + info.getTotalPage());
                        rvList.get(position).setTotalPage(info.getTotalPage());
                        CustomRecyclerView customRv = rvList.get(position);
                        MultiTypeAdapter adapter = customRv.getAdapter();
                        if (pageList.get(position) == 1) {
                            if (list.size() == 0) {
                                customRv.showEmptyView();
                            } else {
                                customRv.hideEmptyView();
                            }
                            adapter.refreshData(list);
                            if (customRv.isRefreshing()) {
                                showShortToast("刷新成功");
                            }
                            customRv.stopSwipeRefresh();
                        } else {
                            adapter.addMoreData(list);
                        }
                        booleanList.set(position, false);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        CustomRecyclerView customRv = rvList.get(position);
                        if (errorCode != 1001) {
                            showShortToast(data + "");
                        } else {
                            customRv.showEmptyView();
                        }
                        customRv.stopSwipeRefresh();
//                        if (errorCode == 1001 && !booleanList.get(position)){
//                            showShortToast(data + "");
//                        }
                        booleanList.set(position, false);
                    }
                }

        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlecenter(RefreshEvent ev) {
        switch (ev.founctionTag) {
            case IEventTag.REFRSHLIST:
                for (int i = 0; i < titles.length; i++) {
                    pageList.set(i, 1);
                    loadOrderList(1, i);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoadData(LoadDataEvent ev) {
        switch (ev.functionTag) {
            case IEventTag.LOAD_DATA:
                pageList.set(ev.tag, ev.page);
                loadOrderList(ev.page, ev.tag);

                break;
            case IEventTag.TO_DETAIL:
//                ev.page;
//                Intent i = new Intent(this, OrderDetailActivity.class);
//                i.putExtra("orderid", model.getOrder_id());
//                i.putExtra("buyBackStatus", model.getBuy_back_status());
//                i.putExtra("buyBackAmount", model.getBuy_back_amount());
//                i.putExtra("ordersn", model.getOrder_sn());
//                i.putExtra("status", model.getOrder_status_code());
//                context.startActivity(i);
                break;
        }
    }

}
