package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
public class ApplyReturnActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_code_tip)
    TextView tvCodeTip;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.post)
    TextView post;
    @BindView(R.id.iv_goods)
    ImageView ivGoods;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_meal)
    TextView tvMeal;
    @BindView(R.id.goodscount)
    TextView goodscount;
    @BindView(R.id.tv_content)
    LinearLayout tvContent;
    @BindView(R.id.et_reason)
    EditText etReason;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String orderid = "";
    private String goodsid = "";
    private String order_sn = "";
    private String name = "";
    private String meal = "";
    private String price = "";
    private String number = "";
    private String pic = "";
    private String spec_key = "";


    @Override
    protected int setViewId() {
        return R.layout.activity_apply_return;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("申请退款");
    }

    @Override
    protected void initEvent() {
        Intent i = getIntent();
        if (i.hasExtra("orderid") && i.getStringExtra("orderid") != null) {
            orderid = i.getStringExtra("orderid");
        }
        if (i.hasExtra("goodsid") && i.getStringExtra("goodsid") != null) {
            goodsid = i.getStringExtra("goodsid");
        }
        if (i.hasExtra("order_sn") && i.getStringExtra("order_sn") != null) {
            order_sn = i.getStringExtra("order_sn");
        }
        if (i.hasExtra("name") && i.getStringExtra("name") != null) {
            name = i.getStringExtra("name");
        }
        if (i.hasExtra("meal") && i.getStringExtra("meal") != null) {
            meal = i.getStringExtra("meal");
        }
        if (i.hasExtra("price") && i.getStringExtra("price") != null) {
            price = i.getStringExtra("price");
        }
        if (i.hasExtra("number") && i.getStringExtra("number") != null) {
            number = i.getStringExtra("number");
        }
        if (i.hasExtra("pic") && i.getStringExtra("pic") != null) {
            pic = i.getStringExtra("pic");
        }
        if (i.hasExtra("spec_key") && i.getStringExtra("spec_key") != null) {
            spec_key = i.getStringExtra("spec_key");
        }
    }

    @Override
    protected void init() {
        tvCode.setText(order_sn);
        tvGoodsName.setText(name);
        if (meal != null && !meal.equals("")) {
            meal = "规格：" + meal;
        }
        tvMeal.setText(meal);
        goodscount.setText("数量：" + number);

        Glide.with(ApplyReturnActivity.this)
                .load(HttpApi.getFullImageUrl(pic))
                .into(ivGoods);
        tvPay.setText("¥" + price);
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                if (etReason.getText().toString().trim().length() != 0) {
                    GoodsDAO.applyRefund(orderid, goodsid,
                            etReason.getText().toString(), order_sn, spec_key, new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    showShortToast("申请成功");
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    showShortToast(data + "");
                                }
                            });
                } else {
                    showShortToast("请填写退款理由");
                }
                break;
        }
    }

}
