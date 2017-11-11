package com.yidu.sevensecondmall.widget.convenientbanner.holder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yidu.sevensecondmall.Activity.WebViewActivity;
import com.yidu.sevensecondmall.bean.Main.GameBean;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.widget.convenientbanner.CBPageAdapter;

import java.util.List;


/**
 * Created by zhuang on 15/10/14.
 */
public class GameBannerHolder implements CBPageAdapter.Holder<GameBean.ResultBean.BannerBean> {
    //        private ImageView imageView;
    private SimpleDraweeView imageView;

    @Override
    public View createView(Context context) {
        imageView = new SimpleDraweeView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, final int position, final List<GameBean.ResultBean.BannerBean> list, final GameBean.ResultBean.BannerBean data) {
        imageView.setImageURI(Uri.parse(data.getSrc() + ""));
        Log.e("游戏bannerurl", "" + data.getSrc());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
                if(data != null){
                    context.startActivity(new Intent(context, WebViewActivity.class).putExtra("url",data
                            .getHref()));
                }

            }
        });
    }
}
