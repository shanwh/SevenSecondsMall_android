package com.yidu.sevensecondmall.Activity.Store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.fragments.Store.AllGoodsFragment;
import com.yidu.sevensecondmall.fragments.Store.HomeFragment;
import com.yidu.sevensecondmall.fragments.Store.NewGoodsFragment;
import com.yidu.sevensecondmall.fragments.Store.SalesVolumeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/25.
 */
public class StoreHomepageActivity extends BaseActivity {
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.store_photo)
    ImageView storePhoto;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.tv_salesNum)
    TextView tvSalesNum;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv_store_class)
    TextView tvStoreClass;
    @BindView(R.id.tv_store_introduce)
    TextView tvStoreIntroduce;
    @BindView(R.id.ivLine)
    ImageView ivLine;
    @BindView(R.id.rd1)
    RadioButton rd1;
    @BindView(R.id.rd2)
    RadioButton rd2;
    @BindView(R.id.rd3)
    RadioButton rd3;
    @BindView(R.id.rd4)
    RadioButton rd4;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private List<Fragment> fragments = new ArrayList<>();
    private int mScream1_4;

    @Override
    protected int setViewId() {
        return R.layout.activity_store_homepage;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

        //获得屏幕宽度并给1/4设置初始值
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        mScream1_4 = width / 4;
        //设置提示条长度
        ViewGroup.LayoutParams params = ivLine.getLayoutParams();
        params.width = mScream1_4;
        ivLine.setLayoutParams(params);

        fragments.add(new AllGoodsFragment());
        fragments.add(new HomeFragment());
        fragments.add(new NewGoodsFragment());
        fragments.add(new SalesVolumeFragment());
        viewPager.setAdapter(new ViewPageAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new PageChangeListener());
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class ViewPageAdapter extends FragmentPagerAdapter {

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //动态改变提示条的左边距
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivLine.getLayoutParams();
            lp.leftMargin = (int) (positionOffset * mScream1_4) + mScream1_4 * position;
            ivLine.setLayoutParams(lp);

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    rd1.setChecked(true);
                    // rd1.setTextColor(getResources().getColor(R.color.app_theme));
                    break;
                case 1:
                    rd2.setChecked(true);
                    break;
                case 2:
                    rd3.setChecked(true);
                    break;
                case 3:
                    rd4.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @OnClick({R.id.rd1, R.id.rd2, R.id.rd3, R.id.rd4, R.id.tv_store_introduce, R.id.tv_store_class, R.id.back_button})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rd1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rd2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rd3:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rd4:
                viewPager.setCurrentItem(3);
                break;
            case R.id.tv_store_introduce:
                intent = new Intent(StoreHomepageActivity.this, StoreIntroduceActivity.class);
                intent.putExtra("store_id", "12");
                startActivity(intent);
                break;
            case R.id.tv_store_class:
                intent = new Intent(StoreHomepageActivity.this, GoodsClassificationActivity.class);
                intent.putExtra("store_id", "12");
                startActivity(intent);
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }
}
