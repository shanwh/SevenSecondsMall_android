package com.yidu.sevensecondmall.bean.Order;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class CollectionBean {
    /**
     * collect_id : 124
     * add_time : 1490863461
     * goods_id : 121
     * goods_name : 科智50000通用充电宝20000毫安冲手机蘋果6s超薄可爱便携移动电源
     * shop_price : 69.90
     * original_img : /Public/upload/goods/2016/04-21/571837b30942a.jpg
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int collect_id;
        private int add_time;
        private int goods_id;
        private String goods_name;
        private String shop_price;
        private String original_img;
        private boolean isCart;

        public boolean isCart() {
            return isCart;
        }

        public void setCart(boolean cart) {
            isCart = cart;
        }

        public int getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(int collect_id) {
            this.collect_id = collect_id;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }
    }
}
