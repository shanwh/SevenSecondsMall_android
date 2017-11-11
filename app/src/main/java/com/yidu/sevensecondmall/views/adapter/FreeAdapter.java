package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.networks.HttpApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/1.
 */
public class FreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<OrderListBean.GoodsListBean> list;

    public FreeAdapter(Context context, List<OrderListBean.GoodsListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreeViewHolder(inflater.inflate(R.layout.free_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list!=null&&list.size()>0) {
            OrderListBean.GoodsListBean bean = list.get(position);
//            ((FreeViewHolder) holder).status.setText(bean.getGoods_name());
            ((FreeViewHolder) holder).name.setText(bean.getGoods_name());
            ((FreeViewHolder) holder).price.setText(bean.getGoods_price());
            Glide.with(context)
                    .load(HttpApi.getFullImageUrl(bean.getOriginal_img() + ""))
                    .placeholder(R.drawable.default_loading_pic)
                    .into(((FreeViewHolder) holder).head);
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    public class FreeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.status)
        TextView status;

        public FreeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
