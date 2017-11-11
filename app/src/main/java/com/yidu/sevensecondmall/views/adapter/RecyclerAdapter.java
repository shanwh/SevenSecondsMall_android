package com.yidu.sevensecondmall.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by zhuang on 16/5/18.
 */
public abstract class RecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mData;
    protected OnItemClickListener mItemClick;
    protected OnItemLongClickListener mItemLongClick;

    public RecyclerAdapter(){
        mData = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClick = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){this.mItemLongClick = listener;}

    public void addAll(Collection<T> items){
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addAll(int index,Collection<T> collection){
        if(collection!=null){
            mData.addAll(index,collection);
            notifyDataSetChanged();
        }
    }

    public List<T> getList(){
        return mData;
    }

    public void addAll(T... items){
        mData.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }

    public void reset(Collection<T> items){
        mData.clear();
        addAll(items);
    }

    public void add(T item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void remove(T item){
        if(mData.contains(item)){
            mData.remove(item);
            notifyDataSetChanged();
        }
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    public void reset(T... items){
        mData.clear();
        addAll(items);
    }

    public T getItem(int position){
        return  mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface OnItemClickListener{
        void onClick(View v, int position);
    }

    public interface OnItemLongClickListener{
        boolean onLongClick(View v, int position);
    }
}
