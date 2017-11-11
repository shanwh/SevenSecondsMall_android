package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14.
 */
public class SafeActivity extends BaseActivity {


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
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.linear)
    RelativeLayout linear;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.trans_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("安全设置");
        toolbarTitle.setText("安全设置");

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


    @OnClick({R.id.linear, R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear:
                Intent i = new Intent(SafeActivity.this, passwordActivity.class);
                startActivity(i);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

}
