package com.yidu.sevensecondmall.Activity.Order;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.setAddressActivity;
import com.yidu.sevensecondmall.DAO.GoodsDAO;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.bean.OrderMessage.Cart2Bean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.PaySuccess;
import com.yidu.sevensecondmall.i.WechatPayEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.PayPopWindow;
import com.yidu.sevensecondmall.views.adapter.trolleyAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/18.
 * 确认订单
 */
public class OrderSureActivity extends BaseActivity implements PaySuccess {

    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.buyer_add)
    TextView buyerAdd;
    @BindView(R.id.icon_add)
    IconFontTextView iconAdd;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;
    @BindView(R.id.shop_list)
    RecyclerView shopList;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.rl_send)
    RelativeLayout rlSend;
    @BindView(R.id.text2)
    TextView text2;
    @BindView(R.id.buyer_edit)
    EditText buyerEdit;
    @BindView(R.id.rl_edit)
    RelativeLayout rlEdit;
    @BindView(R.id.counts)
    TextView counts;
    @BindView(R.id.howmutch)
    TextView howmutch;
    @BindView(R.id.cost)
    LinearLayout cost;
    @BindView(R.id.post)
    LinearLayout post;
    @BindView(R.id.icon_loc)
    ImageView iconLoc;
    @BindView(R.id.buyer_name)
    TextView buyerName;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.buyer_txt)
    TextView buyerTxt;
    @BindView(R.id.totalprice)
    TextView totalprice;
    @BindView(R.id.telnum)
    TextView telnum;
    @BindView(R.id.costprice)
    TextView costprice;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private trolleyAdapter adapter;
    private Cart2Bean bean = new Cart2Bean();
    private List<CartlistBean.CartListBean> list = new ArrayList<CartlistBean.CartListBean>();
    private ArrayList<AddressBean> addresslist = new ArrayList<>();
    private String[] addstring;

    private String addressid = "";
    private String shippingid = "";
    private String address = "";

    private String name = "";
    private String total = "";
    //frombuynow : 0 购物车
    //1 立即购买
    //2 去付款
    private int frombuynow = 0;
    private String array;
    private String postprice = "";
    private String weight = "";
    private String orderid = "";
    private PositionDao positionDao = PositionDao.getInstance();
    private boolean isCod = true;
