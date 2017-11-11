package com.yidu.sevensecondmall.DAO;

import android.util.Log;

import com.yidu.sevensecondmall.bean.Main.BannerBean;
import com.yidu.sevensecondmall.bean.Main.GoodListBean;
import com.yidu.sevensecondmall.bean.Main.ShopListBean;
import com.yidu.sevensecondmall.bean.Order.AttrsBean;
import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.bean.Order.CollectionBean;
import com.yidu.sevensecondmall.bean.Order.CommendInfo;
import com.yidu.sevensecondmall.bean.Order.GoodInfoBean;
import com.yidu.sevensecondmall.bean.Order.STCategoryBean;
import com.yidu.sevensecondmall.bean.Order.SearchHotBean;
import com.yidu.sevensecondmall.bean.Order.goodCategoryBean;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.bean.OrderMessage.Cart2Bean;
import com.yidu.sevensecondmall.bean.OrderMessage.CouponBean;
import com.yidu.sevensecondmall.bean.OrderMessage.ShippingListBean;
import com.yidu.sevensecondmall.bean.OrderMessage.TotalPriceBean;
import com.yidu.sevensecondmall.bean.OrderMessage.UserInfo;
import com.yidu.sevensecondmall.bean.Others.ArticleBean;
import com.yidu.sevensecondmall.bean.Others.CommentBean;
import com.yidu.sevensecondmall.bean.Others.CommentListBean;
import com.yidu.sevensecondmall.bean.Others.goodlistBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.networks.HttpBuilder;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */
public class GoodsDAO {

//
//    /**获取网站配置*/
//    public static void getConfig(final BaseCallBack callBack) {
//        OkHttpClientManager.doOkHttpPost(HttpApi.getConfig)
//                  .setBaseCallBack(callBack);
//    }
//
//    /**获取服务器时间戳*/ //1491034922
//    public static void getServerTime(final BaseCallBack callBack) {
//        OkHttpClientManager.doOkHttpPost(HttpApi.getServerTime)
//                  .setBaseCallBack(callBack);
//    }

    public static void GetHomeIndex(final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.IndexHome)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //广告栏
    public static void GetBanner(final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.IndexBanner)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONArray array  =new JSONObject(data.toString()).getJSONArray("bannerlist");
                            BannerBean bean = new BannerBean();
                            List<BannerBean.BannerlistBean> list = new ArrayList<BannerBean.BannerlistBean>();
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject obj = array.getJSONObject(i);
                                    BannerBean.BannerlistBean bean2 = new BannerBean.BannerlistBean();
                                    bean2.setAd_code(obj.getString("ad_code"));
                                    bean2.setAd_name(obj.getString("ad_name"));
                                    list.add(bean2);
                                }
                            }
                            bean.setBannerlist(list);
                            baseCallBack.success(bean);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }


    /**
     * 公共信息
     */
    public static void home( final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.home)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        callBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                    }
                });

    }



    //首页商品列表
    public static void GetShopList(int page,String order,String priceby,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.ShopList)
                .addCode("page",page)
