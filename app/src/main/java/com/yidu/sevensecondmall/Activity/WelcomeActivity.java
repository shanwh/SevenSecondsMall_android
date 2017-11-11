package com.yidu.sevensecondmall.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.PermissionUtil;
import com.yidu.sevensecondmall.views.adapter.HomePagerAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private final String VERSION_CODE = "VERSION_CODE";
    private BGABanner banner_guide_content;
    private View go;
    private ImageView welcom;
    private ViewPager vp;
    private String initDataStr = "";
    private TelephonyManager telephonyManager;
    private String deviceId = "";
    private static final String TAG = "WelcomeActivity";
    private LayoutInflater mLayoutInflater;
    private HomePagerAdapter adapter;
    private List<View> childViews = new ArrayList<>();
    private boolean isFirst = true;
    private int[] imgs = new int[]{R.drawable.yindao01, R.drawable.yindao02, R.drawable.yindao03};

    @Override
    protected int setViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void findViews() {
        mLayoutInflater = LayoutInflater.from(this);
        go = findViewById(R.id.go);
        go.setVisibility(View.GONE);
//        banner_guide_content = (BGABanner) findViewById(R.id.banner_guide_content);
        vp = (ViewPager) findViewById(R.id.vp);
        welcom = (ImageView) findViewById(R.id.welcom);
        welcom.setVisibility(View.GONE);
//        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        String deviceId = getDeviceId();
//        Log.e(TAG, "findViews:deviceId " + deviceId);
        SystemUtil.setSharedString("deviceId", deviceId == null ? "unionid001" : deviceId);
    }

    public String getDeviceId() {
        try {
            if (PermissionUtil.isLacksOfPermission(PermissionUtil.PERMISSION[0])) {
                ActivityCompat.requestPermissions(this, PermissionUtil.PERMISSION, 0x12);
            } else {
                if (telephonyManager != null) {
                    deviceId = telephonyManager.getDeviceId();//String
                } else {
                    deviceId = "unionid001";
                }
                return deviceId;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "unionid001";
        }

        return "unionid001";
    }

    @Override
    protected void initEvent() {
        go.setOnClickListener(this);
    }

    @Override
    protected void init() {
        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        int oldVersionCode = SystemUtil.getSharedInt(VERSION_CODE, -1);
        int newVersionCode = SystemUtil.getSystemVersionCode();
        if (isFirst) {
            isFirst = false;
            if (oldVersionCode == -1 || newVersionCode > oldVersionCode) {
                vp.setVisibility(View.VISIBLE);
//            banner_guide_content.setVisibility(View.VISIBLE);
                welcom.setVisibility(View.GONE);
                showGuide();
            } else {
                vp.setVisibility(View.GONE);
//            banner_guide_content.setVisibility(View.GONE);
                welcom.setVisibility(View.VISIBLE);
                Glide.with(WelcomeActivity.this)
                        .load(HttpApi.getFullImageUrl(SystemUtil.getSharedString("fastBgUrl")))
                        .placeholder(R.drawable.fish_bg)
                        .error(R.drawable.fish_bg)
                        .into(welcom);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        jumpToMain();
                    }
                }, 1000);


            }
        }
    }

    private void jumpToMain() {
//        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//        Intent intent = new Intent(WelcomeActivity.this, MainActivityCopy.class);
        Intent intent = new Intent(WelcomeActivity.this, NewMainActivity.class);
        intent.putExtra("initDataInfo", initDataStr);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    private void showGuide() {
//        final List<View> views = new ArrayList<>();
//        views.add(BGABannerUtil.getItemImageView(this, R.drawable.yindao01));
//        views.add(BGABannerUtil.getItemImageView(this, R.drawable.yindao02));
//        views.add(BGABannerUtil.getItemImageView(this, R.drawable.yindao03));
//        banner_guide_content.setData(views);
//        banner_guide_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == views.size() - 1) {
//                    go.setVisibility(View.VISIBLE);
//                } else {
//                    go.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        for (int i = 0; i < imgs.length; i++) {
            childViews.add(mLayoutInflater.inflate(R.layout.item_banner, vp, false));
            ImageView iv = (ImageView) childViews.get(i).findViewById(R.id.iv);
            Glide.with(WelcomeActivity.this)
                    .load(imgs[i])
                    .into(iv);
        }
        adapter = new HomePagerAdapter(childViews);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgs.length-1){
                    go.setVisibility(View.VISIBLE);
                }else {
                    go.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void loadData() {
        initSession();
    }

    private void initSession() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
                jumpToMain();
                SystemUtil.setSharedInt(VERSION_CODE, SystemUtil.getSystemVersionCode());
                break;
        }
    }


}
