package com.yidu.sevensecondmall.bean.OrderMessage;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1 0001.
 */
public class OrderDetailInfo {

    private OrderDetailBean orderDetailBean;
    private ArrayList<GoodsBean> list = new ArrayList<>();

    public OrderDetailBean getOrderDetailBean() {
        return orderDetailBean;
    }

    public void setOrderDetailBean(OrderDetailBean orderDetailBean) {
        this.orderDetailBean = orderDetailBean;
    }

    public ArrayList<GoodsBean> getList() {
        return list;
    }

    public void setList(ArrayList<GoodsBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "OrderDetailInfo{" +
                "orderDetailBean=" + orderDetailBean +
                ", list=" + list +
                '}';
    }
}
