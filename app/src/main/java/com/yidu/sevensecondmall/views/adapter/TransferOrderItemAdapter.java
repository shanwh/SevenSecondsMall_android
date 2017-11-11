package com.yidu.sevensecondmall.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/2.
 */

public class TransferOrderItemAdapter extends RecyclerViewAdapter<String, TransferOrderItemAdapter.MyHolder> {


    private Context context;

    public TransferOrderItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_transfer_order;
    }

    @Override
    public MyHolder getViewHolder(View view) {
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String url = getItem(position);
        if (!url.equals("") && url != null) {
            holder.ivItem.setImageURI(Uri.parse(url));
        }
    }

    class MyHolder extends BaseHolder {
        @BindView(R.id.iv_item)
        SimpleDraweeView ivItem;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
