package com.yidu.sevensecondmall.QrCode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.google.zxing.Result;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Login.ResigterActivity;
import com.yidu.sevensecondmall.Activity.UserCenter.BindingActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.QrCode.camera.CameraManager;
import com.yidu.sevensecondmall.QrCode.decode.CaptureActivityHandler;
import com.yidu.sevensecondmall.QrCode.decode.DecodeImageCallback;
import com.yidu.sevensecondmall.QrCode.decode.DecodeImageThread;
import com.yidu.sevensecondmall.QrCode.decode.DecodeManager;
import com.yidu.sevensecondmall.QrCode.decode.InactivityTimer;
import com.yidu.sevensecondmall.QrCode.view.QrCodeFinderView;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.utils.ToastUtil;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by xingli on 12/26/15.
 * <p/>
 * 二维码扫描类。
 */
public class QrCodeActivity extends Activity implements Callback, OnClickListener {
    private static final String TAG = "QrCodeActivity";
    private static final int REQUEST_SYSTEM_PICTURE = 0;
    private static final int REQUEST_PICTURE = 1;
    public static final int MSG_DECODE_SUCCEED = 1;
    public static final int MSG_DECODE_FAIL = 2;
    private CaptureActivityHandler mCaptureActivityHandler;
    private boolean mHasSurface;
    private boolean mPermissionOk;
    private InactivityTimer mInactivityTimer;
    private QrCodeFinderView mQrCodeFinderView;
    private SurfaceView mSurfaceView;
    //    private View mLlFlashLight;
    private final DecodeManager mDecodeManager = new DecodeManager();
    /**
     * 声音和振动相关参数
     */
    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    private MediaPlayer mMediaPlayer;
    private boolean mPlayBeep;
    private boolean mVibrate;
    private boolean mNeedFlashLightOpen = true;
    //    private ImageView mIvFlashLight;
//    private TextView mTvFlashLightText;
    private Executor mQrCodeExecutor;
    private Handler mHandler;

    private ProgressDialog dialog;
    private String rid = "";

    //    private IconFontTextView img;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;
    private CropOptions cropOptions;
    private Uri imageUri;
    private ArrayList<File> filelist = new ArrayList<>();
    private IconFontTextView iconFontTextView;
    private String strCode;
    private String strName;
    private String strPrice;
    private String strPic;
    private String origincountry;
    private String originplace;
    private String unspsc;
    private String type;
    private String strBrand;

    private TextView toolbarTitle ;
    private TextView toolbarRight ;

    private static Intent createIntent(Context context) {
        Intent i = new Intent(context, QrCodeActivity.class);
        return i;
    }

    public static void launch(Context context) {
        Intent i = createIntent(context);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
        initData();
        dialog = new ProgressDialog(this);
        dialog.setMessage("请等待");
        hyalinize();
    }

    /**
     * 沉浸式
     **/
    private void hyalinize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    private void checkPermission() {
        boolean hasHardware = checkCameraHardWare(this);
        if (hasHardware) {
            if (!hasCameraPermission()) {
                findViewById(R.id.qr_code_view_background).setVisibility(View.VISIBLE);
                mQrCodeFinderView.setVisibility(View.GONE);
                mPermissionOk = false;
            } else {
                mPermissionOk = true;
            }
        } else {
            mPermissionOk = false;
            finish();
        }
    }

    private void initView() {
        IconFontTextView tvPic = (IconFontTextView) findViewById(R.id.qr_code_header_black_pic);
//        mIvFlashLight = (ImageView) findViewById(R.id.qr_code_iv_flash_light);
//        mTvFlashLightText = (TextView) findViewById(R.id.qr_code_tv_flash_light);
        mQrCodeFinderView = (QrCodeFinderView) findViewById(R.id.qr_code_view_finder);
        mSurfaceView = (SurfaceView) findViewById(R.id.qr_code_preview_view);
//        mLlFlashLight = findViewById(R.id.qr_code_ll_flash_light);
        mHasSurface = false;
//        mIvFlashLight.setOnClickListener(this);
        tvPic.setOnClickListener(this);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("二维码扫一扫");
        toolbarRight = (TextView) findViewById(R.id.toolbar_right);
        toolbarRight.setBackground(getResources().getDrawable(R.drawable.toolbar_pic));

        iconFontTextView = (IconFontTextView) findViewById(R.id.back);
        iconFontTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                QrCodeActivity.this.finish();
            }
        });

