package com.yidu.sevensecondmall.Activity.Video;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.utils.PlayerControl;
import com.yidu.sevensecondmall.utils.StatusListener;
import com.yidu.sevensecondmall.views.anim.HeartLayout;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class LiveActivity extends BaseActivity {
public static final int STATUS_START = 1;
public static final int STATUS_STOP = 2;
public static final int STATUS_PAUSE = 3;
public static final int STATUS_RESUME = 4;

public static final int CMD_START = 1;
public static final int CMD_STOP = 2;
public static final int CMD_PAUSE = 3;
public static final int CMD_RESUME = 4;
public static final int CMD_VOLUME = 5;
public static final int CMD_SEEK = 6;

public static final int TEST = 0;
@BindView(R.id.video_view)
SurfaceView mSurfaceView;
@BindView(R.id.if_tv_close)
IconFontTextView ifTvClose;
@BindView(R.id.iv_head)
ImageView ivHead;
@BindView(R.id.tv_name)
TextView tvName;
@BindView(R.id.tv_location)
TextView tvLocation;
@BindView(R.id.rl_head)
RelativeLayout rlHead;
@BindView(R.id.if_tv_relation)
IconFontTextView ifTvRelation;
@BindView(R.id.tv_relation)
TextView tvRelation;
@BindView(R.id.ll_relation)
LinearLayout llRelation;
@BindView(R.id.tv_num)
TextView tvNum;
@BindView(R.id.buyer_come_notice)
RecyclerView buyerComeNotice;
@BindView(R.id.buyer_comment)
RecyclerView buyerComment;
@BindView(R.id.rl_buyer)
LinearLayout rlBuyer;
@BindView(R.id.if_tv_video_bg)
IconFontTextView ifTvVideoBg;
@BindView(R.id.if_tv_video_icon)
IconFontTextView ifTvVideoIcon;
@BindView(R.id.tv_like_num)
TextView tvLikeNum;
@BindView(R.id.rl_like)
RelativeLayout rlLike;
@BindView(R.id.tv_send)
TextView tvSend;
@BindView(R.id.et_content)
EditText etContent;
@BindView(R.id.heart_layout)
HeartLayout heartLayout;

private AliVcMediaPlayer mPlayer;
private static final String TAG = "LiveActivity";
private PowerManager.WakeLock mWakeLock = null;
private boolean isLastWifiConnected = false;
private int mPosition = 0;
private int mVolumn = 50;
private GestureDetector mGestureDetector;
private SurfaceHolder mSurfaceHolder = null;
private PlayerControl mPlayerControl = null;

private int mPlayingIndex = -1;

private StatusListener mStatusListener = null;

private StringBuilder msURI = new StringBuilder("");
private StringBuilder msTitle = new StringBuilder("");

// 标记播放器是否已经停止
private boolean isStopPlayer = false;
// 标记播放器是否已经暂停
private boolean isPausePlayer = false;
private boolean isPausedByUser = false;
//用来控制应用前后台切换的逻辑
private boolean isCurrentRunningForeground = true;

// 重点:发生从wifi切换到4g时,提示用户是否需要继续播放,此处有两种做法:
// 1.从历史位置从新播放
// 2.暂停播放,因为存在网络切换,续播有时会不成功
private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {

@Override
public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        Log.d(TAG, "mobile " + mobNetInfo.isConnected() + " wifi " + wifiNetInfo.isConnected());

        if (!isLastWifiConnected && wifiNetInfo.isConnected()) {
        isLastWifiConnected = true;
        }
        if (isLastWifiConnected && mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
        isLastWifiConnected = false;
        if (mPlayer != null) {
        mPosition = mPlayer.getCurrentPosition();
        // 重点:新增接口,此处必须要将之前的surface释放掉
        mPlayer.releaseVideoSurface();
        //mPlayer.stop();
        //mPlayer.destroy();
        //mPlayer = null;
        }
        dialog();
        }
        }
        };

