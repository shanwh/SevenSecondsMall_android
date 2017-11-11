package com.yidu.sevensecondmall.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.BannerBean;
import com.yidu.sevensecondmall.bean.Main.ShopListBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.MainRefresh;
import com.yidu.sevensecondmall.utils.EasyPermissionsEx;
import com.yidu.sevensecondmall.utils.OnRcvScrollListener;
import com.yidu.sevensecondmall.views.adapter.MainRecylceAdapter;
import com.yidu.sevensecondmall.views.widget.SpacesItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/3/13.
 */
public class MallFragment extends BaseFragment {

    @BindView(R.id.shopgoods_list)
    RecyclerView shopgoodsList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private MainRecylceAdapter mainRecylceAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<View> viewlist = new ArrayList<>();
    private BannerBean bean;
    private List<BannerBean.BannerlistBean> list = new ArrayList<>();
    private BGABanner.Adapter bannerAdapter;
    private ShopListBean shopbean;
    private List<ShopListBean.GoodsListBean> shoplist = new ArrayList<>();
    private int currentpage = 0;
    private ArrayList<TextView> textlist = new ArrayList<>();
    private int totalpage;
    private int nowpage = 0;
    private RelativeLayout error;
    private boolean isFirstCreate = true;

    public MallFragment() {
//        EventBus.getDefault().register(this);
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
        error = (RelativeLayout) view.findViewById(R.id.error);

    }

    @Override
    protected void initEvent() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(MainRefresh event) {
        switch (event.functionTag) {
            case IEventTag.REFRSHITEM:
                if (!EasyPermissionsEx.isNetworkAvailable(getActivity())) {
                    error.setVisibility(View.VISIBLE);
                } else {
                    error.setVisibility(View.INVISIBLE);
                }
                GoodsDAO.GetBanner(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            BannerBean bean = (BannerBean) data;
                            mainRecylceAdapter.setHeader(bean);
                            error.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(getActivity(), "" + data, Toast.LENGTH_SHORT).show();
                        error.setVisibility(View.VISIBLE);
                    }
                });

                GoodsDAO.GetShopList(1, "is_recommend", "asc", new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if(!isFirstCreate){
                            ShopListBean bean2 = (ShopListBean) data;
                            shoplist.clear();
                            shoplist.addAll(bean2.getGoodsList());
                            mainRecylceAdapter.notifyDataSetChanged();
                        }else {
                            //设置mainrecycle
                            shopgoodsList.setAdapter(mainRecylceAdapter);
                            shopgoodsList.addItemDecoration(new SpacesItemDecoration(1));
                            shopbean = (ShopListBean) data;
                            shoplist = shopbean.getGoodsList();
                            totalpage = Integer.parseInt(shopbean.getTotal_page());
                            nowpage = 1;
                            //设置商品的recylceview
                            mainRecylceAdapter.setItems(shoplist);
                            isFirstCreate = false;
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        Toast.makeText(getActivity(),""+data,Toast.LENGTH_SHORT).show();
                    }
                });

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(LoginEvent event){
        switch(event.founctionTag){
            case IEventTag.REFRESHNAV:
                break;
        }
    }

    @Override
    protected void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mainRecylceAdapter = new MainRecylceAdapter(getActivity());
        shopgoodsList.setLayoutManager(layoutManager);
    }

    @Override
    protected void loadData() {
        if (!EasyPermissionsEx.isNetworkAvailable(getActivity())) {
            error.setVisibility(View.VISIBLE);
        } else {
            error.setVisibility(View.INVISIBLE);
        }
        EventBus.getDefault().post(new MainRefresh(IEventTag.REFRSHITEM));
//        GoodsDAO.GetBanner(new BaseCallBack() {
//            @Override
//            public void success(Object data) {
//                if (data != null) {
//                    BannerBean bean = (BannerBean) data;
//                    mainRecylceAdapter.setHeader(bean);
//                    error.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void failed(int errorCode, Object data) {
//                Toast.makeText(getActivity(), "" + data, Toast.LENGTH_SHORT).show();
//                error.setVisibility(View.VISIBLE);
//            }
//        });
//
//
//        GoodsDAO.GetShopList(1, "is_recommend", "asc", new BaseCallBack() {
//            @Override
//            public void success(Object data) {
//                if (data != null) {
//
//                }
//            }
//
//            @Override
//            public void failed(int errorCode, Object data) {
//                Toast.makeText(getActivity(), "" + data, Toast.LENGTH_SHORT).show();
//            }
//        });
//        EventBus.getDefault().post(new MainRefresh(IEventTag.REFRSHITEM));

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(true);
                if(EasyPermissionsEx.isNetworkAvailable(getActivity())){
                    String type = switchState(currentpage);
                    GoodsDAO.GetShopList(1, "is_recommend", "asc", new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            nowpage = 1;
                            shoplist.clear();
                            ShopListBean bean2 = (ShopListBean) data;
                            List<ShopListBean.GoodsListBean> list2 = bean2.getGoodsList();
                            totalpage = Integer.parseInt(bean2.getTotal_page());
                            shoplist.addAll(list2);
                            mainRecylceAdapter.notifyDataSetChanged();
                            error.setVisibility(View.INVISIBLE);
                            refresh.setRefreshing(false);
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            refresh.setRefreshing(false);
                            Toast.makeText(getActivity(),""+data,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT).show();
                    refresh.setRefreshing(false);
                }

            }
        });

        shopgoodsList.setOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void isBottom() {
                if(EasyPermissionsEx.isNetworkAvailable(getActivity())){
                    nowpage++;
                    if (nowpage <= totalpage) {
                        String type = switchState(currentpage);
                        GoodsDAO.GetShopList(nowpage, "is_recommend", "asc", new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                if (data != null) {
                                    ShopListBean bean2 = (ShopListBean) data;
                                    List<ShopListBean.GoodsListBean> list2 = bean2.getGoodsList();
                                    totalpage = Integer.parseInt(bean2.getTotal_page());
                                    shoplist.addAll(list2);
                                    mainRecylceAdapter.notifyDataSetChanged();
                                    error.setVisibility(View.INVISIBLE);
                                }

                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                Toast.makeText(getActivity(), "" + data, Toast.LENGTH_SHORT).show();
                                error.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        nowpage--;
                    }
                }else {
                    Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT).show();
                    refresh.setRefreshing(false);
                }

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }


    public String switchState(int currentpage) {
        String state = "";
        switch (currentpage) {
            case 0:
                state = "is_recommend";
                break;
            case 1:
                state = "sales_sum";
                break;
            case 2:
                state = "last_update";
                break;
            case 3:
                state = "shop_price";
                break;
        }
        return state;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
