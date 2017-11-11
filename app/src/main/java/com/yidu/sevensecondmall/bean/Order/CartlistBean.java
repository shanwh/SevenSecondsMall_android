package com.yidu.sevensecondmall.bean.Order;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */
public class CartlistBean {
    /**
     * total_fee : 31.8
     * cut_fee : 200
     * num : 2
     */

    private TotalPriceBean total_price;
    /**
     * id : 1679
     * user_id : 2590
     * session_id : 1
     * goods_id : 92
     * goods_sn : TP0000092
     * goods_name : 泊泉雅男士女士香水50ml 淡香清新持久留香 淡雅香氛诱惑 正品
     * market_price : 115.90
     * goods_price : 15.90
     * member_goods_price : 15.90
     * goods_num : 2
     * spec_key :
     * spec_key_name :
     * bar_code :
     * selected : 1
     * add_time : 1490264803
     * prom_type : 0
     * prom_id : 0
     * sku :
     * goods_fee : 31.8
     * store_count : 1000
     */

    private List<CartListBean> cartList;

    private JSONArray array;


    public TotalPriceBean getTotal_price() {
        return total_price;
    }

    public void setTotal_price(TotalPriceBean total_price) {
        this.total_price = total_price;
    }

    public List<CartListBean> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartListBean> cartList) {
        this.cartList = cartList;
    }

    public JSONArray getArray() {
        return array;
    }

    public void setArray(JSONArray array) {
        this.array = array;
    }

    public static class TotalPriceBean {
        private double total_fee;
        private int cut_fee;
        private int num;

        public double getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(double total_fee) {
            this.total_fee = total_fee;
        }

        public int getCut_fee() {
            return cut_fee;
        }

        public void setCut_fee(int cut_fee) {
            this.cut_fee = cut_fee;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class CartListBean {
        private int id;
        private int user_id;
        private String session_id;
        private int goods_id;
        private String goods_sn;
        private String goods_name;
        private String market_price;
        private String goods_price;
        private String member_goods_price;
        private int goods_num;
        private String spec_key;
        private String spec_key_name;
        private String bar_code;
        private int selected;
        private int add_time;
        private int prom_type;
        private int prom_id;
        private String sku;
        private double goods_fee;
        private int store_count;
        private String original_img;
        private String buy_back_price;
        private int is_cod;
        private String shop_name;
        private String business_user_id;
        private Boolean isCart2;
        public String is_free_shipping;

        public String getIs_free_shipping() {
            return is_free_shipping;
        }

        public void setIs_free_shipping(String is_free_shipping) {
            this.is_free_shipping = is_free_shipping;
        }

        public String getBusiness_user_id() {
            return business_user_id;
        }

        public void setBusiness_user_id(String business_user_id) {
            this.business_user_id = business_user_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public int getIs_cod() {
            return is_cod;
        }

        public void setIs_cod(int is_cod) {
            this.is_cod = is_cod;
        }

        public String getBuy_back_price() {
            return buy_back_price;
        }

        public void setBuy_back_price(String buy_back_price) {
            this.buy_back_price = buy_back_price;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_sn() {
            return goods_sn;
        }

        public void setGoods_sn(String goods_sn) {
            this.goods_sn = goods_sn;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public String getMember_goods_price() {
            return member_goods_price;
        }

        public void setMember_goods_price(String member_goods_price) {
            this.member_goods_price = member_goods_price;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
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

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getProm_type() {
            return prom_type;
        }

        public void setProm_type(int prom_type) {
            this.prom_type = prom_type;
        }

        public int getProm_id() {
            return prom_id;
        }

        public void setProm_id(int prom_id) {
            this.prom_id = prom_id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public double getGoods_fee() {
            return goods_fee;
        }

        public void setGoods_fee(double goods_fee) {
            this.goods_fee = goods_fee;
        }

        public int getStore_count() {
            return store_count;
        }

        public void setStore_count(int store_count) {
            this.store_count = store_count;
        }

        public Boolean getCart2() {
            return isCart2;
        }

        public void setCart2(Boolean cart2) {
            isCart2 = cart2;
        }
    }
}
