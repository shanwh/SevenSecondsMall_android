package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.WithdrawActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.bean.User.ServerBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class ServerHolder extends BaseContextViewHolder<ServerBean> {


    public ServerHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final ServerBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();

        IconFontTextView if_tv = (IconFontTextView)getView(R.id.if_tv);
        if_tv.setText(model.getIcon());
        if_tv.setTextColor(ContextCompat.getColor(context, model.getColor()));

        TextView tv = (TextView)getView(R.id.tv);
        tv.setText(model.getText());

        getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(context, "正在建设中,敬请期待");
            }
        });
    }
}
