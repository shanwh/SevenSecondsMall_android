package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Distribution.GoldDetailActivity;
import com.yidu.sevensecondmall.Activity.Distribution.VipDetailActivity;
import com.yidu.sevensecondmall.Activity.Order.SearchActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Main.BanBean;
import com.yidu.sevensecondmall.bean.Main.BanListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
public class HomeHeaderHolder extends BaseContextViewHolder<BanListBean> {
    public HomeHeaderHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final BanListBean models, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();

        BGABanner banner_main_flip = (BGABanner) getView(R.id.banner_main_flip);
        banner_main_flip.setData(models.getList(), null);
        BGABanner.Adapter banneradpter = new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(context)
                        .load(
                                HttpApi.getFullImageUrl((
                                        (BanBean) model).getAd_code()
                                )
                        )
                        .into((ImageView) view);
            }
        };
        banner_main_flip.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object modint, int position) {
                BanBean be = models.getList().get(position);
                if (null != be.getType()) {
                    if (be.getType().equals("search_id")) {
                        if (be.getCat_id() != null) {
                            Intent i = new Intent(getHolderContext(), SearchActivity.class);
                            i.putExtra("id", Integer.parseInt(be.getCat_id()));
                            getHolderContext().startActivity(i);
                        } else {
                            Log.e("搜索ID为空", "---------");
                        }
                    }else if(be.getType().equals("vip_link")) {
                        Intent intent = new Intent(context, VipDetailActivity.class);
                        context.startActivity(intent);
                    } else if(be.getType().equals("gold_link")) {
                        Intent intent = new Intent(context, GoldDetailActivity.class);
                        context.startActivity(intent);
                    } else if (be.getType().equals("search_keywords")) {
                        if (be.getKeywords() != null) {
                            Intent i = new Intent(getHolderContext(), SearchActivity.class);
                            i.putExtra("keyword", be.getKeywords());
                            getHolderContext().startActivity(i);
                        } else {
                            Log.e("搜索keyword为空", "---------");
                        }
                    }
                }
            }
        });
//                if (position == 0){
//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, GameGoldDetailActivity.class);
//                            context.startActivity(intent);
//                        }
//                    });
//                }else if (position == 3){
//                    view.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(context, VipDetailActivity.class);
//                            context.startActivity(intent);
//                        }
//                    });
//                }


        banner_main_flip.setAdapter(banneradpter);

    }
}