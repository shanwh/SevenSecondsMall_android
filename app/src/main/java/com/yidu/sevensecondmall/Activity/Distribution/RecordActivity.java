package com.yidu.sevensecondmall.Activity.Distribution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/11 0011.
 * 已弃用
 */
public class RecordActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.if_tv_day_li)
    IconFontTextView ifTvDayLi;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.rv)
    RecyclerView rv;

    MultiTypeAdapter adapter;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private ArrayList<Visitable> list = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.activity_record;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("奖金记录");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecordActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new MultiTypeAdapter(list);
        rv.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        DistributionDAO.getRebateList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                list = (ArrayList<Visitable>) data;
                adapter.refreshData(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (!LoginUtils.isLogin()) RecordActivity.this.finish();
            }
        });
    }


    @OnClick({R.id.back, R.id.if_tv_day_li})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.if_tv_day_li:

                break;
        }
    }

}
