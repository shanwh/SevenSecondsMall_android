package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.networks.HttpApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/16.
 */
public class imgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;
    private boolean big = false;
    private int BIGPIC = 0;
    private int SMALLPIC = 1;

    public imgAdapter(Context context, List<String> list, boolean big) {
        this.list = list;
        this.context = context;
        this.big = big;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (big) {
            return BIGPIC;
        } else {
            return SMALLPIC;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BIGPIC) {
            return new imgViewHolder(inflater.inflate(R.layout.img_item, null));
        } else {
            return new smallViewHolder(inflater.inflate(R.layout.small_item_layout, null));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof imgViewHolder) {
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position)))
                    .placeholder(R.drawable.default_loading_pic)
                    .into(((imgViewHolder) holder).img);
        }else if(holder instanceof smallViewHolder){
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(list.get(position)))
                    .placeholder(R.drawable.default_loading_pic)
                    .into(((smallViewHolder) holder).smallImg);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class imgViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        public imgViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class smallViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.small_img)
        ImageView smallImg;

        public smallViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
