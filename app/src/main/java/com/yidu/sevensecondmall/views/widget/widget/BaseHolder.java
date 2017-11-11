package com.yidu.sevensecondmall.views.widget.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yidu.sevensecondmall.views.adapter.RecyclerAdapter;

/**
 * Created by dell on 2016/12/6.
 */

public class BaseHolder extends RecyclerView.ViewHolder {

    private RecyclerAdapter.OnItemClickListener itemClick;
    private RecyclerAdapter.OnItemLongClickListener itemLongClick;

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener){
        this.itemClick = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClick!=null){
                    itemClick.onClick(view,getLayoutPosition());
                }
            }
        });
    }

    public void setOnItemLongClickListener(RecyclerAdapter.OnItemLongClickListener listener){
        this.itemLongClick = listener;
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(itemLongClick!=null){
                    return itemLongClick.onLongClick(view,getLayoutPosition());
                }
                return false;
            }
        });
    }
}
