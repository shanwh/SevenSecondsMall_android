package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.GoodsBean;
import com.yidu.sevensecondmall.views.holder.StoreGoodsHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
public class StoreGoodsAdapter extends RecyclerView.Adapter {


    private Context context;
    private List<GoodsBean> models;
    private LayoutInflater inflater;

    public StoreGoodsAdapter(List<GoodsBean> models, Context context) {
        this.context = context;
        this.models = models;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StoreGoodsHolder(inflater.inflate(R.layout.item_store_goods, null), context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
