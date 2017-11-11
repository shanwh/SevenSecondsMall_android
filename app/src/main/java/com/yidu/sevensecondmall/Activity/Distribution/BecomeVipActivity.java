package com.yidu.sevensecondmall.Activity.Distribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.setAddressActivity;
import com.yidu.sevensecondmall.DAO.DistributionDAO;
import com.yidu.sevensecondmall.DAO.OrderDAO;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.InviteOrderBean;
import com.yidu.sevensecondmall.bean.Order.PayTypeBean;
import com.yidu.sevensecondmall.bean.OrderMessage.AddressBean;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.GiftEvent;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.NumberEvent;
import com.yidu.sevensecondmall.i.SelectEvent;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.GlideRoundTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/12 0012.
 * 已弃用
 */
public class BecomeVipActivity extends BaseActivity {
    private static final String TAG = "BecomeVipActivity";

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_fre)
    TextView tvFre;
    @BindView(R.id.et_fre)
    TextView etFre;
    @BindView(R.id.tv_gift)
    TextView tvGift;
    @BindView(R.id.et_gift)
    TextView etGift;
    @BindView(R.id.if_tv1)
    IconFontTextView ifTv1;
    @BindView(R.id.rl_gift)
    RelativeLayout rlGift;
    @BindView(R.id.tv_receive_address)
    TextView tvReceiveAddress;
    @BindView(R.id.et_receive_address)
    TextView etReceiveAddress;
    @BindView(R.id.if_tv2)
    IconFontTextView ifTv2;
    @BindView(R.id.rl_receive_address)
    RelativeLayout rlReceiveAddress;
    @BindView(R.id.rv_gif)
    RecyclerView rvGif;
    @BindView(R.id.tv_next)
    TextView tvNext;

    Context context;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private boolean hasAddress;
    private boolean isClickGiftList;
    private String gifId;
    private String addressid = "";
    MultiTypeAdapter adapter;
    ArrayList<Visitable> list = new ArrayList<>();
    private String[] addstring;
    private ArrayList<AddressBean> addresslist = new ArrayList<>();

    private String card_id;
    private String order_id;
    private String shipping_code;

    private String payType;

    private static final String PAY_TYPE_BANK_CARD = "pay_type_bank_card";
    private static final String PAY_TYPE_ALI_PAY = "pay_type_ali_pay";
    private static final String PAY_TYPE_WE_CHAT = "pay_type_we_chat";
    private static final String PAY_TYPE_USER_MONEY = "pay_type_user_money";

    CustomPopWindow popWindow;

    @Override
    protected int setViewId() {
        return R.layout.activity_becom_vip;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("成为会员");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        context = BecomeVipActivity.this;
        if (getIntent().getStringExtra("id") == null) {
//            etName.setText(getIntent().getStringExtra("name"));
//            etAddress.setText(getIntent().getStringExtra("city_id"));
            etName.setEnabled(true);
            etAddress.setEnabled(true);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BecomeVipActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvGif.setLayoutManager(linearLayoutManager);
        adapter = new MultiTypeAdapter(list, BecomeVipActivity.this);
        rvGif.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        String id = getIntent().getStringExtra("id");
        DistributionDAO.createInviteOrder("member", id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                InviteOrderBean bean = (InviteOrderBean) data;
                list = bean.getGift_goods_arr();
                adapter.refreshData(list);
                etName.setText(bean.getGroupinfo().getName());
//                etAddress.setText(bean.getGroupinfo().getCity_id());
                List<ProvinceModel> provinceModels = PositionDao.getInstance().queryProvinceList();
                for (int i = 0; i < provinceModels.size(); i++) {
                    ProvinceModel provinceModel = provinceModels.get(i);
                    List<CityModel> cityList = provinceModel.getCityList();
                    for (int j = 0; j < cityList.size(); j++) {
                        CityModel cityModel = cityList.get(j);
                        if (cityModel.getId() == Integer.parseInt(bean.getGroupinfo().getCity_id())) {
                            etAddress.setText(provinceModel.getName() + cityModel.getName());
                        }
                    }
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });

        loadAddressList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (addresslist == null || addresslist.size() == 0) {
            loadAddressList();
        }
    }

    @OnClick({R.id.rl_gift, R.id.rl_receive_address, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_gift:
                if (isClickGiftList) {
                    rvGif.setVisibility(View.GONE);
                    isClickGiftList = false;
                } else {
                    rvGif.setVisibility(View.VISIBLE);
                    isClickGiftList = true;
                }
                break;
            case R.id.rl_receive_address:
                if (hasAddress) {
                    addstring = new String[addresslist.size()];
                    for (int i = 0; i < addresslist.size(); i++) {
                        addstring[i] = addresslist.get(i).getAddress();
                    }
                    new AlertDialog.Builder(BecomeVipActivity.this)
                            .setTitle("请选择地址")
                            .setItems(addstring, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                        buyerTxt.setText(addstring[which]);
                                    addressid = addresslist.get(which).getAddress_id();
                                    etReceiveAddress.setText(addstring[which]);
                                }
                            }).show();
                } else {
                    Intent i = new Intent(BecomeVipActivity.this, setAddressActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.tv_next:
                if (checkMessage()) {
                    try {
                        showPopupWindow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean checkMessage() {
        if (etName.getText().length() == 0) {
            showShortToast("请输入团队名称");
            return false;
        }
        if (etAddress.getText().length() == 0) {
            showShortToast("请输入具体位置信息");
            return false;
        }
        if (etGift.getText().length() == 0) {
            showShortToast("请选择礼品");
            return false;
        }
        if (addressid.length() == 0) {
            showShortToast("请选择收货地址");
            return false;
        }
        return true;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(GiftEvent event) {
        switch (event.founctionTag) {
            case IEventTag.GIFT_CLICK:
                Glide.with(context)
                        .load(HttpApi.getFullImageUrl(event.img))
                        .placeholder(R.drawable.default_loading_pic)
                        .transform(new GlideRoundTransform(context))
                        .into(iv);
                etGift.setText(event.name);
                gifId = String.valueOf(event.id);

                rvGif.setVisibility(View.GONE);
                isClickGiftList = false;
                break;
        }
    }


    CustomPopWindow popupWindow;
    TextView tvPayType;

    private void showPopupWindow() {
        if (popupWindow2 != null) popupWindow2.dissmiss();
        if (popupWindow3 != null) popupWindow3.dissmiss();
        View contentView = LayoutInflater.from(BecomeVipActivity.this).inflate(R.layout.pop_window_pay_detail, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(tvNext, Gravity.BOTTOM, 0, 0);//显示PopupWindow

    }

    private void handleLogic(View contentView) {
        tvPayType = (TextView) contentView.findViewById(R.id.tv_pay_type);
        for (int i = 0; i < models.size(); i++) {
            PayTypeBean visitable = (PayTypeBean) models.get(i);
            if (visitable.isChoose()) {
                tvPayType.setText(visitable.getName());
                payType = visitable.getType();
            }
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.rl_pay_type:

                        popWindow.dissmiss();

                        showPopupWindowPayType();
                        break;
                    case R.id.tv_affirm:
                        if (tvPayType.getText().length() != 0) {
                            popWindow.dissmiss();
                            switch (payType) {
                                case PAY_TYPE_USER_MONEY:
                                    showPopupWindowPassWord();
                                    break;
                                case PAY_TYPE_BANK_CARD:

                                    break;
                                case PAY_TYPE_ALI_PAY:

                                    break;
                                case PAY_TYPE_WE_CHAT:

                                    break;
                            }
                        } else {
                            showShortToast("请选择支付方式");
                        }
                        break;
                    case R.id.if_tv_close:
                        if (popWindow != null) {
                            popWindow.dissmiss();
                        }
                        break;
                }
            }
        };
        TextView tv_amount = (TextView) contentView.findViewById(R.id.tv_amount);
        tv_amount.setText(etFre.getText() == null ? "" : etFre.getText());
        contentView.findViewById(R.id.rl_market_id).setVisibility(View.GONE);
        contentView.findViewById(R.id.rl_pay_type).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_affirm).setOnClickListener(listener);
        contentView.findViewById(R.id.if_tv_close).setOnClickListener(listener);
    }

    private List<Visitable> models = new ArrayList<>();
    CustomPopWindow popupWindow2;
    IconFontTextView ifTvBack;

    private void showPopupWindowPayType() {
        if (popupWindow != null) popupWindow.dissmiss();
        if (popupWindow3 != null) popupWindow3.dissmiss();
        View contentView = LayoutInflater.from(BecomeVipActivity.this).inflate(R.layout.pop_window_pay_type, null);
        //处理popWindow 显示内容
        handlePayType(contentView);
        popupWindow2 = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(tvNext, Gravity.BOTTOM, 0, 0);//显示PopupWindow
    }

    private void handlePayType(View contentView) {
        models = new ArrayList<>();
        PayTypeBean bean0 = new PayTypeBean();
        bean0.setName("余额支付");
        bean0.setType(PAY_TYPE_USER_MONEY);
        models.add(bean0);

        PayTypeBean bean1 = new PayTypeBean();
        bean1.setName("支付宝支付");
        bean1.setType(PAY_TYPE_ALI_PAY);
        models.add(bean1);

        PayTypeBean bean2 = new PayTypeBean();
        bean2.setName("微信支付");
        bean2.setType(PAY_TYPE_WE_CHAT);
        models.add(bean2);

        PayTypeBean bean3 = new PayTypeBean();
        bean3.setName("银行卡支付");
        bean3.setType(PAY_TYPE_BANK_CARD);
        models.add(bean3);

        RecyclerView rv = (RecyclerView) contentView.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        MultiTypeAdapter adapter = new MultiTypeAdapter(models, BecomeVipActivity.this);

        rv.setAdapter(adapter);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.if_tv_back:
                        if (popupWindow2 != null) {
                            popupWindow2.dissmiss();
                        }
                        showPopupWindow();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.if_tv_back).setOnClickListener(listener);
    }

    TextView passWork1;
    TextView passWork2;
    TextView passWork3;
    TextView passWork4;
    TextView passWork5;
    TextView passWork6;
    TextView tvFindPassword;
    RecyclerView rvPassword;
    private ArrayList<String> passwordList = new ArrayList<>();
    private List<Visitable> passwordNumbers = new ArrayList<>();
    CustomPopWindow popupWindow3;

    private void showPopupWindowPassWord() {
        popupWindow.dissmiss();
        popupWindow2.dissmiss();
        View contentView = LayoutInflater.from(BecomeVipActivity.this).inflate(R.layout.pop_window_password, null);
        //处理popWindow 显示内容
        handlePassword(contentView);
        popupWindow3 = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(tvNext, Gravity.BOTTOM, 0, 0);//显示PopupWindow

    }

    private void handlePassword(View contentView) {
        passWork1 = (TextView) contentView.findViewById(R.id.tv_password1);
        passWork2 = (TextView) contentView.findViewById(R.id.tv_password2);
        passWork3 = (TextView) contentView.findViewById(R.id.tv_password3);
        passWork4 = (TextView) contentView.findViewById(R.id.tv_password4);
        passWork5 = (TextView) contentView.findViewById(R.id.tv_password5);
        passWork6 = (TextView) contentView.findViewById(R.id.tv_password6);
        tvFindPassword = (TextView) contentView.findViewById(R.id.tv_find_password);
        rvPassword = (RecyclerView) contentView.findViewById(R.id.rv_password);
        ifTvBack = (IconFontTextView) contentView.findViewById(R.id.if_tv_back);
        ifTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow2.dissmiss();
                showPopupWindow();
            }
        });
        passwordNumbers = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            PasswordBean bean = new PasswordBean();
            if (i < 9) {
                bean.setNumber(i + 1 + "");
            } else {
                if (i == 10) {
                    bean.setNumber("0");
                }
                if (i == 11) {
                    bean.setNumber("×");
                }
            }
            passwordNumbers.add(bean);
        }

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPassword.setLayoutManager(linearLayoutManager);
        MultiTypeAdapter adapter = new MultiTypeAdapter(passwordNumbers);
        rvPassword.setAdapter(adapter);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(SelectEvent event) {
        switch (event.founctionTag) {
            case IEventOrderTag.SHOW_PAY_TYPE_DETAIL:
                popupWindow2.dissmiss();
                showPopupWindow();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlerCenter(NumberEvent event) {
        switch (event.founctionTag) {
            case IEventOrderTag.SEND_PASSWORD:
                if (!"".equals(event.number)) {
                    if ("×".equals(event.number)) {
                        delPassword();
                    } else {
                        addPassword(event.number);
                    }
                }
                break;
        }
    }

    public void addPassword(String password) {
        String pa = "*";
//        String pa = password;
        if (passWork1.getText().length() == 0) {
            passWork1.setText(pa);
        } else if (passWork2.getText().length() == 0) {
            passWork2.setText(pa);
        } else if (passWork3.getText().length() == 0) {
            passWork3.setText(pa);
        } else if (passWork4.getText().length() == 0) {
            passWork4.setText(pa);
        } else if (passWork5.getText().length() == 0) {
            passWork5.setText(pa);
        } else if (passWork6.getText().length() == 0) {
            passWork6.setText(pa);
        }
        if (passwordList.size() < 6) {
            passwordList.add(password);
            if (passwordList.size() == 6) {
                popupWindow2.dissmiss();
                String id = getIntent().getStringExtra("id");
                id = id == null ? "" : id;

                DistributionDAO.submitMember(
                        SystemUtil.getSharedInt("userId", 1) + "",
                        id,
                        gifId,
                        addressid,
                        new BaseCallBack() {
                            @Override
                            public void success(Object data) {
//                                Toast.makeText(context, "申请成功请等待审核", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(BecomeVipActivity.this, )
                                String payPassword = "";
                                for (int i = 0; i < 6; i++) {
                                    payPassword = payPassword + passwordList.get(i);
                                }
                                DistributionDAO.payUserOrderByUserMoney(payPassword, "", new BaseCallBack() {
                                    @Override
                                    public void success(Object data) {

                                    }

                                    @Override
                                    public void failed(int errorCode, Object data) {

                                    }
                                });
                                finish();
                            }

                            @Override
                            public void failed(int errorCode, Object data) {
                                showShortToast("" + data);
                            }
                        }
                );
                finish();
            }
        }
    }

    public void delPassword() {
        if (passWork6.getText().length() != 0) {
            passWork6.setText("");
        } else if (passWork5.getText().length() != 0) {
            passWork5.setText("");
        } else if (passWork4.getText().length() != 0) {
            passWork4.setText("");
        } else if (passWork3.getText().length() != 0) {
            passWork3.setText("");
        } else if (passWork2.getText().length() != 0) {
            passWork2.setText("");
        } else if (passWork1.getText().length() != 0) {
            passWork1.setText("");
        }
        if (passwordList.size() > 0)
            passwordList.remove(passwordList.size() - 1);
    }

    private Activity getActivity() {
        return BecomeVipActivity.this;
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    private void loadAddressList() {
        OrderDAO.getAddressList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                addresslist = (ArrayList<AddressBean>) data;
                if (addresslist.size() > 0) {
                    hasAddress = true;
                    boolean hasDefault = false;
                    for (int i = 0; i < addresslist.size(); i++) {
                        String is_default = addresslist.get(i).getIs_default();
                        if (is_default.equals("1")) {
                            etReceiveAddress.setText(addresslist.get(i).getAddress());
                            addressid = addresslist.get(i).getAddress_id();
                            hasDefault = true;
                        }
                    }
                    if (!hasDefault) {
                        etReceiveAddress.setText(addresslist.get(0).getAddress());
                        addressid = addresslist.get(0).getAddress_id();
                    }
                } else {
                    hasAddress = false;
                    etReceiveAddress.setText("请新增收货地址");
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if (errorCode == 1001) {
                    etReceiveAddress.setText("请新增收货地址");
                    hasAddress = false;
                }
            }
        });
    }

    private void bankCardPay() {

    }

    private void userMoneyPay() {
        showPopupWindowPassWord();
    }

}
