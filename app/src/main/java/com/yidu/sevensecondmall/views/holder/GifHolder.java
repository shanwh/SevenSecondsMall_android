package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Distribution.GiftGoodsArrBean;
import com.yidu.sevensecondmall.i.GiftEvent;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

import com.yidu.sevensecondmall.views.widget.GlideRoundTransform;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class GifHolder extends BaseContextViewHolder<GiftGoodsArrBean> {

    public GifHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final GiftGoodsArrBean model, final int position, final MultiTypeAdapter adapter) {
        Context context = getHolderContext();
        TextView tv_name = (TextView) getView(R.id.tv_name);
        tv_name.setText(model.getGoods_name());
        ImageView iv = (ImageView)getView(R.id.iv);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getOriginal_img()))
                .placeholder(R.drawable.default_loading_pic)
                .transform(new GlideRoundTransform(context))
                .into(iv);
        LinearLayout item = (LinearLayout)getView(R.id.item);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new GiftEvent(IEventTag.GIFT_CLICK, Integer.parseInt(model.getGoods_id()), model.getGoods_name(), model.getOriginal_img()));
            }
        });
    }
}
