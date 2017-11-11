package com.yidu.sevensecondmall.bean.OrderMessage;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class OrderListBean implements Visitable {
    /**
     * order_id : 805
     * order_sn : C328864499241589
     * user_id : 2594
     * order_status : 0
     * shipping_status : 0
     * pay_status : 0
     * consignee : 肖田良250
     * country : 0
     * province : 28240
     * city : 29400
     * district : 29379111
     * twon : 0
     * address : 宝安中心地铁站
     * zipcode :
     * mobile : 13533731794
     * email :
     * shipping_code : alipay
     * shipping_name : 支付宝支付
     * pay_code :
     * pay_name :
     * invoice_title :
     * goods_price : 15.90
     * shipping_price : 0.00
     * user_money : 0.00
     * coupon_price : null
     * integral : 0
     * integral_money : 0.00
     * order_amount : 15.90
     * total_amount : 15.90
     * add_time : 1490686449
     * shipping_time : 0
     * confirm_time : 0
     * pay_time : 0
     * order_prom_id : 0
     * order_prom_amount : 0.00
     * discount : 0.00
     * user_note :
     * admin_note :
     * parent_sn : null
     * is_distribut : 0
     * order_status_code : WAITPAY
     * order_status_desc : 待支付
     * pay_btn : 1
     * cancel_btn : 1
     * receive_btn : 0
     * comment_btn : 0
     * shipping_btn : 0
     * return_btn : 0
     * goods_list : [{"rec_id":926,"order_id":805,"goods_id":92,"goods_name":"泊泉雅男士女士香水50ml 淡香清新持久留香 淡雅香氛诱惑 正品","goods_sn":"TP0000092","goods_num":1,"market_price":"115.90","goods_price":"15.90","cost_price":"0.00","member_goods_price":"15.90","give_integral":0,"spec_key":"","spec_key_name":"","bar_code":"","is_comment":0,"prom_type":0,"prom_id":0,"is_send":0,"delivery_id":0,"sku":"","original_img":"/Public/upload/goods/2016/01-21/56a08aef4ebdf.jpg"}]
     */
    private int num;

    public String getInvoice_no() {
        return invoice_no;
    }

    private String invoice_no;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    private String order_id;
    private String order_sn;
    private String user_id;
    private String order_status;
    private String shipping_status;
    private String pay_status;
    private String consignee;
    private String country;
    private String province;
    private String city;
    private String district;
    private String twon;
    private String address;
    private String zipcode;
    private String mobile;
    private String email;
    private String shipping_code;
    private String shipping_name;
    private String pay_code;
    private String pay_name;
    private String invoice_title;
    private String goods_price;
    private String shipping_price;
    private String user_money;
    private Object coupon_price;
    private String integral;
    private String integral_money;
    private String order_amount;
    private String total_amount;
    private String add_time;
    private String shipping_time;
    private String confirm_time;
    private String pay_time;
    private String order_prom_id;
    private String order_prom_amount;
    private String discount;
    private String user_note;
    private String admin_note;
    private Object parent_sn;
    private String is_distribut;
    private String order_status_code;
    private String order_status_desc;
    private String free_status_code;
    private String free_status_desc;
    private String pay_btn;
    private String cancel_btn;
    private String receive_btn;
    private String comment_btn;
    private String shipping_btn;
    private String return_btn;
    private boolean isFree;
    private String buy_back_amount;
    private String buy_back_status;
    private String shop_name;

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    private int goods_num;

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getBuy_back_status() {
        return buy_back_status;
    }

    public void setBuy_back_status(String buy_back_status) {
        this.buy_back_status = buy_back_status;
    }

    public String getBuy_back_amount() {
        return buy_back_amount;
    }

    public void setBuy_back_amount(String buy_back_amount) {
        this.buy_back_amount = buy_back_amount;
    }

    /**
     * rec_id : 926
     * order_id : 805
     * goods_id : 92
     * goods_name : 泊泉雅男士女士香水50ml 淡香清新持久留香 淡雅香氛诱惑 正品
     * goods_sn : TP0000092
     * goods_num : 1
     * market_price : 115.90
     * goods_price : 15.90
     * cost_price : 0.00
     * member_goods_price : 15.90
     * give_integral : 0
     * spec_key :
     * spec_key_name :
     * bar_code :
     * is_comment : 0
     * prom_type : 0
     * prom_id : 0
     * is_send : 0
     * delivery_id : 0
     * sku :
     * original_img : /Public/upload/goods/2016/01-21/56a08aef4ebdf.jpg
     */



    private List<GoodsListBean> goods_list;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(String shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTwon() {
        return twon;
    }

    public void setTwon(String twon) {
        this.twon = twon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(String invoice_title) {
        this.invoice_title = invoice_title;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public Object getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(Object coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIntegral_money() {
        return integral_money;
    }

    public void setIntegral_money(String integral_money) {
        this.integral_money = integral_money;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getOrder_prom_id() {
        return order_prom_id;
    }

    public void setOrder_prom_id(String order_prom_id) {
        this.order_prom_id = order_prom_id;
    }

    public String getOrder_prom_amount() {
        return order_prom_amount;
    }

    public void setOrder_prom_amount(String order_prom_amount) {
        this.order_prom_amount = order_prom_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getAdmin_note() {
        return admin_note;
    }

    public void setAdmin_note(String admin_note) {
        this.admin_note = admin_note;
    }

    public Object getParent_sn() {
        return parent_sn;
    }

    public void setParent_sn(Object parent_sn) {
        this.parent_sn = parent_sn;
    }

    public String getIs_distribut() {
        return is_distribut;
    }

    public void setIs_distribut(String is_distribut) {
        this.is_distribut = is_distribut;
    }

    public String getOrder_status_code() {
        return order_status_code;
    }

    public void setOrder_status_code(String order_status_code) {
        this.order_status_code = order_status_code;
    }

    public String getOrder_status_desc() {
        return order_status_desc;
    }

    public void setOrder_status_desc(String order_status_desc) {
        this.order_status_desc = order_status_desc;
    }

    public String getPay_btn() {
        return pay_btn;
    }

    public void setPay_btn(String pay_btn) {
        this.pay_btn = pay_btn;
    }

    public String getCancel_btn() {
        return cancel_btn;
    }

    public void setCancel_btn(String cancel_btn) {
        this.cancel_btn = cancel_btn;
    }

    public String getReceive_btn() {
        return receive_btn;
    }

    public void setReceive_btn(String receive_btn) {
        this.receive_btn = receive_btn;
    }

    public String getComment_btn() {
        return comment_btn;
    }

    public void setComment_btn(String comment_btn) {
        this.comment_btn = comment_btn;
    }

    public String getShipping_btn() {
        return shipping_btn;
    }

    public void setShipping_btn(String shipping_btn) {
        this.shipping_btn = shipping_btn;
    }

    public String getReturn_btn() {
        return return_btn;
    }

    public void setReturn_btn(String return_btn) {
        this.return_btn = return_btn;
    }

    public String getFree_status_code() {
        return free_status_code;
    }

    public void setFree_status_code(String free_status_code) {
        this.free_status_code = free_status_code;
    }

    public String getFree_status_desc() {
        return free_status_desc;
    }

    public void setFree_status_desc(String free_status_desc) {
        this.free_status_desc = free_status_desc;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public static class GoodsListBean {
        private String rec_id;
        private String order_id;
        private String goods_id;
        private String goods_name;
        private String goods_sn;
        private String goods_num;
        private String market_price;
        private String goods_price;
        private String cost_price;
        private String member_goods_price;
        private String give_integral;
        private String spec_key;
        private String spec_key_name;
        private String bar_code;
        private String is_comment;
        private String prom_type;
        private String prom_id;
        private String is_send;
        private String delivery_id;
        private String sku;
        private String original_img;

        public String getRec_id() {
            return rec_id;
        }

        public void setRec_id(String rec_id) {
            this.rec_id = rec_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public String getMember_goods_price() {
            return member_goods_price;
        }

        public void setMember_goods_price(String member_goods_price) {
            this.member_goods_price = member_goods_price;
        }

        public String getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(String give_integral) {
            this.give_integral = give_integral;
        }

        public String getSpec_key() {
            return spec_key;
        }

        public void setSpec_key(String spec_key) {
            this.spec_key = spec_key;
        }

        public String getSpec_key_name() {
            return spec_key_name;
        }

        public void setSpec_key_name(String spec_key_name) {
            this.spec_key_name = spec_key_name;
        }

        public String getBar_code() {
            return bar_code;
        }

        public void setBar_code(String bar_code) {
            this.bar_code = bar_code;
        }

        public String getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(String is_comment) {
            this.is_comment = is_comment;
        }

        public String getProm_type() {
            return prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
        }

        public String getProm_id() {
            return prom_id;
        }

        public void setProm_id(String prom_id) {
            this.prom_id = prom_id;
        }

        public String getIs_send() {
            return is_send;
        }

        public void setIs_send(String is_send) {
            this.is_send = is_send;
        }

        public String getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(String delivery_id) {
            this.delivery_id = delivery_id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }
    }
}
