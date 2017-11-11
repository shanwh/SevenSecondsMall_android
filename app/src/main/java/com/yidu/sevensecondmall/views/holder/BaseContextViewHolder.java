package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public abstract class BaseContextViewHolder<T> extends BaseViewHolder<T> {
    private SparseArray<View> views;
    private View mItemView;
    private Context context;

    public BaseContextViewHolder(View itemView, Context context) {
        super(itemView);
        views = new SparseArray<>();
        this.mItemView = itemView;
        this.context = context;
    }

    public Context getHolderContext(){
        return context;
    }

    /**
     * 返回父控件整个item
     */
    public View getItemView(){return mItemView;}

    public View getView(int resID) {
        View view = views.get(resID);

        if (view == null) {
            view = mItemView.findViewById(resID);
            views.put(resID,view);
        }

        return view;
    }

    public abstract void setUpView(T model, int position, MultiTypeAdapter adapter);
}