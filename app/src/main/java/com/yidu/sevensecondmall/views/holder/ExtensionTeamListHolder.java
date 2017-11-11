package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.ExtensionTeamBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.TimeFormatUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class ExtensionTeamListHolder extends BaseContextViewHolder<ExtensionTeamBean> {

    private int id;

    public ExtensionTeamListHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final ExtensionTeamBean model, final int position, final MultiTypeAdapter adapter) {
        Context context = getHolderContext();
        TextView tv_name = (TextView) getView(R.id.tv_name);
        tv_name.setText(model.getName());
        ImageView iv = (ImageView)getView(R.id.iv);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getLogo()))
                .asBitmap()
                .placeholder(R.drawable.default_loading_pic)
                .transform(new GlideCircleTransform(context))
                .into(iv);
        TextView tv_recommend_back = (TextView)getView(R.id.tv_recommend_back);
        tv_recommend_back.setText(model.getMembers());
        TextView tv_consume_back = (TextView)getView(R.id.tv_consume_back);
        tv_consume_back.setText(model.getInvite_rebate());
        TextView tv_sell_back = (TextView)getView(R.id.tv_sell_back);
        tv_sell_back.setText(model.getConsumption_rebate());
        TextView tv_time = (TextView)getView(R.id.tv_time);
        if (model.getCreatetime() == null ||"null".equals(model.getCreatetime())){
            tv_time.setVisibility(View.INVISIBLE);
        }else {
            String format = TimeFormatUtils.format(Long.parseLong(model.getCreatetime())*1000L);
            tv_time.setText(format);
        }

        TextView tv1 = (TextView)getView(R.id.tv1);
        tv1.setText("团队精英");

        TextView tv2 = (TextView)getView(R.id.tv2);
        tv2.setText("推荐返利");

        TextView tv3 = (TextView)getView(R.id.tv3);
        tv3.setText("销售返利");



        RelativeLayout rl_item = (RelativeLayout)getView(R.id.rl_item);
//        rl_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getHolderContext(), TeamActivity.class);
//                intent.putExtra("id", model.getId());
//                getHolderContext().startActivity(intent);
//            }
//        });

//        if (position == adapter.getItemCount()-1){
//            LinearLayout ll_add = (LinearLayout)getView(R.id.ll_add);
//            ll_add.setVisibility(View.VISIBLE);
//            TextView tv_request = (TextView)getView(R.id.tv_request);
//            tv_request.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//        }else {
//            LinearLayout ll_add = (LinearLayout)getView(R.id.ll_add);
//            ll_add.setVisibility(View.GONE);
//        }
//        tvNum.setText(model.get);
//        R.layout.item_member_list
    }
}
