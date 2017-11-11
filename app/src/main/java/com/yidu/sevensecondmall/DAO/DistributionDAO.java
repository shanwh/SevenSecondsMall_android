package com.yidu.sevensecondmall.DAO;

import com.yidu.sevensecondmall.bean.Distribution.GiftGoodsArrBean;
import com.yidu.sevensecondmall.bean.Distribution.InviteOrderBean;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.bean.Distribution.MyJurisdictionBean;
import com.yidu.sevensecondmall.bean.Distribution.ExtensionTeamBean;
import com.yidu.sevensecondmall.bean.Distribution.MyTeamMemberBean;
import com.yidu.sevensecondmall.bean.Distribution.RebateListBean;
import com.yidu.sevensecondmall.bean.Distribution.TeamInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class DistributionDAO {

    /**返利记录*/
    public static void getRebateList(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getRebateList)
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        ArrayList<RebateListBean> list = new ArrayList<>();
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("loglist")){
                                JSONArray rebateList = content.getJSONArray("loglist");
                                for (int i = 0; i < rebateList.length(); i++) {
                                    JSONObject jsonObject = rebateList.getJSONObject(i);
                                    RebateListBean bean = new RebateListBean();
                                    bean.setLog_id(jsonObject.getString("log_id"));
                                    bean.setUser_id(jsonObject.getString("user_id"));
                                    bean.setUser_money(jsonObject.getString("user_money"));
                                    bean.setFrozen_money(jsonObject.getString("frozen_money"));
                                    bean.setPay_points(jsonObject.getString("pay_points"));
                                    bean.setChange_time(jsonObject.getString("change_time"));
                                    bean.setDesc(jsonObject.getString("desc"));
                                    bean.setType(jsonObject.getString("type"));
                                    bean.setOrder_sn(jsonObject.getString("order_sn"));
                                    bean.setOrder_id(jsonObject.getString("order_id"));
                                    list.add(bean);
                                }
                            }
                        }catch (Exception e){
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

    /**用户返利详情*/
    public static void getRebateInfo(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getRebateInfo)
                 .setBaseCallBack(callBack);
    }

    /**获取我推荐的人*/
    public static void getMyRecommend(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMyRecommend)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            ArrayList<MemberListBean> list = new ArrayList<>();
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("userlist")){
                                    JSONArray userlist = content.getJSONArray("userlist");
                                    for (int i = 0; i < userlist.length(); i++) {
                                        JSONObject jsonObject = userlist.getJSONObject(i);
                                        MemberListBean bean = new MemberListBean();
                                        bean.setId(jsonObject.getString("id"));
                                        bean.setUser_id(jsonObject.getString("user_id"));
                                        bean.setNickname(jsonObject.getString("nickname"));
                                        bean.setHead_pic(jsonObject.getString("head_pic"));

                                        bean.setInvite_user_id(jsonObject.getString("invite_user_id"));
                                        bean.setPartner_id(jsonObject.getString("partner_id"));
                                        bean.setBranch_id(jsonObject.getString("branch_id"));
                                        bean.setCreatetime(jsonObject.getString("createtime"));

                                        list.add(bean);
                                    }
                                }
                            }catch (Exception e){
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

    /**会员升级*/
    public static void updateUserLevel(String type, int goods_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.updateUserLevel)
                 .addCode("type", type)
                 .addCode("goods_id", goods_id)
                 .setBaseCallBack(callBack);
    }


    /**获取用户权益*/
    public static void getMyJurisdiction(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMyJurisdiction)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            ArrayList<MyJurisdictionBean> list = new ArrayList<>();
                            try {
                                JSONArray content = new JSONArray(data.toString());
                                for (int i = 0; i < content.length(); i++) {
                                    JSONObject jsonObject = content.getJSONObject(i);
                                    MyJurisdictionBean bean = new MyJurisdictionBean();
                                    bean.setKey(jsonObject.getString("key"));
                                    bean.setValue(jsonObject.getString("value"));
                                    bean.setName(jsonObject.getString("name"));
                                    bean.setCompany(jsonObject.getString("company"));
                                    list.add(bean);
                                }
                            }catch (Exception e){
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


    /**生成邀请二维码*/
    public static void createInviteCode(String type, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.createInviteCode)
                 .addCode("type", type)
                 .setBaseCallBack(callBack);
    }


    /**生成接受推广订单信息*/
    public static void createInviteOrder(String type, String invite_user_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.createInviteOrder)
                 .addCode("type", type)
                 .addCode("invite_user_id", invite_user_id)
                 .setBaseCallBack(new BaseCallBack() {
                     @Override
                     public void success(Object data) {
                         InviteOrderBean bean = new InviteOrderBean();
                         try {
                             JSONObject content = new JSONObject(data.toString());
                             if (content.has("data")){
                                 JSONObject jsonObject = content.getJSONObject("data");
                                 if (jsonObject.has("userinfo")){
                                     JSONObject userinfo = jsonObject.getJSONObject("userinfo");
                                    InviteOrderBean.UserinfoBean userBean = new InviteOrderBean.UserinfoBean();
                                     userBean.setUser_id(userinfo.getString("user_id"));
                                     userBean.setNickname(userinfo.getString("nickname"));
                                     bean.setUserinfo(userBean);
                                 }
                                 if (jsonObject.has("gift_goods_arr")){
                                     ArrayList<Visitable> gifList = new ArrayList<>();
                                     JSONArray arr = jsonObject.getJSONArray("gift_goods_arr");
                                     for (int i = 0; i < arr.length(); i++) {
                                         JSONObject object = arr.getJSONObject(i);
                                         GiftGoodsArrBean giftGoodsArrBean = new GiftGoodsArrBean();
                                         giftGoodsArrBean.setGoods_id(object.getString("goods_id"));
                                         giftGoodsArrBean.setGoods_name(object.getString("goods_name"));
                                         giftGoodsArrBean.setOriginal_img(object.getString("original_img"));
                                         gifList.add(giftGoodsArrBean);
                                     }
                                     bean.setGift_goods_arr(gifList);
                                 }
                                 if (jsonObject.has("levelinfo")){
                                     JSONObject levelinfo = jsonObject.getJSONObject("levelinfo");
                                     InviteOrderBean.LevelinfoBean levelBean = new InviteOrderBean.LevelinfoBean();
                                     levelBean.setLevel_id(levelinfo.getString("level_id"));
                                     levelBean.setLevel_name(levelinfo.getString("level_name"));
                                     levelBean.setAmount(levelinfo.getString("amount"));
                                     levelBean.setDiscount(levelinfo.getString("discount"));
                                     levelBean.setDescribe(levelinfo.getString("describe"));
                                     levelBean.setIntegral(levelinfo.getString("integral"));
                                     levelBean.setConfigname(levelinfo.getString("configname"));
                                     levelBean.setGive(levelinfo.getString("give"));
                                     levelBean.setGiveout(levelinfo.getString("giveout"));
                                     levelBean.setIs_set(levelinfo.getString("is_set"));
                                     bean.setLevelinfo(levelBean);
                                 }
                                 if (jsonObject.has("groupinfo")){
                                     JSONObject groupinfo = jsonObject.getJSONObject("groupinfo");
                                     InviteOrderBean.GroupinfoBean groupBean = new InviteOrderBean.GroupinfoBean();
                                     groupBean.setCity_id(groupinfo.getString("city_id"));
                                     groupBean.setName(groupinfo.getString("name"));
                                     groupBean.setLogo(groupinfo.getString("logo"));
                                     groupBean.setId(groupinfo.getString("id"));
                                     bean.setGroupinfo(groupBean);
                                 }
                             }
                         }catch (Exception e){
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

    /**团队信息*/
    public static void getTeamInfo(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getTeamInfo)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            TeamInfo info = new TeamInfo();
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("teaminfo")){
                                    JSONObject teaminfo = content.getJSONObject("teaminfo");

                                    info.setId(teaminfo.getString("id"));
                                    info.setUser_id(teaminfo.getString("user_id"));
                                    info.setName(teaminfo.getString("name"));
                                    info.setLogo(teaminfo.getString("logo"));
                                    info.setTeam_id(teaminfo.getString("team_id"));
                                    info.setCreatetime(teaminfo.getString("createtime"));
                                    info.setMembers(teaminfo.getString("members"));
                                    info.setExtension_team_nums(teaminfo.getString("extension_team_nums"));
                                    info.setSales_volume(teaminfo.getString("sales_volume"));
                                    info.setSales_rebate(teaminfo.getString("sales_rebate"));
                                    JSONObject userinfo = teaminfo.getJSONObject("userinfo");
                                    TeamInfo.UserinfoBean userBean = new TeamInfo.UserinfoBean();
                                    userBean.setHead_pic(userinfo.getString("head_pic"));
                                    userBean.setNickname(userinfo.getString("nickname"));
                                    userBean.setUser_id(userinfo.getString("user_id"));
                                    info.setUserinfo(userBean);
                                    JSONObject cityinfo = teaminfo.getJSONObject("cityinfo");
                                    TeamInfo.CityinfoBean cityBean = new TeamInfo.CityinfoBean();
                                    cityBean.setMergename(cityinfo.getString("mergename"));
                                    info.setCityinfo(cityBean);
                                }
                            }catch (Exception e){
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

    /**我推广的团队*/
    public static void getMyInviteTeam(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMyInviteTeam)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            ArrayList<ExtensionTeamBean> list = new ArrayList<>();
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("team")){
                                    JSONArray array = content.getJSONArray("team");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject jsonObject = array.getJSONObject(i);
                                        ExtensionTeamBean bean = new ExtensionTeamBean();
                                        bean.setId(jsonObject.getString("id"));
                                        bean.setName(jsonObject.getString("name"));
                                        bean.setLogo(jsonObject.getString("logo"));
                                        bean.setCreatetime(jsonObject.getString("createtime"));
                                        bean.setMembers(jsonObject.getString("members"));
                                        bean.setLinkman(jsonObject.getString("linkman"));
                                        bean.setInvite_rebate(jsonObject.getString("invite_rebate"));
                                        bean.setConsumption_rebate(jsonObject.getString("consumption_rebate"));
                                        list.add(bean);
                                    }
                                }
                            }catch (Exception e){
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

    /**获取团队成员列表*/
    public static void getMyTeamMember(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.getMyTeamMember)
                 .setBaseCallBack(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            ArrayList<MyTeamMemberBean> list = new ArrayList<>();
                            try {
                                JSONObject content = new JSONObject(data.toString());
                                if (content.has("members")){
                                    JSONArray members = content.getJSONArray("members");
                                    for (int i = 0; i < members.length(); i++) {
                                        MyTeamMemberBean bean = new MyTeamMemberBean();
                                        JSONObject jsonObject = members.getJSONObject(i);

                                        bean.setUser_id(jsonObject.getString("user_id"));
                                        bean.setNickname(jsonObject.getString("nickname"));
                                        bean.setHead_pic(jsonObject.getString("head_pic"));
                                        bean.setLevel(jsonObject.getString("level"));
                                        bean.setMemberjointime(jsonObject.getString("memberjointime"));
                                        bean.setInvite_rebate(jsonObject.getString("invite_rebate"));
                                        bean.setConsumption_rebate(jsonObject.getString("consumption_rebate"));
                                        list.add(bean);
                                    }
                                }
                            }catch (Exception e){
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

    /**成为会员接口*/
    public static void submitMember(String user_id, String invitor, String gift_id, String address_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.submitDistributionOrder)
                 .addCode("user_id", user_id)
                 .addCode("order_type", "member")
                 .addCode("invitor", invitor)
                 .addCode("gift_id", gift_id)
                 .addCode("address_id", address_id)
                 .setBaseCallBack(callBack);
    }

    /**成为合伙人接口*/
    public static void submitPartner(String user_id, String invitor, String area_id, String group_name, String link_man, String link_mobile, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.submitDistributionOrder)
                .addCode("user_id", user_id)
                .addCode("order_type", "partner")
                .addCode("invitor", invitor)//可传可不传
                .addCode("area_id", area_id)
                .addCode("group_name", group_name)
                .addCode("link_man", link_man)
                .addCode("link_mobile", link_mobile)
                .setBaseCallBack(callBack);
    }

    /**升级成为付费会员支付*///余额支付
    public static void payUserOrderByUserMoney(String paypassword, String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.payUserOrder)
                 .addCode("paypassword", paypassword)
                .addCode("order_id", order_id)
                .addCode("shipping_code", "shunfeng")
                .setBaseCallBack(callBack);
    }

    /**升级成为付费会员支付*///银行卡支付
    public static void payUserOrderByBankCard(String card_id, String order_id, final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.payUserOrder)
                .addCode("card_id", card_id)
                .addCode("order_id", order_id)
                .addCode("shipping_code", "shunfeng")
                .setBaseCallBack(callBack);
    }

    /**合伙人专区*/
    public static void partnerinfo(final BaseCallBack callBack) {
        OkHttpClientManager.doOkHttpPostWithToken(HttpApi.partnerinfo)
                 .setBaseCallBack(callBack);
    }
}
