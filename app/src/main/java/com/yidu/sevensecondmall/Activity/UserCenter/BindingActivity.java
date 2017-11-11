package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.NewMainActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/5/31.
 */
public class BindingActivity extends BaseActivity {

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
    @BindView(R.id.delete)
    IconFontTextView delete;
    @BindView(R.id.check)
    TextView check;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.success_content)
    LinearLayout successContent;
    @BindView(R.id.content)
    LinearLayout content;

    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
    @BindView(R.id.tel_edit)
    EditText telEdit;
    @BindView(R.id.check_edit)
    EditText checkEdit;
    //    @BindView(R.id.title_login)
//    RelativeLayout titleLogin;
    @BindView(R.id.et_invited)
    EditText etInvited;
    @BindView(R.id.ll_first_bind)
    View ll_first_bind;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private String countryCode = "86";
    private final static int RESULT_COMPLETE = 0;
    private final static int SUBMIT_VERIFICATION_CODE = 1;
    private final static int GET_VERIFICATION_CODE = 2;
    private final static int EVENT_GET_SUPPORTED_COUNTRIES = 4;

    public Handler mHandler;
    public static int TIMESTATUS = 0;
    public static int TIMESTOP = 1;
    public static int TIMEREADY = 0;
    public static int TIMERUNNING = 2;
    public static int SECONDS = 59;
    public boolean send = false;

    public Runnable timerun = new Runnable() {
        @Override
        public void run() {
            //计时停止，倒数开始
            if (TIMESTATUS != TIMESTOP) {
                TIMESTATUS = TIMERUNNING;
                if (SECONDS >= 0) {
                    check.setText("倒计时" + SECONDS);
                    SECONDS--;
                    mHandler.postDelayed(this, 1000);
                } else {
                    check.setText("重新获取");
                    TIMESTATUS = TIMEREADY;
                    mHandler.removeCallbacks(this);
                    SECONDS = 59;
                    send = false;
                }


            }
        }
    };

    public EventHandler eventhandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                handler.sendEmptyMessage(RESULT_COMPLETE);
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    handler.sendEmptyMessage(GET_VERIFICATION_CODE);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    handler.sendEmptyMessage(EVENT_GET_SUPPORTED_COUNTRIES);
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT_COMPLETE:
                    Log.e("RESULT_COMPLETE", "RESULT_COMPLETE");
//                    Toast.makeText(BindingActivity.this, "验证码回调成功", Toast.LENGTH_SHORT).show();
                    break;
                case SUBMIT_VERIFICATION_CODE:
                    Log.e("SUBMIT", "SUBMIT_VERIFICATION_CODE");
//                    Toast.makeText(BindingActivity.this, "提交验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE:
                    Log.e("GET", "GET_VERIFICATION_CODE");
                    Toast.makeText(BindingActivity.this, "获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                case EVENT_GET_SUPPORTED_COUNTRIES:
                    Log.e("COUNTRIES", "EVENT_GET_SUPPORTED_COUNTRIES");
                    break;
            }
        }
    };

    @Override
    protected int setViewId() {
        return R.layout.biinding_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("绑定手机号");
        toolbarTitle.setText("绑定手机号");
    }

    @Override
    protected void initEvent() {
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
        mHandler = new Handler();
    }

    @Override
    protected void init() {
        if (SystemUtil.getSharedBoolean("isFirstBand", false)) {
            String invited_mob = SystemUtil.getSharedString("invited_mob");
            if (invited_mob != null) {
                String[] split = invited_mob.split("");
                int length = split.length;
                String result = "";
                for (int i = 0; i < 4; i++) {
                    String s = split[i];
                    result = result + s;
                }
                result = result + "****";
                for (int i = 0; i < 4; i++) {
                    String s = split[length - 4 + i];
                    result = result + s;
                }
                etInvited.setText(result);

                etInvited.setEnabled(false);
            }
            ll_first_bind.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadData() {

    }

    private static final String TAG = "BindingActivity";

    @OnClick({R.id.back_button, R.id.check, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.check:
                MobSDK.init(this);
                if (TIMESTATUS == TIMEREADY || TIMESTATUS == TIMESTOP) {
                    mHandler.postDelayed(timerun, 1000);
                    if (!send) {
                        if (telEdit.getText().toString().trim().length() != 0) {
                            SMSSDK.getVerificationCode(countryCode, telEdit.getText().toString());
                            send = true;
                        } else {
                            showShortToast("请输入手机号码");
                        }
                    }

                } else {
                    showShortToast("请等待短信");
                }

                break;
            case R.id.submit:
                if (checkMessage()) {
                    if (SystemUtil.getSharedBoolean("isFirstBand", false)) {
                        UserDAO.FirstBindMobile(telEdit.getText().toString(), checkEdit.getText().toString(), etInvited.getText().toString(), new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                Log.e(TAG, "success: data" + data);
                                successContent.setVisibility(View.VISIBLE);
                                content.setVisibility(View.INVISIBLE);
//                                Intent intent = new Intent(BindingActivity.this, MainActivity.class);
                                Intent intent = new Intent(BindingActivity.this, NewMainActivity.class);
                                SystemUtil.setSharedBoolean("toMy", true);
                                intent.putExtra("toMy", true);
                                BindingActivity.this.startActivity(intent);
                                BindingActivity.this.finish();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast("" + data);
                                LoginUtils.setIsLogin(false);
                                EventBus.getDefault().post(new LoginEvent(IEventTag.LOGINOUT));
                            }
                        });
                    } else {
                        UserDAO.ChangeMobile(telEdit.getText().toString(), checkEdit.getText().toString(), new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                successContent.setVisibility(View.VISIBLE);
                                content.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                Toast.makeText(BindingActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        SMSSDK.unregisterEventHandler(eventhandler);
        SystemUtil.setSharedString("invited_mob", null);
        if (mHandler != null) {
            mHandler.removeCallbacks(timerun);
        }
        super.onDestroy();

    }

    private boolean checkMessage() {
        if (telEdit.getText().length() == 0) {
            showShortToast("请输入手机号");
            return false;
        }
        if (checkEdit.getText().length() == 0) {
            showShortToast("请输入短信验证码");
            return false;
        }
        if (SystemUtil.getSharedBoolean("isFirstBand", false)) {
            if (etInvited.getText().length() == 0) {
                showShortToast("请输入推荐人号码");
                return false;
            }
        }
        return true;
    }

}
