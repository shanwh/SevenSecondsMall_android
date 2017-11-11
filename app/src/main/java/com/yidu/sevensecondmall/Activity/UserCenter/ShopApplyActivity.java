package com.yidu.sevensecondmall.Activity.UserCenter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.CityModel;
import com.yidu.sevensecondmall.bean.User.DistrictModel;
import com.yidu.sevensecondmall.bean.User.ProvinceModel;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.AddressAlertDialog;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;
import com.yidu.sevensecondmall.views.widget.widget.OnWheelChangedListener;
import com.yidu.sevensecondmall.views.widget.widget.WheelView;
import com.yidu.sevensecondmall.views.widget.widget.adapters.ArrayWheelAdapter;

import java.io.File;
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
 * Created by Administrator on 2017/6/30 0030.
 */
public class ShopApplyActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, View.OnClickListener {
    private static final String TAG = "ShopApplyActivity";

    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    //    @BindView(R.id.tv_code)
//    TextView tvCode;
//    @BindView(R.id.et_code)
//    EditText etCode;
//    @BindView(R.id.get_code)
//    TextView getCode;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.tv_address_id)
    TextView tvAddressId;
    @BindView(R.id.et_address_id)
    TextView etAddressId;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.iv_choose)
    ImageView ivChoose;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    //    @BindView(R.id.rl_code)
//    RelativeLayout rlCode;

    @BindView(R.id.ll_agreement)
    LinearLayout ll_agreement;

    public Handler mHandler;
    public static int TIMESTATUS = 0;
    public static int TIMESTOP = 1;
    public static int TIMEREADY = 0;
    public static int TIMERUNNING = 2;
    public static int SECONDS = 59;
    public boolean send = false;

    // 默认使用中国区号
    private final String DEFAULT_COUNTRY_ID = "42";
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.img_other)
    ImageView img_other;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.v3)
    View v3;
    @BindView(R.id.v4)
    View v4;
    @BindView(R.id.tv_id_type)
    TextView tvIdType;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.rl_address_id)
    RelativeLayout rlAddressId;
    @BindView(R.id.tv_company_name)
    EditText tvCompanyName;
    @BindView(R.id.tv_company_per_name)
    EditText tvCompanyPerName;
    @BindView(R.id.tv_company_bank)
    EditText tvCompanyBank;
    @BindView(R.id.tv_company_register_num)
    EditText tvCompanyRegisterNum;
    @BindView(R.id.ll_pub)
    LinearLayout llPub;
    @BindView(R.id.img_id_card)
    ImageView imgIdCard;
    @BindView(R.id.ll_id_card)
    LinearLayout llIdCard;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;


    private String countryCode = "86";
    private final static int RESULT_COMPLETE = 0;
    private final static int SUBMIT_VERIFICATION_CODE = 1;
    private final static int GET_VERIFICATION_CODE = 2;
    private final static int EVENT_GET_SUPPORTED_COUNTRIES = 4;

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
//                    Toast.makeText(ShopApplyActivity.this, "验证码回调成功", Toast.LENGTH_SHORT).show();
                    break;
                case SUBMIT_VERIFICATION_CODE:
                    Log.e("SUBMIT", "SUBMIT_VERIFICATION_CODE");
//                    Toast.makeText(ShopApplyActivity.this, "提交验证码", Toast.LENGTH_SHORT).show();
                    break;
                case GET_VERIFICATION_CODE:
                    Log.e("GET", "GET_VERIFICATION_CODE");
                    Toast.makeText(ShopApplyActivity.this, "获取验证码", Toast.LENGTH_SHORT).show();
                    break;
                case EVENT_GET_SUPPORTED_COUNTRIES:
                    Log.e("COUNTRIES", "EVENT_GET_SUPPORTED_COUNTRIES");
                    break;
            }
        }
    };

    private int provinceid = 0;
    private int cityid = 0;
    private int districtid = 0;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private List<ProvinceModel> provincelist = new ArrayList<>();

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
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    private int type = 0;

    private String addressId;

    private boolean isEdit = true;
    private boolean isAgree = false;
    private String address;
    private File file;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;
    private CropOptions cropOptions;
    private Uri imageUri;
    private ArrayList<File> filelist = new ArrayList<>();
    private File otherPic;
    private File idCardPic;
    private ArrayList<CustomPopWindow> popList = new ArrayList<>();

    private String idType;

    private ProgressDialog dialog;
    private int clickIndex;
    private String company_name, company_corporate, company_open_bank, company_tax_id;
