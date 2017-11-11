package com.yidu.sevensecondmall.Activity.Distribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouwei.library.CustomPopWindow;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Order.FeeShoppingActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.PaySuccess;
import com.yidu.sevensecondmall.i.WechatPayEvent;
import com.yidu.sevensecondmall.utils.PayPopWindow;
import com.yidu.sevensecondmall.views.widget.WaveProgressView;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/19 0019.
 */
public class AssignmentBuyActivity extends BaseActivity implements PaySuccess {

    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.waveProgressbar)
    WaveProgressView waveProgressbar;
    @BindView(R.id.rl_circle)
    RelativeLayout rlCircle;
    @BindView(R.id.tv_buy)
    TextView tv_buy;
    @BindView(R.id.tv_present)
    TextView tv_present;
    @BindView(R.id.tv_sell_price)
    TextView tv_sell_price;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    private boolean success = true;
    PayPopWindow payPopWindow;
    private String order_id;
    private String curr_id;

    @Override
    protected int setViewId() {
        return R.layout.activity_assignment_buy;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("赠品转让区");
        toolbarRight.setText("赠品进度");
    }

    @Override
    protected void initEvent() {
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssignmentBuyActivity.this, FeeShoppingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        String num = intent.getStringExtra("num");
        String price = intent.getStringExtra("price");
        String sell_price = intent.getStringExtra("sell_price");

        tvCode.setText(num);
        tvPrice.setText(price + "元");
        tvDate.setText(intent.getStringExtra("rate") + "%");
        tv_present.setText(intent.getStringExtra("rate") + "%");
        tv_sell_price.setText(sell_price + "元");

        int rate = 100 - (int) Double.parseDouble(intent.getStringExtra("rate"));
        if (rate == 0) {
            rate = 1;
        } else if (rate == 100) {
            rate = 120;
        }
        waveProgressbar.setCurrent(rate);
        order_id = intent.getStringExtra("id");

        curr_id = intent.getStringExtra("curr_id");
    }

    @Override
    protected void loadData() {

    }


    @OnClick({R.id.back, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_buy:
                payPopWindow = new PayPopWindow(AssignmentBuyActivity.this, waveProgressbar, getIntent().getStringExtra("sell_price"), order_id, "sellorder", AssignmentBuyActivity.this, curr_id);
                payPopWindow.showPayPopWindow();
                break;
        }
    }

    @Override
    public void paySuccess() {
        success = true;
        showPopupWindowPassWord();
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

    CustomPopWindow popupWindow3;

    private void showPopupWindowPassWord() {
        if (payPopWindow != null) {
            payPopWindow.getPopupWindow().dissmiss();
        }
        View contentView = LayoutInflater.from(AssignmentBuyActivity.this).inflate(R.layout.pop_window_assignment, null);
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, AssignmentBuyActivity.this.getResources().getDisplayMetrics());
        int h = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, AssignmentBuyActivity.this.getResources().getDisplayMetrics());
        //处理popWindow 显示内容
        handlePassword(contentView);
        popupWindow3 = new CustomPopWindow.PopupWindowBuilder(AssignmentBuyActivity.this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(w, h)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .create()//创建PopupWindow
                .showAtLocation(waveProgressbar, Gravity.CENTER, 0, 0);//显示PopupWindow

    }

    private void handlePassword(View contentView) {
        IconFontTextView it = (IconFontTextView) contentView.findViewById(R.id.it);
        TextView tv_tip = (TextView) contentView.findViewById(R.id.tv_tip);
        TextView tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        TextView tv_next = (TextView) contentView.findViewById(R.id.tv_next);
        if (success) {
            it.setText(R.string.icon_cancel_success);
            it.setTextColor(ContextCompat.getColor(AssignmentBuyActivity.this, R.color.app_theme));
            tv_tip.setText("恭喜!您的订单已支付成功");
            tv_cancel.setText("知道了");
            tv_next.setText("回首页");
            tv_buy.setText("已购买");
            tv_buy.setClickable(false);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow3.dissmiss();
                }
            });
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AssignmentBuyActivity.this.finish();
                }
            });
        } else {
            it.setText(R.string.icon_cancel_failed);
            it.setTextColor(ContextCompat.getColor(AssignmentBuyActivity.this, R.color.colorCancelFailed));
            tv_tip.setText("余额不足,您的订单已支付失败");
            tv_cancel.setText("去充值");
            tv_next.setText("使用其他方式");
            tv_buy.setText("已购买");
            tv_buy.setClickable(false);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AssignmentBuyActivity.this, RechargeActivity.class);
                    AssignmentBuyActivity.this.startActivity(intent);
                }
            });
            tv_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("敬请期待");
                }
            });
        }
    }

}
