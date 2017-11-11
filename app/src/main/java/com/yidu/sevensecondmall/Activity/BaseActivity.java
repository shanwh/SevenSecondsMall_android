package com.yidu.sevensecondmall.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Order.OrderExActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.BankCardActivity;
import com.yidu.sevensecondmall.JPush.ExampleUtil;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.ActionEvent;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.PermissionUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/9.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String MESSAGE_RECEIVED_ACTION = "com.yidu.sevensecondmall.MESSAGE_RECEIVED_ACTION";
    public static final String FREE_MESSAGE_RECEIVED_ACTION = "com.yidu.sevensecondmall.FREE_MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private MessagesReceiver mMessageReceiver;

    private AlertDialog show;
    private static BaseActivity instance;
    private TelephonyManager telephonyManager;
    private String deviceId = "";

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewId());
        initToolbar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        registerMessageReceiver();
        findViews();
        handlerSaveInstanceState(savedInstanceState);
        initEvent();
        init();
        loadData();
//        hyalinize();
//        AlphTitle();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    protected void onEvent(String event) {

    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationIcon(R.drawable.back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //实现状态栏图标和文字颜色为暗色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    protected void setEventBus() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /***
     * 处理saveInstanceState
     ***/
    protected void handlerSaveInstanceState(Bundle savedInstanceState) {

    }


    /**
     * 设置布局id
     *
     * @return
     */
    protected abstract int setViewId();

    /**
     * 初始化控件
     */
    protected abstract void findViews();

    /**
     * 初始化控件的点击事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void init();

    /**
     * 加载数据
     */
    protected abstract void loadData();


    /**
     * 沉浸式
     **/
    protected void hyalinize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    private void AlphTitle() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //隐藏listview
    public boolean isHideListView(View v) {
        if (v != null && v instanceof ListView) {
            if (v.getVisibility() == View.VISIBLE) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();

        //config.setToDefaults();
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    protected void registerMessageReceiver() {
        mMessageReceiver = new MessagesReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        filter.addAction(FREE_MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessagesReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                String msgtitle = "";

                if (intent.hasExtra("title")) {
                    msgtitle = intent.getStringExtra("title");
                }

                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                if (msgtitle.equals("异地登陆")) {
                    if (LoginUtils.isLogin()) {
                        if (!SystemUtil.getSharedBoolean("ReceiveLoginError", false)) {
                            show = new AlertDialog.Builder(BaseActivity.this).setTitle("异地登录提醒,请重新登录")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setCancelable(false)
                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            LoginUtils.setUserId("");
                                            LoginUtils.setToken("");
                                            LoginUtils.setUsername("");
                                            LoginUtils.setPassword("");
                                            LoginUtils.setLoginOpenid("");
                                            LoginUtils.setIsLogin(false);
                                            EventBus.getDefault().post(new LoginEvent(IEventTag.LOGINOUT));
                                            SystemUtil.setSharedBoolean("ReceiveLoginError", true);
                                            EventBus.getDefault().post(new ActionEvent(IEventTag.HIDE_LOGOUT_DIALOG));

                                            EventBus.getDefault().post(new LoginEvent(IEventTag.CLOSE));
                                        }
                                    })
                                    .show();
                        }
                    }
                }

//                if ("Xiaomi".equals(Build.MANUFACTURER)) {//小米手机
//                    Log.e("机型", "小米手机");
//                    requestPermission();
//                } else if ("Meizu".equals(Build.MANUFACTURER)) {//魅族手机
//                    Log.e("机型", "魅族手机");
//                    requestPermission();
//                } else {//其他手机
//                    Log.e("机型", "其他手机");
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        if (!Settings.canDrawOverlays(MainsActivity.this)) {
//                            Intent intent3 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                            startActivityForResult(intent3, 12);
//                        } else {
//                            createFloatView(msgtitle,userid,teamid,matchid);
//                        }
//                    } else {
//                        createFloatView(msgtitle,userid,teamid,matchid);
//                    }
//                }
            } else if (FREE_MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String msgtitle = "";

                if (intent.hasExtra("title")) {
                    msgtitle = intent.getStringExtra("title");
                }

                String msg = "";
                String name = "";
                String head = "";
                switch (msgtitle) {
                    case "1":
                        msg = "领到了赠品";
                        name = "恭喜用户" + intent.getStringExtra("name");
                        head = intent.getStringExtra("icon");
                        break;
                    case "2":
                        msg = "参与促销了";
                        name = "用户" + intent.getStringExtra("name");
                        head = intent.getStringExtra("icon");
                        break;
                }
                if (LoginUtils.getSwitch()) {
                    showMsg(head, name, msg);
                }
            }

        }
    }

    public void showMsg(String head, String name, String msg) {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (SystemUtil.getSharedBoolean("ReceiveLoginError", false)) {
            if (show != null) {
                show.dismiss();
            }
        }
        super.onResume();
    }

    public void showShortToast(String str) {
        ToastUtil.showToast(BaseActivity.this, str);
    }

    public void showLongToast(String str) {
        ToastUtil.showToast(BaseActivity.this, str, true);
    }

    public String getDeviceId() {
        try {
            if (PermissionUtil.isLacksOfPermission(PermissionUtil.PERMISSION[0])) {
                ActivityCompat.requestPermissions(this, PermissionUtil.PERMISSION, 0x12);
            } else {
                if (telephonyManager != null) {
                    deviceId = telephonyManager.getDeviceId();//String
                    SystemUtil.setSharedString("deviceId", deviceId);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(ActionEvent event) {
        switch (event.founctionTag) {
            case IEventTag.HIDE_LOGOUT_DIALOG:
                show.dismiss();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(LoginEvent event) {
        Intent intent;
        switch (event.founctionTag) {

            case IEventTag.CLOSE:
                BaseActivity.this.finish();
//                intent = new Intent(SystemUtil.getContext(), MainActivity.class);
                intent = new Intent(SystemUtil.getContext(), NewMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                SystemUtil.getContext().startActivity(intent);
                break;
            case IEventTag.EXIT:

                break;
            case IEventTag.TO_ORDER:
                intent = new Intent(SystemUtil.getContext(), OrderExActivity.class);
                intent.putExtra("clickIndex", 1);
                startActivity(intent);
                break;
        }
    }
}
