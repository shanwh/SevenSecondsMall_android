package com.yidu.sevensecondmall.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.GameBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.views.adapter.GameAdapter;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;
import com.yidu.sevensecondmall.widget.RecyclerViewHeader;
import com.yidu.sevensecondmall.widget.convenientbanner.CBViewHolderCreator;
import com.yidu.sevensecondmall.widget.convenientbanner.ConvenientBanner;
import com.yidu.sevensecondmall.widget.convenientbanner.holder.GameBannerHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/25.
 */

public class GameFragment extends BaseFragment {
    @BindView(R.id.recycle_game)
    RecyclerView recycleGame;
    Unbinder unbinder;
    GameAdapter adapter;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    Unbinder unbinder1;
    private RecyclerViewHeader header;
    private ConvenientBanner banner;

    @Override
    protected int setViewId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void findViews(View view) {
        toolbarTitle.setText("游戏");
        recycleGame.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GameAdapter(getActivity());
        recycleGame.setAdapter(adapter);
        header = RecyclerViewHeader.fromXml(getActivity(), R.layout.fragment_game_banner);
        banner = (ConvenientBanner) header.findViewById(R.id.main_banner);
        banner.setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_focus}).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
        header.attachTo(recycleGame);
        recycleGame.addItemDecoration(new DividerItemDecoration(getActivity()));
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        OkHttpUtil.downJSON(HttpApi.getGameURL, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("游戏===========url", url);
                Log.e("游戏==========json", json);
                GameBean bean = new Gson().fromJson(json, GameBean.class);
                if (bean.getCode() == 1) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public GameBannerHolder createHolder() {
                            return new GameBannerHolder();
                        }
                    }, bean.getResult().getBanner()).startTurning(4000);
                    adapter.addAll(bean.getResult().getGameList());
                }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        unbinder1.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
