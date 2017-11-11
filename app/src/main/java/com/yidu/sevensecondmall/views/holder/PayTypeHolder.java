package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Order.PayTypeBean;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.SelectEvent;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;
import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class PayTypeHolder extends BaseContextViewHolder<PayTypeBean> {

    public PayTypeHolder(View itemView ,Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final PayTypeBean model, final int position, final MultiTypeAdapter adapter) {
        Context context = getHolderContext();
        TextView tvName = (TextView) getView(R.id.tv_name);
        IconFontTextView ifTvIsChoose = (IconFontTextView) getView(R.id.if_tv_choose);
        ifTvIsChoose.setText(model.isChoose()? R.string.icon_select:R.string.icon_un_select);
        RelativeLayout rl = (RelativeLayout) getView(R.id.rl);
        TextView if_tv_icon = (TextView)getView(R.id.if_tv_icon);


        String name;
        int icon;
        int color;
        switch (position){
            case 0:
                name = "余额支付";
                icon = R.string.icon_user_money;
                color = ContextCompat.getColor(context, R.color.light_red);
                break;
            case 1:
                name = "支付宝支付";
                icon = R.string.icon_ali_pay;
                color = ContextCompat.getColor(context, R.color.btn_blue_normal);

                break;
            case 2:
                name = "微信支付";
                icon = R.string.icon_we_chat;
                color = ContextCompat.getColor(context, R.color.green);

                break;
            case  3:
                name = "银行卡支付";
                icon = R.string.icon_bank;
                color = ContextCompat.getColor(context, R.color.colorIconYellow);
                break;
            case  4:
                name = "货到付款";
                icon = R.string.icon_bank;
                color = ContextCompat.getColor(context, R.color.colorGreenPb);
                break;
            default:
                name = "其他支付方式";
                icon = R.string.icon_ali_pay;
                color = ContextCompat.getColor(context, R.color.gray_normal);
                break;
        }

        tvName.setText(name);
        if_tv_icon.setText(icon);
        if_tv_icon.setTextColor(color);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new SelectEvent(IEventOrderTag.SHOW_PAY_TYPE_DETAIL, position));
            }
        });
        tvName.setText(model.getName());
//        ifTvIsChoose.setVisibility(model.isChoose() ? View.VISIBLE : View.INVISIBLE);
    }
}
