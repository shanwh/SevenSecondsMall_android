package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public class VerifyActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_shop_tip)
    TextView tvShopTip;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_verify;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("审核资料");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        SpannableString ss = new SpannableString("申请商家提示：你的申请已提交后台，工作人员将在1个工作日内回访，请保持电话畅通~");
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorMySha)), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvShopTip.setText(ss);
    }

    @Override
    protected void loadData() {

    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
