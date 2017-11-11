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
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/5.
 * 我的余额
 */
public class MyMoneyActivity extends BaseActivity {
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
    @BindView(R.id.money_list)
    TextView moneyList;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;
    @BindView(R.id.tv_user_money)
    TextView tv_user_money;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    @Override
    protected int setViewId() {
        return R.layout.activity_mymoney;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("我的余额");
        toolbarTitle.setText("我的余额");
        toolbarRight.setText("余额明细");
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyMoneyActivity.this, MoneyListActivity.class));
            }
        });
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
    protected void onResume() {
        setValue();
        super.onResume();
    }

    @OnClick({R.id.back, R.id.money_list, R.id.btn_recharge, R.id.btn_withdraw})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.money_list:
                intent = new Intent(MyMoneyActivity.this, MoneyListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_recharge:
                intent = new Intent(MyMoneyActivity.this, RechargeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_withdraw:
                intent = new Intent(MyMoneyActivity.this, WithdrawActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setValue() {
        UserDAO.getUserInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    UserBean bean = (UserBean) data;
                    String user_money = bean.getUser_money();
                    tv_user_money.setText("¥" + user_money);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast("" + data);
            }
        });
    }

}
