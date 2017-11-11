package com.yidu.sevensecondmall.Activity.Distribution;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.SetPayPasswordActivity;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.PayTypeBean;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.DistrictModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.NumberEvent;
import com.yidu.sevensecondmall.i.SelectEvent;
import com.yidu.sevensecondmall.i.WechatPayEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.AddressAlertDialog;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;
import com.yidu.sevensecondmall.views.widget.widget.OnWheelChangedListener;
import com.yidu.sevensecondmall.views.widget.widget.WheelView;
import com.yidu.sevensecondmall.views.widget.widget.adapters.ArrayWheelAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2017/5/11 0011.
 * 已弃用
 */
public class RequestActivity extends BaseActivity {


    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.et_connect)
    EditText etConnect;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.tv_address_id)
    TextView tvAddressId;
    @BindView(R.id.et_address_id)
    TextView etAddressId;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_pay_info)
    RelativeLayout rl_pay_info;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.iv_choose)
    ImageView ivChoose;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.play_tip)
    TextView playTip;
    @BindView(R.id.play_tip2)
    TextView playTip2;
    @BindView(R.id.tv_order_fee)
    TextView tv_order_fee;
    @BindView(R.id.ll_agreement)
    LinearLayout ll_agreement;
    @BindView(R.id.rl_address_id)
    RelativeLayout rl_address_id;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    private int provinceid = 0;
    private int cityid = 0;
    private int districtid = 0;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private List<ProvinceModel> provincelist = new ArrayList<>();

    public Handler mTimeHandler;

    public static int TIMESTATUS = 0;
    public static int TIMESTOP = 1;
    public static int TIMEREADY = 0;
    public static int TIMERUNNING = 2;
    public static int SECONDS = 59;
    public boolean send = false;

    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
    private String countryCode = "86";
    private final static int RESULT_COMPLETE = 0;
    private final static int SUBMIT_VERIFICATION_CODE = 1;
    private final static int GET_VERIFICATION_CODE = 2;
    private final static int EVENT_GET_SUPPORTED_COUNTRIES = 4;

    public Runnable timerun = new Runnable() {
        @Override
        public void run() {
            //计时停止，倒数开始
            if (TIMESTATUS != TIMESTOP) {
                TIMESTATUS = TIMERUNNING;
                if (SECONDS >= 0) {
                    getCode.setText("倒计时" + SECONDS);
                    SECONDS--;
                    mTimeHandler.postDelayed(this, 1000);
                } else {
                    getCode.setText("重新获取");
                    TIMESTATUS = TIMEREADY;
                    mTimeHandler.removeCallbacks(this);
                    SECONDS = 59;
                    send = false;
                }


            }
        }
    };

    public EventHandler eventhandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                handler.sendEmptyMessage(RESULT_COMPLETE);
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    handler.sendEmptyMessage(SUBMIT_VERIFICATION_CODE);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    handler.sendEmptyMessage(GET_VERIFICATION_CODE);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Log.e("mobile", data.toString());
                    handler.sendEmptyMessage(EVENT_GET_SUPPORTED_COUNTRIES);
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT_COMPLETE:
                    Log.e("RESULT_COMPLETE", "RESULT_COMPLETE");
