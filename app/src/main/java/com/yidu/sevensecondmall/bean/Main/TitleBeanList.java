package com.yidu.sevensecondmall.bean.Main;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/8.
 */

public class TitleBeanList implements Visitable{
    private ArrayList<TitleBean> list = new ArrayList<>();

    public ArrayList<TitleBean> getList() {
        return list;
    }

    public void setList(ArrayList<TitleBean> list) {
        this.list = list;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
