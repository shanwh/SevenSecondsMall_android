package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.GoldDetailAdapter;
import com.yidu.sevensecondmall.views.holder.GoldDetailBean;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;
import com.yidu.sevensecondmall.widget.RecyclerOnScrollListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/27.
 */

public class GameGoldDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recycle_detail)
    RecyclerView recycleDetail;
    GoldDetailAdapter adapter;
    int page = 1;
    int totalCount;

    @Override
    protected int setViewId() {
        return R.layout.activity_gold_detail;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("金币明细");
        recycleDetail.setLayoutManager(new LinearLayoutManager(this));
        recycleDetail.addItemDecoration(new DividerItemDecoration(this));
        adapter = new GoldDetailAdapter(this);
        recycleDetail.setAdapter(adapter);
        recycleDetail.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (page < totalCount) {
                    ++page;
                    load();
                }else{
                    ToastUtil.showToast(GameGoldDetailActivity.this, "到底了，无更多数据！！");
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override

    protected void loadData() {
        load();
    }

    private void load() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put("page", page + "");
        OkHttpUtil.postSubmitForm(HttpApi.getUserGameRecord, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("金币明细json", json);
                Log.e("金币明细url", url);
                GoldDetailBean bean = new Gson().fromJson(json, GoldDetailBean.class);
                if (bean.getCode() == 1) {
                    if (bean.getResult().getGameList() != null && bean.getResult().getGameList().size() > 0) {
                        adapter.addAll(bean.getResult().getGameList());
                        totalCount = bean.getResult().getPageCount();
                    } else {
                        ToastUtil.showToast(GameGoldDetailActivity.this, "无更多数据");
                    }
                } else {
                    ToastUtil.showToast(GameGoldDetailActivity.this, bean.getMessage());
                }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });

    }
}
