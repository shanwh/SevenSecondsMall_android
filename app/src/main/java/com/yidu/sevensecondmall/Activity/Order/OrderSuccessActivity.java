package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.NewMainActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/20.
 * 支付成功
 */
public class OrderSuccessActivity extends BaseActivity {

    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.buyer_txt)
    TextView buyerTxt;
    @BindView(R.id.add_txt)
    TextView addTxt;
    @BindView(R.id.priceleft)
    TextView priceleft;
    @BindView(R.id.readdetail)
    TextView readdetail;
    @BindView(R.id.goback)
    TextView goback;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private String addressid = "";
    private String shippingid = "";
    private boolean frombuynow = false;
    private String ordersn = "";
    private String orderid = "";

    @Override
    protected int setViewId() {
        return R.layout.activity_paysuccess;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        //传入必要参数
        Intent i = getIntent();
        if (i.hasExtra("name") && i.getStringExtra("name") != null) {
            buyerTxt.setText(i.getStringExtra("name"));
        }
        if (i.hasExtra("address") && i.getStringExtra("address") != null) {
            addTxt.setText(i.getStringExtra("address"));
        }
        if (i.hasExtra("amount") && i.getStringExtra("amount") != null) {
            price.setText(i.getStringExtra("amount"));
        }


        if (i.hasExtra("addressid")) {
            addressid = i.getStringExtra("addressid");
        }
        if (i.hasExtra("shippingid") && i.getStringExtra("shippingid") != null) {
            shippingid = i.getStringExtra("shippingid");
        }

        if (i.hasExtra("frombuy")) {
            frombuynow = i.getBooleanExtra("frombuy", false);
        }
        if (i.hasExtra("orderid") && i.getStringExtra("orderid") != null) {
            orderid = i.getStringExtra("orderid");
        }

        if (i.hasExtra("ordersn") && i.getStringExtra("ordersn") != null) {
            ordersn = i.getStringExtra("ordersn");
        }

        if (!frombuynow) {
            GoodsDAO.cart3(addressid, shippingid, "", "", "", false, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    Toast.makeText(OrderSuccessActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failed(int errorCode, Object data) {

                }
            });
        }

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.readdetail, R.id.goback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.readdetail:
                Intent detail = new Intent(OrderSuccessActivity.this, OrderDetailActivity.class);
                detail.putExtra("fromSuccess", true);
                detail.putExtra("orderid", orderid);
//                detail.putExtra("ordersn",ordersn);
                detail.putExtra("status", "WAITSEND");
                startActivity(detail);
                finish();
                break;
            case R.id.goback:
//                Intent i = new Intent(OrderSuccessActivity.this, MainActivity.class);
                Intent i = new Intent(OrderSuccessActivity.this, NewMainActivity.class);
                startActivity(i);
                finish();

                EventBus.getDefault().post(new LoginEvent(IEventTag.SKIP_TO_HOME));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
