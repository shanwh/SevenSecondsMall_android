package com.yidu.sevensecondmall.Activity.Video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class VideoActivity extends Activity {

    @BindView(R.id.vv)
    VideoView mVv;
    @BindView(R.id.if_cancle)
    IconFontTextView cancle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {

        mVv.setMediaController(new MediaController(VideoActivity.this));
        int position = getIntent().getIntExtra("position", 0);
        Uri rawUri =null;
        switch (position){
            case 0:
                rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ding);
                break;
//            case 1:
//                rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.m1);
//                break;
//            case 3:
//                rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.m3);
//                break;
        }
//        File video = new File("android.resource://" + getPackageName() + "/" + R.raw.demo);

        //设置视频源播放res/raw中的文件,文件名小写字母,格式: 3gp,mp4等,flv的不一定支持;

        mVv.setVideoURI(rawUri);

        mVv.start();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }
    @OnClick(R.id.if_cancle)
    public void click(){
        finish();
    }


}
