package com.yidu.sevensecondmall.fragments.Store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.MessageActivity;
import com.yidu.sevensecondmall.QrCode.QrCodeActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.fragments.BaseFragment;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.PermissionUtil;
import com.yidu.sevensecondmall.views.adapter.MainFragmentAdapter;
import com.yidu.sevensecondmall.widget.RecyclerViewHeader;
import com.yidu.sevensecondmall.widget.convenientbanner.CBViewHolderCreator;
import com.yidu.sevensecondmall.widget.convenientbanner.ConvenientBanner;
import com.yidu.sevensecondmall.widget.convenientbanner.holder.ImageViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/10/18.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.recycler_main)
    RecyclerView recyclerMain;
    Unbinder unbinder;
    @BindView(R.id.layout_main)
    LinearLayout layout_main;
    @BindView(R.id.main_iv_saoyisao)
    ImageView mainIvSaoyisao;
    @BindView(R.id.main_rl_search)
    RelativeLayout mainRlSearch;
    @BindView(R.id.main_iv_message)
    ImageView mainIvMessage;

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private ConvenientBanner banner;
    private RecyclerViewHeader header;
    private MainFragmentAdapter adapter;

    private List<List> list = new ArrayList<>();

    private int mDistanceY = 0;

    @Override
    protected int setViewId() {
        return R.layout.fragment_main_copy;
    }

    @Override
    protected void initEvent() {
        mainIvMessage.setOnClickListener(this);
        mainIvSaoyisao.setOnClickListener(this);
        mainRlSearch.setOnClickListener(this);
    }

    @Override
    protected void findViews(View view) {
        recyclerMain.setHasFixedSize(true);
        recyclerMain.setFocusable(true);
        recyclerMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        header = RecyclerViewHeader.fromXml(getActivity(), R.layout.fragment_main_banner);
        banner = (ConvenientBanner) header.findViewById(R.id.main_banner);
        banner.setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_focus}).setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
//        adapter = new MainAdapter(getActivity());
//        recyclerMain.setAdapter(adapter);
        header.attachTo(recyclerMain);
        recyclerMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //计算滑动的高度
                mDistanceY += dy;
                //toolbar的距离
                int toolbarHeight = layout_main.getBottom() * 3;
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    Log.e("scale=====", alpha + "");
                    layout_main.setBackgroundColor(Color.argb((int) alpha, 245, 245, 245));
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
//                    toolbar.setBackgroundResource(R.color.colorPrimary);
                    layout_main.setBackgroundColor(Color.argb(255, 245, 245, 245));
                }
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        OkHttpUtil.downJSON(HttpApi.getMainURL, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("首页URL", url);
                MainBean bean = new Gson().fromJson(json, MainBean.class);
                if (bean.getCode() == 0) {
                    banner.setPages(new CBViewHolderCreator() {
                        @Override
                        public ImageViewHolder createHolder() {
                            return new ImageViewHolder();
                        }
                    }, bean.getResult().getBanner()).startTurning(4000);

                    list.add(bean.getResult().getNav());
                    list.add(bean.getResult().getZone());
                    list.add(bean.getResult().getShop());
//                    adapter.addAll(bean);
                    adapter = new MainFragmentAdapter(getActivity(), list);
                    recyclerMain.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
        if (banner != null) {
            banner.stopTurning();
        }
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(getActivity(), QrCodeActivity.class));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_saoyisao:
                try {
                    String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                    if (!PermissionUtil.hasPermission(getActivity(), writeExternalStorage)) {
                        PermissionUtil.reqPermission(getActivity(), SCANNIN_GREQUEST_CODE, new String[]{writeExternalStorage});
                    } else {
                        //有权限，直接拍照
                        startActivity(new Intent(getActivity(), QrCodeActivity.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.main_rl_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.main_iv_message:
                if (LoginUtils.isLogin()) {
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }

}
