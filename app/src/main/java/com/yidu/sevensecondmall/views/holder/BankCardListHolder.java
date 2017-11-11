package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.UnBindBankCardActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.WithdrawActivity;
import com.yidu.sevensecondmall.Activity.Video.LiveActivity;
import com.yidu.sevensecondmall.Activity.Video.VideoActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.bean.Video.VideoListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class BankCardListHolder extends BaseContextViewHolder<BankCardBean> {
    public static final int FROM_RECHARGE = 1;
    public static final int FROM_WITHDRAW = 2;

    public BankCardListHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final BankCardBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();

        ImageView iv = (ImageView)getView(R.id.iv);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getImgUrl()))
                .into(iv);

//        TextView tv_bank_name = (TextView)getView(R.id.tv_bank_name);
//        tv_bank_name.setText(model.getBankname());
//        TextView tv_card_type = (TextView)getView(R.id.tv_card_type);
        TextView tv_card_number = (TextView)getView(R.id.tv_card_number);
        String cardnumber = model.getCardnumber();
        String[] split = cardnumber.split("");
        int length = split.length;
        String result = "**** **** **** ";
        for (int i = 0; i < 4; i++) {
            String s = split[length - 4 + i];
            result =  result + s ;
        }

        tv_card_number.setText(result);

        RelativeLayout rl_item = (RelativeLayout)getView(R.id.rl_item);
        rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                int bankCardListFrom = SystemUtil.getSharedInt("BankCardListFrom", 0);
                switch (bankCardListFrom){
                    case 0:
                        intent = new Intent(context, UnBindBankCardActivity.class);
                        intent.putExtra("Id", model.getId());
                        intent.putExtra("img", model.getImgUrl());
                        intent.putExtra("Bankname", model.getBankname());
                        intent.putExtra("Cardnumber", model.getCardnumber());
                        context.startActivity(intent);
                        break;
                    case FROM_RECHARGE:
                        intent = new Intent(context, RechargeActivity.class);
                        intent.putExtra("Id", model.getId());
                        intent.putExtra("Bankname", model.getBankname());
                        intent.putExtra("Cardnumber", model.getCardnumber());
                        context.startActivity(intent);
                        break;
                    case FROM_WITHDRAW:
                        intent = new Intent(context, WithdrawActivity.class);
                        intent.putExtra("Id", model.getId());
                        intent.putExtra("Bankname", model.getBankname());
                        intent.putExtra("Cardnumber", model.getCardnumber());
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }
}
