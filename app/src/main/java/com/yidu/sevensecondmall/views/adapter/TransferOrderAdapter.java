package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.Distribution.AddVelocityDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.TransferBean;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.DensityUtils;
import com.yidu.sevensecondmall.views.widget.itemdecorator.DividerItemDecoration;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;
import com.yidu.sevensecondmall.views.widget.widget.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/1.
 */

public class TransferOrderAdapter extends RecyclerViewAdapter<TransferBean.ResultBean.ListBean, TransferOrderAdapter.MyHolder> {


    private Context context;

    public TransferOrderAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getLayoutResId() {
        return R.layout.item_tandsfer_order;
    }

    @Override
    public MyHolder getViewHolder(View view) {
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        final TransferBean.ResultBean.ListBean bean = getItem(position);
        holder.rlProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.showToast(context, "progress" + position);
                context.startActivity(new Intent(context, AddVelocityDetailActivity.class).putExtra("order_id",bean.getOrder_id()));
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recycleImage.setLayoutManager(manager);
        TransferOrderItemAdapter adapter = new TransferOrderItemAdapter(context);
        adapter.setOnItemClickListener(null);
        holder.recycleImage.setAdapter(adapter);
        holder.tvName.setText(bean.getNickname());
        holder.orderNumber.setText("订单："+bean.getOrder_sn());
        holder.tvPrice.setText("剩余：¥"+bean.getRedis_amount());
        holder.time.setText("转让时间："+bean.getFirst_quick_time());
        holder.totalPrice.setText("总金额："+bean.getTotal_amount());
        holder.peopleNum.setText(bean.getCount());
        holder.progressNum.setText(bean.getRedis_rate()+"%");
        holder.tvMessage.setText("当前回购折扣："+bean.getTotal_amount()+"折，"+"天后降为"+"折");
        holder.circularProgressBar.setCircleWidth(DensityUtils.dip2px(context,7));
        holder.circularProgressBar.setMax(100);
        holder.circularProgressBar.setProgress(Float.valueOf(bean.getRedis_rate()));
        List<String> listImg = new ArrayList<>();
        for (int i = 0 ; i < bean.getOriginal_img().size();i ++){
            listImg.add(bean.getOriginal_img().get(i).getImgurl());
        }
        adapter.addAll(listImg);
    }

    class MyHolder extends BaseHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.total_price)
        TextView totalPrice;
        @BindView(R.id.recycle_image)
        RecyclerView recycleImage;
        @BindView(R.id.tv_message)
        TextView tvMessage;
        @BindView(R.id.people_num)
        TextView peopleNum;
        @BindView(R.id.circular_progress_bar)
        CircularProgressBar circularProgressBar;
        @BindView(R.id.progress_num)
        TextView progressNum;
        @BindView(R.id.rl_progress)
        RelativeLayout rlProgress;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

