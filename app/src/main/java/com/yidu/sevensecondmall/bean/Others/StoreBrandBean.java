package com.yidu.sevensecondmall.bean.Others;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public class StoreBrandBean implements Visitable{

    private  String brand;
    private List<String> subBrands;


    public StoreBrandBean() {

    }

    public StoreBrandBean(String brand, List<String> subBrands) {
        this.brand = brand;
        this.subBrands = subBrands;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getSubBrands() {
        return subBrands;
    }

    public void setSubBrands(List<String> subBrands) {
        this.subBrands = subBrands;
    }

    public String getBrand() {

        return brand;
    }
    public void setBrands(String brand) {
        this.brand = brand;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
