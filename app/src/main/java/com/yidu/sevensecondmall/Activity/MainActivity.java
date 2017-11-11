package com.yidu.sevensecondmall.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Order.FeeShoppingActivity;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.MessageActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.allSettingActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.QrCode.QrCodeActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.fragments.BuyCarFragment;
import com.yidu.sevensecondmall.fragments.MallFragmentCopy;
import com.yidu.sevensecondmall.fragments.MyFragment;
import com.yidu.sevensecondmall.fragments.VideoFragment;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IBottom;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.DES;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.PermissionUtil;
import com.yidu.sevensecondmall.utils.UpdateManager;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.yidu.sevensecondmall.R.id.rl_title;

public class MainActivity extends BaseActivity {


    @BindView(R.id.home_msg)
    IconFontTextView logo;
    @BindView(R.id.iv_default)
    IconFontTextView ivDefault;
    //    @BindView(R.id.search_edit)
//    EditText searchEdit;
    @BindView(R.id.layout_default)
    LinearLayout layoutDefault;
    @BindView(R.id.edit_linear)
    RelativeLayout editLinear;
    @BindView(R.id.zxing)
    IconFontTextView zxing;
    @BindView(R.id.if_tv_msg)
    IconFontTextView ifTvMsg;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(rl_title)
    public RelativeLayout rlTitle;
    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.if_tv_video)
    IconFontTextView ifTvVideo;
    //    @BindView(R.id.if_tv_sort)
//    IconFontTextView ifTvSort;
    @BindView(R.id.if_tv_buy_car)
    IconFontTextView ifTvBuyCar;
    @BindView(R.id.if_tv_my)
    IconFontTextView ifTvMy;
    @BindView(R.id.ll_bottom_tab)
    LinearLayout llBottomTab;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.container)
    LinearLayout container;
    //    @BindView(R.id.draw_layout)
//    DrawerLayout drawLayout;

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_inform_name)
    TextView tvInformName;
    @BindView(R.id.tv_inform_msg)
    TextView tvInformMsg;
    @BindView(R.id.ll_inform)
    LinearLayout llInform;
    @BindView(R.id.rl_inform)
    RelativeLayout rlInform;
    //    @BindView(R.id.iv_home)
//    ImageView iv_home;
    @BindView(R.id.if_tv_setting)
    IconFontTextView if_tv_setting;
    @BindView(R.id.if_tv_message)
    IconFontTextView if_tv_message;
    @BindView(R.id.v)
    public View v;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_bubble_msg)
    TextView tvBubbleMsg;
    @BindView(R.id.tv_msg_more)
    TextView tvMsgMore;
    @BindView(R.id.if_tv_home)
    IconFontTextView ifTvHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    //    @BindView(R.id.tv_sort)
//    TextView tvSort;
//    @BindView(R.id.rl_sort)
//    RelativeLayout rlSort;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.rl_my)
    RelativeLayout rlMy;
    @BindView(R.id.rl_to_top)
    RelativeLayout rl_to_top;

    private boolean isChange = true;

    private ArrayList<IconFontTextView> ifTvList = new ArrayList<>();
    private ArrayList<TextView> tvList = new ArrayList<>();
    //    private DrawerLayout drawerLayout;
//    private NavigationView navigationView;
    private IconFontTextView headIcon;
    //    private EditText editText;
//    private LinearLayout textlayout;
//    private PopupWindow popupWindow;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private MallFragmentCopy mallFragment;
//    private MainFragment mainFragment;

    //    private SortFragment sortFragment;
    private VideoFragment videoFragment;
    private BuyCarFragment buyCarFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    //    private View headView;
    private ImageView img_head;
    private UserBean bean;
    private boolean click = false;
//    private MessagesReceiver mMessageReceiver;

    /* 当前显示的是第几个fragment */
    int currentFragmentIndex = 0;
    private int clickIndex = 0;

    private static final String HOME_TAG = "home_tag";
    private static final String SORT_TAG = "sort_tag";
    private static final String VIDEO_TAG = "video_tag";
    private static final String BUY_CART_TAG = "circles_tag";
    private static final String MY_TAG = "ranking_tag";
    private Fragment[] fragments;
    private Fragment showFragment;

    private FragmentManager fmSlidng;
    private FragmentTransaction ftSliding;
    private String rid = "";

    private String[] tags = new String[]{HOME_TAG, VIDEO_TAG, BUY_CART_TAG, MY_TAG};
