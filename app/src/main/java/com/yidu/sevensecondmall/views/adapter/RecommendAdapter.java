package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.AddVelocityDetailBean;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/3.
 */

public class RecommendAdapter extends RecyclerViewAdapter<AddVelocityDetailBean.ResultBean.RandomGoodsBean, RecommendAdapter.MyHolder> {

private Context context;

    public RecommendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_recommend;
    }

    @Override
    public MyHolder getViewHolder(View view) {
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        AddVelocityDetailBean.ResultBean.RandomGoodsBean bean = getItem(position);
        if(bean != null){
            holder.iv.setImageURI(Uri.parse(bean.getOriginal_img()));
            holder.tvTitle.setText(bean.getGoods_name());
            holder.price.setText(bean.getShop_price());

        }
    }

    class MyHolder extends BaseHolder {
        @BindView(R.id.iv)
        SimpleDraweeView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.instruction)
        TextView instruction;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