//
//    private String payType = PAY_TYPE_USER_MONEY;
//    private static final String PAY_TYPE_BANK_CARD = "pay_type_bank_card";
//    private static final String PAY_TYPE_ALI_PAY = "pay_type_ali_pay";
//    private static final String PAY_TYPE_WE_CHAT = "pay_type_we_chat";
//    private static final String PAY_TYPE_USER_MONEY = "pay_type_user_money";
//    private static final String PAY_TYPE_COD = "pay_type_cod";
//    private static final int SDK_PAY_FLAG = 1;
//
//    private static final String TAG = "OrderSureActivity";
//
//    MultiTypeAdapter payTypeAdapter;
//    private int payTypeInt;

    @Override
    protected int setViewId() {
        return R.layout.activity_sureorder;
    }

    @Override
    protected void findViews() {
        if (!LoginUtils.isLogin())
            OrderSureActivity.this.finish();
        ButterKnife.bind(this);
        Intent i = getIntent();

        if (i.hasExtra("frombuy")) {
            frombuynow = i.getIntExtra("frombuy", 0);
        }
        if (i.hasExtra("postprice")) {
            postprice = i.getStringExtra("postprice");
        }

        if (i.hasExtra("array") && i.getStringExtra("array") != null) {
            array = i.getStringExtra("array");
        }

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        titleName.setText("确认订单");
        toolbarTitle.setText("确认订单");

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        if (frombuynow == 0) {
            //购物车确认订单详情
            GoodsDAO.cart2(new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (Cart2Bean) data;
                        list = bean.getCartList();
                        if (list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                int is_cod = list.get(i).getIs_cod();
                                if (is_cod == 0) {
                                    isCod = false;
                                }
//                                isCod = is_cod == 1;
                            }
                        }
                        addresslist.clear();
                        addresslist = bean.getAddressList();
                        total = String.valueOf(bean.getTotalPriceBean().getTotal_fee());
                        howmutch.setText(total + "");
                        counts.setText(bean.getTotalPriceBean().getNum() + "");
                        if (bean.getShippingList().size() > 0) {
                            shippingid = bean.getShippingList().get(0).getCode();
                        }

                        if (addresslist == null || addresslist.size() == 0) {
                            buyerName.setText("");
                            telnum.setText("");
                            buyerAdd.setText("请新增收货地址");
                            addressid = "";
                            totalprice.setText(Double.parseDouble(total) + "");
                        } else {
                            buyerName.setText(addresslist.get(0).getConsignee());
                            telnum.setText(addresslist.get(0).getMobile());
                            String preaddress = positionDao.getPreName(addresslist.get(0).getDistrict());
                            buyerAdd.setText(preaddress + addresslist.get(0).getAddress());
                            addressid = addresslist.get(0).getAddress_id();
                            address = addresslist.get(0).getAddress();
                            name = bean.getAddressList().get(0).getConsignee();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderSureActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        shopList.setLayoutManager(layoutManager);
                        if (adapter == null) {
                            adapter = new trolleyAdapter(OrderSureActivity.this, list);
                            shopList.setAdapter(adapter);
                        } else {
                            ArrayList<CartlistBean.CartListBean> list2 = bean.getCartList();
                            list.clear();
                            list.addAll(list2);
                            adapter.notifyDataSetChanged();
                        }

                        adapter.fromorder(true);
                        adapter.hidecheck(true);
                        weight = bean.getTotalPriceBean().getWeight();
                        costprice.setText(bean.getTotalPriceBean().getAll_shipping_price() + "");
                        totalprice.setText(bean.getTotalPriceBean().getTotal_fee() + "");


                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    if (!LoginUtils.isLogin()) OrderSureActivity.this.finish();
                }
            });

        } else {
            //立即购买确认订单详情
            OrderDAO.BuyNow(array, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (Cart2Bean) data;
                        list = bean.getCartList();
                        if (list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                int is_cod = list.get(i).getIs_cod();
                                if (is_cod == 0) {
                                    isCod = false;
                                }
//                                isCod = is_cod==1;
                            }
                        }
                        addresslist.clear();
                        addresslist = bean.getAddressList();
                        total = String.valueOf(bean.getPricebean().getTotal_price());
                        howmutch.setText(total + "");
                        counts.setText(bean.getPricebean().getNum() + "");
                        costprice.setText(bean.getPricebean().getShipping_price() + "");
                        if (bean.getShippingList().size() > 0) {
                            shippingid = bean.getShippingList().get(0).getCode();
                        }
                        totalprice.setText(bean.getPricebean().getTotal_price() + "");
                        if (addresslist == null || addresslist.size() == 0) {
                            buyerName.setText("");
                            telnum.setText("");
                            buyerAdd.setText("请新增收货地址");
                            addressid = "";
//                            totalprice.setText(Double.parseDouble(total) + "");
                        } else {
                            buyerName.setText(addresslist.get(0).getConsignee());
                            telnum.setText(addresslist.get(0).getMobile());
                            String preaddress = positionDao.getPreName(addresslist.get(0).getDistrict());
                            buyerAdd.setText(preaddress + addresslist.get(0).getAddress());
                            addressid = addresslist.get(0).getAddress_id();
                            address = addresslist.get(0).getAddress();
                            name = bean.getAddressList().get(0).getConsignee();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(OrderSureActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        shopList.setLayoutManager(layoutManager);
                        if (adapter == null) {
                            adapter = new trolleyAdapter(OrderSureActivity.this, list);
                            shopList.setAdapter(adapter);
                        } else {
                            ArrayList<CartlistBean.CartListBean> list2 = bean.getCartList();
                            list.clear();
                            list.addAll(list2);
                            adapter.notifyDataSetChanged();
                        }

                        adapter.hidecheck(true);
                        adapter.fromorder(true);
                        weight = bean.getPricebean().getWeight();
//                        if (bean.getAddressList().size() > 0) {
//                            OrderDAO.calculatePost(bean.getShippingList().get(0).getCode(),
//                                    bean.getAddressList().get(0).getProvince(), bean.getAddressList().get(0).getCity(),
//                                    bean.getAddressList().get(0).getDistrict(), weight,
//                                    new BaseCallBack() {
//                                        @Override
//                                        public void success(Object data) {
//                                            String p = (String) data;
//                                            costprice.setText(p + "");
//                                            totalprice.setText(Double.parseDouble(total) + Double.parseDouble(p) + "");
//
//                                        }
//
//                                        @Override
//                                        public void failed(int errorCode, Object data) {
//                                            if (!LoginUtils.isLogin()) {
//                                                OrderSureActivity.this.finish();
//                                            } else {
//                                                showShortToast(data + "");
//                                            }
//                                        }
//                                    });
//                        }
                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    if (!LoginUtils.isLogin()) OrderSureActivity.this.finish();
                }
            });
        }
        super.onResume();

    }

    @OnClick({R.id.back, R.id.post, R.id.rl_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.post:
                if (addressid.equals("")) {
                    Toast.makeText(OrderSureActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                } else {
                    if (frombuynow == 0) {
                        GoodsDAO.cart3(addressid, shippingid, "", "", "", true, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                if (data != null) {
                                    orderid = (String) data;
                                    Toast.makeText(OrderSureActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    String money = totalprice.getText() == null ? "" : "¥" + totalprice.getText();
                                    PayPopWindow payPopWindow = new PayPopWindow(OrderSureActivity.this, post, money, orderid, "order", OrderSureActivity.this, isCod);
                                    payPopWindow.showPayPopWindow();

//                                    showPopupWindow();
                                }
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                    } else if (frombuynow == 1) {
                        OrderDAO.BuyNext(addressid, shippingid, array, new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                if (data != null) {
                                    orderid = (String) data;
                                    Toast.makeText(OrderSureActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    String money = totalprice.getText() == null ? "" : "¥" + totalprice.getText();
                                    PayPopWindow payPopWindow = new PayPopWindow(OrderSureActivity.this, post, money, orderid, "order", OrderSureActivity.this, isCod);
                                    payPopWindow.showPayPopWindow();
//                                    showPopupWindow();
                                }
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast(data + "");
                            }
                        });
                    } else {
                        String money = totalprice.getText() == null ? "" : "¥" + totalprice.getText();
                        PayPopWindow payPopWindow = new PayPopWindow(OrderSureActivity.this, post, money, orderid, "order", OrderSureActivity.this, isCod);
                        payPopWindow.showPayPopWindow();
//                        showPopupWindow();
                    }

                }
                break;
            case R.id.rl_add:
                if (addresslist.size() > 0) {
                    if (addresslist.size() > 0) {
                        addstring = new String[addresslist.size()];
                        for (int i = 0; i < addresslist.size(); i++) {
                            addstring[i] = addresslist.get(i).getAddress();
                        }
                        new AlertDialog.Builder(OrderSureActivity.this)
                                .setTitle("请选择地址")
                                .setItems(addstring, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String preaddress = positionDao.getPreName(addresslist.get(which).getDistrict());
                                        buyerAdd.setText(preaddress + addstring[which]);
                                        buyerName.setText(addresslist.get(which).getConsignee());
                                        telnum.setText(addresslist.get(which).getMobile());
                                        addressid = addresslist.get(which).getAddress_id();
                                        address = addresslist.get(which).getAddress();
                                        name = addresslist.get(which).getConsignee();

                                    }
                                }).show();
                    }

                } else {
                    Intent i = new Intent(OrderSureActivity.this, setAddressActivity.class);
                    startActivity(i);
                }

                break;
        }
    }

    public Activity getActivity() {
        return OrderSureActivity.this;
    }


