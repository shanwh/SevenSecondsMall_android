package com.yidu.sevensecondmall.bean.Distribution;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/8/21 0021.
 */
public class DingCoinRecordBean implements Visitable{


    /**
     * id : 1
     * user_id : 15
     * change_amount : 20000.00
     * current_amount : 20000.00
     * type : 1
     * note : 订单分成
     * order_sn : G726575217488165
     * change_time : 1503130958
     */

    private String id;
    private String user_id;
    private String change_amount;
    private String current_amount;
    private String type;
    private String note;
    private String order_sn;
    private String change_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getChange_amount() {
        return change_amount;
    }

    public void setChange_amount(String change_amount) {
        this.change_amount = change_amount;
    }

    public String getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(String current_amount) {
        this.current_amount = current_amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getChange_time() {
        return change_time;
    }

    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
