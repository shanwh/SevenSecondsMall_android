package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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

public class MainitemAdapter extends RecyclerAdapter<MainBean.ResultBean.ZoneBean.ListBean, MainitemAdapter.MyAdapter> {


    public MainitemAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.recycle_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter holder, final int position) {
        final MainBean.ResultBean.ZoneBean.ListBean listBean =  getItem(position);
        if(listBean != null){
            holder.tv.setText("¥"+listBean.getPrice());
            holder.ivTitle.setImageURI(Uri.parse(listBean.getPic()));
            holder.item_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, GoodsDetailActivity.class);
                    i.putExtra("id",Integer.valueOf(listBean.getGoods_id()));
                    context.startActivity(i);
                }
            });
        }
    }

    class MyAdapter extends BaseHolder {
        @BindView(R.id.iv_title)
        SimpleDraweeView ivTitle;
        @BindView(R.id.item_linear)
        LinearLayout item_linear;
        @BindView(R.id.tv)
        TextView tv;

        public MyAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