//                    Toast.makeText(RequestActivity.this, "验证码回调成功", Toast.LENGTH_SHORT).show();
                    break;
                case SUBMIT_VERIFICATION_CODE:
                    Log.e("SUBMIT", "SUBMIT_VERIFICATION_CODE");
                    Toast.makeText(RequestActivity.this, "提交验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE:
                    Log.e("GET", "GET_VERIFICATION_CODE");
                    Toast.makeText(RequestActivity.this, "获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                case EVENT_GET_SUPPORTED_COUNTRIES:
                    Log.e("COUNTRIES", "EVENT_GET_SUPPORTED_COUNTRIES");
                    break;
            }
        }
    };

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName = "";
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName = "";
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    private String payType = PAY_TYPE_USER_MONEY;
    private static final String PAY_TYPE_BANK_CARD = "pay_type_bank_card";
    private static final String PAY_TYPE_ALI_PAY = "pay_type_ali_pay";
    private static final String PAY_TYPE_WE_CHAT = "pay_type_we_chat";
    private static final String PAY_TYPE_USER_MONEY = "pay_type_user_money";
    private static final int SDK_PAY_FLAG = 1;
    private String order_id;
    private boolean isEdit = true;
    private boolean isAgree = true;
    private String address;
    private int payTypeInt;
    private MultiTypeAdapter payTypeAdapter;

    private UserBean bean;
    private boolean editAble = true;

    //    private String type = "partner";
    @Override
    protected int setViewId() {
        return R.layout.activity_request;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        tv_order_fee.setText(SystemUtil.getSharedString("partner_order_fee") + "元");
        toolbarTitle.setText("VIP申请");
        toolbarRight.setText("编辑");
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editAble) {
                    tvTitle.setText("VIP会员申请");
                    tvEdit.setVisibility(View.GONE);
                    etName.setEnabled(true);
                    etPhone.setEnabled(true);
//                rlCode.setVisibility(View.VISIBLE);
                    etAddressId.setEnabled(true);
                    etAddress.setEnabled(true);
                    tvSend.setText("下一步");
                    rl_pay_info.setVisibility(View.GONE);
                    tvTip.setVisibility(View.VISIBLE);
                    ll_agreement.setVisibility(View.VISIBLE);
                    rl_address_id.setVisibility(View.VISIBLE);
                    etAddress.setText(address);
                    isEdit = true;
                } else {
                    showShortToast("该订单已确认过，无法编辑");
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        String name = getIntent().getStringExtra("name");
        etConnect.setText(name);
    }

    @Override
    protected void init() {
        mTimeHandler = new Handler();
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
    }

    @Override
    protected void loadData() {
        if (LoginUtils.isLogin()) {
            UserDAO.getUserInfo(
                    new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (data != null) {
                                bean = (UserBean) data;
                                UserBean.partnerOrderInfo info = bean.getInfo();
                                if (info != null) {
                                    etConnect.setText(info.getSubmit_info().getRealname());
                                    etPhone.setText(info.getSubmit_info().getMobile());
                                    etAddress.setText(info.getSubmit_info().getAddress());
                                    order_id = info.getId();
                                    editAble = false;
                                    next();
                                }
                            }

                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            showShortToast(data + "");
                        }
                    }

            );
        }
    }


    @OnClick({R.id.back, R.id.tv_send, R.id.et_address_id, R.id.tv_edit, R.id.iv_choose})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.et_address_id:
                View dialogView = showDialog();
                final AddressAlertDialog dialog = new AddressAlertDialog(RequestActivity.this)
                        .builder().setView(dialogView).setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (int i = 0; i < provincelist.size(); i++) {
                                    if (provincelist.get(i).getName().equals(mCurrentProviceName)) {
                                        provinceid = provincelist.get(i).getId();

                                        for (int j = 0; j < provincelist.get(i).getCityList().size(); j++) {
                                            if (provincelist.get(i).getCityList().get(j).getName().equals(mCurrentCityName)) {
                                                cityid = provincelist.get(i).getCityList().get(j).getId();

                                                for (int k = 0; k < provincelist.get(i).getCityList().get(j).getDistrictList().size();
                                                     k++) {
                                                    if (provincelist.get(i).getCityList().get(j).getDistrictList().get(k).getName().equals(mCurrentDistrictName)) {
                                                        districtid = provincelist.get(i).getCityList().get(j).getDistrictList().get(k).getId();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                Log.e("id", "provinceid:" + provinceid + " cityid:" + cityid + " districtid:" + districtid);
                                etAddressId.setText(mCurrentProviceName + " " + mCurrentCityName + " "
                                        + mCurrentDistrictName);

                            }
                        });
                dialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.show();
                break;
            case R.id.tv_send:
                next();
                break;
            case R.id.tv_edit:
                if (editAble) {
                    tvTitle.setText("VIP会员申请");
                    tvEdit.setVisibility(View.GONE);
                    etName.setEnabled(true);
                    etPhone.setEnabled(true);
//                rlCode.setVisibility(View.VISIBLE);
                    etAddressId.setEnabled(true);
                    etAddress.setEnabled(true);
                    tvSend.setText("下一步");
                    rl_pay_info.setVisibility(View.GONE);
                    tvTip.setVisibility(View.VISIBLE);
                    ll_agreement.setVisibility(View.VISIBLE);
                    rl_address_id.setVisibility(View.VISIBLE);
                    etAddress.setText(address);
                    isEdit = true;
                } else {
                    showShortToast("该订单已确认过，无法编辑");
                }
                break;
            case R.id.iv_choose:
                if (isAgree) {
                    ivChoose.setImageResource(R.drawable.icon_un_agree);
                    isAgree = false;
                } else {
                    ivChoose.setImageResource(R.drawable.icon_agree);
                    isAgree = true;
                }
                break;
        }
    }

    private void next() {
        if (isEdit) {
            if (checkMessage()) {
                tvTitle.setText("确认信息");
                tvEdit.setVisibility(View.VISIBLE);
                etConnect.setEnabled(false);
                etPhone.setEnabled(false);
                rlCode.setVisibility(View.GONE);
                etAddressId.setEnabled(false);
                etAddress.setEnabled(false);
                tvSend.setText("确认支付");
                tvTip.setVisibility(View.GONE);

                rl_address_id.setVisibility(View.GONE);
                address = etAddress.getText().toString();
                String allAddress = mCurrentProviceName + mCurrentCityName + mCurrentDistrictName + address;
                etAddress.setText(allAddress);
                ll_agreement.setVisibility(View.GONE);

                rl_pay_info.setVisibility(View.VISIBLE);
                isEdit = false;
            }
        } else {
            showPopupWindow();
        }
    }

    private boolean checkMessage() {
        if (etConnect.getText().length() == 0) {
            showShortToast("请输入联系人姓名");
            return false;
        }
        if (etPhone.getText().length() == 0) {
            showShortToast("请输入手机号");
            return false;
        }
//        if (etCode.getText().length() == 0) {
//            showShortToast("请输入验证码");
//            return false;
//        }
        if (editAble) {
            if (etAddressId.getText().length() == 0) {
                showShortToast("请选择所属区域");
                return false;
            }
        }
        if (etAddress.getText().length() == 0) {
            showShortToast("请输入具体位置信息");
            return false;
        }

        if (!isAgree) {
            showShortToast("请先同意协议");
            return false;
        }

        return true;
    }

    private View showDialog() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.view_dialog_selectzone, null);
        setUpViews(contentView);
        setUpData();
        return contentView;
    }

    private void setUpViews(View view) {
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        setEvent();
        updateCities();
        updateAreas();
    }

    private void setEvent() {
        mViewProvince.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });
        mViewCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });

        mViewDistrict.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (wheel == mViewProvince) {
                    updateCities();
                } else if (wheel == mViewCity) {
                    updateAreas();
                } else if (wheel == mViewDistrict) {
                    mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                    mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
                }
            }
        });
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        try {
            if (mCitisDatasMap.size() > 0) {
                int pCurrent = mViewCity.getCurrentItem();
                mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
                String[] areas = mDistrictDatasMap.get(mCurrentCityName);

                if (areas == null) {
                    areas = new String[]{""};
                }
                mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
                mViewDistrict.setCurrentItem(0);
                mViewDistrict.setCurrentItem(0);
                mCurrentDistrictName = areas[0];
            } else {
                mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, new String[]{""}));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        try {
            if (mCitisDatasMap.size() > 0) {
                int pCurrent = mViewProvince.getCurrentItem();
                mCurrentProviceName = mProvinceDatas[pCurrent];
                String[] cities = mCitisDatasMap.get(mCurrentProviceName);
                if (cities == null) {
                    cities = new String[]{""};
                }
                mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
                mViewCity.setCurrentItem(0);
                updateAreas();
            } else {
                mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, new String[]{""}));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        try {
            provinceList = PositionDao.getInstance().list;
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();

                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
//                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
                provincelist = provinceList;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    CustomPopWindow popupWindow;
    TextView tvPayType;

    private void showPopupWindow() {
        if (popupWindow3 != null) popupWindow3.dissmiss();
        View contentView = LayoutInflater.from(RequestActivity.this).inflate(R.layout.pop_window_pay_type2, null);
        //处理popWindow 显示内容
//        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(tvSend, Gravity.BOTTOM, 0, 0);//显示PopupWindow
    }

//    private void handleLogic(View contentView) {
//        TextView tv_money = (TextView) contentView.findViewById(R.id.tv_money);
//        tv_money.setText(tv_order_fee.getText() == null ? "" : "¥" + tv_order_fee.getText());
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
//                String s = etAddressId.getText().toString();
//                String[] split = s.split(" ");
//                s = "";
//                for (int i = 0; i < split.length; i++) {
//                    s = s + split[i];
//                }
//                if (order_id == null) {
//                    UserDAO.applyToPartner(etPhone.getText().toString(), etConnect.getText().toString(),
//                            s + etAddress.getText().toString(), provinceid + "", cityid + "", districtid + "",
//                            new BaseCallBack() {
//                                @Override
//                                public void success(Object data) {
//                                    try {
//                                        JSONObject content = new JSONObject(data.toString());
//                                        if (content.has("order_id")) {
//                                            order_id = content.getString("order_id");
//                                            switch (payType) {
//                                                case PAY_TYPE_USER_MONEY://弹出输入密码弹窗，调用余额支付
//                                                    UserDAO.getUserInfo(new BaseCallBack() {
//                                                        @Override
//                                                        public void success(Object data) {
//                                                            if (data != null) {
//                                                                UserBean bean = (UserBean) data;
//                                                                String user_money_pwd = bean.getUser_money_pwd();
//                                                                if (user_money_pwd != null && !user_money_pwd.equals("")) {
//                                                                    //有支付密码
//                                                                    showPopupWindowPassWord();
//                                                                } else {
//                                                                    showShortToast("请先设置支付密码");
//                                                                }
//                                                            }
//                                                        }
//
//                                                        @Override
//                                                        public void failed(int errorCode, Object data) {
//                                                            showShortToast(data + "");
//                                                        }
//                                                    });
//                                                    break;
//                                                case PAY_TYPE_BANK_CARD://调用银联支付的接口
//                                                    UserDAO.UnionpayOrder(order_id, type, new BaseCallBack() {
//                                                        @Override
//                                                        public void success(Object data) {
//                                                            String tn = (String) data;
//
//                                                            if (UPPayAssistEx.checkInstalled(getApplicationContext())) {
////                                Log.e(TestCart.LOG,"流水号："+tn);
//                                                                String serverMode = "00";
//                                                                UPPayAssistEx.startPay(RequestActivity.this, null, null, tn, serverMode);
//                                                            } else {
//                                                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RequestActivity.this);
//                                                                builder.setTitle("提示");
//                                                                builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
//                                                                builder.setNegativeButton("确定",
//                                                                        new DialogInterface.OnClickListener() {
//                                                                            @Override
//                                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                                UPPayAssistEx.installUPPayPlugin(RequestActivity.this);
//                                                                                dialog.dismiss();
//                                                                            }
//                                                                        });
//
//                                                                builder.setPositiveButton("取消",
//                                                                        new DialogInterface.OnClickListener() {
//
//                                                                            @Override
//                                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                                dialog.dismiss();
//                                                                            }
//                                                                        });
//                                                                builder.create().show();
//                                                            }
//
//                                                        }
//
//                                                        @Override
//                                                        public void failed(int errorCode, Object data) {
//                                                            showShortToast(data+"");
//                                                        }
//                                                    });
//                                                    break;
//                                                case PAY_TYPE_ALI_PAY://调用支付宝支付的接口
//
//                                                    UserDAO.PayOrder("alipay", order_id, type, new BaseCallBack() {
//                                                        @Override
//                                                        public void success(Object data) {
//                                                            showShortToast("调用支付宝支付的接口");
//                                                            String orderinfo = (String) data;
//                                                            aliPay(orderinfo);
//                                                        }
//
//                                                        @Override
//                                                        public void failed(int errorCode, Object data) {
//                                                            showShortToast(data + "");
//                                                        }
//                                                    });
//                                                    break;
//                                                case PAY_TYPE_WE_CHAT://调用微信支付的接口
//                                                    UserDAO.payOrderWeChat(order_id, type, new BaseCallBack() {
//                                                        @Override
//                                                        public void success(Object data) {
//                                                            try {
//                                                                JSONObject mData  = new JSONObject((data.toString()));
//                                                                JSONObject content = mData.getJSONObject("data");
//                                                                String appId = content.getString("appid");
//                                                                IWXAPI api = WXAPIFactory.createWXAPI(RequestActivity.this, null);
//                                                                api.registerApp(appId);
//                                                                PayReq request = new PayReq();
//                                                                request.appId = appId;
//                                                                request.partnerId = content.getString("partnerid");
//                                                                request.prepayId =content.getString("prepayid");
//                                                                request.packageValue = content.getString("package");
//                                                                request.nonceStr = content.getString("noncestr");
//                                                                request.timeStamp = content.getString("timestamp");
//                                                                request.sign = content.getString("sign");
//                                                                api.sendReq(request);
//                                                            } catch (JSONException e) {
//                                                                e.printStackTrace();
//                                                            }
////                                                            showShortToast("调用微信支付的接口");
////                                                            try {
////                                                                JSONObject content = new JSONObject(data.toString());
////                                                                if (content.has("pay_id")) {
////                                                                    String pay_id = content.getString("pay_id");
////                                                                    RequestMsg msg = new RequestMsg();
////                                                                    msg.setTokenId(pay_id);
////
////                                                                    //微信
////                                                                    msg.setTradeType(MainApplication.WX_APP_TYPE);
////                                                                    msg.setAppId("wx8c04c8ef6226fce8");//wxd3a1cdf74d0c41b3
////                                                                    PayPlugin.unifiedAppPay(RequestActivity.this, msg);
////                                                                }
////                                                            } catch (Exception e) {
////                                                                e.printStackTrace();
////                                                            }
//
//                                                        }
//
//                                                        @Override
//                                                        public void failed(int errorCode, Object data) {
//                                                            showShortToast(data + "");
//                                                        }
//                                                    });
//                                                    break;
//                                            }
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void failed(int errorCode, Object data) {
//                                    showShortToast(data + "");
//                                }
//                            });
//
//                } else {
//                    switch (payType) {
//                        case PAY_TYPE_USER_MONEY://弹出输入密码弹窗，调用余额支付
//                            showPopupWindowPassWord();
//                            break;
//                        case PAY_TYPE_BANK_CARD://调用银联支付的接口
//                            UserDAO.UnionpayOrder(order_id, type, new BaseCallBack() {
//                                @Override
//                                public void success(Object data) {
//                                    String tn = (String) data;
//
//                                    if (UPPayAssistEx.checkInstalled(getApplicationContext())) {
////                                Log.e(TestCart.LOG,"流水号："+tn);
//                                        String serverMode = "00";
//                                        UPPayAssistEx.startPay(RequestActivity.this, null, null, tn, serverMode);
//                                    } else {
//                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RequestActivity.this);
//                                        builder.setTitle("提示");
//                                        builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
//                                        builder.setNegativeButton("确定",
//                                                new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        UPPayAssistEx.installUPPayPlugin(RequestActivity.this);
//                                                        dialog.dismiss();
//                                                    }
//                                                });
//
//                                        builder.setPositiveButton("取消",
//                                                new DialogInterface.OnClickListener() {
//
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                    }
//                                                });
//                                        builder.create().show();
//                                    }
//
//                                }
//
//                                @Override
//                                public void failed(int errorCode, Object data) {
//                                    showShortToast(data+"");
//                                }
//                            });
//                            break;
//                        case PAY_TYPE_ALI_PAY://调用支付宝支付的接口
//
//                            UserDAO.PayOrder("alipay", order_id, type, new BaseCallBack() {
//                                @Override
//                                public void success(Object data) {
//                                    showShortToast("调用支付宝支付的接口");
//                                    String orderinfo = (String) data;
//                                    aliPay(orderinfo);
//                                }
//
//                                @Override
//                                public void failed(int errorCode, Object data) {
//                                    showShortToast(data + "");
//                                }
//                            });
//                            break;
//                        case PAY_TYPE_WE_CHAT://调用微信支付的接口
//                            UserDAO.payOrderWeChat(order_id, type, new BaseCallBack() {
//                                @Override
//                                public void success(Object data) {
//                                    try {
//                                        JSONObject mData  = new JSONObject((data.toString()));
//                                        JSONObject content = mData.getJSONObject("data");
//                                        String appId = content.getString("appid");
//                                        IWXAPI api = WXAPIFactory.createWXAPI(RequestActivity.this, null);
//                                        api.registerApp(appId);
//                                        PayReq request = new PayReq();
//                                        request.appId = appId;
//                                        request.partnerId = content.getString("partnerid");
//                                        request.prepayId =content.getString("prepayid");
//                                        request.packageValue = content.getString("package");
//                                        request.nonceStr = content.getString("noncestr");
//                                        request.timeStamp = content.getString("timestamp");
//                                        request.sign = content.getString("sign");
//                                        api.sendReq(request);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
////                                    showShortToast("调用微信支付的接口");
////                                    try {
////                                        JSONObject content = new JSONObject(data.toString());
////                                        if (content.has("pay_id")) {
////                                            String pay_id = content.getString("pay_id");
////                                            RequestMsg msg = new RequestMsg();
////                                            msg.setTokenId(pay_id);
////
////                                            //微信
////                                            msg.setTradeType(MainApplication.WX_APP_TYPE);
////                                            msg.setAppId("wx8c04c8ef6226fce8");//wxd3a1cdf74d0c41b3
////                                            PayPlugin.unifiedAppPay(RequestActivity.this, msg);
////                                        }
////                                    } catch (Exception e) {
////                                        e.printStackTrace();
////                                    }
//
//                                }
//
//                                @Override
//                                public void failed(int errorCode, Object data) {
//                                    showShortToast(data + "");
//                                }
//                            });
//                            break;
//                    }
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


    private List<Visitable> models = new ArrayList<>();
    CustomPopWindow popupWindow2;
    IconFontTextView ifTvBack;


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
        if (popupWindow != null) {
            popupWindow.dissmiss();
        }
        if (popupWindow2 != null) {
            popupWindow2.dissmiss();
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_window_password, null);
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
                .showAtLocation(tvSend, Gravity.BOTTOM, 0, 0);//显示PopupWindow

    }

    private void handlePassword(View contentView) {
        passWork1 = (TextView) contentView.findViewById(R.id.tv_password1);
        passWork2 = (TextView) contentView.findViewById(R.id.tv_password2);
        passWork3 = (TextView) contentView.findViewById(R.id.tv_password3);
        passWork4 = (TextView) contentView.findViewById(R.id.tv_password4);
        passWork5 = (TextView) contentView.findViewById(R.id.tv_password5);
        passWork6 = (TextView) contentView.findViewById(R.id.tv_password6);
        contentView.findViewById(R.id.tv_find_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestActivity.this, SetPayPasswordActivity.class);
                RequestActivity.this.startActivity(intent);
            }
        });
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
                payTypeInt = event.position;
                switch (payTypeInt) {
                    case 0:
                        payType = PAY_TYPE_USER_MONEY;
                        break;
                    case 1:
                        payType = PAY_TYPE_ALI_PAY;
                        break;
                    case 2:
                        payType = PAY_TYPE_WE_CHAT;
                        break;
                    case 3:
                        payType = PAY_TYPE_BANK_CARD;
                        break;
                }
                models = new ArrayList<>();
                PayTypeBean bean0 = new PayTypeBean();
                bean0.setName("余额支付");
                bean0.setType(PAY_TYPE_USER_MONEY);
                if (payTypeInt == 0) {
                    bean0.setChoose(true);
                }
                models.add(bean0);

                PayTypeBean bean1 = new PayTypeBean();
                bean1.setName("支付宝支付");
                bean1.setType(PAY_TYPE_ALI_PAY);
                if (payTypeInt == 1) {
                    bean1.setChoose(true);
                }
                models.add(bean1);

                PayTypeBean bean2 = new PayTypeBean();
                bean2.setName("微信支付");
                bean2.setType(PAY_TYPE_WE_CHAT);
                if (payTypeInt == 2) {
                    bean2.setChoose(true);
                }
                models.add(bean2);

                PayTypeBean bean3 = new PayTypeBean();
                bean3.setName("银行卡支付");
                bean3.setType(PAY_TYPE_BANK_CARD);
                if (payTypeInt == 3) {
                    bean3.setChoose(true);
                }
                models.add(bean3);
                payTypeAdapter.refreshData(models);
