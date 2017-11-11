package com.yidu.sevensecondmall.Activity.Login;

import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.EasyPermissionsEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/3/13.
 * 忘记密码界面
 */
public class ForgetActivity extends BaseActivity {

    @BindView(R.id.r_telnum)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText rPw;
    @BindView(R.id.et_password_c)
    EditText rPw2;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.finish)
    TextView finish;

    public Handler mTimeHandler;
    public static int TIMESTATUS = 0;
    public static int TIMESTOP = 1;
    public static int TIMEREADY = 0;
    public static int TIMERUNNING = 2;
    public static int SECONDS = 59;
    public boolean send = false;

    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
    private String countryCode = "86";
    private final static int RESULT_COMPLETE = 0;
    private final static int SUBMIT_VERIFICATION_CODE = 1;
    private final static int GET_VERIFICATION_CODE = 2;
    private final static int EVENT_GET_SUPPORTED_COUNTRIES = 4;

    public Runnable timerun = new Runnable() {
        @Override
        public void run() {
            //计时停止，倒数开始
            if (TIMESTATUS != TIMESTOP) {
                TIMESTATUS = TIMERUNNING;
                if (SECONDS >= 0) {
                    getCode.setText("倒计时" + SECONDS);
                    SECONDS--;
                    mTimeHandler.postDelayed(this, 1000);
                } else {
                    getCode.setText("重新获取");
                    TIMESTATUS = TIMEREADY;
                    mTimeHandler.removeCallbacks(this);
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
                    Log.e("mobile", data.toString());
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
//                    Toast.makeText(RequestActivity.this, "验证码回调成功", Toast.LENGTH_SHORT).show();
                    break;
                case SUBMIT_VERIFICATION_CODE:
                    Log.e("SUBMIT", "SUBMIT_VERIFICATION_CODE");
                    Toast.makeText(ForgetActivity.this, "提交验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE:
                    Log.e("GET", "GET_VERIFICATION_CODE");
                    Toast.makeText(ForgetActivity.this, "获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                case EVENT_GET_SUPPORTED_COUNTRIES:
                    Log.e("COUNTRIES", "EVENT_GET_SUPPORTED_COUNTRIES");
                    break;
            }
        }
    };
    @Override
    protected int setViewId() {
        return R.layout.activity_forgetpw;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        mTimeHandler = new Handler();
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
        etPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.get_code, R.id.finish, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                if (etPhone.getText().length() != 0) {
                    if (TIMESTATUS == TIMEREADY || TIMESTATUS == TIMESTOP) {
                        mTimeHandler.postDelayed(timerun, 1000);
                        if (!send) {
                            SMSSDK.getVerificationCode(countryCode, etPhone.getText().toString());
                            send = true;
                        }
                    } else {
                        showShortToast("请等待短信");
                    }
                }else {
                    showShortToast("请输入手机号码");
                }
                break;
            case R.id.finish:
                if(!EasyPermissionsEx.isNetworkAvailable(this)){
                    Toast.makeText(ForgetActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(rPw.getText().toString().length() < 6 || rPw.getText().toString().length() > 12){
                    Toast.makeText(ForgetActivity.this,"密码长度不正确",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(rPw2.getText().toString().length() < 6 || rPw2.getText().toString().length() > 12){
                    Toast.makeText(ForgetActivity.this,"密码长度不正确",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(!rPw.getText().toString().equals(rPw2.getText().toString())){
                    Toast.makeText(ForgetActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(etCode.getText().toString().trim().length() == 0){
                    Toast.makeText(ForgetActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    break;
                }
                UserDAO.forgetPw(etPhone.getText().toString(), rPw.getText().toString(), rPw2.getText().toString(),
                        etCode.getText().toString(), new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                Toast.makeText(ForgetActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                Toast.makeText(ForgetActivity.this,""+data,Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case R.id.delete:
                etPhone.setText("");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventhandler);
        super.onDestroy();
    }
}
