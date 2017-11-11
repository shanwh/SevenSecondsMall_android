package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.bean.Distribution.MyJurisdictionBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.BaseViewHolder;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class MyJurisdictionHolder extends BaseViewHolder<MyJurisdictionBean> {

    public MyJurisdictionHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final MyJurisdictionBean model, final int position, final MultiTypeAdapter adapter) {
        try {
            TextView tv = (TextView) getView(R.id.tv);
            String key = model.getKey();
            String name = model.getName();
            String company = model.getCompany();
            String value = model.getValue();
            String str;
            if ("recommend_upgrade_peoples".equals(key)) {
                String[] split = name.split("几人");
                str = split[0] + value + company + split[1];
            } else if ("recommend_upgrade_recharge".equals(key)) {
                String[] split = name.split("多少");
                str = split[0] + value + company + split[1];
            } else {
                str = name + value + company;
            }
            tv.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
