package com.yidu.sevensecondmall.DAO;

import android.util.Log;

import com.yidu.sevensecondmall.bean.Distribution.AssignmentBean;
import com.yidu.sevensecondmall.bean.Distribution.DingCoinRecordBean;
import com.yidu.sevensecondmall.bean.Distribution.DingCoinRecordInfo;
import com.yidu.sevensecondmall.bean.Distribution.InviteListBean;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.bean.Order.FeeShoppingBean;
import com.yidu.sevensecondmall.bean.User.AccountBean;
import com.yidu.sevensecondmall.bean.User.CouponBean;
import com.yidu.sevensecondmall.bean.User.InviteInfo;
import com.yidu.sevensecondmall.bean.User.MessageBean;
import com.yidu.sevensecondmall.bean.User.MessageDetailList;
import com.yidu.sevensecondmall.bean.User.MessageListInfo;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.bean.Video.LiveOnlineUserBean;
import com.yidu.sevensecondmall.bean.Video.PushListBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.networks.HttpBuilder;
import com.yidu.sevensecondmall.networks.HttpBuilderMulti;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class UserDAO {

    //登录
    public static void Login(String username, String password, String registration_id, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.Login)
                .addCode("username", username)
                .addCode("password", password)
                .addCode("registration_id", registration_id)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString());
                            Log.e("token", obj.getString("token"));
                            LoginUtils.setToken(obj.getString("token"));
                            LoginUtils.setUserId(obj.getString("user_id"));
                            LoginUtils.setPlatform(LoginUtils.PlatformPhone);
                            LoginUtils.setIsLogin(true);
                            baseCallBack.success(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            baseCallBack.failed(0, "登录失败");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode, data);
                    }
                });
    }

    //获取验证码
    public static void getRegCode(String mobile, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.getRegCode)
                .addCode("mobile", mobile)
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

    //注册
    public static void Resigter(String username, String password, String password2,
                                String code, String invite_mobile, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.reg)
                .addCode("username", username)
                .addCode("password", password)
                .addCode("password2", password2)
                .addCode("code", code)
                .addCode("invite_mobile", invite_mobile)
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

    //忘记密码
    public static void forgetPw(String mobile, String new_password, String confirm_password, String code,
                                final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.forgetpw)
                .addCode("mobile", mobile)
                .addCode("new_password", new_password)
                .addCode("confirm_password", confirm_password)
                .addCode("code", code)
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

    //获取用户信息
    public static void getUserInfo(final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getUserInfo)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString());
                            UserBean bean = new UserBean();
                            bean.setVip_valid_period(obj.getString("vip_valid_period"));
                            bean.setDing_coin(obj.getString("ding_coin"));
                            bean.setNew_level(obj.getString("new_level"));
                            bean.setRebate_points(obj.getString("rebate_points"));
                            bean.setUser_id(obj.getInt("user_id"));
                            bean.setUser_money_pwd(obj.getString("user_money_pwd"));
                            bean.setSex(obj.getInt("sex"));
                            bean.setBirthday(obj.getInt("birthday"));
                            bean.setUser_money(obj.getString("user_money"));
                            bean.setFrozen_money(obj.getString("frozen_money"));
//                            bean.setDistribut_money(obj.getString("distribut_money"));
                            bean.setPay_points(obj.getInt("pay_points"));
                            bean.setAddress_id(obj.getInt("address_id"));
                            bean.setLast_login(obj.getInt("last_login"));
                            bean.setShareholder_apply_status(obj.getString("shareholder_apply_status"));
                            bean.setLast_ip(obj.getString("last_ip"));
                            bean.setQq(obj.getString("qq"));
                            bean.setId_number(obj.getString("id_number"));
                            bean.setMobile(obj.getString("mobile"));
                            bean.setMobile_validated(obj.getInt("mobile_validated"));
//                            bean.setOauth(obj.getString("oauth"));
//                            bean.setOpenid(obj.getString("openid"));
                            bean.setHead_pic(obj.getString("head_pic"));
                            bean.setProvince(obj.getInt("province"));
                            bean.setCity(obj.getInt("city"));
                            bean.setDistrict(obj.getInt("district"));
                            bean.setEmail_validated(obj.getInt("email_validated"));
                            bean.setNickname(obj.getString("nickname"));
