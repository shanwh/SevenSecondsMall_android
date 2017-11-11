package com.yidu.sevensecondmall.bean.Main;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class HomeGoodListBean implements Visitable {

    /**
     * goods_id : 1
     * cat_id : 12
     * goods_sn : TP000000
     * goods_name : Apple iPhone 6s Plus 16G 玫瑰金 移动联通电信4G手机
     * shop_price : 6007.00
     * comment_count : 5
     * original_img : /Public/upload/goods/2016/03-09/56e01a4088d3b.jpg
     */

    private String goods_id;
    private String buy_people;
    private String goods_name;
    private String shop_price;

    private String original_img;

    public String getBuy_people() {
        return buy_people;
    }

    public void setBuy_people(String buy_people) {
        this.buy_people = buy_people;
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

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
