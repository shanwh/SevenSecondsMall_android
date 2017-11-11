package com.yidu.sevensecondmall.Activity.UserCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.PaymentDao;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.NumberEvent;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.holder.BankCardListHolder;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_card_tip)
    TextView tvCardTip;
    @BindView(R.id.tv_withdraw_tip)
    TextView tvWithdrawTip;
    @BindView(R.id.et_count)
    EditText etCount;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.tv_all_count)
    TextView tvAllCount;
    @BindView(R.id.tv_can_count)
    TextView tvCanCount;
    @BindView(R.id.rl_count)
    RelativeLayout rlCount;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.if_tv)
    IconFontTextView ifTv;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.tv_finish_card)
    TextView tvFinishCard;
    @BindView(R.id.rl_finish_card)
    LinearLayout rlFinishCard;
    @BindView(R.id.tv_finish_count)
    TextView tvFinishCount;
    @BindView(R.id.rl_finish_count)
    RelativeLayout rlFinishCount;
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.tv_count_tip)
    TextView tvCountTip;
    @BindView(R.id.tv_withdraw_all)
    TextView tvWithdrawAll;

    @BindView(R.id.linearlayout)
    LinearLayout linearLayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private String card_id = "";
    private ArrayList<Visitable> list = new ArrayList<>();
    private boolean hasBankCard;
    private String mNumber;
    private String mBankName;

    private TextView passWork1;
    private TextView passWork2;
    private TextView passWork3;
    private TextView passWork4;
    private TextView passWork5;
    private TextView passWork6;
    private TextView tvFindPassword;
    private RecyclerView rvPassword;
    private List<Visitable> passwordNumbers = new ArrayList<>();
    private PopupWindow popupWindow3;
    private IconFontTextView ifTvBack;
    private ArrayList<String> passwordList = new ArrayList<>();
    private String user_money = "0";

    @Override
    protected int setViewId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("余额提现");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        // 获取编辑框焦点
        etCount.setFocusable(true);

        //打开软键盘
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void loadData() {
        loadBankCard();
        UserDAO.getUserInfo(new BaseCallBack() {
            @Override
            public void success(Object data) {
                if (data != null) {
                    UserBean bean = (UserBean) data;
                    user_money = bean.getVaid().getValid_balance();
                    if (user_money.equals("") || user_money == null) {
                        user_money = "0";
                    }
                    Log.e("user_money", user_money + "");
                    tvAllCount.setText("总金额：¥ " + bean.getUser_money());
                    tvCanCount.setText("可提现金额：¥ " + user_money);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast("" + data);
            }
        });
    }


    @OnClick({R.id.back, R.id.rl_card, R.id.tv_next, R.id.rl_count, R.id.tv_withdraw_all})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_card:
                if (hasBankCard) {
                    intent = new Intent(this, BankCardActivity.class);
                    SystemUtil.setSharedInt("BankCardListFrom", BankCardListHolder.FROM_WITHDRAW);
                    startActivity(intent);
                } else {
                    UserDAO.getUserInfo(new BaseCallBack() {
                        @Override
                        public void success(Object data) {
                            if (data != null) {
                                UserBean bean = (UserBean) data;
                                String is_authentication = bean.getIs_authentication();
                                if (is_authentication.equals("0")) {
                                    showPopupWindow();
                                } else {
                                    Intent intent = new Intent(WithdrawActivity.this, AddBankCardActivity.class);
                                    SystemUtil.setSharedInt("BankCardListFrom", BankCardListHolder.FROM_WITHDRAW);
                                    intent.putExtra("name", bean.getRealname());
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void failed(int errorCode, Object data) {
                            showShortToast(data + "");
                        }
                    });

                }
                break;
            case R.id.tv_next:
                if (hasBankCard) {
                    if (checkMessage()) {
                        getPassWord();
                    }
                } else {
                    showShortToast("您还未绑定银行卡");
                }
                break;
            case R.id.tv_withdraw_all:
                etCount.setText(user_money + "");
                break;
        }
    }


    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(WithdrawActivity.this).inflate(R.layout.pop_window_indentification, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(tvNext, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {

        contentView.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithdrawActivity.this, IndentActivity.class);
                WithdrawActivity.this.startActivity(intent);
                popupWindow.dissmiss();
            }
        });

    }

    private boolean checkMessage() {
        if (user_money.equals("0")) {
            showShortToast("没有可提现的金额");
            return false;
        }
        if (etCount.getText().toString().trim().length() == 0) {
            showShortToast("请输入金额");
            return false;
        }
        double input = Double.parseDouble(etCount.getText().toString());
        double userMoney = Double.parseDouble(user_money + "");
        if (input > userMoney) {
            showShortToast("超过最大可提现金额");
            return false;
        }
        return true;
    }

    private void loadBankCard() {
        PaymentDao.getBankcardList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                list = (ArrayList<Visitable>) data;
                if (list.size() > 0) {
                    BankCardBean bean = (BankCardBean) list.get(0);
                    String bankname = bean.getBankname();
                    String cardnumber = bean.getCardnumber();
                    String result = getNumber(cardnumber);
                    mNumber = result;
                    mBankName = bankname;
                    tvCard.setText(bankname + "(" + result + ")");
                    card_id = bean.getId();
                    hasBankCard = true;
                } else {
                    hasBankCard = false;
                    tvCard.setText("您还未绑定银行卡");
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

    private String getNumber(String cardnumber) {
        String[] split = cardnumber.split("");
        int length = split.length;
        String result = "";
        for (int i = 0; i < 4; i++) {
            String s = split[length - 4 + i];
            result = result + s;
        }
        return result;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String bankname = null;
        String cardnumber = null;
        if (intent.getStringExtra("Id") != null) card_id = intent.getStringExtra("Id");
        if (intent.getStringExtra("Bankname") != null) {
            bankname = intent.getStringExtra("Bankname");
        }
        if (intent.getStringExtra("Cardnumber") != null) {
            cardnumber = intent.getStringExtra("Cardnumber");
        }
        if (bankname != null && cardnumber != null) {
            String result = getNumber(cardnumber);
            String strCardName = bankname + "(" + result + ")";
            mNumber = result;
            mBankName = bankname;
            tvCard.setText(strCardName);
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        if (SystemUtil.getSharedBoolean("hasNewBankCard", false)) {
            loadBankCard();
        }
        super.onResume();
    }

    private void getPassWord() {
        showPopupWindowPassWord();
    }

    private void showPopupWindowPassWord() {
        passwordList.clear();
        backgroundAlpha(getActivity(), 0.5f);//0.0-1.0

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_window_password, null);

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
                popupWindow3.dismiss();
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

//        rv.addItemDecoration(new SpacesItemDecoration(1));

        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPassword.setLayoutManager(linearLayoutManager);
//                adapter = new DisWithdrawRcvAdapter(list, this, DistrictWithdrawActivity.this);
        MultiTypeAdapter adapter = new MultiTypeAdapter(passwordNumbers);
        rvPassword.setAdapter(adapter);

        popupWindow3 = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        setPopupWindow(popupWindow3, tvNext);
    }


    public void setPopupWindow(PopupWindow popupWindow, View locationView) {

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.i("mengdd", "onTouch : ");
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(getActivity(), 1f);//0.0-1.0
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.alert_dialog_bg));

        // 设置好参数之后再show
        popupWindow.showAtLocation(locationView, Gravity.BOTTOM, 0, 0);
//        return popupWindow;
    }

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public Activity getActivity() {
        return WithdrawActivity.this;
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
//                else {
//                    String password = "";
//                    for (int i = 0; i < passwordList.size(); i++) {
//                        password = password + passwordList.get(i);
//                    }
//                    Toast.makeText(TrolleyActivity.this, password, Toast.LENGTH_SHORT).show();
//                }
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

                if (popupWindow3.isShowing()) {
                    popupWindow3.dismiss();
                }
                String payPassword = "";
                for (int i = 0; i < 6; i++) {
                    payPassword = payPassword + passwordList.get(i);
                }
                PaymentDao.moneyWithdrawals(card_id, etCount.getText().toString(), payPassword, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        rlCard.setVisibility(View.GONE);
                        rlCount.setVisibility(View.GONE);
                        tvNext.setVisibility(View.GONE);
                        tvFinishCard.setText(mBankName + "尾号" + mNumber);
                        tvFinishCount.setText(etCount.getText().toString());
                        rlFinish.setVisibility(View.VISIBLE);
                        rlFinishCard.setVisibility(View.VISIBLE);
                        rlFinishCount.setVisibility(View.VISIBLE);
                        SystemUtil.setSharedBoolean("hasNewMoneyChange", true);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        showShortToast(data + "");
                    }
                });

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


}