//    private String[] tags = new String[]{HOME_TAG, VIDEO_TAG, SORT_TAG, BUY_CART_TAG, MY_TAG};

    private String[] titles = new String[]{"商品", "直播", "购物车", "我的"};
//    private String[] titles = new String[]{"商品", "直播", "分类", "购物车", "我的"};


    private static MainActivity instance;

    private long exitTime = 0;
    private TelephonyManager telephonyManager;
    private String deviceId = "";

    private boolean isShowIcon;


    private boolean showLayout = true;

    private boolean isFirst = true;

    /**
     * 声音和振动相关参数
     */
    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    private MediaPlayer mMediaPlayer;
    private boolean mPlayBeep;
    private boolean mVibrate;

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        ifTvList.add(ifTvHome);
        ifTvList.add(ifTvVideo);
//        ifTvList.add(ifTvSort);
        ifTvList.add(ifTvBuyCar);
        ifTvList.add(ifTvMy);
        tvList.add(tvHome);
        tvList.add(tvVideo);
//        tvList.add(tvSort);
        tvList.add(tvCart);
        tvList.add(tvMy);
        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
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

    boolean workThred = true;

    @Override
    protected void initEvent() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showLayout) {
                    try {
                        Thread.sleep(5000);
                        if (currentFragmentIndex == 0) {
                            EventBus.getDefault().post(new LoginEvent(IEventTag.SHOW_LAYOUT));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    protected void handlerSaveInstanceState(Bundle savedInstanceState) {
        fmSlidng = getSupportFragmentManager();
        ftSliding = fmSlidng.beginTransaction();
        if (savedInstanceState != null) {
            mallFragment = (MallFragmentCopy) fmSlidng.findFragmentByTag(tags[0]);
//            mainFragment = (MainFragment) fmSlidng.findFragmentByTag(tags[0]);

            videoFragment = (VideoFragment) fmSlidng.findFragmentByTag(tags[1]);
//            sortFragment = (SortFragment) fmSlidng.findFragmentByTag(tags[2]);
            buyCarFragment = (BuyCarFragment) fmSlidng.findFragmentByTag(tags[2]);
            myFragment = (MyFragment) fmSlidng.findFragmentByTag(tags[3]);


            //初始化，主页界面
            if (mallFragment == null)
                mallFragment = new MallFragmentCopy();
//            if (mainFragment == null)
//                mainFragment = new MainFragment();
//            if (sortFragment == null)
//                sortFragment = new SortFragment();
            if (videoFragment == null)
                videoFragment = new VideoFragment();
            if (buyCarFragment == null)
                buyCarFragment = new BuyCarFragment();
            if (myFragment == null)
                myFragment = new MyFragment();

            fragments = new Fragment[]{
                    mallFragment,
//                    mainFragment,
                    videoFragment,
//                    sortFragment,
                    buyCarFragment,
                    myFragment
            };
            for (int i = 0; i < fragments.length; i++) {
                if (!fragments[i].isAdded()) {
                    ftSliding.add(R.id.container, fragments[i], tags[i]);
                }
            }
            ftSliding
                    .show(mallFragment)
//                    .hide(sortFragment)
//                    .show(mainFragment)
                    .hide(videoFragment)
                    .hide(buyCarFragment)
                    .hide(myFragment)
                    .commitAllowingStateLoss();
        } else {
            initFragment();
        }
        super.handlerSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void init() {
        instance = MainActivity.this;

        zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                    if (!PermissionUtil.hasPermission(MainActivity.this, writeExternalStorage)) {
                        PermissionUtil.reqPermission(MainActivity.this, SCANNIN_GREQUEST_CODE, new String[]{writeExternalStorage});
                    } else {
                        //有权限，直接拍照
                        startActivity(new Intent(MainActivity.this, QrCodeActivity.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        editLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this, QrCodeActivity.class));
                }
                break;
        }
    }

    @Override
    protected void loadData() {
        autoLogin();
        UserDAO.appImgList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("flash_img")) {
                        String fastBgUrl = content.getString("flash_img");

                        try {
                            FutureTarget<File> future = Glide.with(SystemUtil.getContext())
                                    .load(HttpApi.getFullImageUrl(fastBgUrl))
                                    .downloadOnly(500, 500);
                            File cacheFile = future.get();
                            SystemUtil.setSharedString("fastBgUrl", fastBgUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
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

    @Override
    public void onRestart() {
        super.onRestart();
        if (LoginUtils.isLogin()) {
            EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));
        }
    }

    public void initFragment() {
        try {
            //初始化，主页界面
            mallFragment = new MallFragmentCopy();
//            mainFragment = new MainFragment();
            videoFragment = new VideoFragment();
//            sortFragment = new SortFragment();
            buyCarFragment = new BuyCarFragment();
            myFragment = new MyFragment();

            fragments = new Fragment[]{
                    mallFragment,
//                    mainFragment,
                    videoFragment,
//                    sortFragment,
                    buyCarFragment,
                    myFragment
            };
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mallFragment, tags[0])
//                    .add(R.id.container,mainFragment,tags[0])
                    .add(R.id.container, videoFragment, tags[1])
                    .show(mallFragment)
//                    .show(mainFragment)
                    .hide(videoFragment)
                    .commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO: 2017/9/12
        if (clickIndex == 0) {
            changeTitleColor(true);
            changeText(true, false, false, false);
            ifTvList.get(0).setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.app_theme));
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(LoginEvent event) {
        switch (event.founctionTag) {

            case IEventTag.SKIP_TO_HOME:
                clickIndex = 0;
                changeFragment();
                break;
            case IEventTag.HIDE_LAYOUT:
                llInform.setVisibility(View.GONE);
                break;
            case IEventTag.FIND_FIRST_VIEW_POSITION:
                isShowIcon = event.page != 0;
                if (isShowIcon) {
                    rl_to_top.setVisibility(View.VISIBLE);
                } else {
                    rl_to_top.setVisibility(View.GONE);
                }
                break;
            case IEventTag.AUTO_LOGIN:
                autoLogin();
                break;
            case IEventTag.SHOW_LAYOUT:
                checkInfo();
                break;
            case IEventTag.CLOSE:

                break;
            case IEventTag.HIDE_AND_SCREEN:
//                llBottomTab.setVisibility(View.GONE);
//                v.setVisibility(View.GONE);
//                rlTitle.setVisibility(View.GONE);
                // TODO: 2017/9/9
                break;
            case IEventTag.SHOW_SCREEN:
//                llBottomTab.setVisibility(View.VISIBLE);
//                rlTitle.setVisibility(View.VISIBLE);
//                v.setVisibility(View.VISIBLE);
                break;
//            case IEventTag.BACKGROUND_NULL:
//                rlTitle.getBackground().setAlpha(0);
//                v.getBackground().setAlpha(0);
//                break;
//            case IEventTag.BACKGROUND:
//                rlTitle.getBackground().setAlpha(255);
//                v.getBackground().setAlpha(255);
//                break;
        }
    }

    private void checkInfo() {
        if (headList.size() != 0) {
            playBeepSoundAndVibrate();
            llInform.setVisibility(View.VISIBLE);
            setInfo(headList.remove(0), nameList.remove(0), msgList.remove(0));
        } else {
            llInform.setVisibility(View.GONE);
        }
    }

    private void setInfo(String head, String name, String msg) {
        Glide.with(MainActivity.this)
                .load(HttpApi.getFullImageUrl(head))
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.default_loading_pic)
                .transform(new GlideCircleTransform(MainActivity.this))
                .into(ivHead);
        tvInformName.setText(name);
        tvInformMsg.setText(msg);
    }

    private void autoLogin() {
        //设置fragment手机登录
        if (!LoginUtils.isLogin()) {
            if (LoginUtils.getLoginPlatform().equals(LoginUtils.PlatformPhone)) {
                if (LoginUtils.getUsername() != null && LoginUtils.getPassword() != null &
                        !"".equals(LoginUtils.getUsername()) && !"".equals(LoginUtils.getPassword())) {
                    rid = JPushInterface.getRegistrationID(MainActivity.this);
                    String password = DES.decryptDES(LoginUtils.getPassword(), "48158222");
                    String username = DES.decryptDES(LoginUtils.getUsername(), "48158222");
                    UserDAO.Login(username, password, rid, new BaseCallBack() {
                        @Override
                        public void success(Object data) {
//                        Toast.makeText(MainActivity.this, "自动登录成功", Toast.LENGTH_SHORT).show();
                            LoginUtils.setIsLogin(true);
                            LoginUtils.setRegistration_id(rid);
                            EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
//                            1012
                            showShortToast(data + "");
                        }
                    });
                }
                //第三方自动登录
            } else {
                if (LoginUtils.getLoginOpenid() != null && !"".equals(LoginUtils.getLoginOpenid())) {
                    rid = JPushInterface.getRegistrationID(MainActivity.this);
                    UserDAO.LoginThird(LoginUtils.getLoginOpenid(), "Wechat", rid, new BaseCallBack() {
                        @Override
                        public void success(Object data) {
//                        Toast.makeText(MainActivity.this, "第三方自动登录成功", Toast.LENGTH_SHORT).show();
                            LoginUtils.setIsLogin(true);
                            LoginUtils.setRegistration_id(rid);
                            EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));

                            String uid = LoginUtils.getLoginOpenid();
                            String mobile;
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("token")) {
                                    LoginUtils.setToken(content.getString("token"));
                                    LoginUtils.setIsLogin(true);
                                }
                                if (content.has("mobile")) {
                                    mobile = content.getString("mobile");
                                    if (mobile.equals("")) {
                                        SystemUtil.setSharedBoolean("isFirstBand", true);
                                        EventBus.getDefault().post(new LoginEvent(IEventTag.LOGINOUT));
                                    } else {
                                        LoginUtils.setIsLogin(true);
                                        LoginUtils.setLoginOpenid(uid);
                                        SystemUtil.setSharedBoolean("isFirstBand", false);
                                        EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));
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

            }
        }

    }


    @OnClick({
            R.id.tv_edit, R.id.if_tv_msg, R.id.rl_inform, R.id.if_tv_setting, R.id.if_tv_message, R.id.home_msg,
            R.id.rl_home, R.id.rl_video, R.id.rl_cart, R.id.rl_my, R.id.rl_to_top
            //, R.id.rl_sort
    })
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_to_top:
                EventBus.getDefault().post(new LoadDataEvent(IEventTag.SCROLL_TO_TOP));
                rl_to_top.setVisibility(View.GONE);
                break;
            case R.id.tv_edit:
                if (buyCarFragment != null) {
                    buyCarFragment.openEdit();
                }
                break;
            case R.id.if_tv_msg:
            case R.id.home_msg:
            case R.id.if_tv_message:
                if (LoginUtils.isLogin()) {
                    intent = new Intent(this, MessageActivity.class);
                    startActivity(intent);
                } else {
                    Intent login = new Intent(this, LoginActivity.class);
                    startActivity(login);
                }
                break;
            case R.id.rl_inform:
                if (LoginUtils.isLogin()) {
                    intent = new Intent(this, FeeShoppingActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.if_tv_setting:
                if (LoginUtils.isLogin()) {
                    intent = new Intent(this, allSettingActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_home:
                clickIndex = 0;
                break;
            case R.id.rl_video:
                clickIndex = 1;
                break;
//            case R.id.rl_sort:
//                clickIndex = 2;
//                break;
            case R.id.rl_cart:
                clickIndex = 2;
                break;
            case R.id.rl_my:
                clickIndex = 3;
                break;
        }

        changeFragment();
    }

    public void changeTitleColor(boolean isFirestPag) {
        rlTitle.setBackgroundColor(isFirestPag ? Color.parseColor("#00000000") : Color.parseColor("#eb8a2f"));
        v.setBackgroundColor(isFirestPag ? Color.parseColor("#00000000") : Color.parseColor("#eb8a2f"));
//        layoutDefault.setBackground(getResources().getDrawable(isFirestPag?R.drawable.round_editext_grad:R.drawable.round_edittext));

    }

    public void changeFragment() {
        if (clickIndex != currentFragmentIndex) {
            switch (clickIndex) {
                case 0:
                    if (null != rlTitle.getTag()) {
                        changeTitleColor(!rlTitle.getTag().toString().equals("2"));
                    }
                    showHomeIcon();
                    break;
                case 1:
                    changeTitleColor(false);
                    showLiveIcon();
                    break;
//                case 2:
//                    showSortIcon();
//                    break;
                case 2:
                    changeTitleColor(false);
                    showBuyCarIcon();
                    break;
                case 3:
                    changeTitleColor(false);
                    showMyIcon();
                    break;
            }
            // 单击别的按钮，显示另外一个fragment
            showFragment = fragments[clickIndex];
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!showFragment.isAdded()) {
                // 以前没有显示过
                transaction.add(R.id.container, showFragment, tags[clickIndex]);
            }
            transaction.hide(fragments[currentFragmentIndex])
                    .show(showFragment)
                    .commitAllowingStateLoss();


            ifTvList.get(clickIndex).setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.app_theme));
//                iv_home.setVisibility(View.GONE);
            rlInform.setVisibility(View.GONE);
            llInform.setVisibility(View.GONE);
            tvList.get(clickIndex).setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.app_theme));
            rl_to_top.setVisibility(View.GONE);

            switch (clickIndex) {
                case 0:
                    changeText(true, false, false, false);
                    break;
                case 1:
                    changeText(false, true, false, false);
                    break;
                case 2:
                    changeText(false, false, true, false);
                    break;
                case 3:
                    changeText(false, false, false, true);
                    break;
            }


            if (clickIndex == 3) {
                v.setVisibility(View.GONE);
                rlTitle.setVisibility(View.GONE);
            } else {
                v.setVisibility(View.VISIBLE);
                rlTitle.setVisibility(View.VISIBLE);
            }
            ifTvList.get(currentFragmentIndex).setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.colorBottomBlack));
            tvList.get(currentFragmentIndex).setTextColor(ContextCompat.getColor(SystemUtil.getContext(), R.color.colorBottomBlack));
            tvTitle.setText(titles[clickIndex]);
            currentFragmentIndex = clickIndex;

        }

    }

    //改变底部字体颜色
    public void changeText(boolean b1, boolean b2, boolean b3, boolean b4) {
        ifTvList.get(0).setText(getResources().getString(b1 ? R.string.icon_home_1_full : R.string.icon_home_1));
        ifTvList.get(1).setText(getResources().getString(b2 ? R.string.icon_live_1_full : R.string.icon_live_1));
        ifTvList.get(2).setText(getResources().getString(b3 ? R.string.icon_shop_cart_full : R.string.icon_shop_cart));
        ifTvList.get(3).setText(getResources().getString(b4 ? R.string.icon_me_full : R.string.icon_me));
    }

    public void showHomeIcon() {
        editLinear.setVisibility(View.VISIBLE);
        zxing.setVisibility(View.VISIBLE);
        ifTvMsg.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.INVISIBLE);
        logo.setVisibility(View.VISIBLE);
        if_tv_setting.setVisibility(View.GONE);
        if_tv_message.setVisibility(View.GONE);
    }

    public void showLiveIcon() {
        editLinear.setVisibility(View.GONE);
        zxing.setVisibility(View.GONE);
        ifTvMsg.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
        if_tv_setting.setVisibility(View.GONE);
        if_tv_message.setVisibility(View.GONE);
    }


    public void showSortIcon() {
        editLinear.setVisibility(View.GONE);
        zxing.setVisibility(View.GONE);
        ifTvMsg.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
        if_tv_setting.setVisibility(View.GONE);
        if_tv_message.setVisibility(View.GONE);
    }

    public void showBuyCarIcon() {
        editLinear.setVisibility(View.GONE);
        zxing.setVisibility(View.GONE);
        ifTvMsg.setVisibility(View.VISIBLE);
        tvEdit.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
        if_tv_setting.setVisibility(View.GONE);
        if_tv_message.setVisibility(View.GONE);
    }

    public void showMyIcon() {
        editLinear.setVisibility(View.GONE);
        zxing.setVisibility(View.GONE);
        ifTvMsg.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.VISIBLE);
        logo.setVisibility(View.INVISIBLE);
        if_tv_setting.setVisibility(View.VISIBLE);
        if_tv_message.setVisibility(View.VISIBLE);
    }


    public static MainActivity getInstance() {
        return instance;
    }


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

    ArrayList<String> headList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> msgList = new ArrayList<>();

    @Override
    public void showMsg(String head, String name, String msg) {
        headList.add(head);
        nameList.add(name);
        msgList.add(msg);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemUtil.getSharedBoolean("toMy", false)) {
            clickIndex = 3;
            changeFragment();
            SystemUtil.setSharedBoolean("toMy", false);
        }
        if (SystemUtil.getSharedBoolean("toCart", false)) {
            clickIndex = 2;
            changeFragment();
            SystemUtil.setSharedBoolean("toCart", false);
        }
        if (SystemUtil.getSharedBoolean("ReceiveLoginError", false)) {
            SystemUtil.setSharedBoolean("ReceiveLoginError", false);
        }

        mPlayBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            mPlayBeep = false;
        }
        initBeepSound();
        mVibrate = true;

        if (popupWindow != null) {
            popupWindow.dissmiss();
        }

        if (isFirst) {
            showDialog();
            isFirst = false;
        }
    }

    private void initBeepSound() {
        if (mPlayBeep && mMediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(mBeepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mMediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mMediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (mPlayBeep && mMediaPlayer != null) {
            mMediaPlayer.start();
        }
        if (mVibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener mBeepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onDestroy() {
        workThred = false;
        showLayout = false;
        super.onDestroy();
    }

    private static final String TAG = "MainActivity";

    private String versionName;
    private String tips;
    private String title;
    private UpdateManager updateManager;
    private String downloadurl;

    private void showDialog() {
        try {
            // ---get the package info---
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            versionName = pi.versionName;

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        UserDAO.startUp(1, versionName, new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("versioninfo")) {
                        JSONObject versioninfo = content.getJSONObject("versioninfo");
                        boolean is_newest = versioninfo.getBoolean("is_newest");
                        if (is_newest) {

                        } else {
                            JSONObject versiondata = versioninfo.getJSONObject("data");
                            downloadurl = versiondata.getString("downloadurl");
                            String newversion = versiondata.getString("newversion");
                            String packagesize = versiondata.getString("packagesize");
//                            String upgradetext = versiondata.getString("upgradetext");
                            tips = "最新版本大小:" + packagesize + "\n";
                            tips = tips + "\n【注意】 如遇到版本升级失败，请到DING官网下载最新版本安装";
//                            if(!upgradetext.isEmpty() && !upgradetext.equals("")){
//                                tips = tips + "\n" + "【优化】 " + upgradetext;
//                            }
                            title = "发现新版本:" + newversion;
                            showPopupWindow();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });

    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
//        if (popupWindow2 != null) popupWindow2.dissmiss();
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_update_layout, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(container, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        TextView tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tv = (TextView) contentView.findViewById(R.id.tv);
        tv.setText(tips);

        contentView.findViewById(R.id.tv_update_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateManager == null) {
                    updateManager = new UpdateManager(MainActivity.this);
                }
                updateManager.setI(new IBottom() {
                    @Override
                    public void isBottom(boolean bottom) {
                        if (bottom) {
                            finish();
                        } else {

                        }
                    }
                });
                if (!TextUtils.isEmpty(downloadurl)) {
                    updateManager.SetApkUrl(downloadurl);
                    updateManager.checkUpdateInfo();
                }
            }
        });
    }
}
