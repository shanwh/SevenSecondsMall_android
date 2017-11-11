package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Order.GoodsDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.GoodListBean;
import com.yidu.sevensecondmall.bean.Others.goodlistBean;
import com.yidu.sevensecondmall.networks.HttpApi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/6.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<GoodListBean> list = new ArrayList<>();
    private LayoutInflater inflater;

    public SearchResultAdapter(Context context, ArrayList<GoodListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(ArrayList<GoodListBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(inflater.inflate(R.layout.layout_main_listitem, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchResultViewHolder) {
//            SearchResultViewHolder viewHolder = (SearchResultViewHolder) holder;
            GoodListBean bean = list.get(position);
            final int goodsId = Integer.parseInt(bean.getGoods_id());
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(bean.getOriginal_img() + ""))
//                    .asBitmap()
//                    .centerCrop()
                    .placeholder(R.drawable.default_loading_pic)
                    .dontAnimate()
                    .into(((SearchResultViewHolder) holder).goodsPic);
            ((SearchResultViewHolder) holder).inText.setText(bean.getGoods_name());
            ((SearchResultViewHolder) holder).price.setText("￥" + bean.getShop_price());
            ((SearchResultViewHolder) holder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, GoodsDetailActivity.class);
                    i.putExtra("id", goodsId);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_pic)
        ImageView goodsPic;
        @BindView(R.id.in_text)
        TextView inText;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.item)
        LinearLayout item;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