private PlayerControl.ControllerListener mController = new PlayerControl.ControllerListener() {

@Override
public void notifyController(int cmd, int extra) {
        Message msg = Message.obtain();
        switch (cmd) {
        case PlayerControl.CMD_PAUSE:
        msg.what = CMD_PAUSE;
        break;
        case PlayerControl.CMD_RESUME:
        msg.what = CMD_RESUME;
        break;
        case PlayerControl.CMD_SEEK:
        msg.what = CMD_SEEK;
        msg.arg1 = extra;
        break;
        case PlayerControl.CMD_START:
        msg.what = CMD_START;
        break;
        case PlayerControl.CMD_STOP:
        msg.what = CMD_STOP;
        break;
        case PlayerControl.CMD_VOLUME:
        msg.what = CMD_VOLUME;
        msg.arg1 = extra;

        break;

default:
        break;

        }

        if (TEST != 0) {
        mTimerHandler.sendMessage(msg);
        }
        }
        };
private Handler mTimerHandler = new Handler() {
public void handleMessage(Message msg) {

        switch (msg.what) {

        case CMD_PAUSE:
        pause();
        break;
        case CMD_RESUME:
        start();
        break;
        case CMD_SEEK:
        mPlayer.seekTo(msg.arg1);
        break;
        case CMD_START:
        startToPlay();
        break;
        case CMD_STOP:
        stop();
        break;
        case CMD_VOLUME:
        mPlayer.setVolume(msg.arg1);
        break;
default:
        break;
        }
        }
        };

protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LiveActivity.this);
        builder.setMessage("确认继续播放吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

@Override
public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();

        initSurface();

        }
        });

        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {

@Override
public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        LiveActivity.this.finish();
        }
        });

        builder.create().show();
        }

/**
 * 重点:初始化播放器使用的SurfaceView,此处的SurfaceView采用动态添加
 *
 * @return 是否成功
 */
private boolean initSurface() {

        mGestureDetector = new GestureDetector(this, new MyGestureListener());

        mSurfaceView.setZOrderOnTop(false);

        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
private long mLastDownTimestamp = 0;

@Override
public boolean onTouch(View v, MotionEvent event) {

        if (mGestureDetector.onTouchEvent(event))
        return true;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        mLastDownTimestamp = System.currentTimeMillis();
        return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
        if (mPlayer != null && !mPlayer.isPlaying() && mPlayer.getDuration() > 0) {
        start();

        return false;
        }

        //just show the progress bar
        if ((System.currentTimeMillis() - mLastDownTimestamp) > 200) {
//                        show_progress_ui(true);
//                        mTimerHandler.postDelayed(mUIRunnable, 3000);
        return true;
        } else {
        if (mPlayer != null && mPlayer.getDuration() > 0)
        pause();

        }
        return false;
        }
        return false;
        }
        });

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceHolderCB);

        getPlayUrl();

        return true;
        }

private void getPlayUrl() {
        mPlayingIndex = getVideoSourcePath(mPlayingIndex, msURI, msTitle);
        }

private int getVideoSourcePath(int curIndex, StringBuilder sURI, StringBuilder sTitle) {
        //clear all now
        sURI.delete(0, sURI.length());
        sTitle.delete(0, sTitle.length());

        Bundle bundle = (Bundle) getIntent().getExtras();
        int selected = -1;
        if (curIndex == -1) { //we play the selected item
        sTitle.append(bundle.getString("TITLE"));
        sURI.append(bundle.getString("URI"));
        }
        Bundle loopBundle = bundle.getBundle("loopList");
        if (loopBundle != null) {
        int count = loopBundle.getInt("ItemCount");
        if (curIndex == -1) {
        selected = loopBundle.getInt("SelectedIndex");
        } else {
        selected = curIndex + 1;
        selected = (selected == count ? 0 : selected);
        sURI.append(loopBundle.getString("URI" + selected));
        sTitle.append(loopBundle.getString("TITLE" + selected));
        }
        }
        return selected;
        }

