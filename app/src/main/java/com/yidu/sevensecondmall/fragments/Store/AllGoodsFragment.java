package com.yidu.sevensecondmall.fragments.Store;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.GoodsBean;
import com.yidu.sevensecondmall.fragments.BaseFragment;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.GridItemDecoration;
import com.yidu.sevensecondmall.views.adapter.StoreGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/25.
 */
public class AllGoodsFragment extends BaseFragment {
    @BindView(R.id.tv_sort)
    TextView tvSort;
    @BindView(R.id.tv_sort_sales)
    TextView tvSortSales;
    @BindView(R.id.tv_sort_price)
    TextView tvSortPrice;
    @BindView(R.id.tv_sort_custom)
    TextView tvSortCustom;
    @BindView(R.id.rv_goods_list)
    RecyclerView goodsList;
    List<GoodsBean> datas = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.fragment_store_goods;
    }

    @Override
    protected void findViews(View view) {
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

        setAdapter(goodsList,datas,getActivity());
    }

    private void setAdapter(RecyclerView rv, List<GoodsBean> datas, Context context){
        GridLayoutManager manager = new GridLayoutManager(context,2);
        rv.setLayoutManager(manager);
        rv.addItemDecoration( new GridItemDecoration
                .Builder(context)
                .size(8)
                .color(R.color.white)
                .margin(0,0)
                .build());
        rv.setHasFixedSize(true);
        rv.setAdapter(new StoreGoodsAdapter(datas,context));
    }
}
