package com.yidu.sevensecondmall.networks;


import com.yidu.sevensecondmall.utils.MD5;

import okhttp3.FormBody;

/**
 * Created by Administrator on 2016/7/5.
 */
public class HttpApi {


//    "http://api.global.football.gamenew100.com"
//    主服务器地址
//    private static final String mainURL = "http://www.qimiaolife.cn";
    private static final String mainURL = "http://shop.qimiaolife.cn";
//    private static final String mainURL = "http://192.168.1.218";
    public static final  String ImageUrl="http://192.168.1.172";

    /**图片地址*/
    private static final String imageURL = "http://sevenshop.b0.upaiyun.com/";
    //签名用到的randomKey
    private static final String randomKey = "123456";

    //返回值为{code:0,content:响应数据,message:错误详情}，code为0时表示成功，其它均为失败

    public static String uploadurl;

    public static String policy;

    public static String signature;

    /**
     * 初始化：
    接口：/public/init
    参数：version
    返回：versiondata,uploaddata,coverdata,matchcategorylist*/
    public static final String Init = "/public/init";
    /**公共信息*/
    public static final String startUp = "/Api/Index/startUp";

    /**首页分类图片*/
    public static final String home = "/Api/Index/home";

    //region 配置模块
    /**
     * 首页数据*
     * 参数:unique_id
     * page 页数 必选参数 ;
     * order 选择排序【is_recommend是否推荐、是否热门sales_sum、最新last_update、价格shop_price】非必选参数;
     * priceby 价格排序 【升desc 降asc 】非必选参数
     * */
    public static final String IndexHome = "/Api/Index/home";

    /**首页广告数据**/
    public static final String IndexBanner = "/Api/Index/banner";

    /**
     * 首页商品数据
     * 参数:unique_id
     * page 页数 必选参数 ;
     * order 选择排序【is_recommend是否推荐、是否热门sales_sum、最新last_update、价格shop_price】非必选参数;
     * priceby 价格排序 【升desc 降asc 】非必选参数*/
    public static final String ShopList = "/Api/Index/shoplist";


    //region 用户模块
    /**
     * 登录
     * 参数：unique_id username password
     * **/
    public static final String Login ="/Api/User/login";

    /**
     * 获取验证码
     * 参数：unique_id mobile*/
    public static final String getRegCode = "/Api/User/send_sms_reg_code";

    /**注册
     * 参数：username 手机号或邮箱 必选参数 ; password 密码 必选参数 ; password2 必选参数 ; code 验证码*/
    public  static final String reg = "/Api/User/reg";

    /**忘记密码
     * 参数：mobile 手机号码 必选; new_password 新密码 必选; confirm_password 新密码 必选; code 手机验证码 必选**/
    public static final String forgetpw = "/Api/User/forgetpwd";

    /**获取用户信息**/
    public static final String getUserInfo="/Api/User/userInfo";


    /***第三方登录**/
    public static final String thirdLogin = "/Api/User/thirdLogin";

    /**更新用户信息**/
    public static final String UpdateUserInfo = "/Api/User/updateUserInfo";

    /***用户修改密码***/
    public static final String Password = "/Api/User/password";

    /***账户资金**/
    public static final String Account = "/Api/User/account";

    /***建议投诉
     * text 投诉建议内容 必选参数**/
    public static final String toAdvice = "/Api/User/toadvise";

    /**绑定银行卡**/
    public static final String Bindcard = "/Api/User/bindingbankcard";

    /***获取优惠券列表**/
    public static final String getCouponList ="/Api/User/getCouponList";

    /***退出登录
     * bankcardnumber必选参数**/
    public static final String LoginOut ="/Api/User/logout";

    /***改绑手机***/
    public static final String ChangeMobile = "/Api/User/toChangeMobile";


    /**获取用户消息数量*/
    public static final String getMessageCount = "/Api/User/getMessageCount";
    /**app引导图*/
    public static final String appImgList = "/Api/Index/appImgList";
    /**邀请会员*/
    public static final String inviteUser = "/Api/Inviteuser/inviteUser";
    /**我的邀请列表*/
    public static final String myInviteUsers = "/Api/Freeorder/myInviteUsers";
    //endregion

    //region 商品模块
    /**获取商品详情*/
    public static final String GoodInfo = "/api/goods/goodsInfo";

    /***获取商品分类列表
     * parent_id 分类父id 非比选**/
    public static final String goodsCategoryList = "/api/goods/goodsCategoryList";

