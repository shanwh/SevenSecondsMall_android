package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.OrderMessage.GoodsBean;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/25.
 */
public class StoreGoodsHolder extends BaseContextViewHolder {

    @BindView(R.id.iv_goods_pic)
    ImageView ivGoodsPic;
    @BindView(R.id.tv_hot_pic)
    TextView tvHotPic;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_buypeople)
    TextView tvBuypeople;
    @BindView(R.id.add_buycar)
    IconFontTextView addBuycar;

    public StoreGoodsHolder(View itemView, Context context) {
        super(itemView, context);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setUpView(Object model, int position, MultiTypeAdapter adapter) {
        // TODO: 2017/8/25  picture
        if(model instanceof GoodsBean){
            if (null == model)
                return;
            tvHotPic.setVisibility(View.VISIBLE);
            tvGoodsName.setText(((GoodsBean) model).getGoods_name());
            tvPrice.setText(((GoodsBean) model).getGoods_price());
            //购买的商品人数
            tvBuypeople.setText(((GoodsBean) model).getGoods_num());
            addBuycar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //购物车的商品数量加一、
                }
            });
        }

    }
}
