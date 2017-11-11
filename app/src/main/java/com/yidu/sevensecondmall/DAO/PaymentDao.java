package com.yidu.sevensecondmall.DAO;

import com.yidu.sevensecondmall.bean.OrderMessage.CouponBean;
import com.yidu.sevensecondmall.bean.Payment.CountBean;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.networks.HttpBuilder;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
public class PaymentDao {

    /**
     * 余额明细
     */
    public static void getAcountList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.account)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("account_log")) {
                                JSONArray account_log = content.getJSONArray("account_log");
                                ArrayList<CountBean> list = new ArrayList<>();
                                for (int i = 0; i < account_log.length(); i++) {
                                    JSONObject jsonObject = account_log.getJSONObject(i);
                                    CountBean bean = new CountBean();

                                    bean.setLog_id(jsonObject.getString("log_id"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setUser_money(jsonObject.getString("user_money"));
                                    bean.setFrozen_money(jsonObject.getString("frozen_money"));
                                    bean.setPay_points(jsonObject.getString("pay_points"));
                                    bean.setChange_time(jsonObject.getInt("change_time"));
                                    bean.setDesc(jsonObject.getString("desc"));
                                    bean.setOrder_sn(jsonObject.getString("order_sn"));
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
     * 获取银行卡类型
     */
    public static void getBankcardType(String bankcardnumber, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getBankcardType)
                .addCode("bankcardnumber", bankcardnumber)
                .setBaseCallBack(callBack);
    }

    /**
     * 绑定银行卡
     */
    public static void bindingBankCard(String bankcardnumber,  String banktype,
                                       String bankname,
                                       final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.bindingBankCard)
                .addCode("bankcardnumber", bankcardnumber)
                .addCode("banktype", banktype)
                .addCode("bankname", bankname)
                .setBaseCallBack(callBack);
    }


    /**
     * 银行卡列表
     */
    public static void getBankcardList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.bankcardList)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("cardlist")) {
                                JSONArray cardlist = content.getJSONArray("cardlist");
                                ArrayList<BankCardBean> list = new ArrayList<>();
                                for (int i = 0; i < cardlist.length(); i++) {
                                    JSONObject jsonObject = cardlist.getJSONObject(i);
                                    BankCardBean bean = new BankCardBean();
                                    bean.setId(jsonObject.getString("id"));
                                    bean.setCardnumber(jsonObject.getString("cardnumber"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setCardtype(jsonObject.getString("cardtype"));
                                    bean.setBankname(jsonObject.getString("bankname"));
                                    bean.setUsername(jsonObject.getString("username"));
                                    bean.setIdtype(jsonObject.getString("idtype"));
                                    bean.setIdnumber(jsonObject.getString("idnumber"));
                                    bean.setMobile(jsonObject.getString("mobile"));
                                    bean.setCardpasswd(jsonObject.getString("cardpasswd"));
                                    bean.setCreatetime(jsonObject.getString("createtime"));
                                    bean.setNickname(jsonObject.getString("nickname"));
                                    bean.setImgUrl(jsonObject.getString("imgurl"));
                                    list.add(bean);
                                }
                                callBack.success(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed("返回数据异常");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        callBack.failed(errorCode, data);
                    }
                });
    }

    /**
     * 余额充值
     */
    public static void moneyRecharge(String card_id, String amount, String password, final BaseCallBack callBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.moneyRecharge);
        if (card_id != null && !card_id.equals("")) {
            httpBuilder.addCode("card_id", card_id);
        }
        httpBuilder.addCode("amount", amount)
                .addCode("password", password)
                .setBaseCallBack(callBack);
    }

    /**
     * 余额提现
     */
    public static void moneyWithdrawals(String card_id, String amount, String password, final BaseCallBack callBack) {
        HttpBuilder httpBuilder = OkHttpClientManager.doOkHttpPostWithToken(HttpApi.moneyWithdrawals);
        if (card_id != null && !card_id.equals("")) {
            httpBuilder.addCode("card_id", card_id);
        }
        httpBuilder.addCode("amount", amount)
                .addCode("password", password)
                .setBaseCallBack(callBack);
    }

    /**
     * 设置余额支付密码(忘记密码)
     */
    public static void setUserMoneyPwd(String mobile, String code, String password, String confirm_password, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.setUserMoneyPwd)
                .addCode("mobile", mobile)
                .addCode("code", code)
                .addCode("password", password)
                .addCode("confirm_password", confirm_password)
                .setBaseCallBack(callBack);
    }

    /**
     * 重设余额支付密码
     */
    public static void resetPayPwd(String password, String confirm_password, String old_password, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.resetPayPwd)
                .addCode("password", password)
                .addCode("confirm_password", confirm_password)
                .addCode("old_password", old_password)
                .setBaseCallBack(callBack);
    }
}
