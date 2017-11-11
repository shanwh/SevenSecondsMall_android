package com.yidu.sevensecondmall.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidu.sevensecondmall.views.widget.widget.BaseHolder;


/**
 * Created by dell on 2016/12/6.
 */

public abstract class RecyclerViewAdapter<V,T extends BaseHolder> extends RecyclerAdapter<V,T> {

    protected final String image_url = "http://v1.qzone.cc/avatar/201406/29/18/15/53afe73912959815.jpg%21200x200.jpg";

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutResId(),parent,false);

        T holder = getViewHolder(view);
        if(mItemClick!=null) {
            holder.setOnItemClickListener(mItemClick);
        }
        if(mItemLongClick!=null) {
            holder.setOnItemLongClickListener(mItemLongClick);
        }
        return holder;
    }

    public abstract  int getLayoutResId();

    public abstract T getViewHolder(View view);

}
