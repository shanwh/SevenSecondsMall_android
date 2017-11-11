package com.yidu.sevensecondmall.views.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.Activity.Video.LiveActivity;
import com.yidu.sevensecondmall.Activity.Video.RecordLiveActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Video.PushListBean;
import com.yidu.sevensecondmall.bean.Video.VideoListBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.MultiTypeAdapter;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class VideoListHolder extends BaseContextViewHolder<PushListBean> {

    public VideoListHolder(View itemView, Context context) {
        super(itemView, context);
    }

    @Override
    public void setUpView(final PushListBean model, final int position, final MultiTypeAdapter adapter) {
        final Context context = getHolderContext();

        ImageView iv_head = (ImageView) getView(R.id.iv_head);
        if (position == 0) {
            Glide.with(context)
                    .load(R.drawable.icon_video_default)
                    .placeholder(R.drawable.qq)
                    .transform(new GlideCircleTransform(context))
                    .into(iv_head);
        } else {
            Glide.with(context)
                    .load(R.drawable.icon_video_default2)
                    .placeholder(R.drawable.qq)
                    .transform(new GlideCircleTransform(context))
                    .into(iv_head);
        }

        ImageView iv_scale = (ImageView) getView(R.id.iv_scale);
        if (position == 0) {
            Glide.with(context)
                    .load(R.drawable.icon_video_default)
                    .placeholder(R.drawable.default_loading_pic)
//                    .transform(new GlideCircleTransform(context))
                    .into(iv_scale);
        } else {
            Glide.with(context)
                    .load(R.drawable.icon_video_default2)
                    .placeholder(R.drawable.default_loading_pic)
//                    .transform(new GlideCircleTransform(context))
                    .into(iv_scale);
        }

        TextView tv_name = (TextView) getView(R.id.tv_name);
        tv_name.setText("第" + position + "个店铺");

        TextView tv_content = (TextView) getView(R.id.tv_title);
        tv_content.setText("正在直播" + "第" + position + "个店铺");

        LinearLayout rl_relation = (LinearLayout) getView(R.id.ll_relation);
        rl_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        IconFontTextView if_tv_relation = (IconFontTextView) getView(R.id.if_tv_relation);
        TextView tv_relation = (TextView) getView(R.id.tv_relation);

        IconFontTextView if_tv_status = (IconFontTextView) getView(R.id.if_tv_status);
        TextView tv_status = (TextView) getView(R.id.tv_status);

        IconFontTextView if_tv_video_bg = (IconFontTextView) getView(R.id.if_tv_video_bg);
        IconFontTextView if_tv_video_icon = (IconFontTextView) getView(R.id.if_tv_video_icon);

        TextView tv_num = (TextView) getView(R.id.tv_num);//人正在观看
        tv_num.setText(position + "人正在观看");

        FrameLayout fl_video = (FrameLayout) getView(R.id.fl_video);

        fl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO.getWatchUrl(model.getAppName(), model.getStreamName(), new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        Intent intent = new Intent(context, LiveActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("TITLE", "自定义视频");

                        bundle.putString("URI", (String) data);
                        bundle.putInt("decode_type", 1);

                        intent.putExtras(bundle);

                        context.startActivity(intent);
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        ToastUtil.showToast(context, data+"");
                    }
                });

            }
        });


    }
}
