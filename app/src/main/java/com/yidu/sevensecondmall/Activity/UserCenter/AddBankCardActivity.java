package com.yidu.sevensecondmall.Activity.UserCenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.PaymentDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class AddBankCardActivity extends BaseActivity {


//    @BindView(R.id.rl_head)
//    RelativeLayout rlHead;
    @BindView(R.id.tv_card_owner)
    TextView tvCardOwner;
    @BindView(R.id.et_card_owner)
    EditText etCardOwner;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.et_card_type)
    TextView etCardType;
    //    @BindView(R.id.tv_phone_number)
//    TextView tvPhoneNumber;
//    @BindView(R.id.et_phone_number)
//    EditText etPhoneNumber;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    //    @BindView(R.id.tv_code)
//    TextView tvCode;
//    @BindView(R.id.et_code)
//    EditText etCode;
//    @BindView(R.id.rl_code)
//    RelativeLayout rlCode;
//    @BindView(R.id.tv_password)
//    TextView tvPassword;
//    @BindView(R.id.et_password)
//    EditText etPassword;
//    @BindView(R.id.tv_password_c)
//    TextView tvPasswordC;
//    @BindView(R.id.et_password_c)
//    EditText etPasswordC;
//    @BindView(R.id.ll_password)
//    LinearLayout llPassword;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.if_tv_success)
    IconFontTextView ifTvSuccess;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.ll_card_type)
    LinearLayout llCartType;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String banktype;
    private ProgressDialog dialog;

    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
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
                    Toast.makeText(AddBankCardActivity.this, "提交验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE:
                    Log.e("GET", "GET_VERIFICATION_CODE");
                    Toast.makeText(AddBankCardActivity.this, "获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                case EVENT_GET_SUPPORTED_COUNTRIES:
                    Log.e("COUNTRIES", "EVENT_GET_SUPPORTED_COUNTRIES");
                    break;
            }
        }
    };

    @Override
    protected int setViewId() {
        return R.layout.activity_bank_card_add;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("添加银行卡");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llInfo.getVisibility() == View.VISIBLE || rlFinish.getVisibility() == View.VISIBLE) {
                    finish();
                } else if (llCartType.getVisibility() == View.VISIBLE) {
                    llInfo.setVisibility(View.VISIBLE);
                    llCartType.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
    }

    @Override
    protected void init() {
        String name = getIntent().getStringExtra("name");
        etCardOwner.setText(name);
        dialog = new ProgressDialog(this);
        dialog.setMessage("请等待");
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (llInfo.getVisibility() == View.VISIBLE) {
                    if (checkBankInfo()) {
                        dialog.show();
                        PaymentDao.getBankcardType(etCardNumber.getText().toString(), new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                dialog.dismiss();
                                try {
                                    JSONObject content = new JSONObject(data.toString());
                                    if (content.has("bankname")) {
                                        String bankname = content.getString("bankname");
                                        llInfo.setVisibility(View.GONE);
                                        llCartType.setVisibility(View.VISIBLE);
                                        etCardType.setText(bankname);
                                    } else {
                                        ToastUtil.showToast(AddBankCardActivity.this, "返回数据异常");
                                    }

                                    if (content.has("banktype")) {
                                        banktype = content.getString("banktype");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                dialog.dismiss();
                                ToastUtil.showToast(AddBankCardActivity.this, "" + data);
                            }
                        });


                    }
                } else if (llCartType.getVisibility() == View.VISIBLE) {
                    String bankcardnumber, bankname;
                    bankcardnumber = etCardNumber.getText().toString().trim();
                    bankname = etCardType.getText().toString().trim();

                    PaymentDao.bindingBankCard(bankcardnumber, banktype, bankname,
                            new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    tvNext.setVisibility(View.GONE);
                                    rlFinish.setVisibility(View.VISIBLE);
                                    SystemUtil.setSharedBoolean("hasNewBankCard", true);
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    showShortToast(data + "");
                                }
                            });

                }

                break;
        }
    }

    private boolean checkBankInfo() {
        if (etCardNumber.getText().toString().trim().length() == 0) {
            ToastUtil.showToast(this, "请输入卡号");
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
