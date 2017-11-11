package com.yidu.sevensecondmall.Activity.UserCenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/1 0001.
 */
public class ShareHolderRequestActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_connect)
    TextView tvConnect;
    @BindView(R.id.et_connect)
    EditText etConnect;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rl_address_id)
    RelativeLayout rlAddressId;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private ProgressDialog dialog;

    @Override
    protected int setViewId() {
        return R.layout.activity_share_holder_request;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("申请升级");
        dialog = new ProgressDialog(this);
        dialog.setMessage("请等待");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_send:
                if (checkMsg()) {
                    dialog.show();
                    UserDAO.applyToShareholder(etConnect.getText().toString(), etNum.getText().toString(),
                            etName.getText().toString(), etPhone.getText().toString(), new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    dialog.dismiss();
                                    showPopupWindow();
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    dialog.dismiss();
                                    showShortToast(data + "");
                                }
                            });
                }
                break;
        }
    }

    private boolean checkMsg() {
        if (etConnect.getText().toString().trim().length() == 0) {
            showShortToast("请输入公司名称");
            return false;
        }
        if (etNum.getText().toString().trim().length() == 0) {
            showShortToast("请输入营业执照注册号");
            return false;
        }
        if (etName.getText().toString().trim().length() == 0) {
            showShortToast("请输入法人姓名");
            return false;
        }
        if (etPhone.getText().toString().trim().length() == 0) {
            showShortToast("请输入联系电话");
            return false;
        }

        return true;
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(ShareHolderRequestActivity.this).inflate(R.layout.pop_window_commend_success, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, ShareHolderRequestActivity.this.getResources().getDisplayMetrics());

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
                        ShareHolderRequestActivity.this.finish();
                    }
                })
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

                .create()//创建PopupWindow
                .showAtLocation(tvSend, Gravity.CENTER, 0, 0);//显示PopupWindow
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

}
