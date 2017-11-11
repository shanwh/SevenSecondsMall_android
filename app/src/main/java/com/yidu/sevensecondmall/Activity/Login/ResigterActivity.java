package com.yidu.sevensecondmall.Activity.Login;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.MainActivity;
import com.yidu.sevensecondmall.Activity.NewMainActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.DES;
import com.yidu.sevensecondmall.utils.EasyPermissionsEx;
import com.yidu.sevensecondmall.utils.LoginUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/3/13.
 * 注册
 */
public class ResigterActivity extends BaseActivity {

    @BindView(R.id.r_telnum)
    EditText rTelnum;
    @BindView(R.id.r_pw)
    EditText rPw;
    @BindView(R.id.r_pw2)
    EditText rPw2;
    @BindView(R.id.getCNum)
    TextView getCNum;
    @BindView(R.id.r_cnum)
    EditText rCnum;
    @BindView(R.id.resigter)
    Button resigter;
    @BindView(R.id.et_invite_mobile)
    EditText et_invite_mobile;


    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
    private String countryCode = "86";
    private final static int RESULT_COMPLETE = 0;
    private final static int SUBMIT_VERIFICATION_CODE = 1;
    private final static int GET_VERIFICATION_CODE = 2;
    private final static int EVENT_GET_SUPPORTED_COUNTRIES = 4;
    private String rid="";

    public Handler mHandler;
    public static int TIMESTATUS = 0;
    public static int TIMESTOP = 1;
    public static int TIMEREADY = 0;
    public static int TIMERUNNING = 2;
    public static int SECONDS = 59;
    public boolean send = false;



    public EventHandler eventhandler = new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                handler.sendEmptyMessage(RESULT_COMPLETE);
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    handler.sendEmptyMessage(GET_VERIFICATION_CODE);
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                    handler.sendEmptyMessage(EVENT_GET_SUPPORTED_COUNTRIES);
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };

    public Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg){
          switch(msg.what){
              case RESULT_COMPLETE:
                  Log.e("RESULT_COMPLETE","RESULT_COMPLETE");
//                  Toast.makeText(ResigterActivity.this,"验证码回调成功",Toast.LENGTH_SHORT).show();
                  break;
              case SUBMIT_VERIFICATION_CODE:
                  Log.e("SUBMIT","SUBMIT_VERIFICATION_CODE");
//                  Toast.makeText(ResigterActivity.this,"提交验证码",Toast.LENGTH_SHORT).show();
                  break;
              case GET_VERIFICATION_CODE:
                  Log.e("GET","GET_VERIFICATION_CODE");
                  Toast.makeText(ResigterActivity.this,"获取验证码",Toast.LENGTH_SHORT).show();
                  break;
              case  EVENT_GET_SUPPORTED_COUNTRIES:
                  Log.e("COUNTRIES","EVENT_GET_SUPPORTED_COUNTRIES");
                  break;
          }
      }
    };

    @Override
    protected int setViewId() {
        return R.layout.activity_resigter;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
        mHandler = new Handler();
    }

    @Override
    protected void init() {
        String invited_mob = SystemUtil.getSharedString("invited_mob");
        if (invited_mob != null) {
            String[] split = invited_mob.split("");
            int length = split.length;
            String result = "";
            for (int i = 0; i < 4; i++) {
                String s = split[i];
                result =  result + s ;
            }
            result = result + "****";
            for (int i = 0; i < 4; i++) {
                String s = split[length - 4 + i];
                result =  result + s ;
            }
            et_invite_mobile.setText(result);
            et_invite_mobile.setEnabled(false);
        }
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.getCNum, R.id.resigter})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.getCNum:
                if (TIMESTATUS == TIMEREADY || TIMESTATUS == TIMESTOP) {
                    mHandler.postDelayed(timerun, 1000);
                    if(!send){
                        if (rTelnum.getText().toString().trim().length() != 0) {
                            SMSSDK.getVerificationCode(countryCode, rTelnum.getText().toString());
                            send = true;
                        }else {
                            showShortToast("请输入手机号码");
                        }
                    }

                } else {
                    showShortToast("请等待短信");
                }
                break;
            case R.id.resigter:
                if(!EasyPermissionsEx.isNetworkAvailable(this)){
                    Toast.makeText(ResigterActivity.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                    break;
                }
                if (et_invite_mobile.getText().toString().trim().length() == 0){
                    Toast.makeText(ResigterActivity.this,"请输入推荐手机号码",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(rTelnum.getText().toString().trim().length() != 11){
                    Toast.makeText(ResigterActivity.this,"电话长度不正确",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(rPw.getText().toString().length() < 6 || rPw.getText().toString().length() > 12){
                    Toast.makeText(ResigterActivity.this,"密码长度不正确",Toast.LENGTH_SHORT).show();
                    break;
                }

                if(!rPw.getText().toString().equals(rPw2.getText().toString())){
                    Toast.makeText(ResigterActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                    break;
                }
//                SMSSDK.submitVerificationCode(countryCode,rTelnum.getText().toString(),rCnum.getText().toString());
                UserDAO.Resigter(rTelnum.getText().toString(), rPw.getText().toString(),
                        rPw2.getText().toString(), rCnum.getText().toString(),
                        et_invite_mobile.getText().toString(), new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                Toast.makeText(ResigterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                rid = JPushInterface.getRegistrationID(ResigterActivity.this);
                                UserDAO.Login(rTelnum.getText().toString(), rPw.getText().toString(),rid, new BaseCallBack() {
                                    @Override
                                    public void success(Object data) {
                                        showShortToast("登录成功");
                                        EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));
                                        String des = DES.encryptDES(rPw.getText().toString(), "48158222");
                                        LoginUtils.setPassword(des);
                                        String username = DES.encryptDES(rTelnum.getText().toString(), "48158222");
                                        LoginUtils.setUsername(username);
                                        LoginUtils.setRegistration_id(rid);
                                        LoginUtils.setIsLogin(true);
//                                        Intent intent = new Intent(ResigterActivity.this, MainActivity.class);
                                        Intent intent = new Intent(ResigterActivity.this, NewMainActivity.class);
                                        SystemUtil.setSharedBoolean("toMy", true);
                                        ResigterActivity.this.startActivity(intent);
                                        ResigterActivity.this.finish();
                                    }

                                    @Override
                                    public void failed(int errorCode, Object data) {
                                       showShortToast(data + "");
                                        view.setClickable(true);
                                    }
                                });
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                Toast.makeText(ResigterActivity.this,""+data,Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }


    @Override
    public void onDestroy(){
        SMSSDK.unregisterEventHandler(eventhandler);
        SystemUtil.setSharedString("invited_mob", null);
        if (mHandler != null) {
            mHandler.removeCallbacks(timerun);
        }
        super.onDestroy();
    }

    public Runnable timerun = new Runnable() {
        @Override
        public void run() {
            //计时停止，倒数开始
            if (TIMESTATUS != TIMESTOP) {
                TIMESTATUS = TIMERUNNING;
                if (SECONDS >= 0) {
                    getCNum.setText("倒计时" + SECONDS);
                    SECONDS--;
                    mHandler.postDelayed(this, 1000);
                } else {
                    getCNum.setText("重新获取");
                    TIMESTATUS = TIMEREADY;
                    mHandler.removeCallbacks(this);
                    SECONDS = 119;
                    send = false;
                }


            }
        }
    };
}