//                            bean.setUsertype(obj.getInt("usertype"));
                            bean.setLevel(obj.getInt("level"));
                            bean.setDiscount(obj.getString("discount"));
                            bean.setTotal_amount(obj.getString("total_amount"));
                            bean.setIs_lock(obj.getInt("is_lock"));
                            bean.setWaitPay(obj.getInt("waitPay"));
                            bean.setWaitSend(obj.getInt("waitSend"));
                            bean.setWaitReceive(obj.getInt("waitReceive"));
                            bean.setWaitComment(obj.getInt("waitComment"));
                            bean.setIs_authentication(obj.getString("is_authentication"));
                            bean.setId_number(obj.getString("id_number"));
                            bean.setRealname(obj.getString("realname"));
                            bean.setBusiness_apply_status(obj.getString("business_apply_status"));
                            if (obj.has("icon")) {
                                JSONObject jsonIcon = obj.getJSONObject("icon");
                                UserBean.icon icon = new UserBean.icon();
                                icon.setVip(jsonIcon.getBoolean("vip"));
                                icon.setAuthentication(jsonIcon.getBoolean("authentication"));
                                icon.setBusiness(jsonIcon.getBoolean("business"));
                                icon.setPartner(jsonIcon.getBoolean("shareholder"));
                                bean.setIcon(icon);
                            }
                            if(obj.has("valid_balance_by_user_id")){
                                JSONObject jsonObject = obj.getJSONObject("valid_balance_by_user_id");
                                UserBean.valid_balance_by_user_id  valid_balance_by_user_id= new UserBean.valid_balance_by_user_id();
                                valid_balance_by_user_id.setValid_balance(jsonObject.getString("valid_balance"));
                                bean.setValidBalanceByUserId(valid_balance_by_user_id);
                            }
                            if (obj.has("partner_orderinfo")) {
                                if (!obj.isNull("partner_orderinfo")) {
                                    JSONObject json = obj.getJSONObject("partner_orderinfo");
                                    JSONObject jsonObject = json.getJSONObject("orderinfo");

                                    UserBean.partnerOrderInfo info = new UserBean.partnerOrderInfo();
                                    info.setId(jsonObject.getString("id"));
                                    info.setUser_id(jsonObject.getString("user_id"));
                                    info.setType(jsonObject.getString("type"));
                                    info.setOrder_no(jsonObject.getString("order_no"));
                                    info.setOrder_fee(jsonObject.getString("order_fee"));
                                    UserBean.partnerOrderInfo.SubmitInfoBean subBean = new UserBean.partnerOrderInfo.SubmitInfoBean();
                                    JSONObject submit_info = jsonObject.getJSONObject("submit_info");
                                    subBean.setAddress(submit_info.getString("address"));
                                    subBean.setMobile(submit_info.getString("mobile"));
                                    subBean.setRealname(submit_info.getString("realname"));
                                    info.setProvince_id(jsonObject.getString("province_id"));
                                    info.setCity_id(jsonObject.getString("city_id"));
                                    info.setArea_id(jsonObject.getString("area_id"));
                                    info.setSubmit_info(subBean);
                                    info.setPay_status(jsonObject.getString("pay_status"));
                                    info.setStatus(jsonObject.getString("status"));
                                    bean.setInfo(info);


                                }

                            }

                            baseCallBack.success(bean);
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

    //更新用户信息
    public static void UpdatUserInfo(String nickname, String head_pic, int sex,
                                     final BaseCallBack baseCallBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.UpdateUserInfo);
        if (nickname != null) {
            httpBuilder.addCode("nickname", nickname);
        }
        httpBuilder.addCode("head_pic", head_pic)
                .addCode("sex", sex)
                .LogGetURL()
                .setBaseCallBack(baseCallBack);
    }

