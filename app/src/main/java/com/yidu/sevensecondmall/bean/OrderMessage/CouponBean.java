package com.yidu.sevensecondmall.bean.OrderMessage;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class CouponBean {
    /**
     * name : 订单满100优惠券
     * money : 20.00
     * condition : 100.00
     * id : 1
     * cid : 2
     * type : 1
     * uid : 2594
     * order_id : 805
     * use_time : 1490686450
     * code : trtrshdfus
     * send_time : 0
     */

    private String name;
    private String money;
    private String condition;
    private int id;
    private int cid;
    private int type;
    private int uid;
    private int order_id;
    private String use_time;
    private String code;
    private String send_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getUse_time() {
        return use_time;
    }

    public void setUse_time(String use_time) {
        this.use_time = use_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
}
