package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.Activity.WebViewActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */

public class MainItem2Adapter extends RecyclerAdapter<MainBean.ResultBean.ZoneBean, MainItem2Adapter.MyAdapterHolder> {


    private Context context;

    public MainItem2Adapter(Context context) {
        this.context = context;

    }

    @Override
    public MyAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapterHolder(LayoutInflater.from(context).inflate(R.layout.main_item2, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapterHolder holder, final int position) {
        final MainBean.ResultBean.ZoneBean bean = getItem(position);
        if (bean != null) {
            MainitemAdapter adapter = new MainitemAdapter(context);
            holder.title.setText(bean.getAd_name());
            holder.iv.setImageURI(Uri.parse(bean.getTubiao()));
            holder.ivHead.setImageURI(Uri.parse(bean.getBig_pic()));
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recyclerItem2.setLayoutManager(manager);
            holder.recyclerItem2.setAdapter(adapter);
            adapter.addAll(bean.getList());
            holder.ivHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(new Intent( context, WebViewActivity.class).putExtra("url",bean.getUrl()));
                    Intent i = new Intent(context, SearchActivity.class);
                    i.putExtra("nav_type", bean.getNav_type());
                    i.putExtra("more_id", bean.getMore_id());
                    context.startActivity(i);
                }
            });
        }
    }

    public class MyAdapterHolder extends BaseHolder {
        @BindView(R.id.iv)
        SimpleDraweeView iv;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.iv_head)
        SimpleDraweeView ivHead;
        @BindView(R.id.recycler_item2)
        RecyclerView recyclerItem2;

        public MyAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
