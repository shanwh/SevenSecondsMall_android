package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.UserCenter.RechargeActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.UnBindBankCardActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.WithdrawActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Others.CommentBean;
import com.yidu.sevensecondmall.bean.Payment.BankCardBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.TimeUtils;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.adapter.imgAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class CommendListHolder extends BaseContextViewHolder<CommentBean> {


    public CommendListHolder(View itemView, Context context) {
        super(itemView,context);
    }

    @Override
    public void setUpView(final CommentBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();
        TextView tv_username = (TextView)getView(R.id.tv_username);
        tv_username.setText(model.getUsername());
        TextView tv_ime = (TextView)getView(R.id.tv_ime);
        tv_ime.setText(TimeUtils.timedate(String.valueOf(model.getAdd_time())));
        TextView tv_goodscomments = (TextView)getView(R.id.tv_goodscomments);
        tv_goodscomments.setText(model.getContent());

        if (model.getImg()==null||model.getImg().size()==0){
            getView(R.id.img_list).setVisibility(View.GONE);
        }else {
            getView(R.id.img_list).setVisibility(View.VISIBLE);
            RecyclerView imgList = (RecyclerView)getView(R.id.img_list);
            GridLayoutManager manager = new GridLayoutManager(context,3);
            manager.setOrientation(GridLayoutManager.VERTICAL);
            imgAdapter imgAdapter = new imgAdapter(context, model.getImg(), true);
            imgList.setLayoutManager(manager);
            imgList.setAdapter(imgAdapter);
        }
        int goods_rank = model.getGoods_rank();
        ProperRatingBar ratingBar = (ProperRatingBar)getView(R.id.rating_bar);
        switch (goods_rank){
            case 0:
                ratingBar.setRating(0);
                break;
            case 1:
                ratingBar.setRating(1);
                break;
            case 2:
                ratingBar.setRating(2);
                break;
            case 3:
                ratingBar.setRating(3);
                break;
            case 4:
                ratingBar.setRating(4);
                break;
            case 5:
                ratingBar.setRating(5);
                break;
            default:
                ratingBar.setRating(0);
                break; 
        }
        

        ImageView iv = (ImageView)getView(R.id.iv_headimg);
        Glide.with(context)
                .load(HttpApi.getFullImageUrl(model.getHead_pic()))
                .asBitmap()
                .centerCrop()
                .transform(new GlideCircleTransform(context))
                .into(iv);

    }
}
