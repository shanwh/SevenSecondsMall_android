package com.yidu.sevensecondmall.Activity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.BindingActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.UserInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.OnLoginListener;
import com.yidu.sevensecondmall.i.UIEvent;
import com.yidu.sevensecondmall.utils.DES;
import com.yidu.sevensecondmall.utils.EasyPermissionsEx;
import com.yidu.sevensecondmall.utils.LoginApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.MD5Utils;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/3/13.
 * <p/>
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.socket)
    ImageView socket;
    @BindView(R.id.telnum)
    EditText telnum;
    @BindView(R.id.tellayout)
    RelativeLayout tellayout;
    @BindView(R.id.pwnum)
    EditText pwnum;
    @BindView(R.id.login_in)
    Button loginIn;
    @BindView(R.id.forpw)
    TextView forpw;
    @BindView(R.id.signup)
    TextView signup;
    @BindView(R.id.login_content)
    LinearLayout loginContent;
    @BindView(R.id.login_qq)
    ImageView loginQq;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    @BindView(R.id.login_weibo)
    ImageView loginWeibo;
    @BindView(R.id.act_content)
    RelativeLayout actContent;
    @BindView(R.id.tel_icon)
    IconFontTextView telIcon;
    @BindView(R.id.delete)
    IconFontTextView delete;
    private String rid="";
    private Platform[] platformlist;
    private ProgressDialog dialog;

    private String toastStr = "";

    public LoginActivity(){
//        EventBus.getDefault().register(this);
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("请等待");
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void init() {
        initThird();
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.login_in, R.id.signup, R.id.login_content, R.id.login_weixin, R.id.forpw,R.id.delete})
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.login_in:
                if (!EasyPermissionsEx.isNetworkAvailable(this)) {
                    Toast.makeText(LoginActivity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (pwnum.getText().toString().length() < 6 || pwnum.getText().toString().length() > 12) {
                    Toast.makeText(LoginActivity.this, "密码长度不正确", Toast.LENGTH_SHORT).show();
                    break;
                }
                view.setClickable(false);
                rid = JPushInterface.getRegistrationID(LoginActivity.this);
                UserDAO.Login(telnum.getText().toString(), pwnum.getText().toString(),rid, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));
                        SystemUtil.setSharedBoolean("isFirstBand", false);
                        String des = DES.encryptDES(pwnum.getText().toString(), "48158222");
                        LoginUtils.setPassword(des);
                        String username = DES.encryptDES(telnum.getText().toString(), "48158222");
                        LoginUtils.setUsername(username);
                        LoginUtils.setRegistration_id(rid);
                        LoginUtils.setIsLogin(true);
                        finish();
                        view.setClickable(true);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        toastStr = data + "";
                        EventBus.getDefault().post(new UIEvent(IEventTag.TOAST));
                        view.setClickable(true);
                    }
                });
                break;
            case R.id.signup:
                Intent resigter = new Intent(LoginActivity.this, ResigterActivity.class);
                startActivity(resigter);
                break;

            case R.id.forpw:
                Intent forpw = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(forpw);
                break;
            case R.id.login_content:

                break;
            case R.id.login_weixin:
                dialog.show();
                login("Wechat");
                break;
            case R.id.delete:
                telnum.setText("");
                break;
        }
    }

 public void initThird(){
        ShareSDK.initSDK(this);
        platformlist = ShareSDK.getPlatformList();
        if(platformlist !=null){
            for(int i = 0;i<platformlist.length;i++){
                Log.e("platformlist",platformlist[i].getName());
            }
        }
    }

    private static final String TAG = "LoginActivity";
    /*
	 * 执行第三方登录/注册的方法
	 *
	 */
    private void login(String platformName) {
        LoginApi api = LoginApi.getInstance();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res,final String uid) {
                //登录后的回调函数
                Log.e("uid",uid);
                rid = JPushInterface.getRegistrationID(LoginActivity.this);
                UserDAO.LoginThird(uid, "Wechat", rid, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        Log.e(TAG, "success: "+data+"");
                        if(dialog !=null&&dialog.isShowing()){
                            dialog.dismiss();
                        }
                        String mobile ;
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("token")){
                                LoginUtils.setToken(content.getString("token"));
                                LoginUtils.setIsLogin(true);
                            }
                            if (content.has("mobile")){
                                mobile =  content.getString("mobile");
                                if(mobile.equals("")){
                                    Intent i = new Intent(LoginActivity.this, BindingActivity.class);
                                    SystemUtil.setSharedBoolean("isFirstBand", true);
                                    startActivity(i);
                                    LoginActivity.this.finish();
                                }else {
                                    LoginUtils.setIsLogin(true);
                                    LoginUtils.setLoginOpenid(uid);
                                    SystemUtil.setSharedBoolean("isFirstBand", false);
                                    Toast.makeText(SystemUtil.getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                                    EventBus.getDefault().post(new LoginEvent(IEventTag.REFRESHHEAH));
                                    LoginActivity.this.finish();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if(dialog !=null&&dialog.isShowing()){
                            dialog.dismiss();
                        }
                        Toast.makeText(SystemUtil.getContext(),""+data,Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e(""+platform,"login");
                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                Log.e("register","info");
                return true;
            }

            @Override
            public boolean onCancel() {
                if(dialog !=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                return true;
            }

            @Override
            public boolean onError() {
                if(dialog !=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                return true;
            }
        });
        api.login(this);
    }



@Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerUI(UIEvent ev) {
        switch (ev.founctionTag) {
            case IEventTag.TOAST:
                Toast.makeText(SystemUtil.getContext(), toastStr, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
