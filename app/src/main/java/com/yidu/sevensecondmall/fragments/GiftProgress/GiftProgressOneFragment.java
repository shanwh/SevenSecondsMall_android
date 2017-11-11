package com.yidu.sevensecondmall.fragments.GiftProgress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.yidu.sevensecondmall.Activity.Distribution.TransferDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.GiftProgressBean;
import com.yidu.sevensecondmall.fragments.BaseFragment;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.GiftProgressAdapter.GiftProgressAllAdapter;
import com.yidu.sevensecondmall.views.adapter.GiftProgressAdapter.GiftProgressMyAdapter;
import com.yidu.sevensecondmall.views.adapter.RecommendAdapter;
import com.yidu.sevensecondmall.views.adapter.RecyclerAdapter;
import com.yidu.sevensecondmall.views.widget.DensityUtils;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;
import com.yidu.sevensecondmall.views.widget.itemdecorator.GridSpacingItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/7.
 */

public class GiftProgressOneFragment extends BaseFragment {

    public GiftProgressOneFragment instance;
    @BindView(R.id.rv_progress)
    RecyclerView rvProgress;
    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    Unbinder unbinder;

    private GiftProgressMyAdapter adapter;
    private RecommendAdapter myAdapter;
    public GiftProgressOneFragment getInstance(String type) {
//        if (instance == null) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        instance = new GiftProgressOneFragment();
        instance.setArguments(bundle);
//        }
        return instance;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_gift_progress_all;
    }
    String type;
    @Override
    protected void findViews(View view) {
        Bundle bundle = getArguments();
         type = bundle.getString("type");
        Log.e("type", type);
        //进度列表
        rvProgress.setHasFixedSize(true);
        rvProgress.setFocusable(true);
        rvProgress.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProgress.addItemDecoration(new DividerItemDecoration(getActivity(), 12));
        adapter = new GiftProgressMyAdapter(getActivity());
        rvProgress.setAdapter(adapter);

        myAdapter = new RecommendAdapter(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRecommend.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtils.dip2px(getContext(), 12), false));
        rvRecommend.setLayoutManager(manager);
        rvRecommend.setAdapter(myAdapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                startActivity(new Intent(getActivity(), TransferDetailActivity.class));
            }
        });

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    List<String> list;
    List<Integer> reList;

    @Override
    protected void loadData() {
//        list = new ArrayList<>();
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        list.add("");
//        adapter.addAll(list);
//
//        reList = new ArrayList<>();
//        reList.add(1);
//        reList.add(1);
//        reList.add(1);
//        reList.add(1);
//        reList.add(1);
//      myAdapter.addAll(reList);
        HashMap<String,String > params  = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put("type",type);
        OkHttpUtil.postSubmitForm(HttpApi.getUserFreeOrder, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("参与加速json"+type,json);
                GiftProgressBean bean = new Gson().fromJson(json,GiftProgressBean.class);
                if(bean.getCode() == 0){
                    if(bean.getResult().getList()!= null && bean.getResult().getList().size()>0){
                        adapter.addAll(bean.getResult().getList());
                    }
                }else{
                    ToastUtil.showToast(getContext(),bean.getMessage());
                }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
