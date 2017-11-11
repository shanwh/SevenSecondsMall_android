package com.yidu.sevensecondmall.bean.Main;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
public class HomeGoodBean implements Visitable{
    public boolean isList = false;
    ArrayList<Visitable> list = new ArrayList<>();

    public ArrayList<Visitable> getList() {
        return list;
    }

    public void setList(ArrayList<Visitable> list) {
        this.list = list;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
