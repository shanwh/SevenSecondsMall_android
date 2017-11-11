package com.yidu.sevensecondmall.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class AccountBean {
    /**
     * log_id : 248
     * user_id : 2594
     * user_money : 0.00
     * frozen_money : 0.00
     * pay_points : 100
     * change_time : 1490680409
     * desc : 会员注册赠送积分
     * order_sn : null
     * order_id : null
     */

    private List<AccountLogBean> account_log;

    public List<AccountLogBean> getAccount_log() {
        return account_log;
    }

    public void setAccount_log(List<AccountLogBean> account_log) {
        this.account_log = account_log;
    }

    public static class AccountLogBean {
        private int log_id;
        private int user_id;
        private String user_money;
        private String frozen_money;
        private int pay_points;
        private int change_time;
        private String desc;
        private Object order_sn;
        private Object order_id;

        public int getLog_id() {
            return log_id;
        }

        public void setLog_id(int log_id) {
            this.log_id = log_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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

        public int getPay_points() {
            return pay_points;
        }

        public void setPay_points(int pay_points) {
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

        public Object getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(Object order_sn) {
            this.order_sn = order_sn;
        }

        public Object getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Object order_id) {
            this.order_id = order_id;
        }
    }
}
