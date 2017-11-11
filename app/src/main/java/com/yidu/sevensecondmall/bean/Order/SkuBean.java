package com.yidu.sevensecondmall.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class SkuBean {
    //base 属性
    private double price;//价格
    private long stock;//库存

    private List<String> list = new ArrayList<>();

    public SkuBean(double price, long stock) {
        this.price = price;
        this.stock = stock;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
