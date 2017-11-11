package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.ShipStatusBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/6.
 * messageactivity->物流详细信息
 */
public class LogisticsActivity extends BaseActivity {

    @BindView(R.id.tv_noContent)
    TextView tvNoContent;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.pic_goods)
    ImageView picGoods;
    @BindView(R.id.tag_status)
    TextView tagStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.logistics_id)
    TextView logisticsId;
    @BindView(R.id.official_phone)
    TextView officialPhone;
    @BindView(R.id.logistics_list)
    RecyclerView logisticsList;

    @BindView(R.id.ship_title)
    View shipTitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String invoice_no;
    private String shipping_code;
    MultiTypeAdapter adapter;
    List<Visitable> list = new ArrayList<>();
    private ShipStatusBean info;
    private String shipping_name;

    @Override
    protected int setViewId() {
        return R.layout.activity_logistics;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("物流信息");
        toolbarTitle.setText("物流信息");

//        titleName.setText(R.string.order_detail);
        Intent i = getIntent();
        if (i.hasExtra("invoice_no")) {
            invoice_no = i.getStringExtra("invoice_no");
        }
        if (i.hasExtra("shipping_code")) {
            shipping_code = i.getStringExtra("shipping_code");
        }
        if (i.hasExtra("shipping_name")) {
            shipping_name = i.getStringExtra("shipping_name");
        }

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        if (shipping_code == null || invoice_no == null) {
            tvNoContent.setVisibility(View.VISIBLE);
            logisticsList.setVisibility(View.GONE);
            shipTitle.setVisibility(View.GONE);
//            shipping_code = "yuantong";
//            invoice_no ="885951155552519497";
        } else {
            tvNoContent.setVisibility(View.GONE);
            logisticsList.setVisibility(View.VISIBLE);
            shipTitle.setVisibility(View.VISIBLE);
            Log.e("物流解析", "" + shipping_code);
            Log.e("物流解析", "" + invoice_no);
            OrderDAO.getShipStatue(shipping_code, invoice_no, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        info = (ShipStatusBean) data;
                        content.setText("承运来源：" + "" + shipping_name);
                        logisticsId.setText("运单编号：" + invoice_no);
                        tvStatus.setText("1".equals(info.getIscheck()) ? "已签收" : "");
//                        if (null == info.getNu()) {
//                            logisticsId.setText("运单编号：");
//                        } else {
//                            logisticsId.setText("运单编号：" + info.getNu());
//                        }
                        officialPhone.setText("官方电话：" + "");
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LogisticsActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        logisticsList.setLayoutManager(linearLayoutManager);
                        info.getData();
                        adapter = new MultiTypeAdapter(info.getData(), LogisticsActivity.this);
                        logisticsList.setAdapter(adapter);
                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tvNoContent.setVisibility(View.VISIBLE);
                            logisticsList.setVisibility(View.GONE);
                            shipTitle.setVisibility(View.GONE);
                        }
                    });
                }
            });
            ;
        }
    }

    @Override
    protected void loadData() {

//        UserDAO.getMessageList(new BaseCallBack() {
//            @Override
//            public void success(Object data) {
//                MessageDetailList info = (MessageDetailList) data;
//                list = info.getShipList();
//                adapter.refreshData(list);
//            }
//
//            @Override
//            public void failed(int errorCode, Object data) {
//                showShortToast("" + data);
//            }
//        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
