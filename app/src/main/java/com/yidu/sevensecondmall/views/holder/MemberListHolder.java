package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class MemberListHolder extends BaseContextViewHolder<MemberListBean> {

    public MemberListHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final MemberListBean model, final int position, final MultiTypeAdapter adapter) {
        Context context = getHolderContext();
        TextView tv_name = (TextView) getView(R.id.tv_name);
        tv_name.setText(model.getNickname());
        ImageView iv = (ImageView)getView(R.id.iv);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getHead_pic()))
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.default_loading_pic)
                .transform(new GlideCircleTransform(context))
                .into(iv);

        TextView tv_type = (TextView)getView(R.id.tv_type);


        switch (model.getNew_level()){
            case 1:
                tv_type.setVisibility(View.GONE);
                break;
            case 2:
                tv_type.setVisibility(View.VISIBLE);
                tv_type.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape4));
                tv_type.setText("VIP");
                break;
            case 3:
                tv_type.setVisibility(View.VISIBLE);
                tv_type.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape1));
                tv_type.setText("股");
                break;
        }
//        TextView tv_recommend_back = (TextView)getView(R.id.tv_recommend_back);
//        tv_recommend_back.setText(model.getInvite_rebate());
//        TextView tv_consume_back = (TextView)getView(R.id.tv_consume_back);
//        tv_consume_back.setText(model.getConsumption_rebate());
        TextView tv_time = (TextView)getView(R.id.tv_time);
        if (model.getCreatetime() == null ||"null".equals(model.getCreatetime())){
            tv_time.setVisibility(View.INVISIBLE);
        }else {
            String format = TimeFormatUtils.format(Long.parseLong(model.getCreatetime())*1000L);
            tv_time.setText(format);
        }

    }
}
