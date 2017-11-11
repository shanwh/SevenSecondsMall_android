package com.yidu.sevensecondmall.bean.Main;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ShopListBean {
    /**
     * goodsList : [{"goods_id":113,"goods_name":"SIEMENS/西门子 KA92NV09TI双开门家用对开门电冰箱变频旗舰款","original_img":"http://www.sevenshop.com/Public/upload/goods/2016/04-20/5717264ab5d65.jpg","shop_price":"9999.00"},{"goods_id":102,"goods_name":"Hundred Generation 佰黛 天然南非钻石情侣对戒 公主戒指 情侣钻戒镀铂金戒指 婚戒款式定做","original_img":"http://www.sevenshop.com/Public/upload/goods/2016/01-21/56a0923c14436.jpg","shop_price":"8600.00"},{"goods_id":127,"goods_name":"Canon/佳能 EOS 70D套机(18-135mm)数码相机单反套机 苏宁易购","original_img":"http://www.sevenshop.com/Public/upload/goods/2016/04-21/57187c5d36631.jpg","shop_price":"6798.00"},{"goods_id":130,"goods_name":"Nikon/尼康 D7200套机(18-140mm) 尼康D7200 单反相机 正品","original_img":"http://www.sevenshop.com/Public/upload/goods/2016/04-21/57187e635d509.jpg","shop_price":"6340.00"},{"goods_id":1,"goods_name":"Apple iPhone 6s Plus 16G 玫瑰金 移动联通电信4G手机","original_img":"http://www.sevenshop.com/Public/upload/goods/2016/03-09/56e01a4088d3b.jpg","shop_price":"6007.00"}]
     * total_page : 21
     */

    private String total_page;
    /**
     * goods_id : 113
     * goods_name : SIEMENS/西门子 KA92NV09TI双开门家用对开门电冰箱变频旗舰款
     * original_img : http://www.sevenshop.com/Public/upload/goods/2016/04-20/5717264ab5d65.jpg
     * shop_price : 9999.00
     */

    private List<GoodsListBean> goodsList;

    public String getTotal_page() {
        return total_page;
    }

    public void setTotal_page(String total_page) {
        this.total_page = total_page;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean {
        private int goods_id;
        private String goods_name;
        private String original_img;
        private String shop_price;

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

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }
    }
}
