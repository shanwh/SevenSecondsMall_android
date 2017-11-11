package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.TransferDetailBean;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/2.
 */

public class TransferDetailAdapter extends RecyclerViewAdapter<TransferDetailBean.ResultBean, TransferDetailAdapter.MyHolder> {

    private Context context;

    public TransferDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_detail;
    }

    @Override
    public MyHolder getViewHolder(View view) {
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        TransferDetailBean.ResultBean bean = getItem(position);
        holder.userName.setText(bean.getMobile());
        holder.dingNum.setText(bean.getAdd_ding_coin());
        holder.timeTv.setText(bean.getDatetime());

    }

    class MyHolder extends BaseHolder {
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.ding_num)
        TextView dingNum;
        @BindView(R.id.time_tv)
        TextView timeTv;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
