package com.yidu.sevensecondmall.Activity.Distribution;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/19 0019.
 * 已弃用
 */
public class MyRootActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.if_tv_add)
    IconFontTextView ifTvAdd;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.rv)
    RecyclerView rv;

    MultiTypeAdapter adapter;
    ArrayList<Visitable> list = new ArrayList<>();
    private Activity context;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_root;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        context = MyRootActivity.this;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new MultiTypeAdapter(list);
        rv.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        DistributionDAO.getMyJurisdiction(new BaseCallBack() {
            @Override
            public void success(Object data) {
                list = (ArrayList<Visitable>) data;
                adapter.refreshData(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context, data+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
