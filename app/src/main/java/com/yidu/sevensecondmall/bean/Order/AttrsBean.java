package com.yidu.sevensecondmall.bean.Order;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class AttrsBean {


    /**
     * spec_name : 尺码
     * item_id : 79
     * item : L
     * src :
     */

    private List<MulBean> mullist;

    private List<GoodsSpecListBean> goods_spec_list;

    public List<GoodsSpecListBean> getGoods_spec_list() {
        return goods_spec_list;
    }

    public void setGoods_spec_list(List<GoodsSpecListBean> goods_spec_list) {
        this.goods_spec_list = goods_spec_list;
    }

    public List<MulBean> getMullist() {
        return mullist;
    }

    public void setMullist(List<MulBean> mullist) {
        this.mullist = mullist;
    }

    public static class MulBean{
        private int count;
        private String key;
        private String price;
        private String src;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class GoodsSpecListBean {
        private String spec_name;
        private int item_id;
        private String item;
        private String src;
        private int status;
        private long count;

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }


}