//                .addCode("order",order)
                .addCode("priceby",priceby)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            ShopListBean bean = new ShopListBean();
                            List<ShopListBean.GoodsListBean> list = new ArrayList<ShopListBean.GoodsListBean>();
                            JSONArray array = obj.getJSONArray("goodsList");
                            if(array.length() >0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject goods = array.getJSONObject(i);
                                    ShopListBean.GoodsListBean goodbean = new ShopListBean.GoodsListBean();
                                    goodbean.setGoods_id(goods.getInt("goods_id"));
                                    goodbean.setGoods_name(goods.getString("goods_name"));
                                    goodbean.setOriginal_img(goods.getString("original_img"));
                                    goodbean.setShop_price(goods.getString("shop_price"));
                                    list.add(goodbean);
                                }
                            }
                            bean.setGoodsList(list);
                            bean.setTotal_page(obj.getString("total_page"));
                            baseCallBack.success(bean);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //商品详情
    public static void GetGoodsInfo(final String id, final BaseCallBack baseCallBack){
        HttpBuilder builder = OkHttpClientManager.doOkHttpPost(HttpApi.GoodInfo)
                .addCode("id",id);
        if(LoginUtils.isLogin()){
            builder.addCode("token",LoginUtils.getToken());
        }
                builder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        getInfoSuccess(data,baseCallBack);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    private static void getInfoSuccess(Object data,final BaseCallBack baseCallBack){
        try{
            JSONObject obj = new JSONObject(data.toString()).getJSONObject("goodsinfo");
            JSONObject good = obj.getJSONObject("goods");
            GoodInfoBean bean = new GoodInfoBean();
            if(!obj.isNull("is_collect")&&obj.has("is_collect")){
                bean.setCollected(obj.getBoolean("is_collect"));
            }
            GoodInfoBean.GoodsBean goodsbean = new GoodInfoBean.GoodsBean();
            goodsbean.setGoods_id(good.getInt("goods_id"));
            goodsbean.setGoods_name(good.getString("goods_name"));
            goodsbean.setGoods_sn(good.getString("goods_sn"));
            goodsbean.setShop_price(good.getString("shop_price"));
            goodsbean.setStore_count(good.getInt("store_count"));
            goodsbean.setGoods_content(good.getString("goods_content"));
            goodsbean.setSales_sum(good.getInt("sales_sum"));
            goodsbean.setGive_integral(good.getInt("give_integral"));
            goodsbean.setOriginal_img(good.getString("original_img"));
            goodsbean.setWeight(good.getString("weight"));

            GoodInfoBean.GoodsBean.AddressBean addressBean = new GoodInfoBean.GoodsBean.AddressBean();
            GoodInfoBean.GoodsBean.AddressBean.ConfigBean cbean = new GoodInfoBean.GoodsBean.AddressBean.ConfigBean();
            JSONObject add = good.getJSONObject("address");
            addressBean.setShipping_area_name(add.getString("shipping_area_name"));
            JSONObject con = add.getJSONObject("config");
            cbean.setFirst_weight(con.getString("first_weight"));
            cbean.setMoney(con.getString("money"));
            cbean.setSecond_weight(con.getString("second_weight"));
            cbean.setAdd_money(con.getString("add_money"));
            addressBean.setConfig(cbean);
            goodsbean.setAddress(addressBean);

            GoodInfoBean.GoodsBean.ProminfoBean pbean = new GoodInfoBean.GoodsBean.ProminfoBean();
            if(good.has("prominfo")&&!good.isNull("prominfo")){
                JSONObject prom = good.getJSONObject("prominfo");
                pbean.setName(prom.getString("name"));
                goodsbean.setProminfo(pbean);
            }
            bean.setGoods(goodsbean);
            List<GoodInfoBean.GalleryBean> list = new ArrayList<>();
            JSONArray array = obj.getJSONArray("gallery");
            if(array.length() > 0){
                for(int i = 0;i < array.length();i++){
                    GoodInfoBean.GalleryBean galleryBean = new GoodInfoBean.GalleryBean();
                    JSONObject gallery = array.getJSONObject(i);
                    galleryBean.setImage_url(gallery.getString("image_url"));
                    list.add(galleryBean);
                }
            }
            bean.setGallery(list);
            JSONObject o = new JSONObject(data.toString());
            bean.setCartcount(o.getInt("cartcount"));
            baseCallBack.success(bean);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }


    //获取商品属性
    public static void GetGoodAttrs(String id,final BaseCallBack baseCallBack){
        HttpBuilder builder = OkHttpClientManager.doOkHttpPost(HttpApi.GoodInfo)
                .addCode("id",id);
        if(LoginUtils.isLogin()){
            builder.addCode("token",LoginUtils.getToken());
        }
                builder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            //获取单个属性列表
                            JSONObject good= new JSONObject(data.toString()).getJSONObject("goodsinfo")
                                    .getJSONObject("goods");
                            List<AttrsBean.GoodsSpecListBean> list = new ArrayList<AttrsBean.GoodsSpecListBean>();
                            AttrsBean bean = new AttrsBean();
                            //获取提供的组合属性列表
                            List<AttrsBean.MulBean> attrslist = new ArrayList<AttrsBean.MulBean>();
                            JSONObject goodInfo = new JSONObject(data.toString()).getJSONObject("goodsinfo");
                            if(goodInfo.has("spec_goods_price")&&!goodInfo.isNull("spec_goods_price")){
                                JSONObject mulattrs = goodInfo
                                        .getJSONObject("spec_goods_price");
                                Iterator<?> iterator = mulattrs.keys();
                                while(iterator.hasNext()){
                                    AttrsBean.MulBean mulbean = new AttrsBean.MulBean();
                                    String key = (String) iterator.next();
                                    JSONObject mul = new JSONObject(mulattrs.getString(key));
                                    mulbean.setCount(mul.getInt("store_count"));
                                    mulbean.setPrice(mul.getString("price"));
                                    mulbean.setKey(mul.getString("key"));
                                    attrslist.add(mulbean);
                                }

                            }
                            bean.setMullist(attrslist);

                            if(good.has("goods_spec_list")&&!good.isNull("goods_spec_list")){
                                JSONArray attrsarray = good.getJSONArray("goods_spec_list");
                                if(attrsarray.length() > 0){
                                    for(int i = 0;i < attrsarray.length();i++){
                                        JSONObject attr = attrsarray.getJSONObject(i);
                                        AttrsBean.GoodsSpecListBean sbean = new AttrsBean.GoodsSpecListBean();
                                        sbean.setSpec_name(attr.getString("spec_name"));
                                        sbean.setItem(attr.getString("item"));
                                        sbean.setItem_id(attr.getInt("item_id"));
                                        sbean.setSrc(attr.getString("src"));
                                        list.add(sbean);
                                    }
                                }
                            }
                            bean.setGoods_spec_list(list);
                            baseCallBack.success(bean);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });

    }

    //加入购物车
    public static void AddCart(String goods_id,String goods_num,String goods_spec,
                               final BaseCallBack baseCallBack){
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPost(HttpApi.AddCart);
        httpBuilder.addCode("goods_id",goods_id);
        httpBuilder.addCode("goods_num",goods_num);
        if(!goods_spec.isEmpty()) {
            httpBuilder.addCode("goods_spec", goods_spec);
        }
        httpBuilder.LogGetURL();
        httpBuilder.setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    public static void AddCartWithToken(String goods_id,String goods_num,String goods_spec,
                                 final BaseCallBack baseCallBack){
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.AddCart);
        httpBuilder.addCode("goods_id",goods_id);
        httpBuilder.addCode("goods_num",goods_num);
        if(!goods_spec.isEmpty()) {
            httpBuilder.addCode("goods_spec", goods_spec);
        }
        httpBuilder.LogGetURL();
        httpBuilder.setBaseCallBack(new BaseCallBack() {
            @Override
            public void success(Object data) {
                baseCallBack.success(data);
            }

            @Override
            public void failed(int errorCode, Object data) {
                baseCallBack.failed(errorCode, data);
            }
        });
    }


    /**购物车第二步确定页面*/
    public static void cart2(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.cart2)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            Cart2Bean result = new Cart2Bean();
                            try {
                                JSONObject content = new JSONObject(data.toString());

                                if (content.has("addressList")){
                                    ArrayList<AddressBean> list = new ArrayList<>();
                                    JSONArray addressList = content.getJSONArray("addressList");
                                    for (int i = 0; i < addressList.length(); i++) {
                                        JSONObject jsonObject = addressList.getJSONObject(i);
                                        AddressBean bean = new AddressBean();
                                        bean.setAddress_id(jsonObject.getString("address_id"));
                                        bean.setUser_id(jsonObject.getString("user_id"));
                                        bean.setConsignee(jsonObject.getString("consignee"));
                                        bean.setEmail(jsonObject.getString("email"));
                                        bean.setCountry(jsonObject.getString("country"));
                                        bean.setProvince(jsonObject.getString("province"));
                                        bean.setCity(jsonObject.getString("city"));
                                        bean.setDistrict(jsonObject.getString("district"));
                                        bean.setTwon(jsonObject.getString("twon"));
                                        bean.setAddress(jsonObject.getString("address"));
                                        bean.setZipcode(jsonObject.getString("zipcode"));
                                        bean.setMobile(jsonObject.getString("mobile"));
                                        bean.setIs_default(jsonObject.getString("is_default"));
                                        bean.setIs_pickup(jsonObject.getString("is_pickup"));
                                        list.add(bean);
                                    }
                                    result.setAddressList(list);
                                }
                                if (content.has("shippingList")){
                                    ArrayList<ShippingListBean> list = new ArrayList<>();
                                    JSONArray shippingList = content.getJSONArray("shippingList");
                                    for (int i = 0; i < shippingList.length(); i++) {
                                        JSONObject jsonObject = shippingList.getJSONObject(i);
                                        ShippingListBean bean = new ShippingListBean();
                                        bean.setCode(jsonObject.getString("code"));
                                        bean.setName(jsonObject.getString("name"));
                                        bean.setVersion(jsonObject.getString("version"));
                                        bean.setAuthor(jsonObject.getString("author"));
                                        bean.setConfig(jsonObject.getString("config"));
                                        bean.setDesc(jsonObject.getString("desc"));
                                        bean.setStatus(jsonObject.getString("status"));
                                        bean.setType(jsonObject.getString("type"));
                                        bean.setIcon(jsonObject.getString("icon"));
                                        bean.setBank_code(jsonObject.getString("scene"));
                                        list.add(bean);
                                    }
                                    result.setShippingList(list);
                                }
                                if (content.has("cartList")){
                                    ArrayList<CartlistBean.CartListBean> list = new ArrayList<>();
                                    JSONArray cartList = content.getJSONArray("cartList");
                                    for (int i = 0; i < cartList.length(); i++) {
                                        JSONObject jsonObject = cartList.getJSONObject(i);
                                        CartlistBean.CartListBean bean = new CartlistBean.CartListBean();
                                        bean.setId(jsonObject.getInt("id"));
                                        bean.setUser_id(jsonObject.getInt("user_id"));
                                        bean.setSession_id(jsonObject.getString("session_id"));
                                        bean.setGoods_id(jsonObject.getInt("goods_id"));
                                        bean.setGoods_sn(jsonObject.getString("goods_sn"));
                                        bean.setGoods_name(jsonObject.getString("goods_name"));
                                        bean.setMarket_price(jsonObject.getString("market_price"));
                                        bean.setGoods_price(jsonObject.getString("goods_price"));
                                        bean.setMember_goods_price(jsonObject.getString("member_goods_price"));
                                        bean.setGoods_num(jsonObject.getInt("goods_num"));
                                        bean.setSpec_key(jsonObject.getString("spec_key"));
                                        bean.setSpec_key_name(jsonObject.getString("spec_key_name"));
                                        bean.setBar_code(jsonObject.getString("bar_code"));
                                        bean.setSelected(jsonObject.getInt("selected"));
                                        bean.setAdd_time(jsonObject.getInt("add_time"));
                                        bean.setProm_type(jsonObject.getInt("prom_type"));
                                        bean.setProm_id(jsonObject.getInt("prom_id"));
                                        bean.setSku(jsonObject.getString("sku"));
                                        bean.setGoods_fee(jsonObject.getInt("goods_fee"));
                                        bean.setStore_count(jsonObject.getInt("store_count"));
                                        if(jsonObject.has("goodsinfo")&&!jsonObject.isNull("goodsinfo")){
                                            JSONObject info = jsonObject.getJSONObject("goodsinfo");
                                            bean.setOriginal_img(info.getString("original_img"));
                                            bean.setIs_cod(info.getInt("is_cod"));
                                        }
//                                        if(jsonObject.has("business_user_id")&&!jsonObject.isNull("business_user_id")){
//                                            bean.setBusiness_user_id(jsonObject.getString("business_user_id"));
//                                        }
                                        list.add(bean);
                                    }
                                    result.setCartList(list);
                                }
                                if (content.has("totalPrice")){
                                    JSONObject totalPrice = content.getJSONObject("totalPrice");
                                    TotalPriceBean bean = new TotalPriceBean();
                                    bean.setCut_fee(totalPrice.getDouble("cut_fee"));
                                    bean.setTotal_fee(totalPrice.getDouble("total_fee"));
                                    bean.setNum(totalPrice.getInt("num"));
                                    bean.setWeight(totalPrice.getString("all_weight"));
                                    bean.setAll_shipping_price(totalPrice.getDouble("all_shipping_price"));
                                    result.setTotalPriceBean(bean);
                                    Log.e("TotalPriceBean",""+bean.toString());
                                }
                                if (content.has("couponList")){
                                    ArrayList<CouponBean> list = new ArrayList<>();
                                    JSONArray couponList = content.getJSONArray("couponList");
                                    for (int i = 0; i < couponList.length(); i++) {
                                        JSONObject jsonObject = couponList.getJSONObject(i);
                                        CouponBean bean = new CouponBean();
                                        bean.setName(jsonObject.getString("name"));
                                        bean.setMoney(jsonObject.getString("money"));
                                        bean.setCondition(jsonObject.getString("condition"));
                                        bean.setId(jsonObject.getInt("id"));
                                        bean.setCid(jsonObject.getInt("cid"));
                                        bean.setType(jsonObject.getInt("type"));
                                        bean.setUid(jsonObject.getInt("uid"));
                                        bean.setOrder_id(jsonObject.getInt("order_id"));
                                        bean.setUse_time(jsonObject.getString("use_time"));
                                        bean.setCode(jsonObject.getString("code"));
                                        bean.setSend_time(jsonObject.getString("send_time"));
                                        list.add(bean);
                                    }
                                    result.setCouponList(list);
                                }
                                if (content.has("userInfo")){
                                    UserInfo bean = new UserInfo();
                                    JSONObject jsonObject = content.getJSONObject("userInfo");
                                    bean.setUser_id(jsonObject.getInt("user_id"));
                                    bean.setNickname(jsonObject.getString("nickname"));
                                    bean.setMobile(jsonObject.getString("mobile"));
                                    result.setUserInfo(bean);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            callBack.success(result);
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            callBack.failed(errorCode, data);
                        }
                 });
    }

    /**获取订单商品价格 或者提交 订单*/ //return order_id
    public static void cart3(String address_id, String shipping_code,  //必选参数 : address_id  收货地址id  ;shipping_code 物流编号  ;
                             String invoice_title, // 非必选参数 invoice_title 发票; couponTypeSelect 优惠券类型 1 下拉框选择优惠券 2 输入框输入优惠券代码; coupon_id 优惠券id;
                             String pay_points, String user_money,//非必选参数 couponCode 优惠券代码 必选参数 ; pay_points 使用积分 非必选参数 ; user_money 使用余额
                             boolean submit,final BaseCallBack callBack) {
        HttpBuilder builder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.cart3)
                 .addCode("address_id", address_id)
                .addCode("shipping_code", shipping_code);
