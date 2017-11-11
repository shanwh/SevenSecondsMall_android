package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoadDataEvent;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/18 0018.
 */
public class DingOrderActivity extends BaseActivity {

    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_assignment_money)
    TextView tvAssignmentMoney;
    @BindView(R.id.tv_present)
    TextView tvPresent;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.it)
    IconFontTextView it;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_msg)
    LinearLayout llMsg;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private boolean isSuccess;

    private String order_sn;
    private String Cancel_fee;

    @Override
    protected int setViewId() {
        return R.layout.activity_ding_order;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("订单详情");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        tvNum.setText(intent.getStringExtra("order_sn"));
        tvPrice.setText(intent.getStringExtra("price") + "元");
        tvAssignmentMoney.setText(intent.getStringExtra("sell_price") + "元");
        tvPresent.setText(intent.getStringExtra("rate") + "%");
        Cancel_fee = intent.getStringExtra("Cancel_fee");

        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        // layoutParams.alpha = 0.5f; //0.0-1.0
        this.getWindow().setAttributes(layoutParams);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);//让窗口背景后面的任何东
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_cancel:
                showPopupWindow(tvCancel);
                break;
        }
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow(View view) {
        View contentView = LayoutInflater.from(DingOrderActivity.this).inflate(R.layout.pop_window_ding_cancel, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        popupWindow = new CustomPopWindow.PopupWindowBuilder(DingOrderActivity.this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(view, Gravity.CENTER, 0, 0);//显示PopupWindow
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
                order_sn = getIntent().getStringExtra("order_sn");
                UserDAO.sellCancel(order_sn, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        EventBus.getDefault().post(new LoadDataEvent(IEventTag.LOAD_DATA, 1));
                        it.setTextColor(ContextCompat.getColor(DingOrderActivity.this, R.color.app_theme));
                        it.setText(R.string.icon_cancel_success);
                        tvTip.setText("恭喜！撤销成功，已扣除手续费" + Cancel_fee + "元");
                        tvNext.setText("回首页");
                        tvNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DingOrderActivity.this.finish();
                            }
                        });
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        showShortToast(data + "");
                        it.setTextColor(ContextCompat.getColor(DingOrderActivity.this, R.color.colorCancelFailed));
                        it.setText(R.string.icon_cancel_failed);
                        tvTip.setText("抱歉，余额不足，请先");
                        tvNext.setText("充值");
                        tvNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DingOrderActivity.this, RechargeActivity.class);
                                DingOrderActivity.this.startActivity(intent);
                            }
                        });
                    }
                });
                llInfo.setVisibility(View.GONE);
                it.setVisibility(View.VISIBLE);
                llMsg.setVisibility(View.VISIBLE);
                popupWindow.dissmiss();
            }
        });

        contentView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dissmiss();
            }
        });

    }
}
