package com.yidu.sevensecondmall.Activity.UserCenter;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.fragments.GiftProgress.GiftProgressAllFragment;
import com.yidu.sevensecondmall.fragments.GiftProgress.GiftProgressCompleteFragment;
import com.yidu.sevensecondmall.fragments.GiftProgress.GiftProgressMeFragment;
import com.yidu.sevensecondmall.fragments.GiftProgress.GiftProgressOneFragment;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.utils.ViewUtils;
import com.yidu.sevensecondmall.views.widget.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/7.
 */

public class GiftProgressActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabStrip)
    PagerSlidingTabStrip tabStrip;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar_right_iv)
    ImageView toolbarRightIv;
    private MyAdapter adapter;

    private GiftProgressAllFragment giftProgressAllFragment;
    private GiftProgressOneFragment giftProgressOneFragment;
    private GiftProgressMeFragment giftProgressMeFragment;
    private GiftProgressCompleteFragment giftProgressCompleteFragment;
    String[] titles = {"赠品进度", "我的加速", "参与加速", "进度完成"};

    @Override
    protected int setViewId() {
        return R.layout.activity_gift_progress;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("赠品");
        toolbarRightIv.setVisibility(View.VISIBLE);
        toolbarRightIv.setImageDrawable(getResources().getDrawable(R.drawable.question));
        toolbarRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹框
                showPop();
            }
        });

        tabStrip.setSelectTextColor(getResources().getColor(R.color.app_theme));
        adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setOffscreenPageLimit(titles.length);
        viewpager.setAdapter(adapter);
        tabStrip.setViewPager(viewpager);
    }

    @Override
    protected void initEvent() {
        showPop();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    private Fragment getGiftProgressAllFragment() {
        if (giftProgressAllFragment == null) {
            giftProgressAllFragment = new GiftProgressAllFragment().getInstance("0");
        }
        return giftProgressAllFragment;
    }

    private Fragment getGiftProgressMeFragment() {
        if (giftProgressMeFragment == null) {
            giftProgressMeFragment = new GiftProgressMeFragment().getInstance("1");
        }
        return giftProgressMeFragment;
    }

    private Fragment getGiftProgressOneFragment() {
        if (giftProgressOneFragment == null) {
            giftProgressOneFragment = new GiftProgressOneFragment().getInstance("2");
        }
        return giftProgressOneFragment;
    }


    private Fragment getGiftProgressCompleteFragment() {
        if (giftProgressCompleteFragment == null) {
            giftProgressCompleteFragment = new GiftProgressCompleteFragment().getInstance("3");
        }
        return giftProgressCompleteFragment;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    giftProgressAllFragment = (GiftProgressAllFragment) getGiftProgressAllFragment();
                    return giftProgressAllFragment;
                case 1:
                    giftProgressMeFragment = (GiftProgressMeFragment) getGiftProgressMeFragment();
                    return giftProgressMeFragment;
                case 2:
                    giftProgressOneFragment = (GiftProgressOneFragment) getGiftProgressOneFragment();
                    return giftProgressOneFragment;
                case 3:
                    giftProgressCompleteFragment = (GiftProgressCompleteFragment) getGiftProgressCompleteFragment();
                    return giftProgressCompleteFragment;
            }

//            giftProgressOneFragment = (GiftProgressOneFragment) getGiftProgressOneFragment();
//
//     return giftProgressOneFragment;
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private void showPop() {
        ViewUtils.pop_img(this, null, new ViewUtils.ButtonCallback() {
            @Override
            public void onPositive(Dialog d) {

            }

            @Override
            public void onNegative(Dialog d) {
            }
        }).show();
    }

}
