package com.yidu.sevensecondmall.bean.Main;

import java.util.List;

/**
 * Created by Administrator on 2017/11/10.
 */

public class TransferDetailBean {

    /**
     * code : 0
     * message : 成功
     * result : [{"id":"1","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.500","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:50:50","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"2","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:51:33","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"3","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:51:34","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"4","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:51:41","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"5","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:51:43","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"6","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:52:30","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"7","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:52:48","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"8","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 17:52:48","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"9","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 18:02:35","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"},{"id":"11","order_id":"1619","order_sn":"KB06577900279145","total_price":"106.000","old_rate":"0.500","new_rate":"0.000","user_id":"15","add_ding_coin":"0.000","datetime":"2017-11-08 19:28:03","start_time":"2017-11-06 16:43:15","note":"用于订单加速齿轮【KB06577900279145】","nickname":"收到","mobile":"135**94"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * order_id : 1619
         * order_sn : KB06577900279145
         * total_price : 106.000
         * old_rate : 0.500
         * new_rate : 0.500
         * user_id : 15
         * add_ding_coin : 0.000
         * datetime : 2017-11-08 17:50:50
         * start_time : 2017-11-06 16:43:15
         * note : 用于订单加速齿轮【KB06577900279145】
         * nickname : 收到
         * mobile : 135**94
         */

        private String id;
        private String order_id;
        private String order_sn;
        private String total_price;
        private String old_rate;
        private String new_rate;
        private String user_id;
        private String add_ding_coin;
        private String datetime;
        private String start_time;
        private String note;
        private String nickname;
        private String mobile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getOld_rate() {
            return old_rate;
        }

        public void setOld_rate(String old_rate) {
            this.old_rate = old_rate;
        }

        public String getNew_rate() {
            return new_rate;
        }

        public void setNew_rate(String new_rate) {
            this.new_rate = new_rate;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAdd_ding_coin() {
            return add_ding_coin;
        }

        public void setAdd_ding_coin(String add_ding_coin) {
            this.add_ding_coin = add_ding_coin;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
