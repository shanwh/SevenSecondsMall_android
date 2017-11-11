package com.yidu.sevensecondmall.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import com.example.zhouwei.library.CustomPopWindow;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.yidu.sevensecondmall.Activity.UserCenter.SetPayPasswordActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.i.PaySuccess;

import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public class PayPopWindow {
    private static final int PAY_TYPE_USER_MONEY = 0;
    private static final int PAY_TYPE_ALI_PAY = 1;
    private static final int PAY_TYPE_WE_CHAT = 2;
    private static final int PAY_TYPE_BANK_CARD = 3;
    private static final int PAY_TYPE_COD = 4;

    private static final int SDK_PAY_FLAG = 1;
    private int payType = PAY_TYPE_USER_MONEY;
    private CustomPopWindow popupWindow;
    private String orderid;
    private Activity context;
    private View post;
    private String money;
    private String orderType;
    private PaySuccess paySuccess;
    private boolean isCod;
    private boolean def;
    private String curr_id;
    private boolean isGoodsOrder;
    private int[] idList = new int[]{R.id.rl_user_money, R.id.rl_ali_pay, R.id.rl_we_chat, R.id.rl_bank, R.id.rl_cod};
    private int[] numIdList = new int[]{
            R.id.t0, R.id.t1, R.id.t2, R.id.t3, R.id.t4,
            R.id.t5, R.id.t6, R.id.t7, R.id.t8, R.id.t9, R.id.tX
    };
    private int[] payList = new int[]{PAY_TYPE_USER_MONEY, PAY_TYPE_ALI_PAY, PAY_TYPE_WE_CHAT, PAY_TYPE_BANK_CARD, PAY_TYPE_COD};
    private ArrayList<IconFontTextView> itList = new ArrayList<>();

    private String type;
    public PayPopWindow(Activity context, View post, String money, String orderid, String orderType, PaySuccess paySuccess) {
        this.orderid = orderid;
        this.context = context;
        this.post = post;
        this.money = money;
        this.orderType = orderType;
        this.paySuccess = paySuccess;
    }

    public PayPopWindow(Activity context, View post, String money, String orderid, String orderType, PaySuccess paySuccess, boolean isCod) {
        this.orderid = orderid;
        this.context = context;
        this.post = post;
        this.money = money;
        this.orderType = orderType;
        this.paySuccess = paySuccess;
        this.isCod = isCod;
    }

    public PayPopWindow(Activity context, View post, String money, String orderid, String orderType, PaySuccess paySuccess, String curr_id) {
        this.orderid = orderid;
        this.context = context;
        this.post = post;
        this.money = money;
        this.orderType = orderType;
        this.paySuccess = paySuccess;

        this.curr_id = curr_id;
    }

    public PayPopWindow(Activity context, View post, String money, boolean isGoodsOrder,String type, PaySuccess paySuccess) {
        this.context = context;
        this.post = post;
        this.money = money;
        this.isGoodsOrder = isGoodsOrder;
        this.paySuccess = paySuccess;
        this.type=type;
    }


    public CustomPopWindow getPopupWindow() {
        return popupWindow;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    public void showPayPopWindow() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_window_pay_type3, null);
        //处理popWindow 显示内容
        if (def) {
            contentView.findViewById(R.id.if_tv_back).setVisibility(View.GONE);
        }
        handleLogic(contentView, money);

        popupWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(!def)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(!def) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(post, Gravity.BOTTOM, 0, 0);//显示PopupWindow
    }


    private void showShortToast(String msg) {
        ToastUtil.showToast(context, msg);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(final View contentView, String money) {

        TextView tv_money = (TextView) contentView.findViewById(R.id.tv_money);
        tv_money.setText(money);

        contentView.findViewById(R.id.if_tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dissmiss();
            }
        });
        IconFontTextView it1 = (IconFontTextView) contentView.findViewById(R.id.it1);
        IconFontTextView it2 = (IconFontTextView) contentView.findViewById(R.id.it2);
        IconFontTextView it3 = (IconFontTextView) contentView.findViewById(R.id.it3);
        IconFontTextView it4 = (IconFontTextView) contentView.findViewById(R.id.it4);
        IconFontTextView it5 = (IconFontTextView) contentView.findViewById(R.id.it5);

        itList.add(it1);
        itList.add(it2);
        itList.add(it3);
        itList.add(it4);
        itList.add(it5);

        contentView.findViewById(R.id.rl_user_money).setOnClickListener(new payTypeClick());
        contentView.findViewById(R.id.rl_ali_pay).setOnClickListener(new payTypeClick());
        contentView.findViewById(R.id.rl_we_chat).setOnClickListener(new payTypeClick());
        contentView.findViewById(R.id.rl_bank).setOnClickListener(new payTypeClick());
        contentView.findViewById(R.id.rl_cod).setOnClickListener(new payTypeClick());

        if (isCod) {
            contentView.findViewById(R.id.rl_cod).setVisibility(View.VISIBLE);
        } else {
            contentView.findViewById(R.id.rl_cod).setVisibility(View.GONE);
        }

        contentView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               if (tvPayType.getText().length() != 0) {
                popupWindow.dissmiss();
                //TODO 从后台获得支付信息

                switch (payType) {
                    case PAY_TYPE_USER_MONEY://弹出输入密码弹窗，调用余额支付
                        UserDAO.getUserInfo(new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                if (data != null) {
                                    UserBean bean = (UserBean) data;
                                    String user_money_pwd = bean.getUser_money_pwd();
                                    if (user_money_pwd != null && !user_money_pwd.equals("")) {
                                        //有支付密码
                                        showPopupWindowPassWord();
                                    } else {
                                        showShortToast("请先设置支付密码");
                                    }
                                }
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                        break;
                    case PAY_TYPE_BANK_CARD://调用银联支付的接口
                        UserDAO.UnionpayOrder(orderid, orderType, curr_id, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                String tn = (String) data;

                                if (UPPayAssistEx.checkInstalled(context.getApplicationContext())) {
//                                Log.e(TestCart.LOG,"流水号："+tn);
                                    String serverMode = "00";
                                    UPPayAssistEx.startPay(context, null, null, tn, serverMode);
                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                                    builder.setTitle("提示");
                                    builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
                                    builder.setNegativeButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    UPPayAssistEx.installUPPayPlugin(context);
                                                    dialog.dismiss();
                                                }
                                            });

                                    builder.setPositiveButton("取消",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    builder.create().show();
                                }

                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });

                        break;
                    case PAY_TYPE_ALI_PAY://调用支付宝支付的接口

                        UserDAO.PayOrder("alipay", orderid, orderType, curr_id, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                showShortToast("调用支付宝支付的接口");
                                String orderinfo = (String) data;
                                aliPay(orderinfo);
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                        break;
                    case PAY_TYPE_WE_CHAT://调用微信支付的接口

                        UserDAO.payOrderWeChat(orderid, orderType, curr_id, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                Log.e("weixin", data.toString());
                                try {
                                    JSONObject mData = new JSONObject((data.toString()));
                                    JSONObject content = mData.getJSONObject("data");
                                    String appId = content.getString("appid");
                                    IWXAPI api = WXAPIFactory.createWXAPI(context, null);
                                    api.registerApp(appId);
                                    PayReq request = new PayReq();
                                    request.appId = appId;
                                    request.partnerId = content.getString("partnerid");
                                    request.prepayId = content.getString("prepayid");
                                    request.packageValue = content.getString("package");
                                    request.nonceStr = content.getString("noncestr");
                                    request.timeStamp = content.getString("timestamp");
                                    request.sign = content.getString("sign");
                                    api.sendReq(request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

//                                showShortToast("调用微信支付的接口");
//                                try {
//                                    JSONObject content = new JSONObject(data.toString());
//                                    if (content.has("pay_id")){
//                                        String pay_id = content.getString("pay_id");
//                                        RequestMsg msg = new RequestMsg();
//                                        msg.setTokenId(pay_id);
//
//                                        //微信
//                                        msg.setTradeType(MainApplication.WX_APP_TYPE);
//                                        msg.setAppId("wx8c04c8ef6226fce8");//wxd3a1cdf74d0c41b3
//                                        PayPlugin.unifiedAppPay(OrderSureActivity.this, msg);
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }

                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });

                        break;
                    case PAY_TYPE_COD:
                        UserDAO.codOrder(orderid, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                EventBus.getDefault().post(new LoginEvent(IEventTag.TO_ORDER));
                                context.finish();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                        break;
                }
            }

        });

    }

    private class payTypeClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            itList.get(payType).setText(R.string.icon_un_select);
            for (int i = 0; i < idList.length; i++) {
                if (idList[i] == v.getId()) {
                    itList.get(i).setText(R.string.icon_select);
                    payType = payList[i];
                }
            }

        }
    }


    /*支付宝支付*/
    private void aliPay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
