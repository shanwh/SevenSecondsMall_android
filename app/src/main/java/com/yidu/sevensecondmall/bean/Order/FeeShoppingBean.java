package com.yidu.sevensecondmall.bean.Order;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/6/30 0030.
 */
public class FeeShoppingBean implements Visitable{
    private String code;
    private String date;
    private String money;
    private String progress;
    private String free_status;
    private String sell_status;
    private String order_id;
    private String order_sn;
    private String Cancel_fee;
    private String sell_price;
    private boolean isCanAssignment = true;
    private String   gear_rate;

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getCancel_fee() {
        return Cancel_fee;
    }

    public void setCancel_fee(String cancel_fee) {
        Cancel_fee = cancel_fee;
    }

    public String getSell_status() {
        return sell_status;
    }

    public void setSell_status(String sell_status) {
        this.sell_status = sell_status;
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

    public String getFree_status() {
        return free_status;
    }

    public void setFree_status(String free_status) {
        this.free_status = free_status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public boolean isCanAssignment() {
        return isCanAssignment;
    }

    public void setCanAssignment(boolean canAssignment) {
        isCanAssignment = canAssignment;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public String getGear_rate() {
        return gear_rate;
    }

    public void setGear_rate(String gear_rate) {
        this.gear_rate = gear_rate;
    }
}