//
//    //更新用户信息
//    public static void UpdatUserInfo(String nickname,String qq,String head_pic,int sex,String birthday,
//                                     String province,String city,String district,final BaseCallBack baseCallBack){
//        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.UpdateUserInfo)
//                .addCode("nickname",nickname)
//                .addCode("qq",qq)
//                .addCode("head_pic",head_pic)
//                .addCode("sex",sex)
//                .addCode("birthday",birthday)
//                .addCode("province",province)
//                .addCode("city",city)
//                .addCode("district",district)
//                .LogGetURL()
//                .setBaseCallBack(new BaseCallBack() {
//                    @Override
//                    public void success(Object data) {
//                        baseCallBack.success(data);
//                    }
//
//                    @Override
//                    public void failed(int errorCode, Object data) {
//                        baseCallBack.failed(errorCode,data);
//                    }
//                });
//    }


    //用户修改密码
    public static void changePw(String old_password, String new_password, String confirm_password, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.Password)
                .addCode("old_password", old_password)
                .addCode("new_password", new_password)
                .addCode("confirm_password", confirm_password)
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

    //获取优惠券
    public static void getCouponList(int type, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getCouponList)
                .addCode("type", type)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString());
                            JSONArray array = obj.getJSONArray("list");
                            CouponBean cbean = new CouponBean();
                            List<CouponBean.ListBean> list = new ArrayList<CouponBean.ListBean>();
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject cobj = array.getJSONObject(i);
                                    CouponBean.ListBean bean = new CouponBean.ListBean();
                                    bean.setId(cobj.getInt("id"));
                                    bean.setCid(cobj.getInt("cid"));
                                    bean.setType(cobj.getInt("type"));
                                    bean.setUid(cobj.getInt("uid"));
                                    bean.setOrder_id(cobj.getInt("order_id"));
                                    bean.setUse_time(cobj.getInt("use_time"));
                                    bean.setCode(cobj.getString("code"));
                                    bean.setSend_time(cobj.getInt("send_time"));
                                    bean.setName(cobj.getString("name"));
                                    bean.setMoney(cobj.getString("money"));
                                    bean.setUse_end_time(cobj.getInt("use_end_time"));
                                    bean.setCondition(cobj.getString("condition"));
                                    list.add(bean);
                                }
                            }
                            cbean.setList(list);
                            baseCallBack.success(cbean);
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

    //账户资金
    public static void getAccount(int type, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.Account)
                .addCode("type", type)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject obj = new JSONObject(data.toString());
                            JSONArray array = obj.getJSONArray("account_log");
                            AccountBean bean = new AccountBean();
                            List<AccountBean.AccountLogBean> list = new ArrayList<AccountBean.AccountLogBean>();
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject aobj = array.getJSONObject(i);
                                    AccountBean.AccountLogBean lbean = new AccountBean.AccountLogBean();
                                    lbean.setLog_id(aobj.getInt("log_id"));
                                    lbean.setUser_id(aobj.getInt("user_id"));
                                    lbean.setUser_money(aobj.getString("user_money"));
                                    lbean.setFrozen_money(aobj.getString("frozen_money"));
                                    lbean.setPay_points(aobj.getInt("pay_points"));
                                    lbean.setChange_time(aobj.getInt("change_time"));
                                    lbean.setDesc(aobj.getString("desc"));
                                    if (aobj.has("order_sn") && !aobj.isNull("order_sn")) {
                                        lbean.setOrder_sn(aobj.getString("order_sn"));
                                    }
                                    if (aobj.has("order_id") && !aobj.isNull("order_id")) {
                                        lbean.setOrder_id(aobj.getString("order_id"));
                                    }
                                    list.add(lbean);
                                }
                            }
                            bean.setAccount_log(list);
                            baseCallBack.success(bean);
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


    //投诉建议
    public static void sendAdvice(String text, String type, String title, String mobile, String on, final BaseCallBack baseCallBack) {
        HttpBuilder builder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.toAdvice)
                .addCode("text", text)
                .addCode("type", type)
                .addCode("title", title)
                .addCode("on", on);

        if (!mobile.equals("")) {
            builder.addCode("mobile", mobile);
        }
        builder.LogGetURL()
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


    //绑定银行卡
    public static void BindCard(String bankcardnumber, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.Bindcard)
                .addCode("bankcardnumber", bankcardnumber)
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

    //退出登录
    public static void LoginOut(final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.LoginOut)
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


    //第三方登录
    public static void LoginThird(String openid, String from, String registration_id, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.thirdLogin)
                .addCode("openid", openid)
                .addCode("from", from)
                .addCode("registration_id", registration_id)
                .LogGetURL()
                .setBaseCallBack(baseCallBack);
    }

    //第一次绑手机
    public static void FirstBindMobile(String mobile, String code, String invite_mobile, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.ChangeMobile)
                .addCode("code", code)
                .addCode("mobile", mobile)
                .addCode("invite_mobile", invite_mobile)
                .addCode("type", "firstbind")
                .LogGetURL()
                .setBaseCallBack(baseCallBack);
    }

    //改绑手机
    public static void ChangeMobile(String mobile, String code, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.ChangeMobile)
                .addCode("code", code)
                .addCode("mobile", mobile)
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

    /**
     * 获取用户消息列表
     */
    public static void getMessageListInfo(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMessageCount)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        MessageListInfo info = new MessageListInfo();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("messages")) {
                                JSONObject messages = content.getJSONObject("messages");
                                info.setShipCount(messages.getInt("log_message_count"));
                                info.setInformCount(messages.getInt("sys_message_count"));
                                info.setActionCount(messages.getInt("act_message_count"));
                                info.setCount(messages.getInt("act_message_count") + messages.getInt("log_message_count") + messages.getInt("sys_message_count"));
                                if (messages.has("log_message")) {
                                    JSONArray log_message = messages.getJSONArray("log_message");
                                    if (log_message.length() > 0) {
                                        JSONObject first = log_message.getJSONObject(0);
                                        JSONObject jsonObject = first.getJSONObject("content");
                                        String shipTitle = "";
                                        String order_title = jsonObject.getString("order_title");
                                        int order_shipping_status = jsonObject.getInt("order_shipping_status");
                                        switch (order_shipping_status) {
                                            case 0:
                                                shipTitle = "订单待发货！您购买的" + order_title + "待发货";
                                                break;
                                            case 1:
                                                shipTitle = "订单已发货！您购买的" + order_title + "已发货";
                                                break;
                                            case 2:
                                                shipTitle = "订单已收货！您购买的" + order_title + "已收货";
                                                break;
                                        }
                                        info.setShipTitle(shipTitle);
                                        info.setShipTime(TimeFormatUtils.formatAll(first.getInt("send_time") * 1000L));
                                    }
                                }

                                if (messages.has("sys_message")) {
                                    JSONArray sys_message = messages.getJSONArray("sys_message");
                                    if (sys_message.length() > 0) {
                                        JSONObject jsonObject = sys_message.getJSONObject(0);
                                        info.setSysTime(TimeFormatUtils.formatAll(jsonObject.getInt("send_time") * 1000L));
                                        info.setSysTitle(jsonObject.getString("message"));
                                    }
                                }

                                if (messages.has("act_message")) {
                                    JSONArray act_message = messages.getJSONArray("act_message");
                                    if (act_message.length() > 0) {
                                        JSONObject first = act_message.getJSONObject(0);
                                        JSONObject jsonObject = first.getJSONObject("content");
                                        info.setActionTitle(jsonObject.getString("act_title"));
                                        info.setActionTime(TimeFormatUtils.formatAll(first.getInt("send_time") * 1000L));
                                    }
                                }
                            }
                            callBack.success(info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed(0, "数据异常");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 获取用户消息列表
     */
    public static void getMessageList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMessageCount)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        MessageDetailList result = new MessageDetailList();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("messages")) {
                                JSONObject messages = content.getJSONObject("messages");
                                if (messages.has("log_message")) {
                                    ArrayList<Visitable> list = new ArrayList<>();
                                    JSONArray log_message = messages.getJSONArray("log_message");
                                    for (int i = 0; i < log_message.length(); i++) {
                                        MessageBean bean = new MessageBean();
                                        JSONObject first = log_message.getJSONObject(i);
                                        bean.setMessage_id(first.getString("message_id"));
                                        bean.setAdmin_id(first.getString("admin_id"));
                                        bean.setType(first.getString("type"));
                                        bean.setCategory(first.getString("category"));
                                        bean.setSend_time(first.getString("send_time"));
                                        JSONObject jsonObject = first.getJSONObject("content");
                                        MessageBean.ContentBean contentBean = new MessageBean.ContentBean();
                                        contentBean.setOrder_img(jsonObject.getString("order_img"));
                                        contentBean.setOrder_title(jsonObject.getString("order_title"));
                                        contentBean.setOrder_code(jsonObject.getString("order_code"));
                                        contentBean.setOrder_shipping_name(jsonObject.getString("order_shipping_name"));
                                        contentBean.setOrder_shipping_status(jsonObject.getInt("order_shipping_status"));
                                        bean.setContent(contentBean);
                                        list.add(bean);
                                    }
                                    result.setShipList(list);
                                }

                                if (messages.has("sys_message")) {
                                    JSONArray sys_message = messages.getJSONArray("sys_message");
                                    if (sys_message.length() > 0) {
                                        JSONObject jsonObject = sys_message.getJSONObject(0);

                                    }
                                }

                                if (messages.has("act_message")) {
                                    JSONArray act_message = messages.getJSONArray("act_message");
                                    if (act_message.length() > 0) {
                                        JSONObject first = act_message.getJSONObject(0);
                                        JSONObject jsonObject = first.getJSONObject("content");

                                    }
                                }
                            }
                            callBack.success(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed(0, "数据异常");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }


    /**
     * app引导图
     */
    public static void appImgList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.appImgList)
                .setBaseCallBack(callBack);
    }

    /**
     * 支付信息
     **/
    public static void PayOrder(String pay_code, String order_id, String type,  String curr_id, final BaseCallBack baseCallBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.payOrder)
                .addCode("pay_code", pay_code)
                .addCode("order_id", order_id)
                .addCode("type", type);
        if (curr_id!=null) {
            httpBuilder .addCode("curr_id", curr_id);
        }
        httpBuilder.LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject object = new JSONObject(data.toString());
                            String str = object.getString("data");
                            Log.e("str", str);
                            baseCallBack.success(str);
                        } catch (Exception e) {
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
     * 微信信息
     */
    public static void payOrderWeChat(String order_id, String type, String curr_id, final BaseCallBack callBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.payOrderWeChat)
                .addCode("order_id", order_id)
                .addCode("type", type);
        if (curr_id!=null) {
            httpBuilder .addCode("curr_id", curr_id);
        }
        httpBuilder    .setBaseCallBack(callBack);
    }

    /**
     * 支付信息
     **/
    public static void BalancePay(String order_id, String type, String password, String curr_id,final BaseCallBack baseCallBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.balancePay)
                .addCode("order_id", order_id)
                .addCode("type", type)
                .addCode("password", password);
        if (curr_id!=null) {
            httpBuilder .addCode("curr_id", curr_id);
        }
        httpBuilder
                .LogGetURL()
                .setBaseCallBack(baseCallBack);
    }

    /**
     * 邀请会员
     */
    public static void inviteUser(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.inviteUser)
                .setBaseCallBack(callBack);
    }

    /**
     * 我的邀请列表
     */
    public static void myInviteUsers(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.myInviteUsers)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("list")) {
                                ArrayList<InviteListBean> list = new ArrayList<>();
                                JSONArray array = content.getJSONArray("list");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    InviteListBean bean = new InviteListBean();
                                    bean.setId(jsonObject.getString("id"));
                                    bean.setInvite_user_id(jsonObject.getString("invite_user_id"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setCreatetime(jsonObject.getString("createtime"));
                                    bean.setNickname(jsonObject.getString("nickname"));
                                    bean.setHead_pic(jsonObject.getString("head_pic"));
                                    list.add(bean);
                                }
                                callBack.success(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed("没有数据");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }


    /**
     * 申请成为合伙人-新
     */
    public static void applyToPartner(String mobile, String realname, String address,
                                      String province_id, String city_id, String area_id,
                                      final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.applyToPartner)
                .addCode("mobile", mobile)
                .addCode("realname", realname)
                .addCode("address", address)
                .addCode("province_id", province_id)
                .addCode("city_id", city_id)
                .addCode("area_id", area_id)
                .setBaseCallBack(callBack);
    }

    /**
     * 申请成为商家
     */
    public static void applyToBusiness(String mobile, String realname, String address,
                                       String province_id, String city_id, String area_id,
                                       String shop_name, String shop_main, File license_path,
                                       String receivables_num, String receivables_type,
                                       String company_name, String company_corporate,
                                       String company_open_bank, String company_tax_id,
                                       File id_card_image,
                                       final BaseCallBack callBack) {
        HttpBuilderMulti httpBuilderMulti = OkHttpClientManager.doOkHttpPostWithTokenMulti(HttpApi.applyToBusiness)
                .addCode("mobile", mobile)
                .addCode("realname", realname)
                .addCode("address", address)
                .addCode("province_id", province_id)
                .addCode("city_id", city_id)
                .addCode("area_id", area_id)
                .addCode("shop_name", shop_name)
                .addCode("shop_main", shop_main)
                .addCode("license_path", license_path, "image/png")
                .addCode("receivables_num", receivables_num)
                .addCode("receivables_type", receivables_type);

        if ("0".equals(receivables_type) ) {
            httpBuilderMulti
                    .addCode("company_name", company_name)
                    .addCode("company_corporate", company_corporate)
                    .addCode("company_open_bank", company_open_bank)
                    .addCode("company_tax_id", company_tax_id)

                    .setBaseCallBack(callBack);
        }else {
            httpBuilderMulti

                    .addCode("id_card_image", id_card_image, "image/png")

                    .setBaseCallBack(callBack);
        }
    }

    /**
     * 申请实名认证
     */
    public static void realnameAuthentication(String id_num, String realname, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.realnameAuthentication)
                .addCode("realname", realname)
                .addCode("id_num", id_num)
                .setBaseCallBack(callBack);
    }

    /**
     * 我的免单列表
     */
    public static void myFreeOrderList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.myFreeOrderList)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("orderlist")) {
                                JSONArray orderlist = content.getJSONArray("orderlist");
                                ArrayList<Visitable> list = new ArrayList<>();
                                int len = orderlist.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject jsonObject = orderlist.getJSONObject(i);
                                    FeeShoppingBean bean = new FeeShoppingBean();
                                    bean.setOrder_sn(jsonObject.getString("order_sn"));
                                    bean.setSell_price(jsonObject.getString("sell_price"));
                                    bean.setMoney(jsonObject.getString("order_amount"));
                                    bean.setFree_status(jsonObject.getString("free_status"));
                                    bean.setSell_status(jsonObject.getString("sell_status"));
                                    bean.setCancel_fee(jsonObject.getString("Cancel_fee"));
                                    bean.setDate(TimeFormatUtils.formatD(jsonObject.getInt("pay_time") * 1000L));
                                    bean.setCode(jsonObject.getString("order_sn"));
                                    bean.setProgress(jsonObject.getString("rate"));
                                    bean.setOrder_id(jsonObject.getString("order_id"));
                                    bean.setGear_rate(jsonObject.getString("gear_rate"));
                                    list.add(bean);
                                }
                                callBack.success(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed("没有返回数据");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 合伙人支付信息
     **/
    public static void PartnerPayOrder(String pay_code, String order_id, String type, final BaseCallBack baseCallBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.payOrder)
                .addCode("pay_code", pay_code)
                .addCode("order_id", order_id)
                .addCode("type", type)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject object = new JSONObject(data.toString());
                            String str = object.getString("data");
                            Log.e("str", str);
                            baseCallBack.success(str);
                        } catch (Exception e) {
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
    public static void startUp(int page, String now_version, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.startUp)
                .addCode("page", page)
                .addCode("platform", "android")
                .addCode("now_version", now_version)
                .setBaseCallBack(callBack);
    }

    /**
     * 判断余额支付密码
     */
    public static void checkPayPwd(String paypwd, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.checkPayPwd)
                .addCode("paypwd", paypwd)
                .setBaseCallBack(callBack);
    }

    /**
     * 解绑银行卡
     */
    public static void unbundlingBankcard(String bankcard_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.unbundlingBankcard)
                .addCode("bankcard_id", bankcard_id)
                .setBaseCallBack(callBack);
    }

    /**
     * 扫描条形码
     */
    public static void findshopCode(String barcode, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.findshopCode)
                .addCode("barcode", barcode)
                .setBaseCallBack(callBack);
    }

    /**
     * 银联支付信息
     **/
    public static void UnionpayOrder(String order_id, String type, String curr_id, final BaseCallBack baseCallBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.UnionpayOrder)
                .addCode("order_id", order_id)
                .addCode("type", type);
        if (curr_id!=null) {
            httpBuilder .addCode("curr_id", curr_id);
        }
        httpBuilder
        .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject object = new JSONObject(data.toString());
                            JSONObject str = object.getJSONObject("data");
                            String tn = str.getString("tn");
                            Log.e("str", tn);
                            baseCallBack.success(tn);
                        } catch (Exception e) {
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
     * 回购
     */
    public static void doBuyback(String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.doBuyback)
                .addCode("order_id", order_id)
                .setBaseCallBack(callBack);
    }

    /**
     * vip充值/续费
     */
    public static void vipOrder(String option, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.vipOrder)
                .addCode("option", option)
                .setBaseCallBack(callBack);
    }

    /**
     * 我直邀的人
     */
    public static void myInviteList(int page, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.myInviteList)
                .addCode("p", page)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            InviteInfo info = new InviteInfo();
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("page_data")) {
                                JSONObject jsonObject = content.getJSONObject("page_data");
                                info.setTotalPage(jsonObject.getInt("total_page"));
                                info.setTotalCount(jsonObject.getString("total_record"));
                            }
                            if (content.has("list_data")) {
                                JSONArray array = content.getJSONArray("list_data");
                                ArrayList<Visitable> list = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    MemberListBean bean = new MemberListBean();
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    bean.setHead_pic(jsonObject.getString("head_pic"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setNew_level(jsonObject.getInt("new_level"));
                                    bean.setNickname(jsonObject.getString("nickname"));
                                    bean.setCreatetime(jsonObject.getString("create_time"));
                                    list.add(bean);
                                }
                                info.setList(list);
                            }
                            callBack.success(info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed("数据异常");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 我的团队
     */
    public static void myinviteteam(int page, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.myinviteteam)
                .addCode("p", page)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            InviteInfo info = new InviteInfo();
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("page_data")) {
                                JSONObject jsonObject = content.getJSONObject("page_data");
                                info.setTotalPage(jsonObject.getInt("total_page"));
                                info.setTotalCount(jsonObject.getString("total_record"));
                            }
                            if (content.has("list_data")) {
                                JSONArray array = content.getJSONArray("list_data");
                                ArrayList<Visitable> list = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    MemberListBean bean = new MemberListBean();
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    bean.setHead_pic(jsonObject.getString("head_pic"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setNew_level(jsonObject.getInt("new_level"));
                                    bean.setNickname(jsonObject.getString("nickname"));
                                    bean.setCreatetime(jsonObject.getString("create_time"));
                                    list.add(bean);
                                }
                                info.setList(list);
                            }
                            callBack.success(info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed("数据异常");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 股东申请接口
     */
    public static void applyToShareholder(String company_name, String licence_id, String legal_person_name, String contact_mobile, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.applyToShareholder)
                .addCode("company_name", company_name)
                .addCode("licence_id", licence_id)
                .addCode("legal_person_name", legal_person_name)
                .addCode("contact_mobile", contact_mobile)
                .setBaseCallBack(callBack);
    }

    /**
     * 货到付款
     */
    public static void codOrder(String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.codOrder)
                .addCode("order_id", order_id)
                .setBaseCallBack(callBack);
    }

    /**
     * 获取我的丁宝记录
     */
    public static void getMyDingCoinRecord(int page, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMyDingCoinRecord)
                .addCode("p", page)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            ArrayList<Visitable> list = new ArrayList<>();
                            DingCoinRecordInfo info = new DingCoinRecordInfo();
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("list_data")) {
                                JSONArray list_data = content.getJSONArray("list_data");
                                for (int i = 0; i < list_data.length(); i++) {
                                    JSONObject jsonObject = list_data.getJSONObject(i);
                                    DingCoinRecordBean bean = new DingCoinRecordBean();
                                    bean.setId(jsonObject.getString("id"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setChange_amount(jsonObject.getString("change_amount"));
                                    bean.setCurrent_amount(jsonObject.getString("current_amount"));
                                    bean.setType(jsonObject.getString("type"));
                                    bean.setNote(jsonObject.getString("note"));
                                    bean.setOrder_sn(jsonObject.getString("order_sn"));
                                    bean.setChange_time(jsonObject.getString("change_time"));
                                    list.add(bean);
                                }
                                info.setList(list);
                            }
                            if (content.has("page_data")) {
                                JSONObject jsonObject = content.getJSONObject("page_data");
                                info.setTotalPage(jsonObject.getInt("total_page"));
                            }
                            callBack.success(info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed("没有数据");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 查看使用丁宝对订单的影响
     */
    public static void viewEffectIfDingCoinAdd(String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.viewEffectIfDingCoinAdd)
                .addCode("order_id", order_id)
                .setBaseCallBack(callBack);
    }

    /**
     * 确定使用丁宝加速订单
     */
    public static void useDingCoinToIncreaseGearRate(String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.useDingCoinToIncreaseGearRate)
                .addCode("order_id", order_id)
                .setBaseCallBack(callBack);
    }

    /**创建直播*/
    public static void createLive(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.createLive)
                 .setBaseCallBack(callBack);
    }
    /**
     *  "data": {
     "appName": "userlive15",
     "streamName": "46e7f1cea9"
     }
     */

    /**获取推流地址*/
    public static void getPushUrl(String appName, String streamName, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getPushUrl)
                 .addCode("appName", appName)
                 .addCode("streamName", streamName)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("data")){
                                    String string = content.getJSONObject("data").getString("pushurl");
                                    callBack.success(string);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                callBack.failed("没有数据");
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            callBack.failed(errorCode, data);
                        }
                 });
    }

    /**获取直播在线人数*/
    public static void getLiveOnlineUserNum(String appName, String streamName, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getLiveOnlineUserNum)
                 .addCode("appName", appName)
                 .addCode("streamName", streamName)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("data")){
                                    JSONObject jsonObject = content.getJSONObject("data");
                                    LiveOnlineUserBean bean = new LiveOnlineUserBean();
                                    bean.setTotalUserNumber(jsonObject.getInt("TotalUserNumber"));
                                    bean.setRequestId(jsonObject.getString("RequestId"));
                                    LiveOnlineUserBean.OnlineUserInfoBean innerBean = new LiveOnlineUserBean.OnlineUserInfoBean();
                                    JSONArray jsonArray = jsonObject.getJSONObject("OnlineUserInfo").getJSONArray("LiveStreamOnlineUserNumInfo");
                                    ArrayList<LiveOnlineUserBean.OnlineUserInfoBean.LiveStreamOnlineUserNumInfoBean> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        LiveOnlineUserBean.OnlineUserInfoBean.LiveStreamOnlineUserNumInfoBean infoBean =
                                                new LiveOnlineUserBean.OnlineUserInfoBean.LiveStreamOnlineUserNumInfoBean();
                                        infoBean.setStreamUrl(json.getString("StreamUrl"));
                                        infoBean.setTime(json.getString("Time"));
                                        infoBean.setUserNumber(json.getInt("UserNumber"));
                                        list.add(infoBean);
                                    }
                                    innerBean.setLiveStreamOnlineUserNumInfo(list);
                                    bean.setOnlineUserInfo(innerBean);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            callBack.failed(errorCode, data);
                        }
                 });
    }


    /**获取直播播放地址*/
    public static void getWatchUrl(String appName, String streamName, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getWatchUrl)
                .addCode("appName", appName)
                .addCode("streamName", streamName)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("data")){
                                    String string = content.getJSONObject("data").getString("m3u8url");
                                    callBack.success(string);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                callBack.failed("没有数据");
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            callBack.failed(errorCode, data);
                        }
                 });
    }


    /**获取直播推流在线列表*/
    public static void getLiveOnlineList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPost(HttpApi.getLiveOnlineList)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("data")){
                                    JSONArray array = content.getJSONObject("data").getJSONObject("OnlineInfo").getJSONArray("LiveStreamOnlineInfo");
                                    ArrayList<Visitable> list = new ArrayList<>();
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        PushListBean bean = new PushListBean();
                                        bean.setAppName(jsonObject.getString("AppName"));
                                        bean.setDomainName(jsonObject.getString("DomainName"));
                                        bean.setPublishTime(jsonObject.getString("PublishTime"));
                                        bean.setPublishUrl(jsonObject.getString("PublishUrl"));
                                        bean.setStreamName(jsonObject.getString("StreamName"));
                                        list.add(bean);
                                    }
                                    callBack.success(list);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                callBack.failed("没有数据");
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            callBack.failed(errorCode, data);
                        }
                 });
    }