private SurfaceHolder.Callback mSurfaceHolderCB = new SurfaceHolder.Callback() {
@SuppressWarnings("deprecation")
public void surfaceCreated(SurfaceHolder holder) {
        holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
        holder.setKeepScreenOn(true);
        Log.d(TAG, "AlivcPlayer onSurfaceCreated.");

        // 重点:
        if (mPlayer != null) {
        // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
        mPlayer.setVideoSurface(mSurfaceView.getHolder().getSurface());
        } else {
        // 创建并启动播放器
        startToPlay();
        }

        if (mPlayerControl != null)
        mPlayerControl.start();
        Log.d(TAG, "AlivcPlayeron SurfaceCreated over.");
        }

public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.d(TAG, "onSurfaceChanged is valid ? " + holder.getSurface().isValid());
        if (mPlayer != null)
        mPlayer.setSurfaceChanged();
        }

public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "onSurfaceDestroy.");

        if (mPlayer != null) {
        mPlayer.releaseVideoSurface();
        }
        }
        };

private boolean startToPlay() {
        Log.d(TAG, "start play.");
//        resetUI();

        if (mPlayer == null) {
        // 初始化播放器
        mPlayer = new AliVcMediaPlayer(this, mSurfaceView);
        mPlayer.setPreparedListener(new VideoPreparedListener());
        mPlayer.setErrorListener(new VideoErrorListener());
        mPlayer.setInfoListener(new VideoInfolistener());
        mPlayer.setSeekCompleteListener(new VideoSeekCompletelistener());
        mPlayer.setCompletedListener(new VideoCompletelistener());
        mPlayer.setVideoSizeChangeListener(new VideoSizeChangelistener());
        mPlayer.setBufferingUpdateListener(new VideoBufferUpdatelistener());
        mPlayer.setStopedListener(new VideoStoppedListener());
        // 如果同时支持软解和硬解是有用
        Bundle bundle = (Bundle) getIntent().getExtras();
        mPlayer.setDefaultDecoder(1);
        // 重点: 在调试阶段可以使用以下方法打开native log
        mPlayer.enableNativeLog();

        if (mPosition != 0) {
        mPlayer.seekTo(mPosition);
        }
        }

//        TextView vt = (TextView) findViewById(R.id.video_title);
//        vt.setText(msTitle.toString());
//        vt.setVisibility(View.VISIBLE);


        mPlayer.prepareAndPlay(msURI.toString());
        if (mStatusListener != null)
        mStatusListener.notifyStatus(STATUS_START);

        new Handler().postDelayed(new Runnable() {
public void run() {
//                mDecoderTypeView.setText(NDKCallback.getDecoderType() == 0 ? "HardDeCoder" : "SoftDecoder");
        }
        }, 5000);
        return true;

        }


@Override
protected int setViewId() {
        return R.layout.activity_live;
        }

@Override
protected void findViews() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        }

@Override
protected void initEvent() {
        etContent.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

@Override
public void afterTextChanged(Editable s) {
        if (etContent.getText().length() == 0) {
        tvSend.setVisibility(View.GONE);
        } else {
        tvSend.setVisibility(View.VISIBLE);
        }
        }
        });
        }

@Override
protected void init() {
//        //创建player对象
//        mPlayer = new AliVcMediaPlayer(this, mSurfaceView);
////        // 设置图像适配屏幕，适配最长边
////        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//        // 设置图像适配屏幕，适配最短边，超出部分裁剪
//        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//        mPlayer.setErrorListener(new VideoErrorListener());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);

        mPlayingIndex = -1;

        if (TEST == 1) {
        mPlayerControl = new PlayerControl(this);
        mPlayerControl.setControllerListener(mController);
        }
        acquireWakeLock();
        initSurface();
        }


