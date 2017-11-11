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
 * Created by Administrator on 2017/5/26.
 */
public class FeeBackActivity extends BaseActivity {

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
    @BindView(R.id.rl_person)
    RelativeLayout rlPerson;
    @BindView(R.id.rl_funtion)
    RelativeLayout rlFuntion;
    @BindView(R.id.rl_app)
    RelativeLayout rlApp;
    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_fee_back;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        titleName.setText("反馈");
        toolbarTitle.setText("反馈");
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back_button, R.id.rl_person, R.id.rl_funtion, R.id.rl_app, R.id.rl_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.rl_person:
                Intent toPerson = new Intent(FeeBackActivity.this, AdviceActivity.class);
                toPerson.putExtra("type", "0");
                startActivity(toPerson);
                break;
            case R.id.rl_funtion:
                Intent toFuntion = new Intent(FeeBackActivity.this, AdviceActivity.class);
                toFuntion.putExtra("type", "1");
                startActivity(toFuntion);
                break;
            case R.id.rl_app:
                Intent toApp = new Intent(FeeBackActivity.this, AdviceActivity.class);
                toApp.putExtra("type", "2");
                startActivity(toApp);
                break;
            case R.id.rl_order:
                Intent order = new Intent(FeeBackActivity.this, AdviceActivity.class);
                order.putExtra("type", "3");
                startActivity(order);
                break;
        }
    }
}
