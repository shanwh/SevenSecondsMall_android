package com.yidu.sevensecondmall.Activity.UserCenter;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IBottom;
import com.yidu.sevensecondmall.utils.UpdateManager;
import com.yidu.sevensecondmall.views.widget.AddressAlertDialog;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/23.
 */
public class AboutActivity extends BaseActivity {


    //    @BindView(R.id.title_login)
//    RelativeLayout titleLogin;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.update)
    RelativeLayout update;
    @BindView(R.id.goupdate)
    IconFontTextView goupdate;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    String versionName;
    String tips;
    String title;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private UpdateManager updateManager;
    String downloadurl;

    @Override
    protected int setViewId() {
        return R.layout.about_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("关于我们");

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

        try {
            // ---get the package info---
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            versionName = pi.versionName;

            if (versionName == null || versionName.length() <= 0) {
                tvVersion.setText("");
            } else {
                tvVersion.setText("V " + versionName);
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
    }


    @OnClick({ R.id.update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update:
                showDialog();
                break;
        }

    }

    private void showDialog() {
        UserDAO.startUp(1, versionName, new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("versioninfo")) {
                        JSONObject versioninfo = content.getJSONObject("versioninfo");
                        boolean is_newest = versioninfo.getBoolean("is_newest");
                        if (is_newest) {
                            View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_update, null);
                            AddressAlertDialog dismissAlertDialog = new AddressAlertDialog(AboutActivity.this);
                            dismissAlertDialog.builder()
                                    .setView(inflate)
                                    .setPositiveButton("确定", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    }).show();
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
        View contentView = LayoutInflater.from(AboutActivity.this).inflate(R.layout.view_update_layout, null);
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
                .showAtLocation(tvVersion, Gravity.CENTER, 0, 0);//显示PopupWindow
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

                if (ContextCompat.checkSelfPermission(AboutActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                    ActivityCompat.requestPermissions(AboutActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    //有权限，下载
                    download();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    download();
                }
                break;
        }
    }

    private void download() {
        if (updateManager == null) {
            updateManager = new UpdateManager(AboutActivity.this);
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

}