    /***获取根据以及分类获取二三级分类
     * parent_id 父类id 必选参数**/
    public static final String goodsSecAndThirdCategoryList = "/api/goods/goodsSecAndThirdCategoryList";

    /****获取商品列表页
     * id 当前分类id 非必选参数; brand_id 品牌ID 非必选参数; spec 规格 非必选参数; attr 属性 非必选参数;
     * sort 排序 非必选参数; sort_asc 排序 非必选参数;
     * price 价钱 非必选参数; start_price 输入框价钱 非必选参数; end_price 输入框价钱 非必选参数;***/
    public static final String goodslist = "}/api/goods/goodsList";

    /***搜索商品
     * id 当前分类id 非必选参数; brand_id 品牌ID 非必选参数; spec 规格 非必选参数; attr 属性 非必选参数; sort 排序 非必选参数; sort_asc 排序 非必选参数;
     * price 价钱 非必选参数; start_price 输入框价钱 非必选参数; end_price 输入框价钱 非必选参数; q 搜索关键字 必选参数**/
    public static final String searchGoods = "/api/goods/search";

    /**获取商品评价
     * goods_id 商品ID 必选参数 ; page 页数 非比选*/
    public static final String getComment = "/api/goods/getGoodsComment";

    /***添加商品评论
     * goods_id 商品id 必选参数;
     order_id 订单ID 必选参数; service_rank 商家服务态度 1-5 非必选参数;
     deliver_rank 物流评价等级 1-5 非必选参数; goods_rank 商品评价等级 1-5 非必选参数;
     content 评论内容 非必选参数; imgurl 上传图片地址 多个','分割 非必选参数;**/
    public static final String addComment = "/Api/User/add_comment";

    /**加入购物车
     * token 非必选参数; goods_id int 商品id 必选参数; goods_num int 商品数量 必选参数; goods_spec int 商品规格 必选参数; unique_id int 客户端 session_id 必选参数**/
    public static final String AddCart = "/api/cart/addCart";
    /**购物车第二步确定页面*/ //token 必选参数 ; unique_id 客户端的session_id 必选参数 ;
    public static final String cart2 = "/api/cart/cart2";
    /**获取订单商品价格 或者提交 订单*///token 必选参数 ; unique_id 客户端的session_id 必选参数 ; address_id int 收货地址id 必选参数 ; shipping_code 物流编号 必选参数 ; invoice_title 发票 非必选参数 ; couponTypeSelect 优惠券类型 1 下拉框选择优惠券 2 输入框输入优惠券代码 非必选参数 ; coupon_id 优惠券id 必选参数 ; couponCode 优惠券代码 必选参数 ; pay_points 使用积分 非必选参数 ; user_money 使用余额 非必选参数 ; act submit_order 固定参数
    public static final String cart3 = "/api/cart/cart3";

    /**搜索人们关键字
     *
     ***/
    public static final String Hotkeywords = "/api/goods/hotkeywords";

    /***获取购物车列表**/
    public static final String getCartList = "/api/cart/cartList";
    /**删除购物车的商品*/
    public static final String delCart = "/api/cart/delCart";


    /***收藏商品
     * goods_id 商品ID 必选参数 ; type 收藏0/取消收藏1 必选参数 ;***/
    public static final String CollectGoods="/api/goods/collectGoods";

    /***获取收藏商品
     * **/
    public static final String getGoodsCollect = "/Api/User/getGoodsCollect";

    //endregion

    //region 文章模块
    /***文章详情
     * article_id int 文章id 必选参数**/
    public static final String ArticleDetail = "/Api/Article/detail";

    /***文章内详情**/
    public static final String ArticleList = "/Api/Article/articleList";


    //endregion


    //region 订单模块
    /**获取收货地址*/
    public static final String getAddressList = "/Api/User/getaddresslist";
    /**添加地址*/
    public static final String addAddress = "/Api/User/addAddress";
    /**地址删除*/
    public static final String delAddress = "/Api/User/del_address";
    /**用户订单列表*/
    public static final String getOrderList = "/Api/User/getOrderList";
    /**获取订单详情*/
    public static final String getOrderDetail = "/Api/User/getOrderDetail";
    /**收货确认*/
    public static final String orderConfirm = "/Api/User/orderConfirm";
    /**取消订单*/
    public static final String cancelOrder = "/Api/User/cancelOrder";
    /**设置默认收货地址*/
    public static final String setDefaultAddress = "/Api/User/setDefaultAddress";
    /**退换货列表*/
    public static final String returnGoodsList = "/Api/User/return_goods_list";
    /**申请退货*/
    public static final String returnGoods = "/Api/User/return_goods";
    /**申请退货状态*/
    public static final String returnGoodsStatus = "/Api/User/return_goods_status";
    /**退货详情*/
    public static final String returnGoodsInfo = "/Api/User/return_goods_info";
    /**立即购买**/
    public static final String buynow = "/Api/Cart/buynow";
    /***计算运费接口***/
    public static final String calculate = "/api/cart/getShippingPrice";
    /**立即支付测试***/
    public static final String paytest = "/Api/Payment/payOrder";
    /**查看物流接口***/
    public static final String getShipStatus = "/Api/User/getShippingSchedule";

