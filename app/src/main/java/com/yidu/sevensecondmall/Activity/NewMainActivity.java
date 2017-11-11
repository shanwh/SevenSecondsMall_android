package com.yidu.sevensecondmall.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.fragments.BuyCarFragment;
import com.yidu.sevensecondmall.fragments.GameFragment;
import com.yidu.sevensecondmall.fragments.MyFragment;
import com.yidu.sevensecondmall.fragments.Store.MainFragment;
import com.yidu.sevensecondmall.fragments.VideoFragment;
import com.yidu.sevensecondmall.i.ActionEvent;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/27.
 */

public class NewMainActivity extends BaseActivity {

    @BindView(R.id.fragment_main)
    FrameLayout fragmentMain;
    @BindView(R.id.rb_0)
    RadioButton rb0;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    RadioGroup radioGroup;

    //
    private FragmentManager manager;
    private int currentTab;

    private MainFragment mainFragment;
    private GameFragment gameFragment;
    private BuyCarFragment buyCarFragment;
    private MyFragment myFragment;

    @Override
    protected int setViewId() {
        return R.layout.activity_new_main;
    }


    @Override
    protected void findViews() {
        if (LoginUtils.getToken() != null && !LoginUtils.getToken().equals("")) {
            Log.e("token==============", LoginUtils.getToken());
        }
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        manager = getSupportFragmentManager();
        radioGroup.check(R.id.rb_0);
        setFragment(R.id.rb_0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setFragment(checkedId);
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    private void setFragment(int index) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        hideFragment(fragmentTransaction);
        if (index == currentTab) {
            return;
        }
        currentTab = index;
        switch (index) {
            case R.id.rb_0:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    fragmentTransaction.add(R.id.fragment_main, mainFragment);
                } else {
                    fragmentTransaction.show(mainFragment);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                }
                radioGroup.check(R.id.rb_0);
                break;
            case R.id.rb_1:
                if (gameFragment == null) {
                    gameFragment = new GameFragment();
                    fragmentTransaction.add(R.id.fragment_main, gameFragment);
                } else {
                    fragmentTransaction.show(gameFragment);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                radioGroup.check(R.id.rb_1);
                break;
            case R.id.rb_2:

                if (buyCarFragment == null) {
                    buyCarFragment = new BuyCarFragment();
                    fragmentTransaction.add(R.id.fragment_main, buyCarFragment);
                } else {
                    fragmentTransaction.show(buyCarFragment);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                radioGroup.check(R.id.rb_2);
                break;
            case R.id.rb_3:
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.fragment_main, myFragment);
                } else {
                    fragmentTransaction.show(myFragment);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                radioGroup.check(R.id.rb_3);
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mainFragment != null) {
            fragmentTransaction.hide(mainFragment);
        }
        if (gameFragment != null) {
            fragmentTransaction.hide(gameFragment);
        }

        if (buyCarFragment != null) {
            fragmentTransaction.hide(buyCarFragment);
        }
        if (myFragment != null) {
            fragmentTransaction.hide(myFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemUtil.getSharedBoolean("toCart", false)) {
            setFragment(R.id.rb_2);
            SystemUtil.setSharedBoolean("toCart", false);
        }
        if (SystemUtil.getSharedBoolean("toMy", false)) {
            setFragment(R.id.rb_3);
            SystemUtil.setSharedBoolean("toMy", false);
        }
        if (SystemUtil.getSharedBoolean("ReceiveLoginError", false)) {
            SystemUtil.setSharedBoolean("ReceiveLoginError", false);
        }

    }

    @Override
    public void handler(LoginEvent event) {
        switch (event.founctionTag) {
            case IEventTag.CLOSE:
                Log.e("NewMainActivity", "草");
                break;
            case IEventTag.SKIP_TO_HOME:
                setFragment(R.id.rb_0);
                break;

        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                EventBus.getDefault().post(new LoginEvent(IEventTag.CLOSE));
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
