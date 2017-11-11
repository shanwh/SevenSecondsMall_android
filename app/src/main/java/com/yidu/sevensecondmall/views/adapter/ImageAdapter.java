package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.ImageBean;
import com.yidu.sevensecondmall.networks.HttpApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ImageAdapter extends RecyclerView.Adapter {

    private static final int HEAD_TYPE = 00011;
    private static final int BODY_TYPE = 00022;
    private Context context;
    private LayoutInflater inflater;
    private List<ImageBean> list;
    private int headCount = 1;

    public ImageAdapter(Context context, ArrayList<ImageBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHead(position)) {
            return HEAD_TYPE;
        } else {
            return BODY_TYPE;
        }
    }

    private boolean isHead(int position) {
        return headCount != 0 && (position >= (list.size()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD_TYPE:
                return new HeadViewHolder(inflater.inflate(R.layout.recyleview_head_goods, parent, false));
            case BODY_TYPE:
                return new MyHolder(inflater.inflate(R.layout.item_image_inter, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeadViewHolder) {
            holder.itemView.setVisibility(View.VISIBLE);
        } else if (holder instanceof MyHolder) {
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position).getAd_code()))
//                    .placeholder(R.drawable.default_loading_pic)
                    .into(((MyHolder) holder).ivContent);
            handlerMyHolder(holder, position);
        }

    }

    private void handlerMyHolder(RecyclerView.ViewHolder holder, int position) {

        final ImageBean bean = list.get(position);
        ((MyHolder) holder).itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(null != bean.getAd_id() ){
                            Log.e("ImageAdapter_ad_id",bean.getAd_id());
                            Intent i = new Intent(context, SearchActivity.class);
                            i.putExtra("ad_id", bean.getAd_id());
                            context.startActivity(i);
                        }else

                        if (null != bean.getType() && bean.getType().equals("search_id") && bean.getCat_id() != null) {
                            Intent i = new Intent(context, SearchActivity.class);
                            i.putExtra("id", Integer.parseInt(bean.getCat_id()));
                            context.startActivity(i);
                        } else if (null != bean.getType() && bean.getType().equals("search_keywords") && bean.getKeywords() != null) {
                            Intent i = new Intent(context, SearchActivity.class);
                            i.putExtra("keyword", bean.getKeywords());
                            context.startActivity(i);
                        }
                    }
                });
    }


    @Override
    public int getItemCount() {
        return list.size() + headCount;
    }

    /**
     * 设置头部单行显示
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == HEAD_TYPE
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_content)
        ImageView ivContent;

        View itemView;

        public MyHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private static class HeadViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public HeadViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