    //endregion


    //Distribution
    /**返利记录*/
    public static final String getRebateList = "/Api/Inviteuser/rebateList";
    /**用户返利详情*/
    public static final String getRebateInfo = "/Api/Rebate/rebateInfo";
    /**获取我推荐的人*/
    public static final String getMyRecommend = "/Api/Inviteuser/myInviteUsers";
    /**会员升级*/
    public static final String updateUserLevel = "/Api/Rebate/updateUserLevel";
    /**获取用户权益*/
    public static final String getMyJurisdiction = "/Api/Rebate/getMyJurisdiction";
    /**生成邀请二维码*/
    public static final String createInviteCode = "/Api/Rebate/createinvitecode";
    /**生成接受推广订单信息*/
    public static final String createInviteOrder = "/Api/Rebate/createInviteOrder";
    /**团队信息*/
    public static final String getTeamInfo = "/Api/Rebate/getTeamInfo";
    /**我推广的团队*/
    public static final String getMyInviteTeam = "/Api/Rebate/getMyInviteTeam";
    /**获取团队成员列表*/
    public static final String getMyTeamMember = "/Api/Rebate/getMyTeamMember";
    /**成为合伙儿或会员接口*/
    public static final String submitDistributionOrder = "/Api/Rebate/submitDistributionOrder";
    /**升级成为付费会员支付*/
    public static final String payUserOrder = "/Api/Rebate/payUserOrder";
    /**合伙人专区*/
    public static final String partnerinfo = "/Api/Inviteuser/partnerinfo";
    //endregion

    //payment
    /**余额明细*/
    public static final String account = "/Api/payment/account";
    /**获取银行卡类型*/
    public static final String getBankcardType = "/Api/payment/getBankcardTpye";
    /**绑定银行卡*/
    public static final String bindingBankCard = "/Api/payment/bindingBankCard";
    /***申请退款**/
    public static final String applyRefund = "/Api/Payment/applyRefund";
    /**银行卡列表*/
    public static final String bankcardList = "/Api/payment/bankcardList";
    /**余额充值*/
    public static final String moneyRecharge = "/Api/payment/moneyRecharge";
    /**余额提现*/
    public static final String moneyWithdrawals = "/Api/payment/moneyWithdrawals";
    /**设置余额支付密码(忘记密码)*/
    public static final String setUserMoneyPwd = "/Api/payment/setUserMoneyPwd";
    /**重设余额支付密码*/
    public static final String resetPayPwd = "/Api/payment/resetPayPwd";
    /***支付宝信息**/
    public static final String payOrder = "/Api/Alipay/payOrder";
    /***微信信息**/
    public static final String payOrderWeChat = "/Api/Wxpay/payOrder";
    /***余额支付**/
    public static final String balancePay = "/Api/Payment/balancePay";
    /**申请成为合伙人-新*/
    public static final String applyToPartner = "/Api/Inviteuser/applyToPartner";
    /**申请成为商家*/
    public static final String applyToBusiness = "/Api/Inviteuser/applyToBusiness";
    /**申请实名认证*/
    public static final String realnameAuthentication = "/Api/Inviteuser/realnameAuthentication";
    /**我的免单列表*/
    public static final String myFreeOrderList = "/Api/Inviteuser/myFreeOrderList";
    //endregion

