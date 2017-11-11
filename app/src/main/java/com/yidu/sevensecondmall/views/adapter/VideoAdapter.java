package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Video.VideoListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/8/21.
 */
public class VideoAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<VideoListBean> datas;
    private LayoutInflater inflater;

    public VideoAdapter(Context context, List<VideoListBean> datas) {
        this.context =context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(inflater.inflate(R.layout.item_live_anchor,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VideoHolder) {
            VideoListBean bean = datas.get(position);
            ((VideoHolder) holder).tvLiveStatus.setText(bean.getStatus());
            ((VideoHolder) holder).tvWatcherNum.setText(bean.getWatcherNum());
            //((VideoHolder) holder).ivPhoto.setImageBitmap();
            ((VideoHolder) holder).tvTitle.setText(bean.getTitle());
            ((VideoHolder) holder).tvStoreName.setText(bean.getStoreName());
            ((VideoHolder) holder).tvLikesNum.setText(bean.getLikesNum());
            ((VideoHolder) holder).rlAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.live_status)
        TextView tvLiveStatus;
        @BindView(R.id.tv_watcher_number)
        TextView tvWatcherNum;
        @BindView(R.id.seller_photo)
        ImageView ivPhoto;
        @BindView(R.id.seller_set_title)
        TextView tvTitle;
        @BindView(R.id.seller_store_name)
        TextView tvStoreName;
        @BindView(R.id.likes_number)
        TextView tvLikesNum;
        @BindView(R.id.rl_attention)
        RelativeLayout rlAttention;
        public VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
