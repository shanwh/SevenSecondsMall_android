package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.PaymentDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
public class PayPasswordActivity extends BaseActivity {


    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_old_tip)
    TextView tvOldTip;
    @BindView(R.id.et_old)
    EditText etOld;
    @BindView(R.id.tv_new_tip)
    TextView tvNewTip;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.tv_con_tip)
    TextView tvConTip;
    @BindView(R.id.et_con)
    EditText etCon;
    @BindView(R.id.tv_no_password)
    TextView tvNoPassword;
    @BindView(R.id.tv_set_password)
    TextView tvSetPassword;
    @BindView(R.id.tv_for_password)
    TextView tvForPassword;
    @BindView(R.id.tv_reset_password)
    TextView tvResetPassword;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.if_tv_success)
    IconFontTextView ifTvSuccess;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.ll_set)
    LinearLayout llSet;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_pay_password;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("支付密码");
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

    @OnClick({R.id.back, R.id.tv_set_password, R.id.tv_reset_password, R.id.tv_sure})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_set_password:
            case R.id.tv_reset_password:
                intent = new Intent(this, SetPayPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_sure:
                if (checkMessage()) {
                    String password = etNew.getText().toString();
                    String confirm_password = etCon.getText().toString();
                    String old_password = etOld.getText().toString();
                    PaymentDao.resetPayPwd(password, confirm_password, old_password, new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            rlFinish.setVisibility(View.VISIBLE);
                            llSet.setVisibility(View.GONE);
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            showShortToast(data + "");
                        }
                    });
                }
                break;
        }
    }

    private boolean checkMessage() {
        if (etOld.getText().length() == 0) {
            showShortToast("请输入当前密码");
            return false;
        }
        if (etNew.getText().length() == 0) {
            showShortToast("请输入新密码");
            return false;
        }
        if (etCon.getText().length() == 0) {
            showShortToast("请确认新密码");
            return false;
        }
        if (etCon.getText().length() < 6 || etOld.getText().length() < 6 || etNew.getText().length() < 6) {
            showShortToast("密码长度6位");
            return false;
        }
        if (!etNew.getText().toString().equals(etCon.getText().toString())) {
            showShortToast("两次输入的新密码不一致");
            return false;
        }
        return true;
    }
}
