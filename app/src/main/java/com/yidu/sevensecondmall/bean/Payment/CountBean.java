package com.yidu.sevensecondmall.bean.Payment;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
public class CountBean {
    /**
     * log_id : 29
     * user_id : 2600
     * user_money : 555.00
     * frozen_money : 0.00
     * pay_points : 0
     * change_time : 1496824675
     * desc : 帐号余额充值
     * order_sn : 2017060751485497
     * order_id : null
     */

    private String log_id;
    private String user_id;
    private String user_money;
    private String frozen_money;
    private String pay_points;
    private int change_time;
    private String desc;
    private String order_sn;
    private Object order_id;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getPay_points() {
        return pay_points;
    }

    public void setPay_points(String pay_points) {
        this.pay_points = pay_points;
    }

    public int getChange_time() {
        return change_time;
    }

    public void setChange_time(int change_time) {
        this.change_time = change_time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public Object getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Object order_id) {
        this.order_id = order_id;
    }
}
