package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 设置Activity
 */
public class SettingActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


    private static final String TAG = TakePhotoActivity.class.getName();
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
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.head_msg)
    TextView headMsg;
    @BindView(R.id.changeHead)
    IconFontTextView changeHead;
    @BindView(R.id.l_changehead)
    LinearLayout lChangehead;
    @BindView(R.id.change)
    TextView change;
    @BindView(R.id.nametxt)
    IconFontTextView nametxt;
    @BindView(R.id.l_name)
    LinearLayout lName;
    @BindView(R.id.sextxt)
    IconFontTextView sextxt;
    @BindView(R.id.l_sex)
    LinearLayout lSex;
    @BindView(R.id.cmtxt)
    IconFontTextView cmtxt;
    @BindView(R.id.l_changmobile)
    LinearLayout lChangmobile;
    @BindView(R.id.safetxt)
    IconFontTextView safetxt;
    @BindView(R.id.l_safe)
    LinearLayout lSafe;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private UserBean bean;
    private boolean hasChange;

    @Override
    protected int setViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("个人设置");
        toolbarTitle.setText("个人设置");
//        tvBackTip.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        if (LoginUtils.isLogin()) {
            UserDAO.getUserInfo(new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (UserBean) data;
                        Glide.with(SettingActivity.this)
                                .load(HttpApi.getFullImageUrl(bean.getHead_pic()))
                                .transform(new GlideCircleTransform(SettingActivity.this))
                                .into(head);
                        change.setText(bean.getNickname());
                        switch (bean.getSex()) {
                            case 0:
                                sex.setText("保密");
                                break;
                            case 1:
                                sex.setText("男");
                                break;
                            case 2:
                                sex.setText("女");
                                break;
                        }


                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    showShortToast(data + "");
                    Toast.makeText(SettingActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void handlerSaveInstanceState(Bundle savedInstanceState) {
        getPhoto().onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getPhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @OnClick({R.id.back, R.id.l_changehead, R.id.l_name, R.id.l_sex, R.id.l_safe, R.id.l_changmobile})
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.l_changehead:
                i = new Intent(SettingActivity.this, PersonIconActivity.class);
                startActivity(i);
                break;
            case R.id.l_name:
                i = new Intent(SettingActivity.this, EditActivity.class);
                startActivity(i);
                break;
            case R.id.l_sex:
                i = new Intent(SettingActivity.this, SelectSexActivity.class);
                startActivity(i);
                break;

            case R.id.l_safe:
                i = new Intent(SettingActivity.this, SafeActivity.class);
                startActivity(i);
                break;
            case R.id.l_changmobile:
                Intent cm = new Intent(SettingActivity.this, MobileBindingActivity.class);
                startActivity(cm);
                break;

        }
    }

    public TakePhoto getPhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.e(TAG, "takeSuccess：" + result.getImage().getCompressPath());
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent.getStringExtra("sex") != null && !intent.getStringExtra("sex").equals("")) {
            sex.setText(intent.getStringExtra("sex"));
        }
        if (intent.getStringExtra("name") != null && !intent.getStringExtra("name").equals("")) {
            change.setText(intent.getStringExtra("name"));
            hasChange = true;
        }
    }

    @Override
    protected void onResume() {
        if (SystemUtil.getSharedString("user_head") != null) {
            Glide.with(SettingActivity.this)
                    .load(HttpApi.getFullImageUrl(SystemUtil.getSharedString("user_head")))
                    .transform(new GlideCircleTransform(SettingActivity.this))
                    .into(head);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        try {
            String headUri;
            if (SystemUtil.getSharedString("user_head") != null) {
                headUri = SystemUtil.getSharedString("user_head");
            } else {
                headUri = bean.getHead_pic();
            }
            String changeName = hasChange ? change.getText().toString() : null;
            hasChange = false;
            UserDAO.UpdatUserInfo(changeName,
                    headUri,
                    sex.getText().toString().equals("男") ? 1 : 2,
                    new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            Log.e(TAG, "success: " + "个人信息修改成功");
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            if (errorCode == 1205) {
                                showShortToast(data + "");
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

}
