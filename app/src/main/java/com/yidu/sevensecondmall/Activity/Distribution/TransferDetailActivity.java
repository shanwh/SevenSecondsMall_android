package com.yidu.sevensecondmall.Activity.Distribution;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.TransferDetailBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.TransferDetailAdapter;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;
import com.yidu.sevensecondmall.widget.RecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/1.
 */

public class TransferDetailActivity extends BaseActivity {
    @BindView(R.id.rc_transfer_detail)
    RecyclerView rcTransferDetail;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private TransferDetailAdapter adapter;
    private ArrayList<Integer> list;

    @Override
    protected int setViewId() {
        return R.layout.activity_transfer_detail;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("参与会员");
        rcTransferDetail.setLayoutManager(new LinearLayoutManager(this));
        rcTransferDetail.addItemDecoration(new DividerItemDecoration(this));
        adapter = new TransferDetailAdapter(this);
        rcTransferDetail.setAdapter(adapter);
        rcTransferDetail.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMore(++page);
                Log.e("参与会员===page",page+"");
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
    }

    int page = 1;

    @Override
    protected void loadData() {
        loadMore(page);


    }

    private void loadMore(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("order_id", "1619");
        params.put("page", page + "");
        params.put("token", LoginUtils.getToken());
        OkHttpUtil.postSubmitForm(HttpApi.getQuickOrderUserList, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("json", json);
                TransferDetailBean bean = new Gson().fromJson(json, TransferDetailBean.class);
                if (bean.getCode() == 0) {
                    if (bean.getResult().size() > 0 && bean.getResult() != null) {
                        adapter.addAll(bean.getResult());
                    }
                } else {
                    ToastUtil.showToast(TransferDetailActivity.this, bean.getMessage());
                }


            }

            @Override
            public void onFailure(String url, String error) {

            }
        });

    }

}
