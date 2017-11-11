package com.yidu.sevensecondmall.bean.Order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class GoodInfoBean implements Parcelable {


    public GoodInfoBean(){

    }

    /**
     * goods_id : 121
     * cat_id : 100
     * extend_cat_id : 0
     * goods_sn : TP0000121
     * goods_name : 科智50000通用充电宝20000毫安冲手机蘋果6s超薄可爱便携移动电源
     * click_count : 19
     * brand_id : 0
     * store_count : 1000
     * comment_count : 0
     * weight : 500
     * market_price : 169.90
     * shop_price : 69.90
     * cost_price : 0.00
     * keywords :
     * goods_remark :
     * goods_content : <p><img src="http://www.sevenshop.com/Public/upload/goods/2016/04-21/571837a9bc934.jpg" style="float:none;" title="TB2Fdg3apXXXXavXXXXXXXXXXXX_!!1134915469.jpg_q30.jpg"/></p><p><img src="http://www.sevenshop.com/Public/upload/goods/2016/04-21/571837a9d25db.jpg" style="float:none;" title="TB2gROGmVXXXXXqXpXXXXXXXXXX_!!1134915469.jpg"/></p><p><img src="http://www.sevenshop.com/Public/upload/goods/2016/04-21/571837aa02422.jpg" style="float:none;" title="TB2MgwTapXXXXXQXpXXXXXXXXXX_!!1134915469.jpg"/></p><p><img src="http://www.sevenshop.com/Public/upload/goods/2016/04-21/571837aa26451.jpg" style="float:none;" title="TB2rTgsfpXXXXbMXXXXXXXXXXXX_!!1134915469.jpg"/></p><p><br/></p>
     * original_img : http://www.sevenshop.com/Public/upload/goods/2016/04-21/571837b30942a.jpg
     * is_real : 1
     * is_on_sale : 1
     * is_free_shipping : 0
     * on_time : 1461204949
     * sort : 50
     * is_recommend : 1
     * is_new : 0
     * is_hot : 1
     * last_update : 0
     * goods_type : 31
     * spec_type : 31
     * give_integral : 0
     * exchange_integral : 0
     * suppliers_id : 0
     * sales_sum : 0
     * prom_type : 3
     * prom_id : 2
     * commission : 0.00
     * spu :
     * sku :
     * shipping_area_ids : 36
     * goods_attr_list : [{"goods_attr_id":901,"goods_id":121,"attr_id":321,"attr_value":"科智 50000","attr_price":"0","attr_name":"产品名称"},{"goods_attr_id":902,"goods_id":121,"attr_id":322,"attr_value":"科智","attr_price":"0","attr_name":"品牌"},{"goods_attr_id":903,"goods_id":121,"attr_id":323,"attr_value":"20000mAh","attr_price":"0","attr_name":"电池容量"}]
     * goods_spec_list : [{"spec_name":"颜色","item_id":104,"item":"象牙白","src":""}]
     * address : {"shipping_area_id":36,"shipping_area_name":"长三角","shipping_code":"shunfeng","config":{"first_weight":"500","money":"11","second_weight":"500","add_money":"3"},"update_time":null,"is_default":0}
     * prominfo : {"id":2,"name":"正式会员享受8折优惠","type":0,"expression":"80","description":"\t\t\t\t\t\t\t                                                        ","start_time":1490112000,"end_time":1495296000,"is_close":0,"group":"1,2,3,4,5,6","prom_img":null}
     */

    private GoodsBean goods;
    /**
     * image_url : http://www.sevenshop.com/Public/upload/goods/2016/04-21/5718379d770a2.jpg
     */

    private List<GalleryBean> gallery;
    /**
     * comment_id : 225
     * goods_id : 121
     * email : www.99soubao.com
     * username : 美丽淑女
     * content : 少女们都很喜欢.
     * deliver_rank : 2
     * add_time : 1461243531
     * ip_address : 127.0.0.1
     * is_show : 1
     * parent_id : 0
     * user_id : 1
     * img : null
     * order_id : 1
     * goods_rank : 4
     * service_rank : 0
     */

    private List<CommentBean> comment;

    public int getCartcount() {
        return cartcount;
    }

    public void setCartcount(int cartcount) {
        this.cartcount = cartcount;
    }

    private int cartcount;

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    private boolean isCollected;


    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public List<GalleryBean> getGallery() {
        return gallery;
    }

    public void setGallery(List<GalleryBean> gallery) {
        this.gallery = gallery;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    private int mData;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData);
    }

    public static final Parcelable.Creator<GoodInfoBean> CREATOR = new Parcelable.Creator<GoodInfoBean>(){
        @Override
        public GoodInfoBean createFromParcel(Parcel source) {
            return new GoodInfoBean(source);
        }

        @Override
        public GoodInfoBean[] newArray(int size) {
            return new GoodInfoBean[size];
        }
    };

    private GoodInfoBean(Parcel source){
        mData = source.readInt();
    }

    public static class GoodsBean {
        private int goods_id;
        private int cat_id;
        private int extend_cat_id;
        private String goods_sn;
        private String goods_name;
        private int click_count;
        private int brand_id;
        private int store_count;
        private int comment_count;
        private String weight;
        private String market_price;
        private String shop_price;
        private String cost_price;
        private String keywords;
        private String goods_remark;
        private String goods_content;
        private String original_img;
        private int is_real;
        private int is_on_sale;
        private int is_free_shipping;
        private int on_time;
        private int sort;
        private int is_recommend;
        private int is_new;
        private int is_hot;
        private int last_update;
        private int goods_type;
        private int spec_type;
        private int give_integral;
        private int exchange_integral;
        private int suppliers_id;
        private int sales_sum;
        private int prom_type;
        private int prom_id;
        private String commission;
        private String spu;
        private String sku;
        private String shipping_area_ids;
        /**
         * shipping_area_id : 36
         * shipping_area_name : 长三角
         * shipping_code : shunfeng
         * config : {"first_weight":"500","money":"11","second_weight":"500","add_money":"3"}
         * update_time : null
         * is_default : 0
         */

        private AddressBean address;
        /**
         * id : 2
         * name : 正式会员享受8折优惠
         * type : 0
         * expression : 80
         * description :
         * start_time : 1490112000
         * end_time : 1495296000
         * is_close : 0
         * group : 1,2,3,4,5,6
         * prom_img : null
         */

        private ProminfoBean prominfo;
        /**
         * goods_attr_id : 901
         * goods_id : 121
         * attr_id : 321
         * attr_value : 科智 50000
         * attr_price : 0
         * attr_name : 产品名称
         */

        private List<GoodsAttrListBean> goods_attr_list;
        /**
         * spec_name : 颜色
         * item_id : 104
         * item : 象牙白
         * src :
         */

        private List<GoodsSpecListBean> goods_spec_list;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getCat_id() {
            return cat_id;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public int getExtend_cat_id() {
            return extend_cat_id;
        }

        public void setExtend_cat_id(int extend_cat_id) {
            this.extend_cat_id = extend_cat_id;
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

        public int getClick_count() {
            return click_count;
        }

        public void setClick_count(int click_count) {
            this.click_count = click_count;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public int getStore_count() {
            return store_count;
        }

        public void setStore_count(int store_count) {
            this.store_count = store_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getCost_price() {
            return cost_price;
        }

        public void setCost_price(String cost_price) {
            this.cost_price = cost_price;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getGoods_remark() {
            return goods_remark;
        }

        public void setGoods_remark(String goods_remark) {
            this.goods_remark = goods_remark;
        }

        public String getGoods_content() {
            return goods_content;
        }

        public void setGoods_content(String goods_content) {
            this.goods_content = goods_content;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public int getIs_real() {
            return is_real;
        }

        public void setIs_real(int is_real) {
            this.is_real = is_real;
        }

        public int getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(int is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public int getIs_free_shipping() {
            return is_free_shipping;
        }

        public void setIs_free_shipping(int is_free_shipping) {
            this.is_free_shipping = is_free_shipping;
        }

        public int getOn_time() {
            return on_time;
        }

        public void setOn_time(int on_time) {
            this.on_time = on_time;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(int is_recommend) {
            this.is_recommend = is_recommend;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public int getLast_update() {
            return last_update;
        }

        public void setLast_update(int last_update) {
            this.last_update = last_update;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public int getSpec_type() {
            return spec_type;
        }

        public void setSpec_type(int spec_type) {
            this.spec_type = spec_type;
        }

        public int getGive_integral() {
            return give_integral;
        }

        public void setGive_integral(int give_integral) {
            this.give_integral = give_integral;
        }

        public int getExchange_integral() {
            return exchange_integral;
        }

        public void setExchange_integral(int exchange_integral) {
            this.exchange_integral = exchange_integral;
        }

        public int getSuppliers_id() {
            return suppliers_id;
        }

        public void setSuppliers_id(int suppliers_id) {
            this.suppliers_id = suppliers_id;
        }

        public int getSales_sum() {
            return sales_sum;
        }

        public void setSales_sum(int sales_sum) {
            this.sales_sum = sales_sum;
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

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getSpu() {
            return spu;
        }

        public void setSpu(String spu) {
            this.spu = spu;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getShipping_area_ids() {
            return shipping_area_ids;
        }

        public void setShipping_area_ids(String shipping_area_ids) {
            this.shipping_area_ids = shipping_area_ids;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public ProminfoBean getProminfo() {
            return prominfo;
        }

        public void setProminfo(ProminfoBean prominfo) {
            this.prominfo = prominfo;
        }

        public List<GoodsAttrListBean> getGoods_attr_list() {
            return goods_attr_list;
        }

        public void setGoods_attr_list(List<GoodsAttrListBean> goods_attr_list) {
            this.goods_attr_list = goods_attr_list;
        }

        public List<GoodsSpecListBean> getGoods_spec_list() {
            return goods_spec_list;
        }

        public void setGoods_spec_list(List<GoodsSpecListBean> goods_spec_list) {
            this.goods_spec_list = goods_spec_list;
        }

        public static class AddressBean {
            private int shipping_area_id;
            private String shipping_area_name;
            private String shipping_code;
            /**
             * first_weight : 500
             * money : 11
             * second_weight : 500
             * add_money : 3
             */

            private ConfigBean config;
            private Object update_time;
            private int is_default;

            public int getShipping_area_id() {
                return shipping_area_id;
            }

            public void setShipping_area_id(int shipping_area_id) {
                this.shipping_area_id = shipping_area_id;
            }

            public String getShipping_area_name() {
                return shipping_area_name;
            }

            public void setShipping_area_name(String shipping_area_name) {
                this.shipping_area_name = shipping_area_name;
            }

            public String getShipping_code() {
                return shipping_code;
            }

            public void setShipping_code(String shipping_code) {
                this.shipping_code = shipping_code;
            }

            public ConfigBean getConfig() {
                return config;
            }

            public void setConfig(ConfigBean config) {
                this.config = config;
            }

            public Object getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(Object update_time) {
                this.update_time = update_time;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public static class ConfigBean {
                private String first_weight;
                private String money;
                private String second_weight;
                private String add_money;

                public String getFirst_weight() {
                    return first_weight;
                }

                public void setFirst_weight(String first_weight) {
                    this.first_weight = first_weight;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getSecond_weight() {
                    return second_weight;
                }

                public void setSecond_weight(String second_weight) {
                    this.second_weight = second_weight;
                }

                public String getAdd_money() {
                    return add_money;
                }

                public void setAdd_money(String add_money) {
                    this.add_money = add_money;
                }
            }
        }

        public static class ProminfoBean {
            private int id;
            private String name;
            private int type;
            private String expression;
            private String description;
            private int start_time;
            private int end_time;
            private int is_close;
            private String group;
            private Object prom_img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getExpression() {
                return expression;
            }

            public void setExpression(String expression) {
                this.expression = expression;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getStart_time() {
                return start_time;
            }

            public void setStart_time(int start_time) {
                this.start_time = start_time;
            }

            public int getEnd_time() {
                return end_time;
            }

            public void setEnd_time(int end_time) {
                this.end_time = end_time;
            }

            public int getIs_close() {
                return is_close;
            }

            public void setIs_close(int is_close) {
                this.is_close = is_close;
            }

            public String getGroup() {
                return group;
            }

            public void setGroup(String group) {
                this.group = group;
            }

            public Object getProm_img() {
                return prom_img;
            }

            public void setProm_img(Object prom_img) {
                this.prom_img = prom_img;
            }
        }

        public static class GoodsAttrListBean {
            private int goods_attr_id;
            private int goods_id;
            private int attr_id;
            private String attr_value;
            private String attr_price;
            private String attr_name;

            public int getGoods_attr_id() {
                return goods_attr_id;
            }

            public void setGoods_attr_id(int goods_attr_id) {
                this.goods_attr_id = goods_attr_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getAttr_id() {
                return attr_id;
            }

            public void setAttr_id(int attr_id) {
                this.attr_id = attr_id;
            }

            public String getAttr_value() {
                return attr_value;
            }

            public void setAttr_value(String attr_value) {
                this.attr_value = attr_value;
            }

            public String getAttr_price() {
                return attr_price;
            }

            public void setAttr_price(String attr_price) {
                this.attr_price = attr_price;
            }

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }
        }

        public static class GoodsSpecListBean {
            private String spec_name;
            private int item_id;
            private String item;
            private String src;

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

    public static class GalleryBean {
        private String image_url;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }

    public static class CommentBean {
        private int comment_id;
        private int goods_id;
        private String email;
        private String username;
        private String content;
        private int deliver_rank;
        private int add_time;
        private String ip_address;
        private int is_show;
        private int parent_id;
        private int user_id;
        private Object img;
        private int order_id;
        private int goods_rank;
        private int service_rank;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDeliver_rank() {
            return deliver_rank;
        }

        public void setDeliver_rank(int deliver_rank) {
            this.deliver_rank = deliver_rank;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getIp_address() {
            return ip_address;
        }

        public void setIp_address(String ip_address) {
            this.ip_address = ip_address;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getGoods_rank() {
            return goods_rank;
        }

        public void setGoods_rank(int goods_rank) {
            this.goods_rank = goods_rank;
        }

        public int getService_rank() {
            return service_rank;
        }

        public void setService_rank(int service_rank) {
            this.service_rank = service_rank;
        }
    }
}