//    private File id_card_image;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_apply;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        String name = getIntent().getStringExtra("name");
        etName.setText(name);
    }

    @Override
    protected void init() {
        mHandler = new Handler();
        SMSSDK.registerEventHandler(eventhandler);
        SMSSDK.getSupportedCountries();
        initPath();
        dialog = new ProgressDialog(this);
        dialog.setMessage("请等待");
        toolbarTitle.setText("商家申请");
        toolbarRight.setText("编辑");
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditStatus(true);
                llMsg.setVisibility(View.GONE);
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.et_address_id, R.id.tv_next, R.id.tv_edit, R.id.iv_choose,
            R.id.img, R.id.tv_agreement, R.id.tv_id_type, R.id.img_other, R.id.img_id_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_id_type:
                showTypePopupWindow();
                break;
            case R.id.tv_agreement:
                Intent intent = new Intent(ShopApplyActivity.this, ShopApplyMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
//            case R.id.get_code:
//                if (etPhone.getText().length() != 0) {
//                    if (TIMESTATUS == TIMEREADY || TIMESTATUS == TIMESTOP) {
//                        mHandler.postDelayed(timerun, 1000);
//                        if (!send) {
//                            SMSSDK.getVerificationCode(countryCode, etPhone.getText().toString());
//                            send = true;
//                        }
//                    } else {
//                        Toast.makeText(ShopApplyActivity.this, "请等待短信", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    showShortToast("请输入手机号码");
//                }
//                break;
            case R.id.et_address_id:
                View dialogView = showDialog();
                final AddressAlertDialog dialog = new AddressAlertDialog(ShopApplyActivity.this)
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
            case R.id.tv_next:
                if (isEdit) {
                    if (llInfo.getVisibility() == View.VISIBLE && llMsg.getVisibility() == View.GONE) {
                        if (checkInfo()) {
                            llInfo.setVisibility(View.GONE);
                            llMsg.setVisibility(View.VISIBLE);
                        }
                    } else if (llInfo.getVisibility() == View.GONE && llMsg.getVisibility() == View.VISIBLE) {
                        if (checkFile()) {
                            llInfo.setVisibility(View.VISIBLE);
                            setEditStatus(false);
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.GONE);
                            v4.setVisibility(View.GONE);
                        }
                    }
                } else {
                    this.dialog.show();
                    tvNext.setClickable(false);
                    company_name = tvCompanyName.getText().toString();
                    company_corporate = tvCompanyPerName.getText().toString();
                    company_open_bank = tvCompanyBank.getText().toString();
                    company_tax_id = tvCompanyRegisterNum.getText().toString();
                    UserDAO.applyToBusiness(etPhone.getText().toString(), etName.getText().toString(),
                            mCurrentProviceName + mCurrentCityName + mCurrentDistrictName +
                                    etAddress.getText().toString(), provinceid + "", cityid + "", districtid + "",
                            etShopName.getText().toString(), etType.getText().toString(),
                            filelist.get(0), etId.getText().toString(), idType,
                            company_name, company_corporate, company_open_bank, company_tax_id, idCardPic,
                            new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    ShopApplyActivity.this.dialog.dismiss();
                                    showPopupWindow();
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    tvNext.setClickable(true);
                                    ShopApplyActivity.this.dialog.dismiss();
                                    showShortToast(data + "");
                                }
                            });

                }
                break;
            case R.id.tv_edit:
                setEditStatus(true);
                llMsg.setVisibility(View.GONE);
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.VISIBLE);
                v4.setVisibility(View.VISIBLE);