//                .addCode("invoice_title", invoice_title)
//                .addCode("couponTypeSelect", couponTypeSelect)
//                .addCode("coupon_id", coupon_id)
//                .addCode("couponCode", couponCode)
//                .addCode("pay_points", pay_points)
//                .addCode("user_money", user_money)
        if(submit){
            builder.addCode("act", "submit_order");
        }
                builder.setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            String str = obj.getString("order_id");
                            callBack.success(str);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    //搜索关键字列表
    public static void Hotkeywords(final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.Hotkeywords)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            SearchHotBean bean = new SearchHotBean();
                            if(obj.has("hotkeywords") && !obj.isNull("hotkeywords")){
                                JSONArray array = obj.getJSONArray("hotkeywords");
                                List<String> list = new ArrayList<String>();
                                for(int i = 0; i < array.length();i++){
                                    list.add(array.getString(i));
                                }
                                bean.setHotkeywords(list);
                            }
                            baseCallBack.success(bean);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //非登录获取购物车商品列表
    public static void getCartList(final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.getCartList)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            JSONArray array = obj.getJSONArray("cartList");
                            //构建json
                            JSONArray controllarray = new JSONArray();
                            CartlistBean bean = new CartlistBean();
                            List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject cobj = array.getJSONObject(i);
                                    CartlistBean.CartListBean ccbean = new CartlistBean.CartListBean();
                                    ccbean.setId(cobj.getInt("id"));
                                    ccbean.setGoods_id(cobj.getInt("goods_id"));
                                    ccbean.setGoods_name(cobj.getString("goods_name"));
                                    ccbean.setGoods_price(cobj.getString("goods_price"));
                                    ccbean.setGoods_num(cobj.getInt("goods_num"));
                                    ccbean.setSelected(cobj.getInt("selected"));
                                    ccbean.setBuy_back_price(cobj.getString("buy_back_price"));

                                    if(cobj.has("shop_name")&&!cobj.isNull("shop_name")){
                                        ccbean.setShop_name(cobj.getString("shop_name"));
                                    }
                                    if(cobj.has("business_user_id")&&!cobj.isNull("business_user_id")){
                                        ccbean.setBusiness_user_id(cobj.getString("business_user_id"));
                                    }
                                    if(!cobj.isNull("spec_key_name")){
                                        ccbean.setSpec_key_name(cobj.getString("spec_key_name"));
                                    }

                                    if(cobj.has("goodsinfo")&&!cobj.isNull("goodsinfo")){
                                        JSONObject info = cobj.getJSONObject("goodsinfo");
                                        ccbean.setOriginal_img(info.getString("original_img"));

                                    }
                                    list.add(ccbean);

                                    //生成新的obj
                                    JSONObject controllobject = new JSONObject();
                                    controllobject.put("goodsNum",cobj.getInt("goods_num"));
                                    controllobject.put("selected",cobj.getInt("selected"));
                                    controllobject.put("cartID",cobj.getInt("id"));
                                    controllarray.put(controllobject);
                                }
                            }
                            bean.setArray(controllarray);
                            bean.setCartList(list);
                            baseCallBack.success(bean);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //非登录状态下同步数据到后台
    public static void sendCartJsonWithOutToken(String json,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.getCartList)
                .addCode("cart_form_data",json)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });

    }
    //非登录状态下同步数据到后台
    public static void sendCartJsonWithOutTokenBySotre(String json,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.getCartList)
                .addCode("shop_data",json)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });

    }



    //登录状态下同步数据到前端
    public static void sendCartJsonWithToken(String json,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getCartList)
                .addCode("cart_form_data",json)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //登录状态下同步数据到前端
    public static void sendCartJsonWithTokenByStore(String json,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getCartList)
                .addCode("shop_data",json)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //登录状态下获取购物车列表同时构建一份json数据用于控制数据
    public static void getCartListWithToken(final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getCartList)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            //解析json
                            JSONArray array = obj.getJSONArray("cartList");
                            //构建json
                            JSONArray controllarray = new JSONArray();

                            CartlistBean bean = new CartlistBean();
                            List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject cobj = array.getJSONObject(i);
                                    CartlistBean.CartListBean ccbean = new CartlistBean.CartListBean();
                                    ccbean.setId(cobj.getInt("id"));
                                    ccbean.setGoods_id(cobj.getInt("goods_id"));
                                    ccbean.setGoods_name(cobj.getString("goods_name"));
                                    ccbean.setGoods_price(cobj.getString("goods_price"));
                                    ccbean.setGoods_num(cobj.getInt("goods_num"));
                                    ccbean.setSelected(cobj.getInt("selected"));
                                    ccbean.setSpec_key_name(cobj.getString("spec_key_name"));
                                    if(cobj.has("goodsinfo")&&!cobj.isNull("goodsinfo")){
                                        JSONObject info = cobj.getJSONObject("goodsinfo");
                                        ccbean.setIs_free_shipping(info.getString("is_free_shipping"));
                                        ccbean.setOriginal_img(info.getString("original_img"));
                                    }
                                    if(cobj.has("shop_name")&&!cobj.isNull("shop_name")){
                                        ccbean.setShop_name(cobj.getString("shop_name"));
                                        Log.e("shopname-------","-------------------"+cobj.getString("shop_name"));
                                    }
                                    if(cobj.has("business_user_id")&&!cobj.isNull("business_user_id")){
                                        ccbean.setBusiness_user_id(cobj.getString("business_user_id"));
                                    }
                                    list.add(ccbean);

                                    //生成新的obj
                                    JSONObject controllobject = new JSONObject();
                                    controllobject.put("goodsNum",cobj.getInt("goods_num"));
                                    controllobject.put("selected",cobj.getInt("selected"));
                                    controllobject.put("cartID",cobj.getInt("id"));
                                    controllarray.put(controllobject);
                                }
                            }
                            bean.setCartList(list);
                            bean.setArray(controllarray);
                            baseCallBack.success(bean);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //加入收藏
    public static void collectGoods(int type,String goods_id,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.CollectGoods)
                .addCode("type",type)
                .addCode("goods_id",goods_id)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //获取收藏列表
    public static void GetCollectionGoods(final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getGoodsCollect)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            CollectionBean cbean = new CollectionBean();
                            List<CollectionBean.ListBean> list = new ArrayList<CollectionBean.ListBean>();
                            JSONArray array = obj.getJSONArray("list");
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject listobj = array.getJSONObject(i);
                                    CollectionBean.ListBean bean = new CollectionBean.ListBean();
                                    bean.setCollect_id(listobj.getInt("collect_id"));
                                    bean.setGoods_id(listobj.getInt("goods_id"));
                                    bean.setAdd_time(listobj.getInt("add_time"));
                                    bean.setGoods_name(listobj.getString("goods_name"));
                                    bean.setShop_price(listobj.getString("shop_price"));
                                    bean.setOriginal_img(listobj.getString("original_img"));
                                    bean.setCart(listobj.getBoolean("is_cart"));
                                    list.add(bean);
                                }
                            }
                            cbean.setList(list);
                            baseCallBack.success(cbean);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }


    /**删除购物车的商品*/
    public static void delCart(int ids, boolean token,final BaseCallBack callBack) {
        HttpBuilder builder = OkHttpClientManager.doOkHttpPost(HttpApi.delCart)
                 .addCode("ids", ids);
        if(token){
            builder.addCode("token", LoginUtils.getToken());
        }
                 builder.setBaseCallBack(new BaseCallBack() {
                     @Override
                     public void success(Object data) {
                         callBack.success(data);
                     }

                     @Override
                     public void failed(int errorCode, Object data) {
                         callBack.failed(errorCode, data);
                     }
                 });
    }


    /***获取商品分类*/
    public static void getgoodsCategoryList(int parent_id,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.goodsCategoryList)
                .addCode("parent_id",parent_id)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            List<goodCategoryBean> list = new ArrayList<goodCategoryBean>();
                            JSONArray array = obj.getJSONArray("goodsCategoryList");
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject gobj = array.getJSONObject(i);
                                    goodCategoryBean bean = new goodCategoryBean();
                                    bean.setId(gobj.getInt("id"));
                                    bean.setName(gobj.getString("name"));
                                    bean.setMobile_name(gobj.getString("mobile_name"));
                                    bean.setParent_id(gobj.getInt("parent_id"));
                                    bean.setParent_id_path(gobj.getString("parent_id_path"));
                                    bean.setLevel(gobj.getInt("level"));
                                    bean.setSort_order(gobj.getInt("sort_order"));
                                    bean.setIs_show(gobj.getInt("is_show"));
                                    bean.setImage(gobj.getString("image"));
                                    bean.setIs_hot(gobj.getInt("is_hot"));
                                    bean.setCat_group(gobj.getInt("cat_group"));
                                    bean.setCommission_rate(gobj.getInt("commission_rate"));
                                    if (i == 0){
                                        bean.setChoose(true);
                                    }
                                    list.add(bean);
                                }
                            }
                            baseCallBack.success(list);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    /**获取二三级分类**/
    public static void getSecThird(int parent_id,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.goodsSecAndThirdCategoryList)
                .addCode("parent_id",parent_id)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());
                            JSONArray array = obj.getJSONArray("list");
                            List<STCategoryBean> list = new ArrayList<STCategoryBean>();
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject sobj = array.getJSONObject(i);
                                    STCategoryBean bean = new STCategoryBean();
                                    bean.setMobile_name(sobj.getString("mobile_name"));
                                    bean.setImage(sobj.getString("image"));
                                    bean.setId(sobj.getInt("id"));
                                    bean.setParent_id(sobj.getInt("parent_id"));
                                    bean.setLevel(sobj.getInt("level"));

                                    List<STCategoryBean.SubCategoryBean> tlist = new ArrayList<STCategoryBean.SubCategoryBean>();
                                    JSONArray tarray = sobj.optJSONArray("sub_category");
                                    if(tarray != null) {
                                        for (int j = 0; j < tarray.length(); j++) {
                                            JSONObject tobj = tarray.getJSONObject(j);
                                            STCategoryBean.SubCategoryBean tbean = new STCategoryBean.SubCategoryBean();
                                            tbean.setMobile_name(tobj.getString("mobile_name"));
                                            tbean.setImage(tobj.getString("image"));
                                            tbean.setId(tobj.getInt("id"));
                                            tbean.setParent_id(tobj.getInt("parent_id"));
                                            tbean.setLevel(tobj.getInt("level"));
                                            tlist.add(tbean);
                                        }
                                    }
                                    bean.setSub_category(tlist);

                                    list.add(bean);
                                }
                            }
                            baseCallBack.success(list);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //商品列表页
    public static void getGoodsList(String id,String brand_id,String spec,String attr,
                                    String sort,String sort_asc,String price,String start_price,
                                    String end_price,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.goodslist)
                .addCode("id",id)
                .addCode("brand_id",brand_id)
                .addCode("spec",spec)
                .addCode("attr",attr)
                .addCode("sort",sort)
                .addCode("sort_asc",sort_asc)
                .addCode("price",price)
                .addCode("start_price",start_price)
                .addCode("end_price",end_price)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString()).getJSONObject("list");
                            goodlistBean bean = new goodlistBean();

                            //goods_list列表
                            List<goodlistBean.GoodsListBean> goodlist = new ArrayList<goodlistBean.GoodsListBean>();
                            JSONArray goodarray = obj.getJSONArray("goods_list");
                            if(goodarray.length() > 0){
                                for(int i = 0;i < goodarray.length();i++){
                                    JSONObject gobj = goodarray.getJSONObject(i);
                                    goodlistBean.GoodsListBean gbean = new goodlistBean.GoodsListBean();
                                    gbean.setGoods_id(gobj.getInt("goods_id"));
                                    gbean.setCat_id(gobj.getInt("cat_id"));
                                    gbean.setGoods_sn(gobj.getString("goods_sn"));
                                    gbean.setGoods_name(gobj.getString("goods_name"));
                                    gbean.setShop_price(gobj.getString("shop_price"));
                                    gbean.setComment_count(gobj.getInt("comment_count"));
                                    goodlist.add(gbean);
                                }
                            }
                            bean.setGoods_list(goodlist);

                            //filter_spec
                            List<goodlistBean.FilterSpecBean> speclist = new ArrayList<goodlistBean.FilterSpecBean>();
                            JSONArray specarray = obj.getJSONArray("filter_spec");
                            if(specarray.length() > 0){
                                for(int i = 0;i<specarray.length();i++){
                                    JSONObject sobj = specarray.getJSONObject(i);
                                    goodlistBean.FilterSpecBean sbean = new goodlistBean.FilterSpecBean();
                                    sbean.setName(sobj.getString("name"));

                                    //item
                                    List<goodlistBean.FilterSpecBean.ItemBean> itemlist = new ArrayList<goodlistBean.FilterSpecBean.ItemBean>();
                                    JSONArray itemarray = sobj.getJSONArray("item");
                                    if(itemarray.length() > 0){
                                        for(int j = 0;j < itemarray.length();j++){
                                            JSONObject itemobj = itemarray.getJSONObject(i);
                                            goodlistBean.FilterSpecBean.ItemBean itembean = new goodlistBean.FilterSpecBean.ItemBean();
                                            itembean.setName(itemobj.getString("name"));
                                            itembean.setHref(itemobj.getString("href"));
                                            itembean.setId(itemobj.getInt("id"));
                                            itemlist.add(itembean);
                                        }
                                    }
                                    sbean.setItem(itemlist);
                                    speclist.add(sbean);
                                }
                            }
                            bean.setFilter_spec(speclist);


                            //filter_attr
                            List<goodlistBean.FilterAttrBean> filterlist = new ArrayList<goodlistBean.FilterAttrBean>();
                            JSONArray filterarray = obj.getJSONArray("filter_attr");
                            if(filterarray.length() > 0){
                                for(int i = 0;i < filterarray.length();i++){
                                    JSONObject fobj = filterarray.getJSONObject(i);
                                    goodlistBean.FilterAttrBean fbean = new goodlistBean.FilterAttrBean();
                                    fbean.setName(fobj.getString("name"));

                                    //item
                                    List<goodlistBean.FilterAttrBean.ItemBean> itemlist = new ArrayList<goodlistBean.FilterAttrBean.ItemBean>();
                                    JSONArray itemarray = fobj.getJSONArray("item");
                                    if(itemarray.length() > 0){
                                        for(int j = 0;j < itemarray.length();j++){
                                            JSONObject itemobj = itemarray.getJSONObject(i);
                                            goodlistBean.FilterAttrBean.ItemBean itembean = new goodlistBean.FilterAttrBean.ItemBean();
                                            itembean.setName(itemobj.getString("name"));
                                            itembean.setHref(itemobj.getString("href"));
                                            itembean.setId(itemobj.getInt("id"));
                                            itemlist.add(itembean);
                                        }
                                    }
                                    fbean.setItem(itemlist);
                                    filterlist.add(fbean);
                                }
                            }
                            bean.setFilter_attr(filterlist);

                            //filter_brand
                            List<goodlistBean.FilterBrandBean> brandlist = new ArrayList<goodlistBean.FilterBrandBean>();
                            JSONArray brandarray = obj.getJSONArray("filter_brand");
                            if(brandarray.length() > 0){
                                for(int i = 0;i < brandarray.length();i++){
                                    JSONObject bobj = brandarray.getJSONObject(i);
                                    goodlistBean.FilterBrandBean bbean = new goodlistBean.FilterBrandBean();
                                    bbean.setName(bobj.getString("name"));
                                    bbean.setHreg(bobj.getString("hreg"));
                                    bbean.setId(bobj.getInt("id"));
                                    brandlist.add(bbean);
                                }
                            }
                            bean.setFilter_brand(brandlist);

                            //filter_price
                            List<goodlistBean.FilterPriceBean> pricelist = new ArrayList<goodlistBean.FilterPriceBean>();
                            JSONArray pricearray = obj.getJSONArray("filter_price");
                            if(pricearray.length() > 0){
                                for(int i = 0;i < pricearray.length();i++){
                                    JSONObject pobj = pricearray.getJSONObject(i);
                                    goodlistBean.FilterPriceBean pbean = new goodlistBean.FilterPriceBean();
                                    pbean.setName(pobj.getString("name"));
                                    pbean.setHref(pobj.getString("hreg"));
                                    pbean.setId(pobj.getInt("id"));
                                    pricelist.add(pbean);
                                }
                            }
                            bean.setFilter_price(pricelist);
                            baseCallBack.success(bean);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });

    }

    //商品搜索列表
    public static void searchGoods(String q,String id,String brand_id,String sort,String sort_asc,
                                   String price,String start_price,String end_price,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.searchGoods)
                .addCode("q",q)
                .addCode("id",id)
                .addCode("brand_id",brand_id)
                .addCode("sort",sort)
                .addCode("sort_asc",sort_asc)
                .addCode("price",price)
                .addCode("start_price",start_price)
                .addCode("end_price",end_price)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString()).getJSONObject("list");
                            goodlistBean bean = new goodlistBean();

                            //goods_list列表
                            List<goodlistBean.GoodsListBean> goodlist = new ArrayList<goodlistBean.GoodsListBean>();
                            JSONArray goodarray = obj.getJSONArray("goods_list");
                            if(goodarray.length() > 0){
                                for(int i = 0;i < goodarray.length();i++){
                                    JSONObject gobj = goodarray.getJSONObject(i);
                                    goodlistBean.GoodsListBean gbean = new goodlistBean.GoodsListBean();
                                    gbean.setGoods_id(gobj.getInt("goods_id"));
                                    gbean.setCat_id(gobj.getInt("cat_id"));
                                    gbean.setGoods_sn(gobj.getString("goods_sn"));
                                    gbean.setGoods_name(gobj.getString("goods_name"));
                                    gbean.setShop_price(gobj.getString("shop_price"));
                                    gbean.setComment_count(gobj.getInt("comment_count"));
                                    goodlist.add(gbean);
                                }
                            }
                            bean.setGoods_list(goodlist);

                            //filter_brand
                            List<goodlistBean.FilterBrandBean> brandlist = new ArrayList<goodlistBean.FilterBrandBean>();
                            JSONArray brandarray = obj.getJSONArray("filter_brand");
                            if(brandarray.length() > 0){
                                for(int i = 0;i < brandarray.length();i++){
                                    JSONObject bobj = brandarray.getJSONObject(i);
                                    goodlistBean.FilterBrandBean bbean = new goodlistBean.FilterBrandBean();
                                    bbean.setName(bobj.getString("name"));
                                    bbean.setHreg(bobj.getString("hreg"));
                                    bbean.setId(bobj.getInt("id"));
                                    brandlist.add(bbean);
                                }
                            }
                            bean.setFilter_brand(brandlist);

                            //filter_price
                            List<goodlistBean.FilterPriceBean> pricelist = new ArrayList<goodlistBean.FilterPriceBean>();
                            JSONArray pricearray = obj.getJSONArray("filter_price");
                            if(pricearray.length() > 0){
                                for(int i = 0;i < pricearray.length();i++){
                                    JSONObject pobj = pricearray.getJSONObject(i);
                                    goodlistBean.FilterPriceBean pbean = new goodlistBean.FilterPriceBean();
                                    pbean.setName(pobj.getString("name"));
                                    pbean.setHref(pobj.getString("hreg"));
                                    pbean.setId(pobj.getInt("id"));
                                    pricelist.add(pbean);
                                }
                            }
                            bean.setFilter_price(pricelist);
                            baseCallBack.success(bean);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //商品搜索列表
    public static void searchGoods(String q
//            , String sort,String sort_asc
            , final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.searchGoods)
                .addCode("q",q)