    /**判断余额支付密码*/
    public static final String checkPayPwd = "/Api/payment/checkPayPwd";
    /**解绑银行卡*/
    public static final String unbundlingBankcard = "/Api/payment/unbundlingBankcard";
    /**扫描条形码*/
    public static final String findshopCode = "/api/Inviteuser/findshopCode";
    /**银联手机支付*/
    public static final String UnionpayOrder = "/Api/Unionpay/payOrder";
    /**回购*/
    public static final String doBuyback = "/api/User/doBuyback";
    /**vip充值/续费*/
    public static final String vipOrder = "/api/user/vipCardBuy";
    /**我直邀的人*/
    public static final String myInviteList = "/Api/User/myInviteList";
    /**我的团队*/
    public static final String myinviteteam = "/Api/User/myinviteteam";
    /**股东申请接口*/
    public static final String applyToShareholder = "/Api/User/applyToShareholder";
    /**货到付款*/
    public static final String codOrder = "/Api/cart/codOrder";
    /**获取我的丁宝记录*/
    public static final String getMyDingCoinRecord = "/Api/User/getMyDingCoinRecord";
    /**查看使用丁宝对订单的影响*/
    public static final String viewEffectIfDingCoinAdd = "/Api/User/viewEffectIfDingCoinAdd";
    /**确定使用丁宝加速订单*/
    public static final String useDingCoinToIncreaseGearRate = "/Api/User/useDingCoinToIncreaseGearRate";
    /**创建直播*/
    public static final String createLive = "/api/alilive/createLive";
    /**获取推流地址*/
    public static final String getPushUrl = "/api/alilive/getPushUrl";
    /**获取直播在线人数*/
    public static final String getLiveOnlineUserNum = "/api/alilive/getLiveOnlineUserNum";
    /**获取直播播放地址*/
    public static final String getWatchUrl = "/api/alilive/getWatchUrl";
    /**获取用户创建的直播信息*/
    public static final String getUserLiveList = "/api/alilive/getUserLiveList";
    /**获取直播推流在线列表*/
    public static final String getLiveOnlineList = "/api/alilive/getLiveOnlineList";

    /**免单交易出售列表*/
    public static final String getFreeOrderSellList = "/Api/Inviteuser/getFreeOrderSellList";
    /**免单转让*/
    public static final String myFreeOrderSell = "/Api/Inviteuser/myFreeOrderSell";
    /**取消转让*/
    public static final String sellCancel = "/Api/Inviteuser/sellCancel";

    public static final String freeGet ="/Api/Inviteuser/updateFreeOrder";


    //获取post请求URL
    public static String getRouterURL(String router) {
        return mainURL + router;
    }


    //获取post form
    public static FormBody getFormBody(String requestStr) {
        String signData = HttpApi.getSignString(requestStr);
//        Log.e("FormBody", requestStr);
//        Log.e("FormBody", signData);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("code", requestStr)
                .add("sign", signData);
        return builder.build();
    }

    //签名
    public static String getSignString(String sign) {
        String signData = MD5.getMD5(sign) + randomKey;
//        signData = signData.toUpperCase();
        signData = MD5.getMD5(signData);

        return signData;
    }

    //获取图片完整url
    public static String getFullImageUrl(String imageUrl) {
        if (imageUrl == null)
            return "";
        if (imageUrl.contains("http://") || imageUrl.contains("https://")) {
            return imageUrl;
        } else {
            return imageURL + "/" + imageUrl;
        }
    }

    public static String getFullImageUrl(String imageURL, boolean small) {
        if (imageURL == null) return "";
        String fullImageUrl = getFullImageUrl(imageURL);
        if (small) {
            fullImageUrl = fullImageUrl + "!/fwfh/500x500";
        }
        return fullImageUrl;
    }



    public static final String  getMainURL= mainURL+"/api/shouye/returnData";
    public static final String getGameURL = mainURL+"/Daxiong/V1/getGameInfo";
    public static final String getUserMoney = mainURL+"/Daxiong/V1/getUserMoney";
    public static final String loginGame = mainURL+"/Daxiong/V1/loginGame";
    public static final String buyGameCoin = mainURL+"/Daxiong/V1/buyGameCoin";
    public static final String getUserGameRecord = mainURL+"/Daxiong/V1/getUserGameRecord";
    public static final String getUserFreeOrder = mainURL+"/Api/User/getUserFreeOrder";
    public static final String addOrderToQuick = mainURL+"/Api/User/addOrderToQuick";
    public static final String is_get = mainURL+"/Api/User/getUserFreeOrder/is_get";
    public static final String quickOrderList = mainURL+"/Api/User/quickOrderList";
    public static final String getQuickOrderDetail = mainURL+"/Api/User/getQuickOrderDetail";
    public static final String getQuickOrderUserList = mainURL+"/Api/User/getQuickOrderUserList";
    public static final String DingCoinToIncreaseOrderRate = mainURL+"/Api/User/DingCoinToIncreaseOrderRate";

}