//        img = (IconFontTextView) findViewById(R.id.img);
//        img.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }


    private void initData() {
        CameraManager.init(this);
        mInactivityTimer = new InactivityTimer(QrCodeActivity.this);
        mQrCodeExecutor = Executors.newSingleThreadExecutor();
        mHandler = new WeakHandler(this);
    }

    private boolean hasCameraPermission() {
        PackageManager pm = getPackageManager();
        return PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CAMERA", getPackageName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
        if (!mPermissionOk) {
            mDecodeManager.showPermissionDeniedDialog(this);
            return;
        }
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
//        turnFlashLightOff();
        if (mHasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        mPlayBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            mPlayBeep = false;
        }
        initBeepSound();
        mVibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCaptureActivityHandler != null) {
            mCaptureActivityHandler.quitSynchronously();
            mCaptureActivityHandler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        if (null != mInactivityTimer) {
            mInactivityTimer.shutdown();
        }
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     */
    public void handleDecode(Result result) {
        mInactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (null == result) {
            mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener() {
                @Override
                public void refresh() {
                    restartPreview();
                }
            });
        } else {
            String resultString = result.getText();
            handleResult(resultString);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException e) {
            // 基本不会出现相机不存在的情况
            Toast.makeText(this, getString(R.string.qr_code_camera_not_found), Toast.LENGTH_SHORT).show();
            finish();
            return;
        } catch (RuntimeException re) {
            re.printStackTrace();
            mDecodeManager.showPermissionDeniedDialog(this);
            return;
        }
        mQrCodeFinderView.setVisibility(View.VISIBLE);
        mSurfaceView.setVisibility(View.VISIBLE);
//        mLlFlashLight.setVisibility(View.VISIBLE);
        findViewById(R.id.qr_code_view_background).setVisibility(View.GONE);
        if (mCaptureActivityHandler == null) {
            mCaptureActivityHandler = new CaptureActivityHandler(this);
        }
    }

    private void restartPreview() {
        if (null != mCaptureActivityHandler) {
            mCaptureActivityHandler.restartPreviewAndDecode();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /* 检测相机是否存在 */
    private boolean checkCameraHardWare(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mHasSurface) {
            mHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
    }

    public Handler getCaptureActivityHandler() {
        return mCaptureActivityHandler;
    }

    private void initBeepSound() {
        if (mPlayBeep && mMediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(mBeepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mMediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mMediaPlayer = null;
            }
        }
    }

    private void playBeepSoundAndVibrate() {
        if (mPlayBeep && mMediaPlayer != null) {
            mMediaPlayer.start();
        }
        if (mVibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener mBeepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.qr_code_iv_flash_light:
//                if (mNeedFlashLightOpen) {
//                    turnFlashlightOn();
//                } else {
//                    turnFlashLightOff();
//                }
//                break;
            case R.id.qr_code_header_black_pic:
                if (!hasCameraPermission()) {
                    mDecodeManager.showPermissionDeniedDialog(this);
                } else {
                    openSystemAlbum();
                }
                break;
        }
    }

    private void openSystemAlbum() {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, REQUEST_SYSTEM_PICTURE);
    }

//    private void turnFlashlightOn() {
//        mNeedFlashLightOpen = false;
////        mTvFlashLightText.setText(getString(R.string.qr_code_close_flash_light));
////        mIvFlashLight.setBackgroundResource(R.drawable.flashlight_turn_off);
//        CameraManager.get().setFlashLight(true);
//    }
//
//    private void turnFlashLightOff() {
//        mNeedFlashLightOpen = true;
////        mTvFlashLightText.setText(getString(R.string.qr_code_open_flash_light));
////        mIvFlashLight.setBackgroundResource(R.drawable.flashlight_turn_on);
//        CameraManager.get().setFlashLight(false);
//    }

    private void handleResult(String resultString) {

//        if (resultString == null
//                || !resultString.contains("apply_for_district")
//                && !resultString.contains("becomepromoter")
//                && !resultString.contains("product")){
//            mDecodeManager.showResultDialog(QrCodeActivity.this, "请扫描7秒商城的二维码", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//                    dialog.dismiss();
//                    restartPreview();
//                }
//            });
//            return;
//        }
        try {
            dialog.dismiss();
            Log.e(TAG, "handleResult: " + resultString);
            if (resultString.contains("/")) {
                final String[] split = resultString.split("/");
                if ("invite_mobile".equals(split[split.length - 2])) {
                    SystemUtil.setSharedString("invited_mob", split[split.length - 1]);
                }
                if (LoginUtils.isLogin()) {
                    Intent intent;
                    switch (split[split.length - 2]) {
                        case "invite_mobile":
                            UserDAO.inviteUser(new BaseCallBack() {
                                @Override
                                public void success(Object data) {
                                    try {
                                        JSONObject content = new JSONObject(data.toString());
                                        if (content.has("inviterinfo")) {
                                            JSONObject inviterinfo = content.getJSONObject("inviterinfo");
                                            boolean mobile = inviterinfo.has("mobile");
                                            if (mobile) {
                                                String string = inviterinfo.getString("mobile");
                                                if (string != null && !string.equals("")) {
                                                    ToastUtil.showToast(QrCodeActivity.this, "已有邀请", true);
                                                } else {
                                                    Intent i = new Intent(QrCodeActivity.this, BindingActivity.class);
                                                    startActivity(i);
                                                    SystemUtil.setSharedBoolean("isFirstBand", true);
                                                    finish();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(int errorCode, Object data) {
                                    ToastUtil.showToast(QrCodeActivity.this, "" + data);
                                }
                            });
                            break;
                    }
                } else {
                    dialog.show();
                    Intent intent = new Intent(QrCodeActivity.this, ResigterActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    finish();
                }
            } else {
                strCode = resultString;
                UserDAO.findshopCode(resultString, new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try {
                            JSONObject content = new JSONObject(data.toString());
                            if (content.has("showapi_res_body")) {
                                JSONObject result = content.getJSONObject("showapi_res_body");
                                if (result.getBoolean("flag")) {
                                    if (result.has("goodsName")) {
                                        strName = result.getString("goodsName");
                                    }
                                    if (result.has("price")) {
                                        strPrice = result.getString("price");
                                        if (strPrice == null || "".equals(strPrice)) {
                                            strPrice = "暂无价格";
                                        }
                                    }
                                    if (result.has("sptmImg")) {
                                        strPic = result.getString("sptmImg");
                                    }

                                    if (result.has("trademark")) {
                                        strBrand = result.getString("trademark");
                                    }

                                    if (result.has("manuName")) {
                                        origincountry = result.getString("manuName");
                                        if (origincountry == null) origincountry = "";
                                    }
//                                    if (result.has("originplace")) {
//                                        if (origincountry == null) origincountry = "";
//                                        String originplace = result.getString("originplace");
//                                        if (originplace == null) originplace = "";
//                                        origincountry = origincountry + originplace;
//                                    }
                                    if (result.has("goodsType")) {
                                        unspsc = result.getString("goodsType");
                                    }
                                    if (result.has("spec")) {
                                        type = result.getString("spec");
                                    }
                                    showPopupWindow();
                                }else {
                                    ToastUtil.showToast(QrCodeActivity.this, result.getString("msg"));
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        ToastUtil.showToast(QrCodeActivity.this, data + "");
                    }
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
//            mDecodeManager.showResultDialog(QrCodeActivity.this, "请扫描"+ R.string.app_name +"的二维码", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int i) {
//                    dialog.dismiss();
//                    restartPreview();
//                }
//            });
        }
//        if (TextUtils.isEmpty(resultString)) {
//            mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener() {
//                @Override
//                public void refresh() {
//                    restartPreview();
//                }
//            });
//        } else {
//            mDecodeManager.showResultDialog(this, resultString, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    restartPreview();
//                }
//            });
//        }
    }

    CustomPopWindow popupWindow;

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(QrCodeActivity.this).inflate(R.layout.pop_window_bar_code, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        int v = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, QrCodeActivity.this.getResources().getDisplayMetrics());

        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(v, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小,默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.5f) // 控制亮度
                .create()//创建PopupWindow
                .showAtLocation(iconFontTextView, Gravity.CENTER, 0, 0);//显示PopupWindow
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        TextView code = (TextView) contentView.findViewById(R.id.code);
        code.setText(strCode);
        TextView name = (TextView) contentView.findViewById(R.id.name);
        name.setText(strName);
        TextView price = (TextView) contentView.findViewById(R.id.price);
        price.setText("价格：" + strPrice);
        TextView brand = (TextView) contentView.findViewById(R.id.brand);
        if (strBrand != null && !"".equals(strBrand)) {
            brand.setText(strBrand);
        }
        TextView tvunspsc = (TextView) contentView.findViewById(R.id.unspsc);
        if (unspsc != null && !"".equals(unspsc)) {
            tvunspsc.setText(unspsc);
        }
        TextView tvOrigincountry = (TextView) contentView.findViewById(R.id.origincountry);
        if (origincountry != null && !"".equals(origincountry)) {
            tvOrigincountry.setText(origincountry);
        }
        TextView tvType = (TextView) contentView.findViewById(R.id.type);
        tvType.setText(type);
        ImageView iv = (ImageView) contentView.findViewById(R.id.iv);
        Glide.with(QrCodeActivity.this)
                .load(HttpApi.getFullImageUrl(strPic))
                .placeholder(R.drawable.default_loading_pic)
                .into(iv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PICTURE:
                finish();
                break;
            case REQUEST_SYSTEM_PICTURE:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (null != cursor) {
                    cursor.moveToFirst();
                    String imgPath = cursor.getString(1); // 图片文件路径
                    cursor.close();
                    if (null != mQrCodeExecutor && !TextUtils.isEmpty(imgPath)) {
                        mQrCodeExecutor.execute(new DecodeImageThread(imgPath, mDecodeImageCallback));
                    }
                }
                break;
        }
    }

    private DecodeImageCallback mDecodeImageCallback = new DecodeImageCallback() {
        @Override
        public void decodeSucceed(Result result) {
            mHandler.obtainMessage(MSG_DECODE_SUCCEED, result).sendToTarget();
        }

        @Override
        public void decodeFail(int type, String reason) {
            mHandler.sendEmptyMessage(MSG_DECODE_FAIL);
        }
    };


    private static class WeakHandler extends Handler {
        private WeakReference<QrCodeActivity> mWeakQrCodeActivity;
        private DecodeManager mDecodeManager = new DecodeManager();

        public WeakHandler(QrCodeActivity imagePickerActivity) {
            super();
            this.mWeakQrCodeActivity = new WeakReference<>(imagePickerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            QrCodeActivity qrCodeActivity = mWeakQrCodeActivity.get();
            switch (msg.what) {
                case MSG_DECODE_SUCCEED:
                    Result result = (Result) msg.obj;
                    if (null == result) {
                        mDecodeManager.showCouldNotReadQrCodeFromPicture(qrCodeActivity);
                    } else {
                        String resultString = result.getText();
                        qrCodeActivity.handleResult(resultString);
                    }
                    break;
                case MSG_DECODE_FAIL:
                    mDecodeManager.showCouldNotReadQrCodeFromPicture(qrCodeActivity);
                    break;
            }
            super.handleMessage(msg);
        }
//
//        private void handleResult(String resultString) {
//            QrCodeActivity imagePickerActivity = mWeakQrCodeActivity.get();
//            mDecodeManager.showResultDialog(imagePickerActivity, resultString, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//        }

    }

}