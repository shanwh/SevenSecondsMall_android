package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/21 0021.
 */
public class UnBindBankCardActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private AlertDialog dialog;
    private String id;

    @Override
    protected int setViewId() {
        return R.layout.activity_un_bind_bank_card;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("银行卡管理");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        id = getIntent().getStringExtra("Id");
        if (id == null) {
            id = "1";
        }
        String cardnumber = intent.getStringExtra("Cardnumber");

        Glide.with(UnBindBankCardActivity.this)
                .load(HttpApi.getFullImageUrl(img))
                .into(iv);

        String[] split = cardnumber.split("");
        int length = split.length;
        String result = "**** **** **** ";
        for (int i = 0; i < 4; i++) {
            String s = split[length - 4 + i];
            result = result + s;
        }

        tvCardNumber.setText(result);

    }

    @Override
    protected void loadData() {
        dialog = new AlertDialog.Builder(UnBindBankCardActivity.this).setTitle("")
                .setMessage("确认是否要解绑")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        UserDAO.unbundlingBankcard(id, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                showShortToast("解绑成功");
                                SystemUtil.setSharedBoolean("hasNewBankCard", true);
                                finish();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }


    @OnClick({R.id.back, R.id.post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.post:
                dialog.show();
                break;
        }
    }

}