private class VideoErrorListener implements AliVcMediaPlayer.MediaPlayerErrorListener {
    public void onError(int what, int extra) {
        switch (what) {
            case MediaPlayer.ALIVC_ERR_LOADING_TIMEOUT:
                Log.e(TAG, "onError: " + "缓冲超时,请确认网络连接正常后重试");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_NO_INPUTFILE:
                Log.e(TAG, "onError: " + "no input file");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_NO_VIEW:
                Log.e(TAG, "onError: " + "no surface");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_INVALID_INPUTFILE:
                Log.e(TAG, "onError: " + "视频资源或者网络不可用");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_NO_SUPPORT_CODEC:
                Log.e(TAG, "onError: " + "no codec");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_FUNCTION_DENIED:
                Log.e(TAG, "onError: " + "no priority");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_UNKNOWN:
                Log.e(TAG, "onError: " + "unknown error");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_NO_NETWORK:
                Log.e(TAG, "onError: " + "视频资源或者网络不可用");
                mPlayer.reset();
                break;
            case MediaPlayer.ALIVC_ERR_ILLEGALSTATUS:
                Log.e(TAG, "onError: " + "illegal call");
                break;
            case MediaPlayer.ALIVC_ERR_NOTAUTH:
                Log.e(TAG, "onError: " + "auth failed");
                break;
            case MediaPlayer.ALIVC_ERR_READD:
                Log.e(TAG, "onError: " + "资源访问失败,请重试");
                mPlayer.reset();
                break;
            default:
                break;
        }
    }
}

    @Override
    protected void loadData() {
//设置缺省编码类型：0表示硬解；1表示软解；
        //如果缺省为硬解，在使用硬解时如果解码失败，会尝试使用软解
        //如果缺省为软解，则一直使用软解，软解较为耗电，建议移动设备尽量使用硬解
//        mPlayer.setDefaultDecoder(0);
        //如果从历史点开始播放
        //mPlayer.seekTo(position);

    }

//
//    @OnClick({R.id.iv_head, R.id.ll_relation, R.id.if_tv_close, R.id.rl_like, R.id.tv_send})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_head:
//
//                break;
//            case R.id.ll_relation:
//
//                break;
//            case R.id.rl_like:
//                heartLayout.addFavor();
//                break;
//            case R.id.tv_send:
//
//                break;
//            case R.id.if_tv_close:
//                finish();
//                break;
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        //准备开始播放
//        msURI = getIntent().getStringExtra("url");
//        Log.e(TAG, "onStart: "+msURI );
//        mPlayer.prepareAndPlay(msURI.toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 主动停止播放
//        mPlayer.stop();
    }

    private void acquireWakeLock() {
        if (mWakeLock == null) {
            PowerManager pMgr = (PowerManager) this.getSystemService(this.POWER_SERVICE);
            mWakeLock = pMgr.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
                    "SmsSyncService.sync() wakelock.");

        }
        mWakeLock.acquire();
    }

    private void releaseWakeLock() {
        mWakeLock.release();
        mWakeLock = null;
    }

private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {

        final double FLING_MIN_DISTANCE = 0.5;
        final double FLING_MIN_VELOCITY = 0.5;

        if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE
                && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
            onVolumeSlide(1);
        }
        if (e1.getY() - e2.getY() < FLING_MIN_DISTANCE
                && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
            onVolumeSlide(-1);
        }

        return super.onScroll(e1, e2, distanceX, distanceY);
    }
}

    private void onVolumeSlide(int vol) {
        if (mPlayer != null) {
            mVolumn += vol;
            if (mVolumn > 100)
                mVolumn = 100;
            if (mVolumn < 0)
                mVolumn = 0;
            mPlayer.setVolume(mVolumn);
        }
    }

    //start the video
    private void start() {

        if (mPlayer != null) {
            isPausePlayer = false;
            isPausedByUser = false;
            isStopPlayer = false;
            mPlayer.play();
            if (mStatusListener != null)
                mStatusListener.notifyStatus(STATUS_RESUME);
//            show_pause_ui(false, false);
//            show_progress_ui(false);
        }
    }

    //stop the video
    private void stop() {
        Log.d(TAG, "AudioRender: stop play");
        if (mPlayer != null) {
            mPlayer.stop();
            if (mStatusListener != null)
                mStatusListener.notifyStatus(STATUS_STOP);
            mPlayer.destroy();
            mPlayer = null;
        }
    }


    //pause the video
    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
            isPausePlayer = true;
            isPausedByUser = true;
            if (mStatusListener != null)
                mStatusListener.notifyStatus(STATUS_PAUSE);
