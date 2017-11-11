package com.yidu.sevensecondmall.bean.Main;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ImageListBean implements Visitable{

    private ArrayList<ImageBean> list = new ArrayList<>();

    public ArrayList<ImageBean> getList() {
        return list;
    }

    public void setList(ArrayList<ImageBean> list) {
        this.list = list;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
