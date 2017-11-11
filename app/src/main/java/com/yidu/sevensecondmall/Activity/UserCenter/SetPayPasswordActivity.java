package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.PaymentDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
public class SetPayPasswordActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.tv_new_tip)
    TextView tvNewTip;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.tv_con_tip)
    TextView tvConTip;
    @BindView(R.id.et_con)
    EditText etCon;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.ll_next)
    LinearLayout llNext;
    @BindView(R.id.if_tv_success)
    IconFontTextView ifTvSuccess;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private String countryCode = "86";
    private final static int RESULT_COMPLETE = 0;
    private final static int SUBMIT_VERIFICATION_CODE = 1;
    private final static int GET_VERIFICATION_CODE = 2;
    private final static int EVENT_GET_SUPPORTED_COUNTRIES = 4;
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
//                    Toast.makeText(AddBankCardActivity.this,"验证码回调成功",Toast.LENGTH_SHORT).show();
                    break;
                case SUBMIT_VERIFICATION_CODE:
                    Log.e("SUBMIT", "SUBMIT_VERIFICATION_CODE");
//                    Toast.makeText(SetPayPasswordActivity.this, "提交验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE:
                    Log.e("GET", "GET_VERIFICATION_CODE");
                    Toast.makeText(SetPayPasswordActivity.this, "获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                case EVENT_GET_SUPPORTED_COUNTRIES:
                    Log.e("COUNTRIES", "EVENT_GET_SUPPORTED_COUNTRIES");
                    break;
            }
        }
    };

    @Override
    protected int setViewId() {
        return R.layout.activity_set_pay_password;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("设置支付密码");
    }

    @Override
    protected void initEvent() {
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (rlPhone.getVisibility() == View.VISIBLE) {
                    finish();
                } else if (llNext.getVisibility() == View.VISIBLE) {
                    llNext.setVisibility(View.GONE);
                    rlPhone.setVisibility(View.VISIBLE);
                } else if (rlFinish.getVisibility() == View.VISIBLE) {
                    finish();
                }
                break;
            case R.id.tv_sure:
                if (rlPhone.getVisibility() == View.VISIBLE) {
                    if (checkPhone()) {
                        SMSSDK.getVerificationCode(countryCode, etPhoneNumber.getText().toString().trim());
                        rlPhone.setVisibility(View.GONE);
                        llNext.setVisibility(View.VISIBLE);
                    }
                } else if (llNext.getVisibility() == View.VISIBLE) {
                    if (checkMessage()) {
                        String phone = etPhoneNumber.getText().toString();
                        String code = etCode.getText().toString();
                        String password = etNew.getText().toString();
                        String confirm_password = etCon.getText().toString();
                        PaymentDao.setUserMoneyPwd(phone, code, password, confirm_password, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                rlPhone.setVisibility(View.GONE);
                                llNext.setVisibility(View.GONE);
                                tvSure.setVisibility(View.GONE);
                                rlFinish.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                    }
                }
                break;
        }
    }

    private boolean checkPhone() {
        if (etPhoneNumber.getText().length() == 0) {
            showShortToast("请输入手机号");
            return false;
        }
//        if (!isPhoneNumber(etPhoneNumber.getText().toString())) {
//            showShortToast("请输入正确的手机号");
//            return false;
//        }
        return true;
    }

    private boolean checkMessage() {
        if (etCode.getText().length() == 0) {
            showShortToast("请输入您收到的验证码");
            return false;
        }
        if (etNew.getText().length() == 0) {
            showShortToast("请输入密码");
            return false;
        }
        if (etCon.getText().length() == 0) {
            showShortToast("请确认密码");
            return false;
        }
        if (etCon.getText().length() < 6 || etNew.getText().length() < 6) {
            showShortToast("密码长度6位");
            return false;
        }
        if (!etNew.getText().toString().equals(etCon.getText().toString())) {
            showShortToast("两次输入的新密码不一致");
            return false;
        }
        return true;
    }

    public boolean isPhoneNumber(String phoneNumber) {
        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
//        String regexp = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    @Override
    public void onDestroy() {
        SMSSDK.unregisterEventHandler(eventhandler);
        super.onDestroy();
    }

}
