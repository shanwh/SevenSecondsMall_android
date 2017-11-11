package com.yidu.sevensecondmall.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.GoodInfoBean;
import com.yidu.sevensecondmall.i.IWebViewTop;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/11.
 */
public class DetailFragment extends BaseFragment {

    @BindView(R.id.detaillist)
    ListView detaillist;
    private TextView tv_service;
    private IWebViewTop iWebViewTop;
    private List<String> list = new ArrayList<>();
    private GoodInfoBean bean;


    @Override
    protected int setViewId() {
        return R.layout.layout;
    }

    @Override
    protected void findViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        bean = getFragmentManager()
                .findFragmentByTag("dfragment")
                .getArguments()
                .getParcelable("bean");
        if (bean != null) {
            getData();
            detaillist.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_standard, list));
        }

    }

    public void getData() {
        try {
            list.add("名称：" + bean.getGoods().getGoods_name());
            list.add("单号：" + bean.getGoods().getGoods_sn());
            list.add("重量：" + bean.getGoods().getWeight());
            list.add("数量：" + bean.getGoods().getStore_count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadData() {

    }


}
