package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.utils.LoginUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/31.
 */
public class MobileBindingActivity extends BaseActivity {


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
    @BindView(R.id.tobind)
    Button tobind;
    @BindView(R.id.bindedphone)
    TextView bindedphone;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.mobile_biinding_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("手机改绑");
        toolbarTitle.setText("手机改绑");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        bindedphone.setText("已绑定:" + LoginUtils.getMobile() + "");
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back_button, R.id.tobind})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.tobind:
                Intent i = new Intent(MobileBindingActivity.this, BindingActivity.class);
                startActivity(i);
                break;
        }
    }

}
