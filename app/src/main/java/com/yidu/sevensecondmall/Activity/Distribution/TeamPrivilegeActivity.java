package com.yidu.sevensecondmall.Activity.Distribution;

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
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.bean.User.InviteInfo;
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
 * Created by Administrator on 2017/7/28 0028.
 */
public class TeamPrivilegeActivity extends BaseActivity {


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
    private String[] titles = new String[]{"我推荐的人", "我的团队"};

    public ArrayList<String> tabTitles = new ArrayList<>();
    private List<View> childViews = new ArrayList<>();
    private ArrayList<CustomRecyclerView> rvList = new ArrayList();
    private ArrayList<Integer> pageList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private HomePagerAdapter adapter;

    @Override
    protected int setViewId() {
        return R.layout.activity_team_privilege;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("团队特权");
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
            childViews.add(mLayoutInflater.inflate(R.layout.layout_default_list, viewpagerCircles, false));
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
                width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, TeamPrivilegeActivity.this.getResources().getDisplayMetrics());
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
        loadPeopleNetData(1);
        loadTeamNetData(1);
    }

    private void loadPeopleNetData(final int page) {
        UserDAO.myInviteList(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                InviteInfo info = (InviteInfo) data;
                ArrayList<Visitable> list = info.getList();
                CustomRecyclerView customRv = rvList.get(0);
                customRv.setTotalPage(info.getTotalPage());
                MultiTypeAdapter adapter = customRv.getAdapter();
                String totalCount = info.getTotalCount();
                tabTitles.set(0, titles[0] + "(" + totalCount + ")");
                for (int i = 0; i < tabTitles.size(); i++) {
                    tabLayout.getTabAt(i).setText(tabTitles.get(i));
                }
                resetWidth();
                if (page == 1) {
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
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

    private void loadTeamNetData(final int page) {
        UserDAO.myinviteteam(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                InviteInfo info = (InviteInfo) data;
                ArrayList<Visitable> list = info.getList();
                CustomRecyclerView customRv = rvList.get(1);
                customRv.setTotalPage(info.getTotalPage());
                MultiTypeAdapter adapter = customRv.getAdapter();
                String totalCount = info.getTotalCount();
                tabTitles.set(1, titles[1] + "(" + totalCount + ")");
                for (int i = 0; i < tabTitles.size(); i++) {
                    tabLayout.getTabAt(i).setText(tabTitles.get(i));
                }
                resetWidth();
                if (page == 1) {
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
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
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

            adapter.refreshData(list);

            custom_rv.hideDialog();
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
            adapter.refreshData(list);

            custom_rv.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(LoadDataEvent ev) {
        switch (ev.functionTag) {
            case IEventTag.LOAD_DATA:
                int tag = ev.tag;
                pageList.set(tag, 1);
                switch (tag) {
                    case 0:
                        loadPeopleNetData(ev.page);
                        break;
                    case 1:
                        loadTeamNetData(ev.page);
                        break;
                }
                break;
        }
    }

}
