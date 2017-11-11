package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;

import com.yidu.sevensecondmall.bean.User.MessageBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

/**
 * Created by Administrator on 2017/6/9 0009.
 * 旧
 */
public class logisticsHolder extends BaseContextViewHolder<MessageBean> {

    public logisticsHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final MessageBean model, final int position, final MultiTypeAdapter adapter) {
//        Context context = getHolderContext();
//        int order_shipping_status = model.getContent().getOrder_shipping_status();
//        String shipTitle = "";
//        switch (order_shipping_status) {
//            case 0:
//                shipTitle = "订单待发货";
//                break;
//            case 1:
//                shipTitle = "订单已发货";
//                break;
//            case 2:
//                shipTitle = "订单已签收";
//                break;
//        }
//        TextView tv_status = (TextView)getView(R.id.tv_status);
//        tv_status.setText(shipTitle);
//        TextView content = (TextView)getView(R.id.content);
//        content.setText(model.getContent().getOrder_title());
//        TextView logistics_id = (TextView)getView(R.id.logistics_id);
//        logistics_id.setText("运单编号:"+model.getContent().getOrder_code());
//        ImageView iv = (ImageView)getView(R.id.pic_goods);
//        Glide.with(context)
//                .load(HttpApi.getFullImageUrl(model.getContent().getOrder_img()))
//                .placeholder(R.drawable.ic_default)
//                .transform(new GlideRoundTransform(context))
//                .into(iv);

    }
}
