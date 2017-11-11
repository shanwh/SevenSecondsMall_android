package com.yidu.sevensecondmall.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class CouponBean {
    /**
     * id : 1
     * cid : 2
     * type : 1
     * uid : 2594
     * order_id : 805
     * use_time : 1490686450
     * code : trtrshdfus
     * send_time : 0
     * name : 订单满100优惠券
     * money : 20.00
     * use_end_time : 1449763200
     * condition : 100.00
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int id;
        private int cid;
        private int type;
        private int uid;
        private int order_id;
        private int use_time;
        private String code;
        private int send_time;
        private String name;
        private String money;
        private int use_end_time;
        private String condition;

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

        public int getUse_time() {
            return use_time;
        }

        public void setUse_time(int use_time) {
            this.use_time = use_time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getSend_time() {
            return send_time;
        }

        public void setSend_time(int send_time) {
            this.send_time = send_time;
        }

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

        public int getUse_end_time() {
            return use_end_time;
        }

        public void setUse_end_time(int use_end_time) {
            this.use_end_time = use_end_time;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }
    }
}
