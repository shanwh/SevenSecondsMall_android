package com.yidu.sevensecondmall.Activity.UserCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.DAO.PaymentDao;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.NumberEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

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
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;
    @BindView(R.id.rv)
    RecyclerView rv;

    MultiTypeAdapter adapter;
    ArrayList<Visitable> list = new ArrayList<>();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.activity_bank_card;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("银行卡");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BankCardActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new MultiTypeAdapter(list, BankCardActivity.this);
        rv.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        loadBankCardList();
    }


    @OnClick({ R.id.rl_add})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_add:
                if (LoginUtils.isLogin()) {
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
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }


                break;
        }
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(BankCardActivity.this).inflate(R.layout.pop_window_indentification, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(rlAdd, Gravity.CENTER, 0, 0);//显示PopupWindow
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
                Intent intent = new Intent(BankCardActivity.this, IndentActivity.class);
                BankCardActivity.this.startActivity(intent);
                popupWindow.dissmiss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SystemUtil.getSharedBoolean("hasNewBankCard", false)) {
            loadBankCardList();
            SystemUtil.setSharedBoolean("hasNewBankCard", false);
        }
    }

    private void loadBankCardList() {
        PaymentDao.getBankcardList(new BaseCallBack() {
            @Override
            public void success(Object data) {
                list = (ArrayList<Visitable>) data;
                adapter.refreshData(list);
            }

            @Override
            public void failed(int errorCode, Object data) {
                ToastUtil.showToast(BankCardActivity.this, data + "");
            }
        });
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
        if (popupWindow != null) {
            popupWindow.dissmiss();
        }
//        if (popupWindow2 != null) {
//            popupWindow2.dissmiss();
//        }
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
                .create()//创建PopupWindow
                .showAtLocation(rlAdd, Gravity.BOTTOM, 0, 0);//显示PopupWindow

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
                Intent intent = new Intent(BankCardActivity.this, SetPayPasswordActivity.class);
                BankCardActivity.this.startActivity(intent);
            }
        });
        rvPassword = (RecyclerView) contentView.findViewById(R.id.rv_password);
        contentView.findViewById(R.id.if_tv_back).setVisibility(View.GONE);

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

    private static final String TAG = "BankCardActivity";

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
//                popupWindow2.dissmiss();
                String id = getIntent().getStringExtra("id");
                id = id == null ? "" : id;

                String payPassword = "";
                for (int i = 0; i < 6; i++) {
                    payPassword = payPassword + passwordList.get(i);
                }


                UserDAO.checkPayPwd(payPassword, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        UserDAO.getUserInfo(new BaseCallBack() {
                            @Override
                            public void success(Object data) {
                                if (data != null) {
                                    UserBean bean = (UserBean) data;
                                    String is_authentication = bean.getIs_authentication();
                                    if (is_authentication.equals("0")) {
                                        showPopupWindow();
                                    } else {
                                        Intent intent = new Intent(BankCardActivity.this, AddBankCardActivity.class);
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
