package com.yidu.sevensecondmall.widget.convenientbanner.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.Activity.WebViewActivity;
import com.yidu.sevensecondmall.bean.Main.MainBean;
import com.yidu.sevensecondmall.widget.convenientbanner.CBPageAdapter;

import java.util.List;


/**
 * Created by zhuang on 15/10/14.
 */
public class ImageViewHolder implements CBPageAdapter.Holder<MainBean.ResultBean.BannerBean> {
    //        private ImageView imageView;
    private SimpleDraweeView imageView;

    @Override
    public View createView(Context context) {
        imageView = new SimpleDraweeView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, final int position, final List<MainBean.ResultBean.BannerBean> list, final MainBean.ResultBean.BannerBean data) {
        imageView.setImageURI(Uri.parse(data.getAd_code() + ""));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
                if (data != null) {
//                        context.startActivity(new Intent(context, WebViewActivity.class)
//                        .putExtra(WebViewActivity.EXTRA_TITLE,data.getTitle())
//                        .putExtra(WebViewActivity.EXTRA_URL,data.getJumpurl()));
                    context.startActivity(new Intent(context, WebViewActivity.class).putExtra("url",data
                    .getUrl()));
//                    if (null != data.getType()) {
//                        if (data.getType().equals("search_id")) {
//                            if (data.getCat_id() != null) {
//                                Intent i = new Intent(context, SearchActivity.class);
//                                i.putExtra("id", Integer.parseInt(data.getCat_id()));
//                                context.startActivity(i);
//                            } else {
//                                Log.e("搜索ID为空", "---------");
//                            }
//                        } else if (data.getType().equals("vip_link")) {
//                            Intent intent = new Intent(context, VipDetailActivity.class);
//                            context.startActivity(intent);
//                        } else if (data.getType().equals("gold_link")) {
//                            Intent intent = new Intent(context, GoldDetailActivity.class);
//                            context.startActivity(intent);
//                        } else if (data.getType().equals("search_keywords")) {
//                            if (data.getKeywords() != null) {
//                                Intent i = new Intent(context, SearchActivity.class);
//                                i.putExtra("keyword", data.getKeywords());
//                                context.startActivity(i);
//                            } else {
//                                Log.e("搜索keyword为空", "---------");
//                            }
//                        }
//                    }
                }
            }
        });
    }
}
