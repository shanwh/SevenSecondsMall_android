package com.yidu.sevensecondmall.Activity.Store;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.StoreBrandBean;
import com.yidu.sevensecondmall.bean.Others.StoreClassificationBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/23.
 */
public class GoodsClassificationActivity extends BaseActivity {

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.rv_list_class)
    RecyclerView rvClassList;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private List<Visitable> datas = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.activity_goods_classification;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("店铺分类");
        titleName.setText("店铺分类");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        // TODO: 2017/8/23 测试取datas

        datas.add(new StoreClassificationBean("周边"));
        datas.add(new StoreClassificationBean("全部周边"));
        datas.add(new StoreClassificationBean("电脑周边"));
        datas.add(new StoreClassificationBean("手机周边"));
        List<String> list = new ArrayList<>();
        list.add("iphone2");
        list.add("iphone3");
        list.add("iphone4");
        list.add("iphone5");
        list.add("iphone6");
        list.add("iphone7");
        list.add("iphone8");
        datas.add(new StoreBrandBean("苹果", list));
        datas.add(new StoreBrandBean("西瓜", list));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvClassList.setLayoutManager(manager);
        rvClassList.setAdapter(new MultiTypeAdapter(datas, this));
    }

}
