package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.holder.OrderAllHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/17.
 */
public class smallImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context context;
    private LayoutInflater inflater;
    private List<OrderListBean.GoodsListBean> list;
    private OrderListBean model;
    private OrderAllHolder allHolder;

    public smallImageAdapter(Context context, List<OrderListBean.GoodsListBean> list, OrderListBean model, OrderAllHolder allHolder) {
        this.context = context;
        this.list = list;
        this.model = model;
        this.allHolder = allHolder;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new itemViewHolder(inflater.inflate(R.layout.small_item_layout, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof itemViewHolder){
            Log.e("img",list.get(position).getOriginal_img()+""+"position: "+position);
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position).getOriginal_img()))
                    .asBitmap()
                    .centerCrop()
                    .into(((itemViewHolder) holder).smallImg);
            ((itemViewHolder)holder).smallImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allHolder.toDetail(model);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.small_img)
        ImageView smallImg;

        public itemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
