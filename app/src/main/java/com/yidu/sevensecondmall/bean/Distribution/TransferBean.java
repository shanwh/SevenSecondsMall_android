package com.yidu.sevensecondmall.bean.Distribution;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class TransferBean {
    /**
     * code : 0
     * message : 成功
     * result : {"list":[{"id":"1067","user_id":"12","order_sn":"I929946120401223","old_user_id":null,"sell_price":null,"put_add_time":null,"buy_time":null,"pay_status":"0","sell_status":"0","goods_price":"10.00","order_id":"1395","free_status":"0","total_amount":"10.00","pay_time":"1506694615","order_status":"0","order_pay_status":"2","curr_id":"0000000000","curr_add_time":null,"gear_rate":"0.00","gear_amount":"0.00","is_quick":"1","first_quick_time":"0000-00-00 00:00:00","redis_rate":"10.00","redis_amount":"100.00","is_get":"0","promotion_id":"0","promotion_type":"5","nickname":"18675598941","mobile":"18675598941","count":"0","original_img":[{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/upload/goods/2017/08-03/5982ee2c04c96.jpg"}]},{"id":"1148","user_id":"3","order_sn":"JA26267225011628","old_user_id":null,"sell_price":null,"put_add_time":null,"buy_time":null,"pay_status":"0","sell_status":"0","goods_price":"39.00","order_id":"1555","free_status":"0","total_amount":"49.00","pay_time":"1509026740","order_status":"0","order_pay_status":"1","curr_id":"0000000000","curr_add_time":null,"gear_rate":"0.51","gear_amount":"0.00","is_quick":"1","first_quick_time":"2017-11-08 18:53:32","redis_rate":"39.00","redis_amount":"100.00","is_get":"0","promotion_id":"0","promotion_type":"5","nickname":"18911938060","mobile":"18911938060","count":"1","original_img":[{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/upload/goods/2017/08-07/5987e63c644bd.jpg"}]},{"id":"1149","user_id":"3","order_sn":"JA26272638815513","old_user_id":null,"sell_price":null,"put_add_time":null,"buy_time":null,"pay_status":"0","sell_status":"0","goods_price":"40.00","order_id":"1557","free_status":"0","total_amount":"40.00","pay_time":"1509027274","order_status":"0","order_pay_status":"1","curr_id":"0000000000","curr_add_time":null,"gear_rate":"0.50","gear_amount":"0.00","is_quick":"1","first_quick_time":"2017-11-08 19:33:30","redis_rate":"6.86","redis_amount":"2.74","is_get":"0","promotion_id":"0","promotion_type":"5","nickname":"18911938060","mobile":"18911938060","count":"1","original_img":[{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/upload/goods/2017/08-07/5987df6082419.jpg"}]},{"id":"1181","user_id":"15","order_sn":"KB06577900279145","old_user_id":null,"sell_price":null,"put_add_time":null,"buy_time":null,"pay_status":"0","sell_status":"0","goods_price":"106.00","order_id":"1619","free_status":"0","total_amount":"106.00","pay_time":"1509957795","order_status":"0","order_pay_status":"1","curr_id":"0000000000","curr_add_time":null,"gear_rate":"0.00","gear_amount":"0.00","is_quick":"1","first_quick_time":"2017-11-08 17:50:50","redis_rate":"0.00","redis_amount":"0.00","is_get":"0","promotion_id":"0","promotion_type":"5","nickname":"收到","mobile":"13533731794","count":"10","original_img":[{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/upload/goods/2017/07-29/597c7046d9adf.jpg"}]}],"banner":[{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/images/54d32ebcN490d1c3a.jpg"},{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/images/54d32edcNd88a71ce.jpg"}],"ad_list":["x***f会员加速了 4058.00 DING宝","o***u会员加速了 9821.00 DING宝","9***l会员加速了 1613.00 DING宝","p***m会员加速了 4112.00 DING宝","6***a会员加速了 4292.00 DING宝","o***6会员加速了 7233.00 DING宝","i***w会员加速了 8442.00 DING宝","o***s会员加速了 823.00 DING宝","i***r会员加速了 7667.00 DING宝","w***2会员加速了 2371.00 DING宝","8***i会员加速了 1774.00 DING宝","o***b会员加速了 8404.00 DING宝","x***o会员加速了 4537.00 DING宝","a***k会员加速了 5261.00 DING宝","1***m会员加速了 291.00 DING宝"]}
     */

    private int code;
    private String message;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<ListBean> list;
        private List<BannerBean> banner;
        private List<String> ad_list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<String> getAd_list() {
            return ad_list;
        }

        public void setAd_list(List<String> ad_list) {
            this.ad_list = ad_list;
        }

        public static class ListBean {
            /**
             * id : 1067
             * user_id : 12
             * order_sn : I929946120401223
             * old_user_id : null
             * sell_price : null
             * put_add_time : null
             * buy_time : null
             * pay_status : 0
             * sell_status : 0
             * goods_price : 10.00
             * order_id : 1395
             * free_status : 0
             * total_amount : 10.00
             * pay_time : 1506694615
             * order_status : 0
             * order_pay_status : 2
             * curr_id : 0000000000
             * curr_add_time : null
             * gear_rate : 0.00
             * gear_amount : 0.00
             * is_quick : 1
             * first_quick_time : 0000-00-00 00:00:00
             * redis_rate : 10.00
             * redis_amount : 100.00
             * is_get : 0
             * promotion_id : 0
             * promotion_type : 5
             * nickname : 18675598941
             * mobile : 18675598941
             * count : 0
             * original_img : [{"imgurl":"http://sevenshop.b0.upaiyun.com/Public/upload/goods/2017/08-03/5982ee2c04c96.jpg"}]
             */

            private String id;
            private String user_id;
            private String order_sn;
            private Object old_user_id;
            private Object sell_price;
            private Object put_add_time;
            private Object buy_time;
            private String pay_status;
            private String sell_status;
            private String goods_price;
            private String order_id;
            private String free_status;
            private String total_amount;
            private String pay_time;
            private String order_status;
            private String order_pay_status;
            private String curr_id;
            private Object curr_add_time;
            private String gear_rate;
            private String gear_amount;
            private String is_quick;
            private String first_quick_time;
            private String redis_rate;
            private String redis_amount;
            private String is_get;
            private String promotion_id;
            private String promotion_type;
            private String nickname;
            private String mobile;
            private String count;
            private List<OriginalImgBean> original_img;

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

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public Object getOld_user_id() {
                return old_user_id;
            }

            public void setOld_user_id(Object old_user_id) {
                this.old_user_id = old_user_id;
            }

            public Object getSell_price() {
                return sell_price;
            }

            public void setSell_price(Object sell_price) {
                this.sell_price = sell_price;
            }

            public Object getPut_add_time() {
                return put_add_time;
            }

            public void setPut_add_time(Object put_add_time) {
                this.put_add_time = put_add_time;
            }

            public Object getBuy_time() {
                return buy_time;
            }

            public void setBuy_time(Object buy_time) {
                this.buy_time = buy_time;
            }

            public String getPay_status() {
                return pay_status;
            }

            public void setPay_status(String pay_status) {
                this.pay_status = pay_status;
            }

            public String getSell_status() {
                return sell_status;
            }

            public void setSell_status(String sell_status) {
                this.sell_status = sell_status;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
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

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getOrder_pay_status() {
                return order_pay_status;
            }

            public void setOrder_pay_status(String order_pay_status) {
                this.order_pay_status = order_pay_status;
            }

            public String getCurr_id() {
                return curr_id;
            }

            public void setCurr_id(String curr_id) {
                this.curr_id = curr_id;
            }

            public Object getCurr_add_time() {
                return curr_add_time;
            }

            public void setCurr_add_time(Object curr_add_time) {
                this.curr_add_time = curr_add_time;
            }

            public String getGear_rate() {
                return gear_rate;
            }

            public void setGear_rate(String gear_rate) {
                this.gear_rate = gear_rate;
            }

            public String getGear_amount() {
                return gear_amount;
            }

            public void setGear_amount(String gear_amount) {
                this.gear_amount = gear_amount;
            }

            public String getIs_quick() {
                return is_quick;
            }

            public void setIs_quick(String is_quick) {
                this.is_quick = is_quick;
            }

            public String getFirst_quick_time() {
                return first_quick_time;
            }

            public void setFirst_quick_time(String first_quick_time) {
                this.first_quick_time = first_quick_time;
            }

            public String getRedis_rate() {
                return redis_rate;
            }

            public void setRedis_rate(String redis_rate) {
                this.redis_rate = redis_rate;
            }

            public String getRedis_amount() {
                return redis_amount;
            }

            public void setRedis_amount(String redis_amount) {
                this.redis_amount = redis_amount;
            }

            public String getIs_get() {
                return is_get;
            }

            public void setIs_get(String is_get) {
                this.is_get = is_get;
            }

            public String getPromotion_id() {
                return promotion_id;
            }

            public void setPromotion_id(String promotion_id) {
                this.promotion_id = promotion_id;
            }

            public String getPromotion_type() {
                return promotion_type;
            }

            public void setPromotion_type(String promotion_type) {
                this.promotion_type = promotion_type;
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

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public List<OriginalImgBean> getOriginal_img() {
                return original_img;
            }

            public void setOriginal_img(List<OriginalImgBean> original_img) {
                this.original_img = original_img;
            }

            public static class OriginalImgBean {
                /**
                 * imgurl : http://sevenshop.b0.upaiyun.com/Public/upload/goods/2017/08-03/5982ee2c04c96.jpg
                 */

                private String imgurl;

                public String getImgurl() {
                    return imgurl;
                }

                public void setImgurl(String imgurl) {
                    this.imgurl = imgurl;
                }
            }
        }

        public static class BannerBean {
            /**
             * imgurl : http://sevenshop.b0.upaiyun.com/Public/images/54d32ebcN490d1c3a.jpg
             */

            private String imgurl;

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }
    }
}
