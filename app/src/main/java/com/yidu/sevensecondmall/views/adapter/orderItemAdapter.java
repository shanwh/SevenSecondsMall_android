package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Order.AddCommentsActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.holder.OrderAllHolder;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/12.
 */
public class orderItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<OrderListBean.GoodsListBean> list;
    private boolean comment = false;
    private int index;
    private OrderListBean model;
    private OrderAllHolder allHolder;

    public orderItemAdapter(Context context, List<OrderListBean.GoodsListBean> list,boolean comment, OrderListBean model, OrderAllHolder allHolder) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.comment = comment;
        this.model = model;
        this.allHolder = allHolder;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new itemViewHolder(inflater.inflate(R.layout.order_list_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof itemViewHolder) {
            ((itemViewHolder) holder).tvName.setText(list.get(position).getGoods_name());
            ((itemViewHolder) holder).tvPrice.setText(list.get(position).getGoods_price());
            ((itemViewHolder) holder).tv_old_price.setText(list.get(position).getCost_price());
            ((itemViewHolder) holder).tv_describe.setText("");
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position).getOriginal_img() + ""))
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.drawable.default_loading_pic)
                    .into(((itemViewHolder) holder).iv);
            if(comment){
                ((itemViewHolder) holder).tvNext.setVisibility(View.INVISIBLE);
                ((itemViewHolder) holder).tvNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,AddCommentsActivity.class);
                        i.putExtra("id",list.get(position).getGoods_id());
                        i.putExtra("orderid",list.get(position).getOrder_id());
                        i.putExtra("img",list.get(position).getOriginal_img());
                        context.startActivity(i);
                    }
                });
            }else {
                ((itemViewHolder) holder).tvNext.setVisibility(View.INVISIBLE);
            }

            ((itemViewHolder)holder).rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    EventBus.getDefault().post(new LoadDataEvent(IEventTag.TO_DETAIL, index));
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

        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.if_tv)
        IconFontTextView ifTv;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_next)
        TextView tvNext;
        @BindView(R.id.rl_item)
        RelativeLayout rl_item;
        @BindView(R.id.tv_old_price)
         TextView tv_old_price;
        @BindView(R.id.tv_describe)
        TextView tv_describe;

        public itemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
