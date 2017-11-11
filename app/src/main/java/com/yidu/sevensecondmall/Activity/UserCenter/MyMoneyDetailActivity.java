package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class MyMoneyDetailActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_type_tip)
    TextView tvTypeTip;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time_tip)
    TextView tvTimeTip;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_order_tip)
    TextView tvOrderTip;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_user_money)
    TextView tv_user_money;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_money_detail;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("我的DING宝");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String desc = intent.getStringExtra("desc");
        String user_money = intent.getStringExtra("user_money");
        String time = intent.getStringExtra("time");
        String order_sn = intent.getStringExtra("order_sn");
        String type = intent.getStringExtra("type");
        tv_user_money.setText(user_money);
        tvDesc.setText(desc);
        tvTime.setText(time);
        tvOrder.setText(order_sn);
        tvType.setText(type);
    }

    @Override
    protected void loadData() {

    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