//    "data": {
//        "RequestId": "CFBA0908-962E-4078-97DA-82A0E35D1B09",
//                "OnlineInfo": {
//            "LiveStreamOnlineInfo": [
//            {
//                "PublishTime": "2017-08-23T07:11:05Z",
//                    "StreamName": "4bc4f2c66e",
//                    "PublishUrl": "rtmp://live.qimiaolife.cn/userlive15/4bc4f2c66e",
//                    "DomainName": "live.qimiaolife.cn",
//                    "AppName": "userlive15"
//            }
//            ]
//        }
//    }

    /**免单交易出售列表*/
    public static void getFreeOrderSellList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getFreeOrderSellList)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("selllist")){
                                    JSONArray array = content.getJSONArray("selllist");
                                    ArrayList<Visitable> list = new ArrayList<>();
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        AssignmentBean bean = new AssignmentBean();
                                        bean.setCurr_id(jsonObject.getString("curr_id"));
                                        bean.setOrder_id(jsonObject.getString("order_id"));
                                        bean.setFree_status(jsonObject.getString("free_status"));
                                        bean.setOrder_sn(jsonObject.getString("order_sn"));
                                        bean.setGoods_price(jsonObject.getString("goods_price"));
                                        bean.setRate(jsonObject.getString("rate"));
                                        bean.setSell_price(jsonObject.getString("sell_price"));
                                        bean.setUser_id(jsonObject.getString("user_id"));
                                        list.add(bean);
                                    }
                                    callBack.success(list);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                callBack.failed("没有数据");
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            callBack.failed(errorCode, data);
                        }
                 });
    }

    /**免单转让*/
    public static void myFreeOrderSell(String order_sn, String sell_price, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.myFreeOrderSell)
                 .addCode("order_sn", order_sn)
                 .addCode("sell_price", sell_price)
                 .setBaseCallBack(callBack);
    }

    /**免单转让*/
    public static void getFree(String order_id, String rate,String gear_rate, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.freeGet)
                .addCode("order_id", order_id)
                .addCode("rate", rate)
                .addCode("gear_rate", gear_rate)
                .setBaseCallBack(callBack);
    }



    /**取消转让*/
    public static void sellCancel(String order_sn, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.sellCancel)
                 .addCode("order_sn", order_sn)
                 .setBaseCallBack(callBack);
    }

}
