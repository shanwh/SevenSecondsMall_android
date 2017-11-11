package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.MemberListBean;
import com.yidu.sevensecondmall.bean.Distribution.MyTeamMemberBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class MyTeamMemberHolder extends BaseContextViewHolder<MyTeamMemberBean> {

    public MyTeamMemberHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(MyTeamMemberBean model, int position, MultiTypeAdapter adapter) {
        Context context = getHolderContext();
        TextView tv_name = (TextView) getView(R.id.tv_name);
        tv_name.setText(model.getNickname());
        ImageView iv = (ImageView)getView(R.id.iv);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getHead_pic()))
                .placeholder(R.drawable.default_loading_pic)
                .transform(new GlideCircleTransform(context))
                .into(iv);
//        TextView tv_recommend_back = (TextView)getView(R.id.tv_recommend_back);
//        tv_recommend_back.setText(model.getInvite_rebate());
//        TextView tv_consume_back = (TextView)getView(R.id.tv_consume_back);
//        tv_consume_back.setText(model.getConsumption_rebate());
//        TextView tv_sell_back = (TextView)getView(R.id.tv_sell_back);
        TextView tv_time = (TextView)getView(R.id.tv_time);
        if (model.getMemberjointime() == null ||"null".equals(model.getMemberjointime())){
            tv_time.setVisibility(View.INVISIBLE);
        }else {
            String format = TimeFormatUtils.format(Long.parseLong(model.getMemberjointime())*1000L);
            tv_time.setText(format);
        }

    }
}
