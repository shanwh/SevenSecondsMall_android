package com.yidu.sevensecondmall.Activity.UserCenter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CollectionBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.views.adapter.CollectionPagerAdapter;
import com.yidu.sevensecondmall.views.adapter.babyAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10.
 * 收藏activity
 */
public class CollectionActivity extends BaseActivity implements ViewPager.OnPageChangeListener {


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
    @BindView(R.id.more)
    IconFontTextView more;
    @BindView(R.id.c_viewpager)
    ViewPager cViewpager;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.tv_to_buy)
    TextView tvToBuy;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private RecyclerView babylistview;
    private View babyview;
    private View shopview;
    private RecyclerView shoplistview;
    private ArrayList<View> viewList = new ArrayList<View>();//view数组
//    private List<TextView> btnlist = new ArrayList<TextView>();//按键数组

    private CollectionPagerAdapter adapter;
    private babyAdapter babyadapter;
    private int currentPage;
    private int clickPage;
    private List<CollectionBean.ListBean> list = new ArrayList<>();
    private CollectionBean bean;

    @Override
    protected int setViewId() {
        return R.layout.activity_collection;

    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        titleName.setText("我的收藏");
        toolbarTitle.setText("我的收藏");
        initPager();
    }

    @Override
    protected void loadData() {
        GoodsDAO.GetCollectionGoods(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    bean = (CollectionBean) data;
                    list = bean.getList();
                    if (list.size() > 0) {
                        babyadapter = new babyAdapter(CollectionActivity.this, list);
                        babylistview.setAdapter(babyadapter);
                        llEmpty.setVisibility(View.GONE);
                    } else {
                        llEmpty.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode == 1001) {
                    list.clear();
                    if (babyadapter != null) {
                        babyadapter.notifyDataSetChanged();
                    }
                    llEmpty.setVisibility(View.VISIBLE);
                } else {
                    showShortToast(data + "");
                }
            }
        });
    }

    public void initPager() {
//        btnlist.add(tvIcon1);
//        btnlist.add(tvIcon2);
        LayoutInflater inflater = LayoutInflater.from(this);
        babyview = inflater.inflate(R.layout.layout_collectbaby, null);
        shopview = inflater.inflate(R.layout.layout_collectbaby, null);
        viewList.add(babyview);
        viewList.add(shopview);
        adapter = new CollectionPagerAdapter(viewList);
        cViewpager.setAdapter(adapter);
        cViewpager.setOnPageChangeListener(this);
        babylistview = (RecyclerView) babyview.findViewById(R.id.baby_list);
        shoplistview = (RecyclerView) babyview.findViewById(R.id.baby_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CollectionActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        babylistview.setLayoutManager(layoutManager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(RefreshEvent ev) {
        switch (ev.founctionTag) {
            case IEventTag.REFRSHCOLLECT:
                GoodsDAO.GetCollectionGoods(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            list.clear();
                            CollectionBean bean2 = (CollectionBean) data;
                            List<CollectionBean.ListBean> list2 = bean2.getList();
                            if (list2.size() > 0) {
                                list.addAll(list2);
                                babyadapter.notifyDataSetChanged();
                                llEmpty.setVisibility(View.GONE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        if (errorCode == 1001) {
                            list.clear();
                            if (babyadapter != null) {
                                babyadapter.notifyDataSetChanged();
                            }
                            llEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(CollectionActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @OnClick({R.id.back, R.id.more,
//            R.id.rl_icon1, R.id.rl_icon2
            R.id.tv_to_buy
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.more:
                break;
//            case R.id.rl_icon1:
//                clickPage = 0;
//                break;
//            case R.id.rl_icon2:
//                clickPage = 1;
//                break;
            case R.id.tv_to_buy:
                finish();
                EventBus.getDefault().post(new LoginEvent(IEventTag.SKIP_TO_HOME));
                break;
        }
//        if (clickPage != currentPage) {
//            cViewpager.setCurrentItem(clickPage);
//            btnlist.get(clickPage).setTextColor(getResources().getColor(R.color.colorBottomRed));
//        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if (position != currentPage) {
//            for (int i = 0; i < btnlist.size(); i++) {
//                btnlist.get(i).setTextColor(getResources().getColor(R.color.colorBottomBlack));
//            }
//            btnlist.get(position).setTextColor(getResources().getColor(R.color.colorBottomRed));
//        }
//        currentPage = cViewpager.getCurrentItem();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