//                Log.e("pay", orderInfo);
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
//                    Log.e("pay", "alipay success");
                    String resultStatus = "";
                    @SuppressWarnings("unchecked")
                    Map<String, String> rawResult = (Map<String, String>) msg.obj;
//                    Log.e("pay", rawResult.toString());
                    if (rawResult == null) {
                        return;
                    }
                    for (String key : rawResult.keySet()) {
                        if (TextUtils.equals(key, "resultStatus")) {
                            resultStatus = rawResult.get(key);
                        }
                    }
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        paySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showShortToast("支付失败");
                    }
                    break;
            }
        }
    };

    private void paySuccess() {
        paySuccess.paySuccess();
    }

    TextView passWork1;
    TextView passWork2;
    TextView passWork3;
    TextView passWork4;
    TextView passWork5;
    TextView passWork6;

    private ArrayList<String> passwordList = new ArrayList<>();

    private ArrayList<TextView> paList = new ArrayList<>();
    CustomPopWindow popupWindow3;

    public void showPopupWindowPassWord() {
        passwordList.clear();
        paList.clear();
        if (popupWindow != null) {
            popupWindow.dissmiss();
        }

        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_window_password2, null);
        //处理popWindow 显示内容
        handlePassword(contentView);
        popupWindow3 = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(!def)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(!def) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(post, Gravity.BOTTOM, 0, 0);//显示PopupWindow

    }

    private void handlePassword(View contentView) {
        passWork1 = (TextView) contentView.findViewById(R.id.tv_password1);
        passWork2 = (TextView) contentView.findViewById(R.id.tv_password2);
        passWork3 = (TextView) contentView.findViewById(R.id.tv_password3);
        passWork4 = (TextView) contentView.findViewById(R.id.tv_password4);
        passWork5 = (TextView) contentView.findViewById(R.id.tv_password5);
        passWork6 = (TextView) contentView.findViewById(R.id.tv_password6);

        paList.add(passWork1);
        paList.add(passWork2);
        paList.add(passWork3);
        paList.add(passWork4);
        paList.add(passWork5);
        paList.add(passWork6);

        contentView.findViewById(R.id.if_tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow3.dissmiss();
                if (!isGoodsOrder) {
                    showPayPopWindow();
                }
            }
        });

        contentView.findViewById(R.id.tv_find_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetPayPasswordActivity.class);
                context.startActivity(intent);
            }
        });

        NumClick listener = new NumClick();
        for (int i = 0; i < numIdList.length; i++) {
            contentView.findViewById(numIdList[i]).setOnClickListener(listener);
        }
    }

    private class NumClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i = 0; i < numIdList.length; i++) {
                if (numIdList[i] == v.getId()) {
                    if (i != 10) {
                        if (passwordList.size() < 6) {
                            passwordList.add(i + "");
                            paList.get(passwordList.size() - 1).setText("*");
                            if (passwordList.size() == 6) {
                                if (isGoodsOrder) {
                                    //余额支付购买金币
                                    userMoneyPayGold(money,type);
                                } else {
                                    userMoneyPay();
                                }
                            }
                        }
                    } else {
                        if (passwordList.size() > 0) {
                            paList.get(passwordList.size() - 1).setText("");
                            passwordList.remove(passwordList.size() - 1);
                        }
                    }
                }
            }
        }
    }

    private void userMoneyPay() {
        String payPassword = "";
        for (int i = 0; i < 6; i++) {
            payPassword = payPassword + passwordList.get(i);
        }

        UserDAO.BalancePay(orderid, orderType, payPassword, curr_id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                popupWindow3.dissmiss();
                paySuccess();
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

    private void userMoneyPayGold(String price,String type) {
        String payPassword = "";
        for (int i = 0; i < 6; i++) {
            payPassword = payPassword + passwordList.get(i);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("token", LoginUtils.getToken());
        params.put(""+type, price);
        params.put("pay_password", payPassword);
        OkHttpUtil.postSubmitForm(HttpApi.buyGameCoin, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                Log.e("兑换金币的url", url);
                Log.e("兑换金币的json", json);
                popupWindow3.dissmiss();

                try {
                    JSONObject object = new JSONObject(json);
                    ToastUtil.showToast(context, object.getString("message"));
                    if (object.getInt("code") == 1) {
                        //获取数据
                        JSONObject object1 = object.getJSONObject("result");
                        LoginUtils.setDing_coin(object1.getString("ding_coin"));
                        LoginUtils.setUser_money(object1.getString("user_money"));
                        LoginUtils.setDaxiong_coin(object1.getString("curBalance"));

                        Log.e("兑换金币ding_coin", LoginUtils.getDing_coin());
                        Log.e("兑换金币user_money", LoginUtils.getUser_money());
                        Log.e("兑换金币daxiong_coin", LoginUtils.getDaxiong_coin());
                        paySuccess();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

}
