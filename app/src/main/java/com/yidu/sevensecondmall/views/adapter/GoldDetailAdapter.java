package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.holder.GoldDetailBean;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/27.
 */

public class GoldDetailAdapter extends RecyclerAdapter<GoldDetailBean.ResultBean.GameListBean, GoldDetailAdapter.MyHold> {
    Context context;

    public GoldDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHold(LayoutInflater.from(context).inflate(R.layout.item_gold_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHold holder, int position) {
        GoldDetailBean.ResultBean.GameListBean bean = getItem(position);
        if (bean != null) {
            holder.note.setText(bean.getNote());
            holder.daxiongCoin.setText(bean.getDaxiong_coin());
            holder.time.setText(bean.getDatetime());

        }
    }

    class MyHold extends BaseHolder {


        @BindView(R.id.note)
        TextView note;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.daxiong_coin)
        TextView daxiongCoin;

        public MyHold(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
