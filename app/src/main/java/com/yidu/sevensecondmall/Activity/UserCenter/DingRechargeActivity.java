package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.PaySuccess;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.PayPopWindow;
import com.yidu.sevensecondmall.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/27.
 */

public class DingRechargeActivity extends BaseActivity implements PaySuccess {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_ding_recharge)
    TextView tvDingRecharge;
    @BindView(R.id.tv_ding)
    EditText tvDing;
    @BindView(R.id.all_recharge)
    TextView allRecharge;
    @BindView(R.id.sure)
    TextView sure;
    private int price;

    @Override
    protected int setViewId() {
        return R.layout.activity_ding_recharge;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("兑换");
        if (LoginUtils.getDing_coin() != null) {
            price = Integer.parseInt(LoginUtils.getDing_coin().split("\\.")[0]);
        } else {
            price = 0;

        }
    }

    @Override
    protected void initEvent() {
        tvDingRecharge.setText(price+"");
        allRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDing.setText(LoginUtils.getDing_coin());
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDing.getText().toString().equals("") || tvDing.getText().toString() == null) {
                    ToastUtil.showToast(DingRechargeActivity.this, "请输入要兑换的DING宝数");
                } else if (Integer.valueOf(tvDing.getText().toString()) > price) {
                    ToastUtil.showToast(DingRechargeActivity.this, "输入的金额大于拥有的DING宝数");
                } else {
                    PayPopWindow popWindow = new PayPopWindow(DingRechargeActivity.this, sure, tvDing.getText().toString(), true, "ding_coin", DingRechargeActivity.this);
                    popWindow.showPopupWindowPassWord();
                }
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void paySuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }
}
