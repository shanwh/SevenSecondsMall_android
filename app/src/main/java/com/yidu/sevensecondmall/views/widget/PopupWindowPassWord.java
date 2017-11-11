package com.yidu.sevensecondmall.views.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.SetPayPasswordActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PopupWindowPassWord {
    Context context;
    PopupWindow popupWindow3;
    private RecyclerView rvPassword;
    private List<Visitable> passwordNumbers = new ArrayList<>();

    public void showPopupWindow(Activity activity, View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_window_password, null);
        TextView passWork1 = (TextView) contentView.findViewById(R.id.tv_password1);
        TextView passWork2 = (TextView) contentView.findViewById(R.id.tv_password2);
        TextView passWork3 = (TextView) contentView.findViewById(R.id.tv_password3);
        TextView passWork4 = (TextView) contentView.findViewById(R.id.tv_password4);
        TextView passWork5 = (TextView) contentView.findViewById(R.id.tv_password5);
        TextView passWork6 = (TextView) contentView.findViewById(R.id.tv_password6);
        contentView.findViewById(R.id.tv_find_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SetPayPasswordActivity.class);
                context.startActivity(intent);
            }
        });
        RecyclerView rvPassword = (RecyclerView) contentView.findViewById(R.id.rv_password);
        IconFontTextView ifTvBack = (IconFontTextView) contentView.findViewById(R.id.if_tv_back);

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

        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 3);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPassword.setLayoutManager(linearLayoutManager);
//                adapter = new DisWithdrawRcvAdapter(list, this, DistrictWithdrawActivity.this);
        MultiTypeAdapter adapter = new MultiTypeAdapter(passwordNumbers);
        rvPassword.setAdapter(adapter);

        popupWindow3 = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        setPopupWindow(popupWindow3, view, activity);
    }

    public void setPopupWindow(PopupWindow popupWindow, View locationView, final Activity activity) {

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
                backgroundAlpha(activity, 1f);//0.0-1.0
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(
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

}
