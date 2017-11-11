package com.yidu.sevensecondmall.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.NewMainActivity;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.GoodsBean;
import com.yidu.sevensecondmall.bean.OrderMessage.OrderDetailInfo;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.PaySuccess;
import com.yidu.sevensecondmall.i.RefreshEvent;
import com.yidu.sevensecondmall.i.WechatPayEvent;
import com.yidu.sevensecondmall.utils.PayPopWindow;
import com.yidu.sevensecondmall.utils.SpecHelper;
import com.yidu.sevensecondmall.utils.TimeUtils;
import com.yidu.sevensecondmall.views.adapter.detailAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/31 0031.
 */
public class OrderDetailActivity extends BaseActivity implements PaySuccess {


    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.tv_back_tip)
    TextView tvBackTip;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_login)
    RelativeLayout titleLogin;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.status)
    IconFontTextView status;
    @BindView(R.id.tv_consignee)
    TextView tvConsignee;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.order_list)
    RecyclerView orderList;
    @BindView(R.id.tv_freight_tip)
    TextView tvFreightTip;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_pay_tip)
    TextView tvPayTip;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_code_tip)
    TextView tvCodeTip;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_create_time_tip)
    TextView tvCreateTimeTip;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.orderstatus)
    TextView orderstatus;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String orderid = "";
    private String ordersn = "";

    private OrderDetailInfo info;
    private ArrayList<GoodsBean> list = new ArrayList<>();
    private detailAdapter adapter;
    private LinearLayoutManager manager;
    private int statusclick;
    private SpecHelper helper = SpecHelper.getInstance();
    private String postprice = "";
    private String str = "";
    private double weight = 0;

    private String buyBackStatus;
    private String buyBackAmount;

    @Override
    protected int setViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText(R.string.order_detail);
        toolbarTitle.setText(R.string.order_detail);

        Intent i = getIntent();
        if (i.hasExtra("orderid") && i.getStringExtra("orderid") != null) {
            orderid = i.getStringExtra("orderid");
            Log.e("orderid=============", orderid);
        }

        if (i.hasExtra("ordersn") && i.getStringExtra("ordersn") != null) {
            ordersn = i.getStringExtra("ordersn");
        }

        if (i.hasExtra("buyBackStatus") && i.getStringExtra("buyBackStatus") != null) {
            buyBackStatus = i.getStringExtra("buyBackStatus");
        }

        if (i.hasExtra("buyBackAmount") && i.getStringExtra("buyBackAmount") != null) {
            buyBackAmount = i.getStringExtra("buyBackAmount");
        }

        if (i.hasExtra("status")) {
            switch (i.getStringExtra("status")) {
                case "WAITSEND":
                    tv.setText("商品待发货");
                    statusclick = 2;
                    break;
                case "WAITPAY":
                    tv.setText("商品待支付");
                    statusclick = 1;
                    break;
                case "WAITRECEIVE":
                    tv.setText("商品待收货");
                    statusclick = 3;
                    break;
                case "WAITCOMMENT":
                    tv.setText("商品待评价");
                    statusclick = 4;
                    break;
                case "FINISH":
                    tv.setText("商品已退款");
                    statusclick = 5;
                    break;
            }

        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        manager = new LinearLayoutManager(OrderDetailActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        OrderDAO.getOrderDetail(orderid, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    info = (OrderDetailInfo) data;
                    Log.e("info", info.toString());
                    tvGoodsTitle.setText("共" + info.getList().size() + "件商品");
//                    tvGoodsTitle.setText("共" + info.getOrderDetailBean() + "件商品");
                    tvConsignee.setText("收货人：" + info.getOrderDetailBean().getConsignee());
                    String s = "地址：" + PositionDao.getInstance().getPreName(info.getOrderDetailBean().getDistrict()) +
                            info.getOrderDetailBean().getAddress();
                    tvAddress.setText(s);
                    tvPhoneNumber.setText(info.getOrderDetailBean().getMobile());
                    tvPay.setText(info.getOrderDetailBean().getTotal_amount());
                    tvCode.setText(info.getOrderDetailBean().getOrder_sn());
                    tvCreateTime.setText(TimeUtils.timet(info.getOrderDetailBean().getAdd_time()));
                    helper.setShipping_code(info.getOrderDetailBean().getShipping_code());
//                    helper.setShipping_code(info.getOrderDetailBean().getCode());
                    Log.e("物流解析getCode", info.getOrderDetailBean().getCode() + "");
                    Log.e("物流解析getShipping_code", info.getOrderDetailBean().getShipping_code() + "");
                    helper.setProvince(info.getOrderDetailBean().getProvince());
                    helper.setCity(info.getOrderDetailBean().getCity());
                    if ("2".equals(info.getOrderDetailBean().getBuy_back_status())) {
                        tv.setText("商品已回购");
                    }
                    helper.setDistrict(info.getOrderDetailBean().getDistrict());
                    tvFreight.setText("¥" + info.getOrderDetailBean().getShipping_price() + "");
                    list = info.getList();
                    adapter = new detailAdapter(OrderDetailActivity.this, list, statusclick, ordersn, buyBackStatus, buyBackAmount);
                    orderList.setLayoutManager(manager);
                    orderList.setAdapter(adapter);
                    for (int i = 0; i < info.getList().size(); i++) {
                        weight = weight + Double.parseDouble(info.getList().get(i).getWeight());
                    }
                    switch (statusclick) {
                        case 1:
                            orderstatus.setText("去付款");
                            orderstatus.setVisibility(View.VISIBLE);
                            orderstatus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    toPay();
                                    String money = tvPay.getText() == null ? "" : "¥" + tvPay.getText();
                                    PayPopWindow payPopWindow = new PayPopWindow(OrderDetailActivity.this, orderstatus, money, orderid, "order", OrderDetailActivity.this);
                                    payPopWindow.showPayPopWindow();

//                                    showPopupWindow();
                                }
                            });
                            break;
                        case 2:
                            orderstatus.setText("售后申请");
                            orderstatus.setVisibility(View.INVISIBLE);

                            break;
                        case 3:
                            orderstatus.setText("售后申请");
                            orderstatus.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            if (info.getOrderDetailBean().getBuy_back_status().equals("2")) {
                                orderstatus.setVisibility(View.GONE);
                            } else {
                                orderstatus.setText("售后申请");
                                orderstatus.setVisibility(View.INVISIBLE);
                            }
                            break;
                        default:
                            orderstatus.setText("去评价");
                            orderstatus.setVisibility(View.INVISIBLE);
                            break;
                    }

                }
            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemUtil.getSharedBoolean("closeDetail", false)) {
            SystemUtil.setSharedBoolean("closeDetail", false);
            OrderDetailActivity.this.finish();
        }
    }

    //支付
    public void toPay() {
        try {
            JSONArray array = new JSONArray();
            JSONObject obj = new JSONObject();
            for (int position = 0; position < info.getList().size(); position++) {
                postprice = list.get(position).getAllshippingprice() + "";
                obj.put("goods_id", list.get(position).getGoods_id());
                obj.put("goods_price", info.getOrderDetailBean().getTotal_amount());
                obj.put("shipping_price", postprice);
                obj.put("goods_num", list.get(position).getGoods_num());
                obj.put("spec_key", list.get(position).getSpec_key());
                obj.put("spec_key_name", list.get(position).getSpec_key_name());
                array.put(obj);
            }
            str = array.toString();
            OrderDAO.BuyNow(str, new BaseCallBack() {
                @Override
                public void success(Object data) {
                    Intent i = new Intent(OrderDetailActivity.this, OrderSureActivity.class);
                    i.putExtra("postprice", postprice);
                    i.putExtra("array", str);
                    i.putExtra("frombuy", 2);
                    startActivity(i);
                }

                @Override
                public void failed(int errorCode, Object data) {
                    Toast.makeText(OrderDetailActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.clear();
        if (getIntent().getBooleanExtra("fromSuccess", false)) {
//            Intent i = new Intent(OrderDetailActivity.this, MainActivity.class);
            Intent i = new Intent(OrderDetailActivity.this, NewMainActivity.class);
            startActivity(i);
        }
    }


    @OnClick(R.id.back_button)
    public void onClick() {
        EventBus.getDefault().post(new RefreshEvent(IEventTag.REFRSHLIST));
        finish();
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

    public void paySuccess() {
        Intent intent = new Intent(OrderDetailActivity.this, OrderSuccessActivity.class);
        intent.putExtra("name", info.getOrderDetailBean().getConsignee());
        intent.putExtra("address", info.getOrderDetailBean().getAddress());
        intent.putExtra("amount", info.getOrderDetailBean().getTotal_amount());

        intent.putExtra("orderid", orderid);
        startActivity(intent);
        OrderDetailActivity.this.finish();
    }

}
