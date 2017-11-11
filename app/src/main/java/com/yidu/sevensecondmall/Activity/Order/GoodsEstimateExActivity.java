package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CommendInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;
import com.yidu.sevensecondmall.views.adapter.HomePagerAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/12.
 */
public class GoodsEstimateExActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.v)
    View v;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_order)
    ViewPager viewpagerOrder;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String[] titles = new String[]{"全部\n", "好评\n", "中评\n", "差评\n", "晒图\n"};
    private String[] types = new String[]{"", "good", "commonly", "bad", "img"};
    public ArrayList<String> tabTitles = new ArrayList<>();
    private List<View> childViews = new ArrayList<>();
    private ArrayList<CustomRecyclerView> rvList = new ArrayList();
    private ArrayList<Integer> pageList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private HomePagerAdapter adapter;

    private String id;

    @Override
    protected int setViewId() {
        return R.layout.activity_estimate;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
//        context = GoodsEstimateExActivity.this;
        Intent i = getIntent();
        if (i.hasExtra("id") && i.getStringExtra("id") != null) {
            id = i.getStringExtra("id");
        }
        toolbarTitle.setText("商品评价");
        titleName.setText("商品评价");

    }

    @Override
    protected void initEvent() {
        mLayoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < titles.length; i++) {
            tabTitles.add(titles[i] + "0");
        }
    }

    @Override
    protected void init() {
        for (int i = 0; i < tabTitles.size(); i++) {
            childViews.add(mLayoutInflater.inflate(R.layout.layout_default_list, viewpagerOrder, false));
            rvList.add((CustomRecyclerView) childViews.get(i).findViewById(R.id.custom_rv));
            rvList.get(i).setTag(i);
            pageList.add(1);
        }
        adapter = new HomePagerAdapter(childViews);
        initViews();
    }

    private void initViews() {
        for (int i = 0; i < tabTitles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabTitles.get(i)));
        }

        //为ViewPager设置FragmentPagerAdapter
        viewpagerOrder.setAdapter(adapter);
        //TabLaout和ViewPager进行关联
        tabLayout.setupWithViewPager(viewpagerOrder);

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

    @Override
    protected void loadData() {
        OkHttpClientManager.doOkHttpPost(HttpApi.getComment)
                .addCode("goods_id", id)
                .addCode("page", "1")
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("all_count")) {
                                tabTitles.set(0, titles[0] + content.getString("all_count"));
                            }
                            if (content.has("good_count")) {
                                tabTitles.set(1, titles[1] + content.getString("good_count"));
                            }
                            if (content.has("commonly_count")) {
                                tabTitles.set(2, titles[2] + content.getString("commonly_count"));
                            }
                            if (content.has("bad_count")) {
                                tabTitles.set(3, titles[3] + content.getString("bad_count"));
                            }
                            if (content.has("img_count")) {
                                tabTitles.set(4, titles[4] + content.getString("img_count"));
                            }

                            for (int i = 0; i < tabTitles.size(); i++) {
                                tabLayout.getTabAt(i).setText(tabTitles.get(i));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {

                    }
                });
        if (id != null) {
            for (int i = 0; i < titles.length; i++) {
                pageList.set(i, 1);
                loadCommendList(1, i);
            }
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


    @OnClick({R.id.back_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;

        }
    }

    public void loadCommendList(int page, final int position) {
        GoodsDAO.getCommentInfo(id, page + "", types[position],
                new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        CommendInfo info = (CommendInfo) data;
                        ArrayList<Visitable> list = info.getList();
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
                            if (customRv.getSwipeRefresh().isRefreshing()) {
                                showShortToast("刷新成功");
                            }
                            customRv.stopSwipeRefresh();
                        } else {
                            adapter.addMoreData(list);
                        }
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
                    loadCommendList(1, i);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoadData(LoadDataEvent ev) {
        switch (ev.functionTag) {
            case IEventTag.LOAD_DATA:
                pageList.set(ev.tag, ev.page);
                loadCommendList(ev.page, ev.tag);

                break;
        }
    }

}
