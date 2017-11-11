package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.DataDeleteUtils;
import com.yidu.sevensecondmall.utils.LoginApi;
import com.yidu.sevensecondmall.utils.LoginUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/25.
 */
public class allSettingActivity extends BaseActivity {


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
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.rl_msg)
    RelativeLayout rlMsg;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.rl_postmsg)
    RelativeLayout rlPostmsg;
    @BindView(R.id.rl_clear)
    RelativeLayout rlClear;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_pay_password)
    RelativeLayout rlPayPassword;
    @BindView(R.id.loginout)
    TextView loginout;
    @BindView(R.id.msg_switch)
    ImageView msgSwitch;
    @BindView(R.id.rl_identification)
    RelativeLayout rl_identification;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.all_setting_activity;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("设置");
        toolbarTitle.setText("设置");

        if (LoginUtils.getSwitch()) {
            msgSwitch.setImageResource(R.drawable.open);
        } else {
            msgSwitch.setImageResource(R.drawable.close);
        }
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


    @OnClick({R.id.back_button, R.id.rl_msg, R.id.rl_address, R.id.msg_switch, R.id.rl_clear, R.id.rl_about, R.id.loginout, R.id.rl_pay_password, R.id.rl_identification})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.rl_msg:
                intent = new Intent(allSettingActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_address:
                intent = new Intent(allSettingActivity.this, AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.msg_switch:
                //消息推送设置
                LoginUtils.setSwitch(!LoginUtils.getSwitch());
                if (LoginUtils.getSwitch()) {
                    msgSwitch.setImageResource(R.drawable.open);
                } else {
                    msgSwitch.setImageResource(R.drawable.close);
                }
                break;
            case R.id.rl_clear:
                //清空缓存
                AlertDialog dialog = new AlertDialog.Builder(allSettingActivity.this)
                        .setTitle("提示:")
                        .setMessage("清除之后图片，视频等多媒体需要重新下载查看\n" +
                                "确定清除")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //清空
                                DataDeleteUtils.cleanInternalCache(allSettingActivity.this);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.rl_about:
                intent = new Intent(allSettingActivity.this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.loginout:
                if (!LoginUtils.getLoginPlatform().equals(LoginUtils.PlatformPhone)) {
                    LoginApi api = LoginApi.getInstance();
                    api.loginOut();
                    Toast.makeText(SystemUtil.getContext(), "取消授权", Toast.LENGTH_SHORT).show();
                }
                EventBus.getDefault().post(new LoginEvent(IEventTag.LOGINOUT));
                finish();
                break;
            case R.id.rl_pay_password:
                intent = new Intent(this, PayPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_identification:
                if (LoginUtils.isLogin()) {
                    UserDAO.getUserInfo(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (data != null) {
                                UserBean bean = (UserBean) data;
                                String is_authentication = bean.getIs_authentication();
                                if (is_authentication.equals("0")) {
                                    showPopupWindow();
                                } else {
                                    showShortToast("您已经实名认证");
                                }
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            showShortToast(data + "");
                        }
                    });
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(allSettingActivity.this).inflate(R.layout.pop_window_indentification, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(rl_identification, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {

        contentView.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(allSettingActivity.this, IndentActivity.class);
                allSettingActivity.this.startActivity(intent);
                popupWindow.dissmiss();
            }
        });

    }

    @Override
    protected void onDestroy() {
        SystemUtil.setSharedString("user_head", null);
        super.onDestroy();
    }

}