//    CustomPopWindow popupWindow;
//
//    private void showPopupWindow() {
//        if (popupWindow3 != null) popupWindow3.dissmiss();
//        View contentView = LayoutInflater.from(OrderSureActivity.this).inflate(R.layout.pop_window_pay_type2, null);
//        //处理popWindow 显示内容
//        handleLogic(contentView);
//        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
//                .setView(contentView)//显示的布局，还可以通过设置一个View
//                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
//                .setFocusable(true)//是否获取焦点，默认为ture
//                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
//                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
//                .setBgDarkAlpha(0.5f) // 控制亮度
//                .create()//创建PopupWindow
//                .showAtLocation(post, Gravity.BOTTOM, 0, 0);//显示PopupWindow
//    }
//
//    /**
//     * 处理弹出显示内容、点击事件等逻辑
//     *
//     * @param contentView
//     */
//    private void handleLogic(View contentView) {
//
//        TextView tv_money = (TextView) contentView.findViewById(R.id.tv_money);
//        tv_money.setText(totalprice.getText() == null ? "" : "¥" + totalprice.getText());
//
//        contentView.findViewById(R.id.if_tv_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dissmiss();
//            }
//        });
//
//        contentView.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               if (tvPayType.getText().length() != 0) {
//                popupWindow.dissmiss();
//                //TODO 从后台获得支付信息
//
//                switch (payType) {
//                    case PAY_TYPE_USER_MONEY://弹出输入密码弹窗，调用余额支付
//                        UserDAO.getUserInfo(new BaseCallBack() {
//                            @Override
//                            public void success(Object data) {
//                                if (data != null) {
//                                    UserBean bean = (UserBean) data;
//                                    String user_money_pwd = bean.getUser_money_pwd();
//                                    if (user_money_pwd != null && !user_money_pwd.equals("")) {
//                                        //有支付密码
//                                        showPopupWindowPassWord();
//                                    } else {
//                                        showShortToast("请先设置支付密码");
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void failed(int errorCode, Object data) {
//                                showShortToast(data + "");
//                            }
//                        });
//                        break;
//                    case PAY_TYPE_BANK_CARD://调用银联支付的接口
//                        UserDAO.UnionpayOrder(orderid, "order", new BaseCallBack() {
//                            @Override
//                            public void success(Object data) {
//                                String tn = (String) data;
//
//                                if (UPPayAssistEx.checkInstalled(getApplicationContext())) {
////                                Log.e(TestCart.LOG,"流水号："+tn);
//                                    String serverMode = "00";
//                                    UPPayAssistEx.startPay(getActivity(), null, null, tn, serverMode);
//                                } else {
//                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//                                    builder.setTitle("提示");
//                                    builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
//                                    builder.setNegativeButton("确定",
//                                            new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    UPPayAssistEx.installUPPayPlugin(getActivity());
//                                                    dialog.dismiss();
//                                                }
//                                            });
//
//                                    builder.setPositiveButton("取消",
//                                            new DialogInterface.OnClickListener() {
//
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    builder.create().show();
//                                }
//
//                            }
//
//                            @Override
//                            public void failed(int errorCode, Object data) {
//                                showShortToast(data+"");
//                            }
//                        });
//
//                        break;
//                    case PAY_TYPE_ALI_PAY://调用支付宝支付的接口
//
//                        UserDAO.PayOrder("alipay", orderid, "order", new BaseCallBack() {
//                            @Override
//                            public void success(Object data) {
//                                showShortToast("调用支付宝支付的接口");
//                                String orderinfo = (String) data;
//                                aliPay(orderinfo);
//                            }
//
//                            @Override
//                            public void failed(int errorCode, Object data) {
//                                showShortToast(data + "");
//                            }
//                        });
//                        break;
//                    case PAY_TYPE_WE_CHAT://调用微信支付的接口
//
//                        UserDAO.payOrderWeChat(orderid, "order", new BaseCallBack() {
//                            @Override
//                            public void success(Object data) {
//                                Log.e("weixin", data.toString());
//                                try {
//                                    JSONObject mData  = new JSONObject((data.toString()));
//                                    JSONObject content = mData.getJSONObject("data");
//                                    String appId = content.getString("appid");
//                                    IWXAPI api = WXAPIFactory.createWXAPI(OrderSureActivity.this, null);
//                                    api.registerApp(appId);
//                                    PayReq request = new PayReq();
//                                    request.appId = appId;
//                                    request.partnerId = content.getString("partnerid");
//                                    request.prepayId =content.getString("prepayid");
//                                    request.packageValue = content.getString("package");
//                                    request.nonceStr = content.getString("noncestr");
//                                    request.timeStamp = content.getString("timestamp");
//                                    request.sign = content.getString("sign");
//                                    api.sendReq(request);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
////                                showShortToast("调用微信支付的接口");
////                                try {
////                                    JSONObject content = new JSONObject(data.toString());
////                                    if (content.has("pay_id")){
////                                        String pay_id = content.getString("pay_id");
////                                        RequestMsg msg = new RequestMsg();
////                                        msg.setTokenId(pay_id);
////
////                                        //微信
////                                        msg.setTradeType(MainApplication.WX_APP_TYPE);
////                                        msg.setAppId("wx8c04c8ef6226fce8");//wxd3a1cdf74d0c41b3
////                                        PayPlugin.unifiedAppPay(OrderSureActivity.this, msg);
////                                    }
////                                }catch (Exception e){
////                                    e.printStackTrace();
////                                }
//
//                            }
//
//                            @Override
//                            public void failed(int errorCode, Object data) {
//                                showShortToast(data + "");
//                            }
//                        });
//
//                        break;
//                    case PAY_TYPE_COD:
//                        UserDAO.codOrder(orderid, new BaseCallBack() {
//                            @Override
//                            public void success(Object data) {
//                                EventBus.getDefault().post(new LoginEvent(IEventTag.TO_ORDER));
//                                OrderSureActivity.this.finish();
//                            }
//
//                            @Override
//                            public void failed(int errorCode, Object data) {
//                                showShortToast(data+"");
//                            }
//                        });
//                        break;
//                }
//            }
////           else {
////                   showShortToast("请选择支付方式");
////               }
//
//        });
//
//
//        models = new ArrayList<>();
//        PayTypeBean bean0 = new PayTypeBean();
//        bean0.setName("余额支付");
//        bean0.setType(PAY_TYPE_USER_MONEY);
//        if (payTypeInt == 0) {
//            bean0.setChoose(true);
//        }
//        models.add(bean0);
//
//        PayTypeBean bean1 = new PayTypeBean();
//        bean1.setName("支付宝支付");
//        bean1.setType(PAY_TYPE_ALI_PAY);
//        if (payTypeInt == 1) {
//            bean1.setChoose(true);
//        }
//        models.add(bean1);
//
//        PayTypeBean bean2 = new PayTypeBean();
//        bean2.setName("微信支付");
//        bean2.setType(PAY_TYPE_WE_CHAT);
//        if (payTypeInt == 2) {
//            bean2.setChoose(true);
//        }
//        models.add(bean2);
//
//        PayTypeBean bean3 = new PayTypeBean();
//        bean3.setName("银行卡支付");
//        bean3.setType(PAY_TYPE_BANK_CARD);
//        if (payTypeInt == 3) {
//            bean3.setChoose(true);
//        }
//        models.add(bean3);
//
//        if (isCod) {
//            PayTypeBean bean4 = new PayTypeBean();
//            bean4.setName("面对面付款");
//            bean4.setType(PAY_TYPE_COD);
//            if (payTypeInt == 4) {
//                bean4.setChoose(true);
//            }
//            models.add(bean4);
//        }
//
//        RecyclerView rv = (RecyclerView) contentView.findViewById(R.id.rv);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv.setLayoutManager(linearLayoutManager);
//        payTypeAdapter = new MultiTypeAdapter(models, this);
//
//        rv.setAdapter(payTypeAdapter);
//
//    }
//
//
//    private List<Visitable> models = new ArrayList<>();
//    IconFontTextView ifTvBack;
//
//
//    TextView passWork1;
//    TextView passWork2;
//    TextView passWork3;
//    TextView passWork4;
//    TextView passWork5;
//    TextView passWork6;
//    TextView tvFindPassword;
//    RecyclerView rvPassword;
//    private ArrayList<String> passwordList = new ArrayList<>();
//    private List<Visitable> passwordNumbers = new ArrayList<>();
//    CustomPopWindow popupWindow3;
//
//    private void showPopupWindowPassWord() {
//        if (popupWindow != null) {
//            popupWindow.dissmiss();
//        }
//
//        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_window_password, null);
//        //处理popWindow 显示内容
//        handlePassword(contentView);
//        popupWindow3 = new CustomPopWindow.PopupWindowBuilder(this)
//                .setView(contentView)//显示的布局，还可以通过设置一个View
//                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
//                .setFocusable(true)//是否获取焦点，默认为ture
//                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
//                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
//                .setBgDarkAlpha(0.5f) // 控制亮度
//                .create()//创建PopupWindow
//                .showAtLocation(post, Gravity.BOTTOM, 0, 0);//显示PopupWindow
//
//    }
//
//    private void handlePassword(View contentView) {
//        passWork1 = (TextView) contentView.findViewById(R.id.tv_password1);
//        passWork2 = (TextView) contentView.findViewById(R.id.tv_password2);
//        passWork3 = (TextView) contentView.findViewById(R.id.tv_password3);
//        passWork4 = (TextView) contentView.findViewById(R.id.tv_password4);
//        passWork5 = (TextView) contentView.findViewById(R.id.tv_password5);
//        passWork6 = (TextView) contentView.findViewById(R.id.tv_password6);
//        contentView.findViewById(R.id.tv_find_password).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OrderSureActivity.this, SetPayPasswordActivity.class);
//                OrderSureActivity.this.startActivity(intent);
//            }
//        });
//        rvPassword = (RecyclerView) contentView.findViewById(R.id.rv_password);
//        ifTvBack = (IconFontTextView) contentView.findViewById(R.id.if_tv_back);
//        ifTvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                popupWindow2.dissmiss();
//                showPopupWindow();
//            }
//        });
//        passwordNumbers = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            PasswordBean bean = new PasswordBean();
//            if (i < 9) {
//                bean.setNumber(i + 1 + "");
//            } else {
//                if (i == 10) {
//                    bean.setNumber("0");
//                }
//                if (i == 11) {
//                    bean.setNumber("×");
//                }
//            }
//            passwordNumbers.add(bean);
//        }
//
//        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rvPassword.setLayoutManager(linearLayoutManager);
//        MultiTypeAdapter adapter = new MultiTypeAdapter(passwordNumbers);
//        rvPassword.setAdapter(adapter);
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void handlerCenter(SelectEvent event) {
//        switch (event.founctionTag) {
//            case IEventOrderTag.SHOW_PAY_TYPE_DETAIL:
//                payTypeInt = event.position;
//                switch (payTypeInt) {
//                    case 0:
//                        payType = PAY_TYPE_USER_MONEY;
//                        break;
//                    case 1:
//                        payType = PAY_TYPE_ALI_PAY;
//                        break;
//                    case 2:
//                        payType = PAY_TYPE_WE_CHAT;
//                        break;
//                    case 3:
//                        payType = PAY_TYPE_BANK_CARD;
//                        break;
//                    case 4:
//                        payType = PAY_TYPE_COD;
//                        break;
//                }
//                models = new ArrayList<>();
//                PayTypeBean bean0 = new PayTypeBean();
//                bean0.setName("余额支付");
//                bean0.setType(PAY_TYPE_USER_MONEY);
//                if (payTypeInt == 0) {
//                    bean0.setChoose(true);
//                }
//                models.add(bean0);
//
//                PayTypeBean bean1 = new PayTypeBean();
//                bean1.setName("支付宝支付");
//                bean1.setType(PAY_TYPE_ALI_PAY);
//                if (payTypeInt == 1) {
//                    bean1.setChoose(true);
//                }
//                models.add(bean1);
//
//                PayTypeBean bean2 = new PayTypeBean();
//                bean2.setName("微信支付");
//                bean2.setType(PAY_TYPE_WE_CHAT);
//                if (payTypeInt == 2) {
//                    bean2.setChoose(true);
//                }
//                models.add(bean2);
//
//                PayTypeBean bean3 = new PayTypeBean();
//                bean3.setName("银行卡支付");
//                bean3.setType(PAY_TYPE_BANK_CARD);
//                if (payTypeInt == 3) {
//                    bean3.setChoose(true);
//                }
//                models.add(bean3);
//
//                if (isCod) {
//                    PayTypeBean bean4 = new PayTypeBean();
//                    bean4.setName("面对面付款");
//                    bean4.setType(PAY_TYPE_COD);
//                    if (payTypeInt == 4) {
//                        bean4.setChoose(true);
//                    }
//                    models.add(bean4);
//                }
//
//                payTypeAdapter.refreshData(models);
////                popupWindow.dissmiss();
////                showPopupWindow();
//                break;
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void handlerCenter(NumberEvent event) {
//        switch (event.founctionTag) {
//            case IEventOrderTag.SEND_PASSWORD:
//                if (!"".equals(event.number)) {
//                    if ("×".equals(event.number)) {
//                        delPassword();
//                    } else {
//                        addPassword(event.number);
//                    }
//                }
//                break;
//        }
//    }
//
//    public void addPassword(String password) {
//        String pa = "*";
////        String pa = password;
//        if (passWork1.getText().length() == 0) {
//            passWork1.setText(pa);
//        } else if (passWork2.getText().length() == 0) {
//            passWork2.setText(pa);
//        } else if (passWork3.getText().length() == 0) {
//            passWork3.setText(pa);
//        } else if (passWork4.getText().length() == 0) {
//            passWork4.setText(pa);
//        } else if (passWork5.getText().length() == 0) {
//            passWork5.setText(pa);
//        } else if (passWork6.getText().length() == 0) {
//            passWork6.setText(pa);
//        }
//        if (passwordList.size() < 6) {
//            passwordList.add(password);
//            if (passwordList.size() == 6) {
////                popupWindow2.dissmiss();
//                String id = getIntent().getStringExtra("id");
//                id = id == null ? "" : id;
//
//
//                String payPassword = "";
//                for (int i = 0; i < 6; i++) {
//                    payPassword = payPassword + passwordList.get(i);
//                }
//
//
//
//                UserDAO.BalancePay(orderid, "order", payPassword, new BaseCallBack() {
//                    @Override
//                    public void success(Object data) {
//                        paySuccess();
//                    }
//
//                    @Override
//                    public void failed(int errorCode, Object data) {
//                        showShortToast(data + "");
//                    }
//                });
//
//                finish();
//            }
//        }
//
//    }
//
//
//
//    public void delPassword() {
//        if (passWork6.getText().length() != 0) {
//            passWork6.setText("");
//        } else if (passWork5.getText().length() != 0) {
//            passWork5.setText("");
//        } else if (passWork4.getText().length() != 0) {
//            passWork4.setText("");
//        } else if (passWork3.getText().length() != 0) {
//            passWork3.setText("");
//        } else if (passWork2.getText().length() != 0) {
//            passWork2.setText("");
//        } else if (passWork1.getText().length() != 0) {
//            passWork1.setText("");
//        }
//        if (passwordList.size() > 0)
//            passwordList.remove(passwordList.size() - 1);
//    }
//
//    /*支付宝支付*/
//    private void aliPay(final String orderInfo) {
//
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
////                Log.e("pay", orderInfo);
//                PayTask alipay = new PayTask(OrderSureActivity.this);
//                Map<String, String> result = alipay.payV2(orderInfo, true);
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG:
////                    Log.e("pay", "alipay success");
//                    String resultStatus = "";
//                    @SuppressWarnings("unchecked")
//                    Map<String, String> rawResult = (Map<String, String>) msg.obj;
////                    Log.e("pay", rawResult.toString());
//                    if (rawResult == null) {
//                        return;
//                    }
//                    for (String key : rawResult.keySet()) {
//                        if (TextUtils.equals(key, "resultStatus")) {
//                            resultStatus = rawResult.get(key);
//                        }
//                    }
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
////                        Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
//                        paySuccess();
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    };

    public void paySuccess() {
        Intent intent = new Intent(OrderSureActivity.this, OrderSuccessActivity.class);
        intent.putExtra("name", buyerName.getText().toString());
        intent.putExtra("address", buyerAdd.getText().toString());
        intent.putExtra("amount", totalprice.getText().toString());

        intent.putExtra("orderid", orderid);
        startActivity(intent);
        OrderSureActivity.this.finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(WechatPayEvent wechatPayEvent) {
        switch (wechatPayEvent.payEventTag) {
            case IEventOrderTag.WECHAT_PAY_SUCCESS:
                paySuccess();
                break;
            case IEventOrderTag.WECHAT_PAY_FAILURE:
                break;
            case IEventOrderTag.WECHAT_PAY_CANCEL:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            paySuccess();
        } else if (str.equalsIgnoreCase("fail")) {
            Toast.makeText(getApplicationContext(), "支付失败！", Toast.LENGTH_SHORT).show();
        } else if (str.equalsIgnoreCase("cancel")) {
            Toast.makeText(getApplicationContext(), "您取消了支付！", Toast.LENGTH_SHORT).show();
        }
    }

}
