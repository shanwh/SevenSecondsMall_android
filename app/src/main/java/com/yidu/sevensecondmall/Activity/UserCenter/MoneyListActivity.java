package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.PaymentDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Payment.CountBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.MoneyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/5.
 */
public class MoneyListActivity extends BaseActivity {
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
    RecyclerView moneyList;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private LinearLayoutManager manager;
    private MoneyAdapter adapter;

    @Override
    protected int setViewId() {
        return R.layout.activity_moneylist;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("余额明细");
        toolbarTitle.setText("余额明细");

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        moneyList.setLayoutManager(manager);

        adapter = new MoneyAdapter(this);
        moneyList.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        PaymentDao.getAcountList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                ArrayList<CountBean> list = (ArrayList<CountBean>) data;
                adapter.refreshData(list);
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

}
