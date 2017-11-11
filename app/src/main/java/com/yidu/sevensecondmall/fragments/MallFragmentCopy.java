package com.yidu.sevensecondmall.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.MainActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.BanBean;
import com.yidu.sevensecondmall.bean.Main.BanListBean;
import com.yidu.sevensecondmall.bean.Main.HomeGoodBean;
import com.yidu.sevensecondmall.bean.Main.HomeGoodListBean;
import com.yidu.sevensecondmall.bean.Main.ImageBean;
import com.yidu.sevensecondmall.bean.Main.ImageListBean;
import com.yidu.sevensecondmall.bean.Main.TitleBean;
import com.yidu.sevensecondmall.bean.Main.TitleBeanList;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IBottom;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.UpdateManager;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.CustomRecyclerView;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/13.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class MallFragmentCopy extends BaseFragment{
    private static final String TAG = "MallFragmentCopy";

    @BindView(R.id.custom_rv)
    CustomRecyclerView customRv;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    String downloadurl;
    String versionName;
    String tips;
    String title;
    private UpdateManager updateManager;
    private int currentPage = 1;
    public ArrayList<Visitable> dataList = new ArrayList<>();
    public ImageListBean listBean = new ImageListBean();
    public TitleBeanList titlelist = new TitleBeanList();
    private boolean tag = true;
    private boolean adsHasInit = false;
    private boolean goodsHasInit = false;

    @Override
    protected int setViewId() {
        return R.layout.fragment_mall_copy;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initEvent() {

    }

    private float fstartY=0,flastY=0,flastDeltaY;
    @Override
    protected void init() {
        customRv.addFindFirstViewListener();
        loadNetData();
//        customRv.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                Log.e("ONTOUTH","_________________________"+event.getY());
//                final float y=event.getY();
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        //手指放下
//                        fstartY=y;
//                        flastY=fstartY;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        //手指移动
//                        float fDetaY=y-flastY;
//                        float newtransY=translationY+fDetaY;
//                        int height= ((MainActivity)getActivity()).rlTitle.getHeight();
//                        if(height>0){
//                            ((MainActivity)getActivity()).rlTitle.setBackgroundColor(Color.parseColor("#eb8a2f"));
//                            float scale = 255/height;
//                            if(fDetaY<=height)
//                                ((MainActivity) getActivity()).rlTitle.getBackground().setAlpha((int)(fDetaY * scale));
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        //手指抬起
//                        break;
//                }
//                return false;
//            }
//        });
    }

    @Override
    protected void loadData() {
//        loadNetData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(LoadDataEvent event) {
        switch (event.functionTag) {
            case IEventTag.LOAD_DATA:
                currentPage = event.page;
                dataList.clear();
                if(currentPage == 1){
                    tag = true;
                    adsHasInit = false;
                    goodsHasInit = false;
                }
                loadNetData();
                break;
            case IEventTag.SCROLL_TO_TOP:
                customRv.scrollToTop();
                break;
        }
    }

    private void loadNetData() {
        String versionName = "1.0.0";
        try {
            // ---get the package info---
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), 0);
            versionName = pi.versionName;

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }

        if(!adsHasInit){
            initHomeAds();
        }

        UserDAO.startUp(currentPage, versionName, new BaseCallBack() {
            MultiTypeAdapter adapter;

            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("partner_order_fee")) {
                        SystemUtil.setSharedString("partner_order_fee", content.getString("partner_order_fee"));
                    }
                    if (content.has("upyun")) {
                        JSONObject upyun = content.getJSONObject("upyun");
                        HttpApi.uploadurl = upyun.getString("uploadurl");
                        HttpApi.policy = upyun.getString("policy");
                        HttpApi.signature = upyun.getString("signature");
                    }
                    if (content.has("bannerlist")) {
                        JSONArray bannerlist = content.getJSONArray("bannerlist");
                        ArrayList<BanBean> list = new ArrayList<>();
                        for (int i = 0; i < bannerlist.length(); i++) {
                            JSONObject jsonObject = bannerlist.getJSONObject(i);
                            BanBean bean = new BanBean();
                            bean.setAd_code(jsonObject.getString("ad_code"));
                            bean.setAd_name(jsonObject.getString("ad_name"));
                            bean.setType(jsonObject.getString("type"));
                            bean.setCat_id(jsonObject.getString("cat_id"));
                            bean.setKeywords(jsonObject.getString("keywords"));
                            list.add(bean);
                        }
                        BanListBean bean = new BanListBean();
                        bean.setList(list);
                        if (currentPage == 1) {
                            dataList.add(bean);
                        }
                    }
                    if (content.has("goodsList")) {
                        JSONArray goodsList = content.getJSONArray("goodsList");
                        ArrayList<Visitable> list = new ArrayList<>();
                        for (int i = 0; i < goodsList.length(); i++) {
                            JSONObject jsonObject = goodsList.getJSONObject(i);
                            HomeGoodListBean bean = new HomeGoodListBean();

                            bean.setGoods_id(jsonObject.getString("goods_id"));
                            bean.setGoods_name(jsonObject.getString("goods_name"));
                            bean.setShop_price(jsonObject.getString("shop_price"));
                            bean.setOriginal_img(jsonObject.getString("original_img"));
                            if (jsonObject.has("sales_sum")) {
                                bean.setBuy_people(jsonObject.getString("sales_sum"));
                            }
                            list.add(bean);
                        }
                        Log.e(TAG, "success: " + list.size());
                        HomeGoodBean bean = new HomeGoodBean();
                        if (currentPage == content.getInt("total_page")) {
                            bean.isList = true;
                        }
                        bean.setList(list);
                        dataList.add(bean);
                    }
                    if (content.has("total_page")) {
                        customRv.setTotalPage(content.getInt("total_page"));
                    }

                    adapter = customRv.getAdapter(((MainActivity)getActivity()).rlTitle,((MainActivity)getActivity()).v,true);
                    if (currentPage == 1) {
                        if (dataList.size() == 0) {
                            customRv.showEmptyView();
                        } else {
                            customRv.hideEmptyView();
                        }
                        adapter.refreshData(dataList);
                        if (customRv.getSwipeRefresh().isRefreshing()) {
                            showShortToast("刷新成功");
                        }
                        customRv.stopSwipeRefresh();
                    } else {
                        adapter.addMoreData(dataList);
                    }
                    customRv.setShowHasNotMoreData(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                goodsHasInit = true;
                showHomeAdsView();
//                if (tag) {
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (listBean.getList().size() <= 2) {
//                                try {
//                                    Thread.sleep(100);
//                                } catch (Exception e) {
//                                }
//                            } else {
//                                if (dataList.size() >= 2) {
//                                    dataList.add(1, titlelist);
//                                    dataList.add(2, listBean);
//                                    tag = false;
////                                    adapter.notifyDataSetChanged();
//
//                                    Log.e("test:","size::____:"+dataList.size());
//                                    adapter.refreshData(dataList);
//
//                                }
//                            }
//                        }
//                    });
//                }

            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast("" + data);
            }
        });
    }

    private void showHomeAdsView(){
        if(adsHasInit && goodsHasInit && tag) {
            tag = false;
            MultiTypeAdapter adapter = customRv.getAdapter(((MainActivity)getActivity()).rlTitle,((MainActivity)getActivity()).v,true);
            dataList.add(1, titlelist);
            dataList.add(2, listBean);

            Log.e("test:", "size::____:" + dataList.size());
            adapter.refreshData(dataList);
        }
    }

    /*
    *初始化广告图
     */
    private void initHomeAds(){

        GoodsDAO.home(new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());

                    JSONArray array = content.getJSONArray("zone");
                    ArrayList<ImageBean> arrayList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        ImageBean bean = new ImageBean();
                        if(jsonObject.has("cat_id")){
                            bean.setCat_id(jsonObject.getString("cat_id"));
                        Log.e("bean_________","bean_______________2"+bean.toString());
                        }
                        if(jsonObject.has("ad_name")){
                            bean.setAd_name(jsonObject.getString("ad_name"));
                        }
                        if(jsonObject.has("ad_code")){
                            bean.setAd_code(jsonObject.getString("ad_code"));
                        }
                        if(jsonObject.has("keywords")){
                            bean.setKeywords(jsonObject.getString("keywords"));
                        }
                        if(jsonObject.has("type")){
                            bean.setType(jsonObject.getString("type"));
                        }
                        if(jsonObject.has("ad_link")){
                            bean.setAd_link(jsonObject.getString("ad_link"));
                        }
                        if(jsonObject.has("ad_id")){
                            bean.setAd_id(jsonObject.getString("ad_id"));
                        }
                        Log.e("bean_________","bean_______________3"+bean.toString());
                        arrayList.add(bean);
                    }
                    listBean.setList(arrayList);

                    JSONArray arrayNAV = content.getJSONArray("nav");
                    ArrayList<TitleBean> tibeans = new ArrayList<>();
                    for (int i = 0; i < arrayNAV.length(); i++) {
                        JSONObject jo = arrayNAV.getJSONObject(i);
                        TitleBean titlebean = new TitleBean();
                        if(jo.has("cat_id")){
                        titlebean.setCat_id(jo.getString("cat_id"));}
                        if(jo.has("ad_name")){
                            titlebean.setAd_name(jo.getString("ad_name"));}
                        if(jo.has("ad_code")){
                            titlebean.setAd_code(jo.getString("ad_code"));}
                        if(jo.has("keywords")){
                            titlebean.setKeywords(jo.getString("keywords"));}
                        if(jo.has("type")){
                            titlebean.setType(jo.getString("type"));}
                        if(jo.has("ad_link")){
                            titlebean.setAd_link(jo.getString("ad_link"));}
                        tibeans.add(titlebean);
                    }
                    titlelist.setList(tibeans);
                    adsHasInit = true;
                    showHomeAdsView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
    }


    CustomPopWindow popupWindow;

    private void showPopupWindow() {
//        if (popupWindow2 != null) popupWindow2.dissmiss();
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.view_update_layout, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(customRv, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        TextView tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tv = (TextView) contentView.findViewById(R.id.tv);
        tv.setText(tips);

        contentView.findViewById(R.id.tv_update_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateManager == null) {
                    updateManager = new UpdateManager(getActivity());
                }
                updateManager.setI(new IBottom() {
                    @Override
                    public void isBottom(boolean bottom) {

                    }
                });
                if (!TextUtils.isEmpty(downloadurl)) {
                    updateManager.SetApkUrl(downloadurl);
                    updateManager.checkUpdateInfo();
                }
            }
        });
    }



}
