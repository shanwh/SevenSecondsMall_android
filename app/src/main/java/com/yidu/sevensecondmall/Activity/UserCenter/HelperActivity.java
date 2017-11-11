package com.yidu.sevensecondmall.Activity.UserCenter;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.Activity.Order.OrderExActivity;
import com.yidu.sevensecondmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/26.
 */
public class HelperActivity extends BaseActivity {
    @BindView(R.id.v)
    View v;
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
    @BindView(R.id.rl_report)
    RelativeLayout rlReport;
    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_feeback)
    RelativeLayout rlFeeback;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int setViewId() {
        return R.layout.helper_activity_layout;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        titleName.setText("帮助与客服");
        toolbarTitle.setText("帮助与客服");

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


    @OnClick({R.id.back_button, R.id.rl_report, R.id.rl_return, R.id.rl_feeback, R.id.rl_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.rl_report:
                Intent toReport = new Intent(HelperActivity.this, ReportActivity.class);
                startActivity(toReport);
                break;
            case R.id.rl_return:
                Intent toOrder = new Intent(HelperActivity.this, OrderExActivity.class);
                startActivity(toOrder);
                break;
            case R.id.rl_feeback:
                Intent toFeeBack = new Intent(HelperActivity.this, FeeBackActivity.class);
                startActivity(toFeeBack);
                break;
            case R.id.rl_about:
                makeConfirm(HelperActivity.this, "400-662-2226", "呼叫", "取消", new ButtonCallback() {
                    @Override
                    public void onPositive(Dialog d) {
                        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4006622226"));
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent1);
                    }

                    @Override
                    public void onNegative(Dialog d) {

                    }
                }).show();
                break;
        }
    }


    public interface ButtonCallback {
        void onPositive(Dialog d);

        void onNegative(Dialog d);
    }


    public static Dialog makeConfirm(Context context, String msg, String positiveText, String negativeText, final ButtonCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context, R.layout.dialog_tellphone, null);

        TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        tvMsg.setText(msg);

        if (!TextUtils.isEmpty(positiveText)) {
            tvOk.setText(positiveText);
        }
        if (!TextUtils.isEmpty(negativeText)) {
            tvCancel.setText(negativeText);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onPositive(dialog);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.7);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);
        return dialog;
    }
}
