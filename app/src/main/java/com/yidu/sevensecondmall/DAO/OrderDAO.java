package com.yidu.sevensecondmall.DAO;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.bean.Order.OrderInfo;
import com.yidu.sevensecondmall.bean.Order.ShipDataBean;
import com.yidu.sevensecondmall.bean.Order.carPriceBean;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.bean.OrderMessage.Cart2Bean;
import com.yidu.sevensecondmall.bean.OrderMessage.CouponBean;
import com.yidu.sevensecondmall.bean.OrderMessage.GoodsBean;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderDetailBean;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderDetailInfo;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderListBean;
import com.yidu.sevensecondmall.bean.OrderMessage.ReturnGoodsBean;
import com.yidu.sevensecondmall.bean.OrderMessage.ReturnGoodsInfo;
import com.yidu.sevensecondmall.bean.OrderMessage.ShipStatusBean;
import com.yidu.sevensecondmall.bean.OrderMessage.ShippingListBean;
import com.yidu.sevensecondmall.bean.OrderMessage.TotalPriceBean;
import com.yidu.sevensecondmall.bean.OrderMessage.UserInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.networks.HttpBuilder;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class OrderDAO {

    /**
     * 获取收货地址
     */
    public static void getAddressList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getAddressList)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ArrayList<AddressBean> list = new ArrayList<>();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("list")) {
                                JSONArray array = content.getJSONArray("list");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
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
                                    JSONObject districtInfo = jsonObject.getJSONObject("districtinfo");
                                    bean.setMergeName(districtInfo.getString("mergename"));
                                    list.add(bean);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.success(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                        //1001, 不存在数据
                    }
                });
    }

    /**
     * 添加/编辑地址
     */ //address_id 地址id 编辑传 添加地址不传  return "address_id": "365"
    public static void addAddress(String address_id, String consignee, int province, int district, int city, String address, String mobile, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.addAddress)
                .addCode("address_id", address_id)
                .addCode("consignee", consignee)
                .addCode("province", province)
                .addCode("district", district)
                .addCode("city", city)
                .addCode("address", address)
                .addCode("mobile", mobile)
                .setBaseCallBack(new BaseCallBack() {
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


    /**
     * 地址删除
     */
    public static void delAddress(String address_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.delAddress)
                .addCode("address_id", address_id)
                .setBaseCallBack(new BaseCallBack() {
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

    /**
     * 用户订单列表
     */
    public static void getOrderList(int page, final String type, final BaseCallBack callBack) {
        HttpBuilder builder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getOrderList)
                .addCode("page", page);
        if (!type.equals("")) {
            builder.addCode("type", type);
        }
        builder.setBaseCallBack(new BaseCallBack() {
            @Override
            public void success(Object data) {
                OrderInfo info = new OrderInfo();
                ArrayList<Visitable> list = new ArrayList<>();
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("order_list")) {
                        JSONArray order_list = content.getJSONArray("order_list");
                        for (int i = 0; i < order_list.length(); i++) {
                            JSONObject jsonObject = order_list.getJSONObject(i);
                            OrderListBean bean = new OrderListBean();
                            if (type.equals("free_order")) {
                                bean.setFree(true);
                            }
                            bean.setGoods_num(Integer.valueOf(jsonObject.getString("goods_num")));
                            bean.setConsignee(jsonObject.getString("consignee"));
                            bean.setShipping_code(jsonObject.getString("shipping_code"));
                            bean.setOrder_id(jsonObject.getString("order_id"));
                            bean.setOrder_sn(jsonObject.getString("order_sn"));
                            bean.setBuy_back_amount(jsonObject.getString("buy_back_amount"));
                            bean.setBuy_back_status(jsonObject.getString("buy_back_status"));
                            bean.setGoods_price(jsonObject.getString("goods_price"));
                            bean.setOrder_status_code(jsonObject.getString("order_status_code"));
                            bean.setOrder_status_desc(jsonObject.getString("order_status_desc"));
                            if(jsonObject.has("invoice_no")) {
                            bean.setInvoice_no(jsonObject.getString("invoice_no"));
                            }
                            if(jsonObject.has("shop_name")) {
                                bean.setShop_name(jsonObject.getString("shop_name"));
                            }
                            if (jsonObject.has("free_status_code")) {
                                bean.setFree_status_code(jsonObject.getString("free_status_code"));
                            }
                            if (jsonObject.has("free_status_desc")) {
                                bean.setFree_status_desc(jsonObject.getString("free_status_desc"));
                            }
                            if (jsonObject.has("shipping_name")) {
                                bean.setShipping_name(jsonObject.getString("shipping_name"));
                            }


                            bean.setTotal_amount(jsonObject.getString("total_amount"));

                            ArrayList<OrderListBean.GoodsListBean> goodlist = new ArrayList<OrderListBean.GoodsListBean>();
                            JSONArray goodarray = jsonObject.getJSONArray("goods_list");
                            for (int j = 0; j < goodarray.length(); j++) {
                                JSONObject gobj = goodarray.getJSONObject(j);
                                OrderListBean.GoodsListBean gbean = new OrderListBean.GoodsListBean();
                                gbean.setGoods_name(gobj.getString("goods_name"));
                                gbean.setGoods_price(gobj.getString("goods_price"));
                                gbean.setOriginal_img(gobj.getString("original_img"));
                                Log.e("daoimg: ", gobj.getString("original_img"));
                                goodlist.add(gbean);
                            }
                            bean.setGoods_list(goodlist);
                            list.add(bean);
                        }
                        info.setList(list);
                    }
                    if (content.has("total_page")) {
                        info.setTotalPage(content.getInt("total_page"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.failed("没有数据");
                }
                callBack.success(info);
            }

            @Override
            public void failed(final int errorCode, final Object data) {
                Log.e("getOrderList", "errorCode:" + errorCode + ";" + data.toString());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(errorCode, data);
                    }
                });

            }
        });
    }

    /**
     * 获取物流信息
     */
    public static void getShipStatue(String shipping_code, String shipping_sn, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getShipStatus)
                .addCode("shipping_code", shipping_code)
                .addCode("shipping_sn", shipping_sn)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ShipStatusBean info = new ShipStatusBean();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("result")) {
                                JSONObject jsonObject = content.getJSONObject("result");
                                JSONObject jsonObject2 = jsonObject;
                                info.setMessage(jsonObject2.getString("message"));
                                info.setNu(jsonObject2.getString("nu"));
                                info.setIscheck(jsonObject2.getString("ischeck"));
                                info.setCondition(jsonObject2.getString("condition"));
                                info.setCom(jsonObject2.getString("com"));
                                info.setStatus(jsonObject2.getString("status"));
                                info.setState(jsonObject2.getString("state"));

                                Log.e("物流解析",""+info.toString());

                                if (jsonObject.has("data")) {
                                    ArrayList<Visitable> list = new ArrayList<>();
                                    JSONArray array = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++) {
                                        ShipDataBean bean = new ShipDataBean();
                                        JSONObject jo = array.getJSONObject(i);
                                        bean.setContext(jo.getString("context"));
                                        bean.setFtime(jo.getString("ftime"));
                                        bean.setTime(jo.getString("time"));
                                        list.add(bean);
                                    }
                                    info.setData(list);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.success(info);
                    }

                    @Override
                    public void failed(final int errorCode, final Object data) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.failed(errorCode, data);
                            }
                        });
                    }
                });
    }


    /**
     * 获取订单详情
     */
    public static void getOrderDetail(String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getOrderDetail)
                .addCode("order_id", order_id)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        OrderDetailInfo info = new OrderDetailInfo();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("order_info")) {
                                JSONObject jsonObject = content.getJSONObject("order_info");
                                OrderDetailBean bean = new OrderDetailBean();
                                bean.setConsignee(jsonObject.getString("consignee"));
                                bean.setOrder_sn(jsonObject.getString("order_sn"));
                                bean.setAddress(jsonObject.getString("address"));
                                bean.setMobile(jsonObject.getString("mobile"));
                                bean.setGoods_price(jsonObject.getString("goods_price"));
                                bean.setShipping_price(jsonObject.getString("shipping_price"));
                                bean.setTotal_amount(jsonObject.getString("total_amount"));
                                bean.setAdd_time(jsonObject.getString("add_time"));
                                bean.setPay_time(jsonObject.getString("pay_time"));
                                bean.setOrder_status_code(jsonObject.getString("order_status_code"));
                                bean.setOrder_status_desc(jsonObject.getString("order_status_desc"));
                                bean.setCode(jsonObject.getString("shipping_code"));
                                bean.setProvince(jsonObject.getString("province"));
                                bean.setCity(jsonObject.getString("city"));
                                bean.setShipping_code(jsonObject.getString("shipping_code"));
                                bean.setDistrict(jsonObject.getString("district"));
                                bean.setBuy_back_status(jsonObject.getString("buy_back_status"));
                                info.setOrderDetailBean(bean);
                                if (jsonObject.has("goods_list")) {
                                    ArrayList<GoodsBean> list = new ArrayList<>();
                                    JSONArray array = jsonObject.getJSONArray("goods_list");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jObject = array.getJSONObject(i);
                                        GoodsBean gbean = new GoodsBean();
                                        gbean.setRec_id(jObject.getString("rec_id"));
                                        gbean.setOrder_id(jObject.getString("order_id"));
                                        gbean.setGoods_id(jObject.getString("goods_id"));
                                        gbean.setGoods_name(jObject.getString("goods_name"));
                                        gbean.setGoods_sn(jObject.getString("goods_sn"));
                                        gbean.setGoods_num(jObject.getString("goods_num"));
                                        gbean.setMarket_price(jObject.getString("market_price"));
                                        gbean.setGoods_price(jObject.getString("goods_price"));
                                        gbean.setCost_price(jObject.getString("cost_price"));
                                        gbean.setMember_goods_price(jObject.getString("member_goods_price"));
                                        gbean.setGive_Stringegral(jObject.getString("give_integral"));
                                        gbean.setSpec_key(jObject.getString("spec_key"));
                                        gbean.setSpec_key_name(jObject.getString("spec_key_name"));
                                        gbean.setBar_code(jObject.getString("bar_code"));
                                        gbean.setIs_comment(jObject.getString("is_comment"));
                                        gbean.setProm_type(jObject.getString("prom_type"));
                                        gbean.setProm_id(jObject.getString("prom_id"));
                                        gbean.setIs_send(jObject.getString("is_send"));
                                        gbean.setDelivery_id(jObject.getString("delivery_id"));
                                        gbean.setSku(jObject.getString("sku"));
                                        gbean.setOriginal_img(jObject.getString("original_img"));
                                        gbean.setWeight(jObject.getString("weight"));
                                        gbean.setAllshippingprice(jObject.getInt("all_shipping_price"));
                                        if (jObject.has("return_goods_info") && !jObject.isNull("return_goods_info")) {
                                            JSONObject robj = jObject.getJSONObject("return_goods_info");
                                            ReturnGoodsInfo rinfo = new ReturnGoodsInfo();
                                            rinfo.setType(robj.getString("type"));
                                            rinfo.setStatus(robj.getString("status"));
                                            gbean.setInfo(rinfo);
                                        }
                                        list.add(gbean);
                                    }
                                    info.setList(list);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.success(info);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 收货确认
     */
    public static void orderConfirm(String order_id, String goods_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.orderConfirm)
                .addCode("order_id", order_id)
                .addCode("goods_id", goods_id)
                .setBaseCallBack(new BaseCallBack() {
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

    /**
     * 取消订单
     */
    public static void cancelOrder(String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.cancelOrder)
                .addCode("order_id", order_id)
                .setBaseCallBack(new BaseCallBack() {
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

    /**
     * 设置默认收货地址
     */
    public static void setDefaultAddress(String address_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.setDefaultAddress)
                .addCode("address_id", address_id)
                .setBaseCallBack(new BaseCallBack() {
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

    /**
     * 退换货列表
     */
    public static void returnGoodsList(String order_id, String order_sn, String goods_id, String type,
                                       String reason, String spec_key, String img_url, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.returnGoodsList)
                .addCode("order_id", order_id)
                .addCode("order_sn", order_sn)
                .addCode("goods_id", goods_id)
                .addCode("type", type)
                .addCode("reason", reason)
                .addCode("spec_key", spec_key)
                .addCode("img_url", img_url)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ArrayList<ReturnGoodsBean> list = new ArrayList<>();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("list")) {
                                JSONArray array = content.getJSONArray("list");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    ReturnGoodsBean bean = new ReturnGoodsBean();
                                    bean.setId(jsonObject.getString("id"));
                                    bean.setOrder_id(jsonObject.getString("order_id"));
                                    bean.setOrder_sn(jsonObject.getString("order_sn"));
                                    bean.setGoods_id(jsonObject.getString("goods_id"));
                                    bean.setType(jsonObject.getString("type"));
                                    bean.setReason(jsonObject.getString("reason"));
                                    bean.setImgs(jsonObject.getString("imgs"));
                                    bean.setAddtime(jsonObject.getString("addtime"));
                                    bean.setStatus(jsonObject.getString("status"));
                                    bean.setRemark(jsonObject.getString("remark"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setSpec_key(jsonObject.getString("spec_key"));
                                    bean.setGoods_name(jsonObject.getString("goods_name"));
                                    list.add(bean);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.success(list);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 申请退货
     */
    public static void returnGoods(String order_id, String order_sn, String goods_id, String type, String reason, String spec_key, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.returnGoods)
                .addCode("order_id", order_id)
                .addCode("order_sn", order_sn)
                .addCode("goods_id", goods_id)
                .addCode("type", type)
                .addCode("reason", reason)
                .addCode("spec_key", spec_key)
                .setBaseCallBack(new BaseCallBack() {
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


    /**
     * 申请退货状态
     */  //1141已经在申请退货中， 1142可以去申请退货， 1143并不存在该订单， 1144已经提交过退货申请
    public static void returnGoodsStatus(String order_id, String goods_id, String spec_key, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.returnGoodsStatus)
                .addCode("order_id", order_id)
                .addCode("goods_id", goods_id)
                .addCode("spec_key", spec_key)
                .setBaseCallBack(new BaseCallBack() {
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

    /**
     * 退货详情
     */
    public static void returnGoodsInfo(int return_goods_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.returnGoodsInfo)
                .addCode("return_goods_id", return_goods_id)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ReturnGoodsInfo bean = new ReturnGoodsInfo();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("return_goods")) {
                                JSONObject jsonObject = content.getJSONObject("return_goods");
                                bean.setId(jsonObject.getString("id"));
                                bean.setOrder_id(jsonObject.getString("order_id"));
                                bean.setOrder_sn(jsonObject.getString("order_sn"));
                                bean.setGoods_id(jsonObject.getString("goods_id"));
                                bean.setType(jsonObject.getString("type"));
                                bean.setReason(jsonObject.getString("reason"));
                                ArrayList<String> images = new ArrayList<>();
                                JSONArray imgs = jsonObject.getJSONArray("imgs");
                                for (int i = 0; i < imgs.length(); i++) {
                                    images.add(imgs.getString(i));
                                }
                                bean.setImgs(images);
                                bean.setAddtime(jsonObject.getString("addtime"));
                                bean.setStatus(jsonObject.getString("status"));
                                bean.setRemark(jsonObject.getString("remark"));
                                bean.setUser_id(jsonObject.getString("user_id"));
                                bean.setSpec_key(jsonObject.getString("spec_key"));
                                bean.setGoods_name(jsonObject.getString("goods_name"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        callBack.success(bean);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }


    public static void BuyNow(String array, final BaseCallBack callBack) {
        HttpBuilder builder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.buynow);
        if (!array.equals("")) {
            builder.addCode("shoppinglist", array);
        }
        builder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        Cart2Bean result = new Cart2Bean();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("car_price")) {
                                JSONObject obj = content.getJSONObject("car_price");

                                carPriceBean carbean = new carPriceBean();
                                if (!obj.isNull("cut_fee")) {
                                    carbean.setCut_fee(obj.getInt("cut_fee"));
                                }
                                if (!obj.isNull("total_price")) {
                                    carbean.setTotal_price(obj.getDouble("total_price"));
                                }
                                if (!obj.isNull("weight")) {
                                    carbean.setWeight(obj.getString("weight"));
                                }
                                if(!obj.isNull("shipping_price")){
                                    carbean.setShipping_price(obj.getString("shipping_price"));
                                }
                                carbean.setNum(obj.getInt("goods_nums"));

                                result.setPricebean(carbean);
                            }
                            if (content.has("addressList")) {
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

                            if (content.has("shippingList")) {
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
                            if (content.has("cartList")) {
                                ArrayList<CartlistBean.CartListBean> list = new ArrayList<>();
                                JSONArray cartList = content.getJSONArray("cartList");
                                for (int i = 0; i < cartList.length(); i++) {
                                    JSONObject jsonObject = cartList.getJSONObject(i);
                                    CartlistBean.CartListBean bean = new CartlistBean.CartListBean();
//                                    bean.setId(jsonObject.getInt("id"));
//                                    bean.setUser_id(jsonObject.getInt("user_id"));
//                                    bean.setSession_id(jsonObject.getString("session_id"));
                                    bean.setGoods_id(jsonObject.getInt("goods_id"));
                                    if (jsonObject.has("goods_sn")) {
                                        bean.setGoods_sn(jsonObject.getString("goods_sn"));
                                    }
                                    bean.setGoods_name(jsonObject.getString("goods_name"));
//                                    bean.setMarket_price(jsonObject.getString("market_price"));
                                    bean.setGoods_price(jsonObject.getString("goods_price"));
                                    bean.setMember_goods_price(jsonObject.getString("member_goods_price"));
                                    bean.setGoods_num(jsonObject.getInt("goods_num"));
                                    bean.setSpec_key(jsonObject.getString("spec_key"));
                                    bean.setSpec_key_name(jsonObject.getString("spec_key_name"));
//                                    bean.setBar_code(jsonObject.getString("bar_code"));
                                    if (jsonObject.has("selected")) {
                                        bean.setSelected(jsonObject.getInt("selected"));
                                    }

                                    if (jsonObject.has("add_time")) {
                                        bean.setAdd_time(jsonObject.getInt("add_time"));
                                    }
//                                    bean.setProm_type(jsonObject.getInt("prom_type"));
//                                    bean.setProm_id(jsonObject.getInt("prom_id"));
//                                    bean.setSku(jsonObject.getString("sku"));
                                    bean.setGoods_fee(jsonObject.getInt("goods_fee"));
                                    bean.setStore_count(jsonObject.getInt("store_count"));
                                    if (jsonObject.has("goodsinfo") && !jsonObject.isNull("goodsinfo")) {
                                        JSONObject info = jsonObject.getJSONObject("goodsinfo");
                                        bean.setOriginal_img(info.getString("original_img"));
                                    }
                                    if (jsonObject.has("goods_image") && !jsonObject.isNull("goods_image")) {
                                        bean.setOriginal_img(jsonObject.getString("goods_image"));
                                        Log.e(TAG, "success: " + jsonObject.getString("goods_image"));
                                    }
                                    bean.setIs_cod(jsonObject.getInt("is_cod"));
                                    list.add(bean);
                                }
                                result.setCartList(list);
                            }
                            if (content.has("totalPrice")) {
                                JSONObject totalPrice = content.getJSONObject("totalPrice");
                                TotalPriceBean bean = new TotalPriceBean();
                                bean.setCut_fee(totalPrice.getDouble("cut_fee"));
                                bean.setTotal_fee(totalPrice.getDouble("total_fee"));
                                bean.setNum(totalPrice.getInt("num"));
                                bean.setWeight(totalPrice.getString("weight"));
                                result.setTotalPriceBean(bean);
                            }
                            if (content.has("couponList")) {
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
                            if (content.has("userInfo")) {
                                UserInfo bean = new UserInfo();
                                JSONObject jsonObject = content.getJSONObject("userInfo");
                                bean.setUser_id(jsonObject.getInt("user_id"));
                                bean.setNickname(jsonObject.getString("nickname"));
                                bean.setMobile(jsonObject.getString("mobile"));
                                result.setUserInfo(bean);
                            }
                        } catch (Exception e) {
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

    private static final String TAG = "OrderDAO";

    //立即购买下一步
    public static void BuyNext(String addressid, String shipping_code, String array, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.buynow)
                .addCode("address_id", addressid)
                .addCode("shoppinglist", array)
                .addCode("shipping_code", shipping_code)
                .addCode("act", "submit_order")
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString());
                            String id = obj.getString("order_id");
                            baseCallBack.success(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }


    /****计算运费**/
    public static void calculatePost(String shipping_code, String province, String city,
                                     String district, String weight, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.calculate)
                .addCode("shipping_code", shipping_code)
                .addCode("province", province)
                .addCode("city", city)
                .addCode("district", district)
                .addCode("weight", weight)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        if (data != null) {
                            try {
                                JSONObject obj = new JSONObject(data.toString());
                                String result = obj.getString("shipping_price");
                                baseCallBack.success(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    /***立即支付测试***/
    public static void Pay(String order_id, String status, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.paytest)
                .addCode("order_id", order_id)
                .addCode("status", status)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString()).getJSONObject("goodsinfo");
                            String ordersn = obj.getString("order_sn");
                            baseCallBack.success(ordersn);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

}
