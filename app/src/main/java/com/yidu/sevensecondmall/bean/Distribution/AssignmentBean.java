package com.yidu.sevensecondmall.bean.Distribution;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/8/17 0017.
 */
public class AssignmentBean implements Visitable{


    /**
     * free_status : 1
     * order_sn : H808891277327363
     * order_id : 13
     * goods_price : 0.10
     * sell_price : 11134.00
     * rate : 100
     */

    private String free_status;
    private String order_sn;
    private String order_id;
    private String goods_price;
    private String sell_price;
    private String rate;
    private String user_id;
    private String curr_id;

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public String getCurr_id() {
        return curr_id;
    }

    public void setCurr_id(String curr_id) {
        this.curr_id = curr_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFree_status() {
        return free_status;
    }

    public void setFree_status(String free_status) {
        this.free_status = free_status;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