//                .addCode("sort",sort)
//                .addCode("sort_asc",sort_asc)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ArrayList<GoodListBean> list = new ArrayList<>();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("list")){
                                JSONArray array = content.getJSONArray("list");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    GoodListBean bean = new GoodListBean();
                                    bean.setGoods_id(jsonObject.getString("goods_id"));
                                    bean.setCat_id(jsonObject.getString("cat_id"));
                                    bean.setGoods_sn(jsonObject.getString("goods_sn"));
                                    bean.setGoods_name(jsonObject.getString("goods_name"));
                                    bean.setShop_price(jsonObject.getString("shop_price"));
                                    bean.setComment_count(jsonObject.getString("comment_count"));
                                    bean.setOriginal_img(jsonObject.getString("original_img"));
                                    list.add(bean);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            baseCallBack.failed("");
                        }
                        baseCallBack.success(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    public static void SearchGoods(String nav_type,String more_id,final  BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.searchGoods).addCode("nav_type",nav_type).addCode("more_id",more_id).LogGetURL().setBaseCallBack(new BaseCallBack() {
            @Override
            public void success(Object data) {
                ArrayList<GoodListBean> list = new ArrayList<>();
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("list")&&!content.isNull("list")){
                        JSONArray array = content.getJSONArray("list");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            GoodListBean bean = new GoodListBean();
                            bean.setGoods_id(jsonObject.getString("goods_id"));
                            bean.setCat_id(jsonObject.getString("cat_id"));
                            bean.setGoods_sn(jsonObject.getString("goods_sn"));
                            bean.setGoods_name(jsonObject.getString("goods_name"));
                            bean.setShop_price(jsonObject.getString("shop_price"));
                            bean.setComment_count(jsonObject.getString("comment_count"));
                            bean.setOriginal_img(jsonObject.getString("original_img"));
                            Log.e("goods_name",jsonObject.getString("goods_name"));
                            list.add(bean);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                baseCallBack.success(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                baseCallBack.failed(errorCode, data);
            }
        });
    }

    //商品搜索列表
    public static void searchGoods(int id, final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.searchGoods)
                .addCode("id", id)
//                .addCode("sort",sort)
//                .addCode("sort_asc",sort_asc)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ArrayList<GoodListBean> list = new ArrayList<>();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("list")&&!content.isNull("list")){
                                JSONArray array = content.getJSONArray("list");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    GoodListBean bean = new GoodListBean();
                                    bean.setGoods_id(jsonObject.getString("goods_id"));
                                    bean.setCat_id(jsonObject.getString("cat_id"));
                                    bean.setGoods_sn(jsonObject.getString("goods_sn"));
                                    bean.setGoods_name(jsonObject.getString("goods_name"));
                                    bean.setShop_price(jsonObject.getString("shop_price"));
                                    bean.setComment_count(jsonObject.getString("comment_count"));
                                    bean.setOriginal_img(jsonObject.getString("original_img"));
                                    list.add(bean);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        baseCallBack.success(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //获取商品评价
    public static void getCommentInfo(String goods_id, String page, String comment_type, final BaseCallBack baseCallBack){
        HttpBuilder builder = OkHttpClientManager.doOkHttpPost(HttpApi.getComment)
                .addCode("goods_id",goods_id)
                .addCode("page",page);
        if(!comment_type.equals("")){
            builder.addCode("comment_type",comment_type);
        }
        builder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                      try {
                          JSONObject content = new JSONObject(data.toString());
                          CommendInfo info = new CommendInfo();
                          if (content.has("list")){
                              ArrayList<Visitable> list = new ArrayList<>();
                              JSONArray array = content.getJSONArray("list");
                              for(int i = 0;i < array.length();i++){
                                  JSONObject cobj = array.getJSONObject(i);
                                  CommentBean bean = new CommentBean();
                                  bean.setComment_id(cobj.getInt("comment_id"));
                                  bean.setGoods_id(cobj.getInt("goods_id"));
                                  bean.setEmail(cobj.getString("email"));
                                  bean.setUsername(cobj.getString("username"));
                                  bean.setContent(cobj.getString("content"));
                                  bean.setDeliver_rank(cobj.getInt("deliver_rank"));
                                  bean.setAdd_time(cobj.getInt("add_time"));
                                  bean.setIp_address(cobj.getString("ip_address"));
                                  bean.setIs_show(cobj.getInt("is_show"));
                                  bean.setParent_id(cobj.getInt("parent_id"));
                                  bean.setUser_id(cobj.getInt("user_id"));
                                  bean.setHead_pic(cobj.getString("head_pic"));

                                  List<String>imglist = new ArrayList<String>();
                                  if(cobj.has("img")&&!cobj.isNull("img")&&!cobj.get("img").equals("")){
                                      JSONArray imgarray = cobj.getJSONArray("img");
                                      if(imgarray.length() > 0){
                                          for(int j = 0;j < imgarray.length();j++){
                                              imglist.add(imgarray.getString(i));
                                          }
                                      }
                                  }
                                  bean.setImg(imglist);
                                  bean.setOrder_id(cobj.getInt("order_id"));
                                  bean.setGoods_rank(cobj.getInt("goods_rank"));
                                  bean.setService_rank(cobj.getInt("service_rank"));
                                  list.add(bean);
                              }
                              info.setList(list);
                              if (content.has("total_page")){
                                  info.setTotalPage(content.getInt("total_page"));
                              }
                          }
                          baseCallBack.success(info);
                      }catch (Exception e){
                          e.printStackTrace();
                          baseCallBack.failed("数据异常");
                      }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //获取商品评价
    public static void getComment(String goods_id, String page, String comment_type, final BaseCallBack baseCallBack){
        HttpBuilder builder = OkHttpClientManager.doOkHttpPost(HttpApi.getComment)
                .addCode("goods_id",goods_id)
                .addCode("page",page);
        if(!comment_type.equals("")){
            builder.addCode("comment_type",comment_type);
        }
                builder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONArray array = new JSONObject(data.toString()).getJSONArray("list");
                            CommentListBean listbean = new CommentListBean();
                            List<CommentBean> list = new ArrayList<CommentBean>();
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject cobj = array.getJSONObject(i);
                                    CommentBean bean = new CommentBean();
                                    bean.setComment_id(cobj.getInt("comment_id"));
                                    bean.setGoods_id(cobj.getInt("goods_id"));
                                    bean.setEmail(cobj.getString("email"));
                                    bean.setUsername(cobj.getString("username"));
                                    bean.setContent(cobj.getString("content"));
                                    bean.setDeliver_rank(cobj.getInt("deliver_rank"));
                                    bean.setAdd_time(cobj.getInt("add_time"));
                                    bean.setIp_address(cobj.getString("ip_address"));
                                    bean.setIs_show(cobj.getInt("is_show"));
                                    bean.setParent_id(cobj.getInt("parent_id"));
                                    bean.setUser_id(cobj.getInt("user_id"));

                                    List<String>imglist = new ArrayList<String>();
                                    if(cobj.has("img")&&!cobj.isNull("img")&&!cobj.get("img").equals("")){
                                        JSONArray imgarray = cobj.getJSONArray("img");
                                        if(imgarray.length() > 0){
                                            for(int j = 0;j < imgarray.length();j++){
                                                imglist.add(imgarray.getString(i));
                                            }
                                        }
                                    }
                                    bean.setImg(imglist);
                                    bean.setOrder_id(cobj.getInt("order_id"));
                                    bean.setGoods_rank(cobj.getInt("goods_rank"));
                                    bean.setService_rank(cobj.getInt("service_rank"));
                                    list.add(bean);
                                }
                            }
                            listbean.setList(list);
                            JSONObject obj = new JSONObject(data.toString());
                            listbean.setAll_count(obj.getString("all_count"));
                            listbean.setGood_count(obj.getString("good_count"));
                            listbean.setCommonly_count(obj.getString("commonly_count"));
                            listbean.setBad_count(obj.getString("bad_count"));
                            listbean.setImg_count(obj.getString("img_count"));

                            baseCallBack.success(listbean);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //添加评论
    public static void addComment(String goods_id,String order_id,String service_rank,
                                  String deliver_rank,String goods_rank,String content,
                                  String imgurl,final BaseCallBack baseCallBack){
        HttpBuilder builder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.addComment)
                .addCode("goods_id",goods_id)
                .addCode("order_id",order_id)
                .addCode("service_rank",service_rank)
                .addCode("deliver_rank",deliver_rank)
                .addCode("goods_rank",goods_rank)
                .addCode("content",content);
        if(!imgurl.isEmpty()){
            builder.addCode("imgurl",imgurl);
        }
                builder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        baseCallBack.success(data);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }


    //文章详情
    public void getArticleDetail(String article_id,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.ArticleDetail)
                .addCode("article_id",article_id)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString()).getJSONObject("article");
                            ArticleBean bean = new ArticleBean();
                            bean.setArticle_id(obj.getInt("article_id"));
                            bean.setCat_id(obj.getInt("cat_id"));
                            bean.setTitle(obj.getString("title"));
                            bean.setContent(obj.getString("content"));
                            bean.setAuthor(obj.getString("author"));
                            bean.setAuthor_email(obj.getString("author_email"));
                            bean.setKeywords(obj.getString("keywords"));
                            bean.setArticle_type(obj.getInt("article_type"));
                            bean.setIs_open(obj.getInt("is_open"));
                            bean.setAdd_time(obj.getInt("add_time"));
                            bean.setFile_url(obj.getString("file_url"));
                            bean.setOpen_type(obj.getInt("open_type"));
                            bean.setLink(obj.getString("link"));
                            bean.setDescription(obj.getString("description"));
                            bean.setClick(obj.getInt("click"));
                            bean.setPublish_time(obj.getInt("publish_time"));
                            bean.setThumb(obj.getString("thumb"));
                            baseCallBack.success(bean);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.success(errorCode,data);
                    }
                });
    }

    //获取文章列表
    public static void getArticleList(int page,final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPost(HttpApi.ArticleList)
                .addCode("page",page)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            List<ArticleBean> list = new ArrayList<ArticleBean>();
                            JSONArray array = new JSONObject(data.toString()).getJSONArray("list");
                            if(array.length() > 0){
                                for(int i = 0;i < array.length();i++){
                                    JSONObject obj = array.getJSONObject(i);
                                    ArticleBean bean = new ArticleBean();
                                    bean.setArticle_id(obj.getInt("article_id"));
                                    bean.setCat_id(obj.getInt("cat_id"));
                                    bean.setTitle(obj.getString("title"));
                                    bean.setContent(obj.getString("content"));
                                    bean.setAuthor(obj.getString("author"));
                                    bean.setAuthor_email(obj.getString("author_email"));
                                    bean.setKeywords(obj.getString("keywords"));
                                    bean.setArticle_type(obj.getInt("article_type"));
                                    bean.setIs_open(obj.getInt("is_open"));
                                    bean.setAdd_time(obj.getInt("add_time"));
                                    bean.setFile_url(obj.getString("file_url"));
                                    bean.setOpen_type(obj.getInt("open_type"));
                                    bean.setLink(obj.getString("link"));
                                    bean.setDescription(obj.getString("description"));
                                    bean.setClick(obj.getInt("click"));
                                    bean.setPublish_time(obj.getInt("publish_time"));
                                    bean.setThumb(obj.getString("thumb"));
                                    list.add(bean);
                                }
                            }
                            baseCallBack.success(data);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    }

    //申请退款
    public static void applyRefund(String order_id, String goods_id, String reason,
                                   String order_sn, String spec_key, final BaseCallBack baseCallBack){
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.applyRefund)
                .addCode("order_id",order_id)
                .addCode("goods_id",goods_id)
                .addCode("reason",reason)
                .addCode("spec_key", spec_key)
                .addCode("order_sn",order_sn)
                .LogGetURL()
                .setBaseCallBack(baseCallBack);

    }

}
