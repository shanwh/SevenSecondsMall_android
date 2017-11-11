package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/23.
 */

public class MainItem3Adapter extends RecyclerAdapter<MainBean.ResultBean.ShopBean, MainItem3Adapter.MyAdapterHolder> {

    private Context context;

    public MainItem3Adapter(Context context) {
        this.context = context;

    }

    @Override
    public MyAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapterHolder(LayoutInflater.from(context).inflate(R.layout.main_item3, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapterHolder holder, final int position) {
        final MainBean.ResultBean.ShopBean bean = getItem(position);
        if (bean != null) {
            MainItem3ItemAdapter mainItem3ItemAdapter = new MainItem3ItemAdapter(context);
            holder.title.setText(bean.getTitle());
            holder.message.setText(bean.getZtitle());
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.recyclerItem3.setLayoutManager(manager);
            holder.recyclerItem3.setAdapter(mainItem3ItemAdapter);
            mainItem3ItemAdapter.addAll(bean.getGoods());
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtil.showToast(context,"別点我");
                    Intent i = new Intent(context, SearchActivity.class);
                    i.putExtra("nav_type",bean.getNav_type());
                    i.putExtra("more_id",bean.getMore_id());
                    context.startActivity(i);
                }
            });
        }
    }

    public class MyAdapterHolder extends BaseHolder {
        @BindView(R.id.more)
        LinearLayout more;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.message)
        TextView message;
        @BindView(R.id.recycler_item3)
        RecyclerView recyclerItem3;

        public MyAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
