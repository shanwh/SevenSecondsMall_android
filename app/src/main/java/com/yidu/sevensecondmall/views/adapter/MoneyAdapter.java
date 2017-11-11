package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.UserCenter.MyMoneyDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Payment.CountBean;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/5.
 */
public class MoneyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private  ArrayList<CountBean> list = new ArrayList<>();

    public MoneyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(ArrayList<CountBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.layout_monryitem, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.size()>0){
            CountBean bean = list.get(position);
            ((ItemViewHolder)holder).tvName.setText(bean.getDesc());
            int change_time = bean.getChange_time();
            String s = TimeFormatUtils.formatC(change_time * 1000L);
            ((ItemViewHolder)holder).tvTime.setText(s);
            String user_money = bean.getUser_money();
            if (user_money.startsWith("-")){
                ((ItemViewHolder)holder).tvCount.setTextColor(ContextCompat.getColor(context, R.color.colorBottomBlack));
            }else {
                ((ItemViewHolder)holder).tvCount.setTextColor(ContextCompat.getColor(context, R.color.app_theme));
            }
            ((ItemViewHolder)holder).tvCount.setText(user_money);
            ((ItemViewHolder)holder).rlItem.setOnClickListener(new OnItemClickListener(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_item)
        LinearLayout rlItem;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_count)
        TextView tvCount;//+为红色-为黑色
        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class OnItemClickListener implements View.OnClickListener{
        private int position;

        public OnItemClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            CountBean bean = list.get(position);
            String desc = bean.getDesc();
            String user_money = bean.getUser_money();
            String time = TimeFormatUtils.formatC(bean.getChange_time()*1000L);
            String order_sn = bean.getOrder_sn();
            String type = user_money.startsWith("-")? "转出": "转入";
            Intent intent = new Intent(context, MyMoneyDetailActivity.class);
            intent.putExtra("desc", desc);
            intent.putExtra("user_money", user_money);
            intent.putExtra("time", time);
            intent.putExtra("order_sn", order_sn);
            intent.putExtra("type", type);
            context.startActivity(intent);
        }
    }
}
