package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Order.GoodsDetailActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.HomeGoodListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.AttrsPopwindow;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class GoodsListHolder extends BaseContextViewHolder<HomeGoodListBean> {

    private AttrsPopwindow popwindow;

    public GoodsListHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final HomeGoodListBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();
//        getView(R.id.rl_recycle_title).setVisibility(View.GONE);
//        getView(R.id.rl_recycle_title).setVisibility(position ==0?View.VISIBLE:View.GONE);

        ImageView iv = (ImageView) getView(R.id.goods_pic);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getOriginal_img(), true))
                .asBitmap()
                .centerCrop()
                .into(iv);

        TextView in_text = (TextView) getView(R.id.in_text);
        in_text.setText(model.getGoods_name());
         TextView in_people = (TextView)getView(R.id.goods_people);
        if(model.getBuy_people()==null){
            in_people.setVisibility(View.INVISIBLE);
        }else{
           if(Integer.parseInt(model.getBuy_people())>0){
               in_people.setVisibility(View.VISIBLE);
               in_people.setText(model.getBuy_people()+"人已经购买");
           }
        }
        TextView price = (TextView) getView(R.id.price);
        TextView tv= (TextView) getView(R.id.tv);
        tv.setText("¥");
        tv.setIncludeFontPadding(false);
        tv.setGravity(Gravity.BOTTOM);
        Spannable mSpan = new SpannableString(model.getShop_price());
        mSpan.setSpan(new AbsoluteSizeSpan(18, true), 0, mSpan.length()-2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpan.setSpan(new AbsoluteSizeSpan(13, true), mSpan.length()-2, mSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        price.setText("¥" + mSpan);
        price.setText(mSpan);
        price.setIncludeFontPadding(false);
        price.setGravity(Gravity.BOTTOM);
//        TextView content = (TextView) getView(R.id.goods_content);
//        content.setText("30 ml");


//        getItemView().setOnClickListener();
        getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GoodsDetailActivity.class);
                i.putExtra("id", Integer.parseInt(model.getGoods_id()));
                context.startActivity(i);
            }
        });
        popwindow = new AttrsPopwindow(getHolderContext(),model.getGoods_id());
        popwindow.setCancelable(true);
        getView(R.id.if_buy_car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindow.setTip(1);
                popwindow.show();
            }
        });


    }
}
