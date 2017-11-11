package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.DingShopActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/28 0028.
 */
public class MyDingActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.ll_money_ex)
    LinearLayout llMoneyEx;
    @BindView(R.id.tv_ding)
    TextView tv_ding;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_ding;
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

    }

    @Override
    protected void loadData() {
        UserDAO.getUserInfo(
                new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            UserBean bean = (UserBean) data;
                            tv_ding.setText(bean.getDing_coin());

                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode == 1006 || errorCode == 1005) {

                        } else {
                            showShortToast(data + "");
                        }
                    }
                }

        );
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }


    @OnClick({R.id.ll_shop, R.id.ll_money_ex})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_shop:
                intent = new Intent(this, DingShopActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_money_ex:
                intent = new Intent(this, DingCoinRecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