//                popupWindow.dissmiss();
//                showPopupWindow();
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


                String payPassword = "";
                for (int i = 0; i < 6; i++) {
                    payPassword = payPassword + passwordList.get(i);
                }

//                UserDAO.BalancePay(order_id, type, payPassword, new BaseCallBack() {
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

//    /*微信支付*/
//    private void weChatPay(final WeChatPayInfo weChatPayInfo) {
//        final String appId = weChatPayInfo.getAppid();
////        Log.e("appId", appId);
////        Log.e("partnerId", weChatPayInfo.getPartnerid());
////        Log.e("prepayId", weChatPayInfo.getPrepayid());
////        Log.e("package", weChatPayInfo.getPackageX());
////        Log.e("nonceStr", weChatPayInfo.getNoncestr());
////        Log.e("timeStamp", weChatPayInfo.getTimestamp() + "");
////        Log.e("sign", weChatPayInfo.getSign());
//        IWXAPI api = WXAPIFactory.createWXAPI(this, null);
//        api.registerApp(appId);
//        PayReq request = new PayReq();
//        request.appId = appId;
//        request.partnerId = weChatPayInfo.getPartnerid();
//        request.prepayId = weChatPayInfo.getPrepayid();
//        request.packageValue = weChatPayInfo.getPackageX();
//        request.nonceStr = weChatPayInfo.getNoncestr();
//        request.timeStamp = weChatPayInfo.getTimestamp() + "";
//        request.sign = weChatPayInfo.getSign();
//        api.sendReq(request);
//    }


    /*支付宝支付*/
    private void aliPay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
//                Log.e("pay", orderInfo);
                PayTask alipay = new PayTask(RequestActivity.this);
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
                        Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    public boolean isActivityRunning() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = am.getRunningTasks(1);
        ComponentName name = task.get(0).topActivity;
        Log.e("pgname", name.getClassName());
        if (name.getClassName().equals("com.yidu.sevensecondmall.Activity.Distribution.RequestActivity")) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventhandler);
        super.onDestroy();
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

    private void paySuccess() {
        Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RequestActivity.this, DistributionActivity.class);
        startActivity(intent);
        RequestActivity.this.finish();
    }
}
