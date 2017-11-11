package com.yidu.sevensecondmall.Activity.Distribution;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10 0010.
 * 已弃用
 */
public class DistributionActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.if_tv_record)
    IconFontTextView ifTvRecord;
    @BindView(R.id.tv_all_tip)
    TextView tvAllTip;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_circles)
    ViewPager viewpagerCircles;
    @BindView(R.id.tv_user_consumption)
    TextView tvUserConsumption;
    @BindView(R.id.tv_user_update)
    TextView tvUserUpdate;
    @BindView(R.id.tv_user_sale)
    TextView tvUserSale;
    @BindView(R.id.tv_user_all)
    TextView tv_user_all;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String[] titles = new String[]{"我推荐的人", "我的团队"};

    public ArrayList<String> tabTitles = new ArrayList<>();
    private List<View> childViews = new ArrayList<>();
    private ArrayList<CustomRecyclerView> rvList = new ArrayList();
    private ArrayList<Integer> pageList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private HomePagerAdapter adapter;
    private int id;
    private static final String TAG = "DistributionActivity";

    @Override
    protected int setViewId() {
        return R.layout.activity_distribution;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("VIP专区");
        mLayoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < titles.length; i++) {
            tabTitles.add(titles[i]);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        for (int i = 0; i < tabTitles.size(); i++) {
            childViews.add(mLayoutInflater.inflate(R.layout.layout_default_wrap_list, viewpagerCircles, false));
            rvList.add((CustomRecyclerView) childViews.get(i).findViewById(R.id.custom_rv));
            rvList.get(i).setTag(i);
            pageList.add(1);
        }
        adapter = new HomePagerAdapter(childViews);
        initViews();
        id = getIntent().getIntExtra("id", 1);

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
            tabLayout.getTabAt(i).setText(tabTitles.get(i) + "(0)");
        }
        //防止tab太多，都拥挤在一起
        if (tabTitles.size() > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }

        resetWidth();
    }

    @Override
    protected void loadData() {
        loadMessage();
//        loadMyPeople();
//        loadMyTeam();
    }


    @OnClick({R.id.back, R.id.if_tv_record})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.if_tv_record:
                intent = new Intent(this, RecordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(LoadDataEvent ev) {
        switch (ev.functionTag) {
            case IEventTag.LOAD_DATA:
//                int tag = ev.tag;
//                pageList.set(tag, 1);
//                switch (tag) {
//                    case 0:
//                        loadMyPeople();
//                        break;
//                    case 1:
//                        loadMyTeam();
//                        break;
//                }
                break;
        }
    }

    private void loadMessage() {
        DistributionDAO.partnerinfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("partnerinfo")) {
                        JSONObject jsonObject = content.getJSONObject("partnerinfo");
                        tv_user_all.setText("¥" + jsonObject.getString("user_all"));
                        tvUserConsumption.setText(jsonObject.getString("user_consumption"));
                        tvUserUpdate.setText(jsonObject.getString("user_update"));
                        tvUserSale.setText(jsonObject.getString("user_sale"));
                        if (jsonObject.has("my_invite_list")) {
                            JSONArray my_invite_list = jsonObject.getJSONArray("my_invite_list");
                            setMyPeople(my_invite_list);
                        }
                        if (jsonObject.has("my_team_list")) {
                            JSONArray my_team_list = jsonObject.getJSONArray("my_team_list");
                            setMyTeam(my_team_list);
                        }
                        if (jsonObject.has("my_invite_count")) {
                            String my_invite_count = jsonObject.getString("my_invite_count");
                            tabTitles.set(0, tabTitles.get(0) + "(" + my_invite_count + ")");
                        }
                        if (jsonObject.has("my_team_count")) {
                            String my_team_count = jsonObject.getString("my_team_count");
                            tabTitles.set(1, tabTitles.get(1) + "(" + my_team_count + ")");
                        }
                        for (int i = 0; i < tabTitles.size(); i++) {
                            tabLayout.getTabAt(i).setText(tabTitles.get(i));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

    private void setMyTeam(JSONArray my_team_list) {
        try {
            ArrayList<Visitable> list = new ArrayList<>();
            for (int i = 0; i < my_team_list.length(); i++) {
                JSONObject jsonObject = my_team_list.getJSONObject(i);
                MemberListBean bean = new MemberListBean();
                bean.setId(jsonObject.getString("id"));
                bean.setUser_id(jsonObject.getString("user_id"));
                bean.setNickname(jsonObject.getString("nickname"));
                bean.setHead_pic(jsonObject.getString("head_pic"));

                bean.setInvite_user_id(jsonObject.getString("invite_user_id"));
                bean.setPartner_id(jsonObject.getString("partner_id"));
                bean.setBranch_id(jsonObject.getString("branch_id"));
                bean.setCreatetime(jsonObject.getString("createtime"));
                bean.setVip(jsonObject.getBoolean("vip"));
                list.add(bean);
            }

            CustomRecyclerView custom_rv = rvList.get(1);
            MultiTypeAdapter adapter = custom_rv.getAdapter();
            if (pageList.get(1) == 1) {
                if (list.size() == 0) {
                    custom_rv.showEmptyView();
                } else {
                    custom_rv.hideEmptyView();
                }
                adapter.refreshData(list);
                if (custom_rv.getSwipeRefresh().isRefreshing()) {
                    showShortToast("刷新成功");
                }
                custom_rv.stopSwipeRefresh();
            } else {
                adapter.addMoreData(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMyPeople(JSONArray my_invite_list) {
        try {
            ArrayList<Visitable> list = new ArrayList<>();
            for (int i = 0; i < my_invite_list.length(); i++) {
                JSONObject jsonObject = my_invite_list.getJSONObject(i);
                MemberListBean bean = new MemberListBean();
                bean.setId(jsonObject.getString("id"));
                bean.setUser_id(jsonObject.getString("user_id"));
                bean.setNickname(jsonObject.getString("nickname"));
                bean.setHead_pic(jsonObject.getString("head_pic"));
                bean.setInvite_user_id(jsonObject.getString("invite_user_id"));
                bean.setPartner_id(jsonObject.getString("partner_id"));
                bean.setBranch_id(jsonObject.getString("branch_id"));
                bean.setCreatetime(jsonObject.getString("createtime"));
                bean.setVip(jsonObject.getBoolean("vip"));

                list.add(bean);

            }

            CustomRecyclerView custom_rv = rvList.get(0);
            MultiTypeAdapter adapter = custom_rv.getAdapter();
            if (pageList.get(0) == 1) {
                if (list.size() == 0) {
                    custom_rv.showEmptyView();
                } else {
                    custom_rv.hideEmptyView();
                }
                adapter.refreshData(list);
                if (custom_rv.getSwipeRefresh().isRefreshing()) {
                    showShortToast("刷新成功");
                }
                custom_rv.stopSwipeRefresh();
            } else {
                adapter.addMoreData(list);
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
                width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, DistributionActivity.this.getResources().getDisplayMetrics());
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

}
