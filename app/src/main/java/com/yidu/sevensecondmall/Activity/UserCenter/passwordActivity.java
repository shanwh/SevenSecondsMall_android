package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.DES;
import com.yidu.sevensecondmall.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/15.
 */
public class passwordActivity extends BaseActivity {


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
    @BindView(R.id.title_code)
    TextView titleCode;
    @BindView(R.id.ll_tip)
    LinearLayout llTip;
    @BindView(R.id.pass_edit)
    EditText passEdit;
    @BindView(R.id.pass_edit2)
    EditText passEdit2;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_safe;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("设置密码");
        toolbarTitle.setText("设置密码");
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.sure:
                UserDAO.changePw(passEdit.getText().toString(), passEdit2.getText().toString(), edit.getText().toString(), new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        Toast.makeText(passwordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        String s = edit.getText().toString();
                        String des = DES.encryptDES(s, "48158222");
                        LoginUtils.setPassword(des);
                        finish();
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(passwordActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
//            case R.id.tv_code:
//                MobSDK.init(this);
//                SMSSDK.getVerificationCode(countryCode, telNum.getText().toString());
//                Toast.makeText(passwordActivity.this, "请等待验证码", Toast.LENGTH_SHORT).show();
//                break;
        }
    }

}
