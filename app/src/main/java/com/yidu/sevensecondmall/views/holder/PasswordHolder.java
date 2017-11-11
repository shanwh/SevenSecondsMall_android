package com.yidu.sevensecondmall.views.holder;

import android.view.View;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.PasswordBean;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.NumberEvent;

import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class PasswordHolder extends BaseViewHolder<PasswordBean> {

    public PasswordHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final PasswordBean model, final int position, final MultiTypeAdapter adapter) {
        final TextView tvNumber = (TextView) getView(R.id.tv_number);

        tvNumber.setText(model.getNumber());

        tvNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new NumberEvent(IEventOrderTag.SEND_PASSWORD, model.getNumber()));
            }
        });
    }
}
