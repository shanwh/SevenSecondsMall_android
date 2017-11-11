package com.yidu.sevensecondmall.bean.Others;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/8/23.
 */
public class StoreClassificationBean implements Visitable{

    private  String classifications;


    public StoreClassificationBean() {

    }

    public StoreClassificationBean(String classifications) {
        this.classifications = classifications;
    }

    public String getClassifications() {

        return classifications;
    }

    public void setClassifications(String classifications) {
        this.classifications = classifications;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
