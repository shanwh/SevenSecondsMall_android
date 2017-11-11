package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.ShareHolderRequestActivity;
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
public class UpLvActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_request)
    TextView tvRequest;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_privilege)
    TextView tvPrivilege;
    @BindView(R.id.tv_welfare)
    TextView tvWelfare;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_up_lv;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("会员升级");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        String s = "股东卡会员推广特权：";
        SpannableString spanString = new SpannableString("股东卡会员推广特权：");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#747474"));
        spanString.setSpan(span, 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        StyleSpan spans = new StyleSpan(Typeface.BOLD);
        spanString.setSpan(spans, 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrivilege.setText(spanString);

        String s2 = "获取邀请会员、VIP会员及股东卡会员的权限。";
        SpannableString spanString2 = new SpannableString("获取邀请会员、VIP会员及股东卡会员的权限。");
        ForegroundColorSpan span2 = new ForegroundColorSpan(Color.parseColor("#808080"));
        spanString2.setSpan(span2, 0, s2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPrivilege.append(spanString2);

        String s3 = "股东卡会员福利：";
        SpannableString spanString3 = new SpannableString("股东卡会员福利：");
        ForegroundColorSpan span3 = new ForegroundColorSpan(Color.parseColor("#747474"));
        spanString3.setSpan(span3, 0, s3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        StyleSpan spans2 = new StyleSpan(Typeface.BOLD);
        spanString3.setSpan(spans2, 0, s3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvWelfare.setText(spanString3);

        String s4 = "股东卡会员邀请会员（普通会员、VIP会员及股东卡会员）进入平台消费，可获优厚奖励金。";
        SpannableString spanString4 = new SpannableString("股东卡会员邀请会员（普通会员、VIP会员及股东卡会员）进入平台消费，可获优厚奖励金。");
        ForegroundColorSpan span4 = new ForegroundColorSpan(Color.parseColor("#808080"));
        spanString4.setSpan(span4, 0, s4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvWelfare.append(spanString4);
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.tv_request})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_request:
                UserDAO.getUserInfo(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
//                        shareholder_apply_status
                        UserBean bean = (UserBean) data;
                        String shareholder_apply_status = bean.getShareholder_apply_status();
                        if ("1".equals(shareholder_apply_status)) {
                            Intent intent = new Intent(UpLvActivity.this, ShareHolderRequestActivity.class);
                            startActivity(intent);
                        } else {
                            showShortToast("您已经申请了股东卡");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        showShortToast(data + "");
                    }
                });

                break;
        }
    }

}
