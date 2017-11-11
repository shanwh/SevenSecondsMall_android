package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.Activity.Order.GoodsDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/24.
 */

public class MainItem3ItemAdapter extends RecyclerAdapter<MainBean.ResultBean.ShopBean.GoodsBean, MainItem3ItemAdapter.MyHolder> {


    public MainItem3ItemAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item3_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final MainBean.ResultBean.ShopBean.GoodsBean goodsBean = getItem(position);
        if (goodsBean != null) {
            holder.iv.setImageURI(Uri.parse(goodsBean.getOriginal_img()));
//            holder.instruction.setText(goodsBean.getGoods_remark());
            holder.price.setText("¥" + goodsBean.getShop_price());
            holder.tvTitle.setText(goodsBean.getGoods_name());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, GoodsDetailActivity.class);
                    i.putExtra("id",Integer.valueOf(goodsBean.getGoods_id()));
                    context.startActivity(i);

                }
            });

        }
    }

    class MyHolder extends BaseHolder {
        @BindView(R.id.iv)
        SimpleDraweeView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.instruction)
        TextView instruction;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;
        @BindView(R.id.price)
        TextView price;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