//                tvTitle.setText("商家申请");
//                tvEdit.setVisibility(View.GONE);
//                etName.setEnabled(true);
//                etPhone.setEnabled(true);
////                rlCode.setVisibility(View.VISIBLE);
//                rl_address_id.setVisibility(View.VISIBLE);
//                etShopName.setEnabled(true);
//                etAddressId.setEnabled(true);
//                etAddress.setEnabled(true);
//                ll_agreement.setVisibility(View.VISIBLE);
//                tvNext.setText("下一步");
//                etAddress.setText(address);
//                isEdit = true;
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
            case R.id.img:
                showImgPopupWindow();
                clickIndex = 0;
                break;
            case R.id.img_other:
                showImgPopupWindow();
                clickIndex = 1;
                break;
            case R.id.img_id_card:
                showImgPopupWindow();
                clickIndex = 2;
                break;
        }
    }

    CustomPopWindow typePopWindow;

    private void showTypePopupWindow() {
        View contentView = LayoutInflater.from(ShopApplyActivity.this).inflate(R.layout.pop_window_id_type, null);
        //处理popWindow 显示内容
        handleType(contentView);
        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, ShopApplyActivity.this.getResources().getDisplayMetrics());

        typePopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(v, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(img, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleType(View contentView) {
        contentView.findViewById(R.id.tv_pub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idType = "0";
                tvIdType.setText("对公账户");
                typePopWindow.dissmiss();
                llPub.setVisibility(View.VISIBLE);
                llIdCard.setVisibility(View.GONE);

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });

        contentView.findViewById(R.id.tv_per).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idType = "1";
                tvIdType.setText("对私账户");
                typePopWindow.dissmiss();
                llPub.setVisibility(View.GONE);
                llIdCard.setVisibility(View.VISIBLE);
                sv.fullScroll(ScrollView.FOCUS_DOWN);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    CustomPopWindow popWindow;

    private void showImgPopupWindow() {
        View contentView = LayoutInflater.from(ShopApplyActivity.this).inflate(R.layout.pop_window_take_photo, null);
        //处理popWindow 显示内容
        handleImg(contentView);
//        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, ShopApplyActivity.this.getResources().getDisplayMetrics());

        popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(img, Gravity.BOTTOM, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleImg(View contentView) {
        contentView.findViewById(R.id.if_tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dissmiss();
            }
        });
        contentView.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "/Ding/" + System.currentTimeMillis() + ".jpeg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                imageUri = Uri.fromFile(file);
                getPhoto().onEnableCompress(compressConfig, true);
                getPhoto().onPickFromCapture(imageUri);
                popWindow.dissmiss();
            }
        });
        contentView.findViewById(R.id.tv_select_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto().onEnableCompress(compressConfig, true);
                getPhoto().onPickFromDocuments();
                popWindow.dissmiss();
            }
        });
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ShopApplyActivity.this).inflate(R.layout.pop_window_commend_success, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, ShopApplyActivity.this.getResources().getDisplayMetrics());

        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(v, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ShopApplyActivity.this.finish();
                    }
                })
                .create()//创建PopupWindow
                .showAtLocation(tvNext, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        contentView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dissmiss();
            }
        });
    }


    private void setEditStatus(boolean b) {
        tvTitle.setText(b ? "商家申请" : "确认信息");
        tvEdit.setVisibility(b ? View.GONE : View.VISIBLE);
        etId.setEnabled(b);
        img.setClickable(b);
        img_other.setClickable(b);
        tvIdType.setClickable(b);
        etName.setEnabled(b);
        etPhone.setEnabled(b);
        etShopName.setEnabled(b);
        etAddressId.setEnabled(b);
        etAddress.setEnabled(b);
        etType.setEnabled(b);
        rlAddressId.setVisibility(b ? View.VISIBLE : View.GONE);
        address = etAddress.getText().toString();
        String allAddress = mCurrentProviceName + mCurrentCityName + mCurrentDistrictName + address;
        etAddress.setText(b ? address : allAddress);
        ll_agreement.setVisibility(b ? View.VISIBLE : View.GONE);
        tvNext.setText(b ? "下一步" : "确认并提交");
        tvCompanyName.setClickable(b);
        tvCompanyPerName.setClickable(b);
        tvCompanyBank.setClickable(b);
        tvCompanyRegisterNum.setClickable(b);
        imgIdCard.setClickable(b);
        isEdit = b;
    }

    private boolean checkInfo() {
        if (etName.getText().toString().trim().length() == 0) {
            showShortToast("请输入真实姓名");
            return false;
        }
        if (etPhone.getText().toString().trim().length() == 0) {
            showShortToast("请输入手机号码");
            return false;
        }
//        if (etCode.getText().toString().trim().length() == 0) {
//            showShortToast("请输入验证码");
//            return false;
//        }
        if (etShopName.getText().toString().trim().length() == 0) {
            showShortToast("请输入公司名称");
            return false;
        }
        if (etType.getText().toString().trim().length() == 0) {
            showShortToast("请输入店铺主营商品");
            return false;
        }
        if (etAddressId.getText().toString().trim().length() == 0) {
            showShortToast("请选择省市区");
            return false;
        }
        if (etAddress.getText().toString().trim().length() == 0) {
            showShortToast("请输入详细地址");
            return false;
        }
        if (!isAgree) {
            showShortToast("请先同意协议");
            return false;
        }
        return true;
    }

    private boolean checkFile() {
        if (filelist == null || filelist.size() == 0) {
            showShortToast("请选择要上传的营业执照");
            return false;
        }
        if (etId.getText().toString().trim().length() == 0) {
            showShortToast("请输入收款账户信息");
            return false;
        }
        if (idType == null) {
            showShortToast("请选择收款账户的类型");
            return false;
        }
        if ("1".equals(idType)) {
            if (idCardPic == null) {
                showShortToast("请选择要上传的身份证照片");
                return false;
            }
        } else if ("0".equals(idType)) {
            if (tvCompanyName.getText().toString().trim().length() == 0) {
                showShortToast("请输入收款公司名称");
                return false;
            }
            if (tvCompanyPerName.getText().toString().trim().length() == 0) {
                showShortToast("请输入收款公司法人姓名");
                return false;
            }
            if (tvCompanyBank.getText().toString().trim().length() == 0) {
                showShortToast("请输入收款公司银行开户行");
                return false;
            }
            if (tvCompanyRegisterNum.getText().toString().trim().length() == 0) {
                showShortToast("请输入收款公司税务登记号");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventhandler);
        super.onDestroy();
    }

//    public Runnable timerun = new Runnable() {
//        @Override
//        public void run() {
//            //计时停止，倒数开始
//            if (TIMESTATUS != TIMESTOP) {
//                TIMESTATUS = TIMERUNNING;
//                if (SECONDS >= 0) {
//                    getCode.setText("倒计时" + SECONDS);
//                    SECONDS--;
//                    mHandler.postDelayed(this, 1000);
//                } else {
//                    getCode.setText("重新获取");
//                    TIMESTATUS = TIMEREADY;
//                    mHandler.removeCallbacks(this);
//                    SECONDS = 59;
//                    send = false;
//                }
//
//
//            }
//        }
//    };

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

            int pCurrent = mViewCity.getCurrentItem();
            mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
            String[] areas = mDistrictDatasMap.get(mCurrentCityName);

            if (areas == null) {
                areas = new String[]{""};
            }
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
//        mViewDistrict.setCurrentItem(0);
            mViewDistrict.setCurrentItem(0);
            mCurrentDistrictName = areas[0];

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        try {
            int pCurrent = mViewProvince.getCurrentItem();
            mCurrentProviceName = mProvinceDatas[pCurrent];
            String[] cities = mCitisDatasMap.get(mCurrentProviceName);
            if (cities == null) {
                cities = new String[]{""};
            }
            mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
            mViewCity.setCurrentItem(0);
            updateAreas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        try {
            provinceList = PositionDao.getInstance().list;
            Log.d(TAG, "initProvinceDatas: " + provinceList.size());
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();

                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            } else {
                showShortToast("城市列表为空");
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

    private void initPath() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
//        File file = new File(Environment.getExternalStorageDirectory(), "/Ding/" + System.currentTimeMillis() + ".jpeg");
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        imageUri = Uri.fromFile(file);
        compressConfig = new CompressConfig.Builder().setMaxSize(1000 * 1024).create();
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setOutputX(500).setOutputY(500).create();
    }


    public TakePhoto getPhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void handlerSaveInstanceState(Bundle savedInstanceState) {
        getPhoto().onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getPhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.e(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        try {
//            Bitmap bitmap = Bimp.revitionImageSize(result.getImage().getCompressPath());

//            Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());

            if (clickIndex == 0) {
                Glide.with(ShopApplyActivity.this)
                        .load(result.getImage().getCompressPath())
                        .asBitmap()
                        .fitCenter()
                        .into(img);
                File file = new File(result.getImage().getCompressPath());
                if (filelist.size() == 0) {
                    filelist.add(file);
                } else {
                    filelist.set(0, file);
                }
            } else if (clickIndex == 1) {
                Glide.with(ShopApplyActivity.this)
                        .load(result.getImage().getCompressPath())
                        .asBitmap()
                        .fitCenter()
                        .into(img_other);
                otherPic = new File(result.getImage().getCompressPath());
            } else {
                Glide.with(ShopApplyActivity.this)
                        .load(result.getImage().getCompressPath())
                        .asBitmap()
                        .fitCenter()
                        .into(imgIdCard);
                idCardPic = new File(result.getImage().getCompressPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {
        showShortToast("拍照失败，请注意权限");
    }


    @Override
    public void takeCancel() {

    }


}