//            show_pause_ui(true, false);
//            show_progress_ui(true);
        }
    }

    public void setStatusListener(StatusListener listener) {
        mStatusListener = listener;
    }

/**
 * 准备完成监听器:调度更新进度
 */
private class VideoPreparedListener implements MediaPlayer.MediaPlayerPreparedListener {

    @Override
    public void onPrepared() {
        Log.d(TAG, "onPrepared");
        if (mPlayer != null) {
            mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//                update_total_duration(mPlayer.getDuration());
//                mTimerHandler.postDelayed(mRunnable, 1000);
//                show_progress_ui(true);
//                mTimerHandler.postDelayed(mUIRunnable, 3000);
        }
    }
}

/**
 * 信息通知监听器:重点是缓存开始/结束
 */
private class VideoInfolistener implements MediaPlayer.MediaPlayerInfoListener {

    public void onInfo(int what, int extra) {
        Log.d(TAG, "onInfo what = " + what + " extra = " + extra);
        switch (what) {
            case MediaPlayer.MEDIA_INFO_UNKNOW:
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //pause();
//                    show_buffering_ui(true);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                //start();
//                    show_buffering_ui(false);
                break;
            case MediaPlayer.MEDIA_INFO_TRACKING_LAGGING:
                break;
            case MediaPlayer.MEDIA_INFO_NETWORK_ERROR:
//                    report_error("�������!", true);
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                if (mPlayer != null)
                    Log.d(TAG, "on Info first render start : " + ((long) mPlayer.getPropertyDouble(AliVcMediaPlayer.FFP_PROP_DOUBLE_1st_VFRAME_SHOW_TIME, -1) - (long) mPlayer.getPropertyDouble(AliVcMediaPlayer.FFP_PROP_DOUBLE_OPEN_STREAM_TIME, -1)));

                break;
        }
    }
}

    private boolean mEnableUpdateProgress = true;

/**
 * 快进完成监听器
 */
private class VideoSeekCompletelistener implements MediaPlayer.MediaPlayerSeekCompleteListener {

    public void onSeekCompleted() {
        mEnableUpdateProgress = true;
    }
}

/**
 * 视频播完监听器
 */
private class VideoCompletelistener implements MediaPlayer.MediaPlayerCompletedListener {

    public void onCompleted() {
        Log.d(TAG, "onCompleted.");

        if (mPlayer != null) {
//            stop();
//                mTimerHandler.removeCallbacks(mRunnable);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(LiveActivity.this);
        builder.setMessage("播放结束");

        builder.setTitle("提示");


        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                LiveActivity.this.finish();
            }
        });

        builder.create().show();
    }
}

/**
 * 视频大小变化监听器
 */
private class VideoSizeChangelistener implements MediaPlayer.MediaPlayerVideoSizeChangeListener {

    public void onVideoSizeChange(int width, int height) {
        Log.d(TAG, "onVideoSizeChange width = " + width + " height = " + height);
    }
}

/**
 * 视频缓存变化监听器: percent 为 0~100之间的数字】
 */
private class VideoBufferUpdatelistener implements MediaPlayer.MediaPlayerBufferingUpdateListener {

    public void onBufferingUpdateListener(int percent) {

    }
}

private class VideoStoppedListener implements MediaPlayer.MediaPlayerStopedListener {
    @Override
    public void onStopped() {
        Log.d(TAG, "onVideoStopped.");
        isStopPlayer = true;
    }
}

    @Override
    protected void onDestroy() {
//        if (mPlayer != null) {
////            stop();
//            mTimerHandler.removeCallbacks(mRunnable);
//        }

        releaseWakeLock();

        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
        // 重点:在 activity destroy的时候,要停止播放器并释放播放器
        if (mPlayer != null) {
            mPosition = mPlayer.getCurrentPosition();
            stop();
            if (mPlayerControl != null)
                mPlayerControl.stop();
        }

        super.onDestroy();

    }
}
