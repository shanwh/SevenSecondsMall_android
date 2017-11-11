package com.yidu.sevensecondmall.Activity.Video;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.Video.VideoListBean;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/19.
 */
public class HomepageActivity extends BaseActivity{

    @BindView(R.id.if_return)
    IconFontTextView if_return;
    @BindView(R.id.im_photo)
    ImageView imPhoto;
    @BindView(R.id.person_name)
    TextView tvName;
    @BindView(R.id.fans_number)
    TextView tvFansNum;
//    关注
    @BindView(R.id.ll_attention)
    LinearLayout llAttention;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.if_tv_attention)
    TextView ifAttention;

    private VideoListBean videoListBean;
    private boolean isLike;

    @Override
    protected int setViewId() {
        return R.layout.activity_live_person_homepage;
    }

    @Override
    protected void findViews() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.if_return)
    public void onClick(View  v){
        switch (v.getId()) {
            case R.id.if_return:
            finish();
                break;

            case R.id.ll_attention:
                if(videoListBean != null) {
                    ifAttention.setVisibility(isLike?View.GONE:View.VISIBLE);
                    llAttention.setBackgroundResource(isLike? R.drawable.attention_bg_gray:R.drawable.attention_bg_red);
                    tvAttention.setText(isLike?"已关注":"关注");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handler(VideoListBean videoListBean){
        this.videoListBean = videoListBean;
        tvName.setText(videoListBean.getName());
        tvFansNum.append(videoListBean.getFans_num());
       Glide.with(this)
               .load(HttpApi.getFullImageUrl(videoListBean.getHead_icon()))
               .asBitmap()
               .centerCrop()
               .transform(new GlideCircleTransform(this))
               .into(imPhoto);
    }
}
