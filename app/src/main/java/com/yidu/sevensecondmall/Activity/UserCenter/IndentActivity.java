package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public class IndentActivity extends BaseActivity {
    private static final String TAG = "IndentActivity";
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.et_connect)
    EditText etConnect;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    @Override
    protected int setViewId() {
        return R.layout.activity_indent;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("实名认证");
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

    @OnClick({R.id.back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_submit:
                if (checkMessage()) {
                    UserDAO.realnameAuthentication(etCardNumber.getText().toString(), etConnect.getText().toString(), new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            showShortToast("验证成功");
                            finish();
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
        if (etConnect.getText().toString().trim().length() == 0) {
            showShortToast("请输入真实姓名");
            return false;
        }
        if (etCardNumber.getText().toString().trim().length() == 0) {
            showShortToast("请输入身份证号码");
            return false;
        }
        return true;
    }

}
