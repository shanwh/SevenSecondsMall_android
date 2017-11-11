package com.yidu.sevensecondmall.views.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/13.
 */
public class FlowBaseAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();


    /***注册观察者*/
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    /***
     * 注销观察者**/
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * 刷新界面
     */
    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /***获取数量*/
    public int getCount(){
        return 0;
    }

    /**生成每个view**/
    public View getView(int position, View convertView, ViewGroup parent){
        return null;
    }

    /***获取每个子项**/
    public Object getItem(){
        return null;
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }
}
