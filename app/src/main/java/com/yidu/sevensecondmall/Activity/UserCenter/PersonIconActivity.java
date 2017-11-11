package com.yidu.sevensecondmall.Activity.UserCenter;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.bean.User.UserBean;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.HttpApi;
import com.yidu.sevensecondmall.utils.LoginUtils;
import com.yidu.sevensecondmall.views.widget.GlideCircleTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/14.
 */
public class PersonIconActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, View.OnClickListener {

    private static final String TAG = TakePhotoActivity.class.getName();
    View photo;
    ImageView icon;
    LinearLayout back;
    View selectpic;
    View savepic;
    TextView title;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private CompressConfig compressConfig;
    private CropOptions cropOptions;
    private Uri imageUri;
    private ArrayList<File> filelist = new ArrayList<>();

    private UserBean bean;

    private ProgressDialog dialog;

    @Override
    protected int setViewId() {
        return R.layout.icon_activity;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("请等待");
        back = (LinearLayout) findViewById(R.id.back);
        icon = (ImageView) findViewById(R.id.icon);
        photo = findViewById(R.id.photo);
        selectpic = findViewById(R.id.selectpic);
        title = (TextView) findViewById(R.id.title_name);
        savepic = findViewById(R.id.savepic);
        title.setText("个人头像");
        toolbarTitle.setText("个人头像");
        toolbarRight.setText("保存");
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePic();
            }
        });
        back.setOnClickListener(this);
        photo.setOnClickListener(this);
        selectpic.setOnClickListener(this);
        savepic.setOnClickListener(this);
        initPath();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {
        if (LoginUtils.isLogin()) {
            UserDAO.getUserInfo(new BaseCallBack() {
                @Override
                public void success(Object data) {
                    if (data != null) {
                        bean = (UserBean) data;
                        Glide.with(PersonIconActivity.this)
                                .load(HttpApi.getFullImageUrl(bean.getHead_pic()))
                                .transform(new GlideCircleTransform(PersonIconActivity.this))
                                .into(icon);
                    }
                }

                @Override
                public void failed(int errorCode, Object data) {
                    showShortToast(data + "");
                }
            });
        }
    }

    @Override
    protected void loadData() {

    }

    private void initPath() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        File file = new File(Environment.getExternalStorageDirectory(), "/Ding/" + System.currentTimeMillis() + ".jpeg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
        compressConfig = new CompressConfig.Builder().setMaxSize(1000 * 1024).create();
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setOutputX(500).setOutputY(500).create();
    }


    public TakePhoto getPhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    protected void handlerSaveInstanceState(Bundle savedInstanceState) {
        getPhoto().onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getPhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getPhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        Log.e(TAG, "takeSuccess：" + result.getImage().getCompressPath());
        try {
//            Bitmap bitmap = Bimp.revitionImageSize(result.getImage().getCompressPath());

//            Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());

            Glide.with(PersonIconActivity.this)
                    .load(result.getImage().getCompressPath())
                    .transform(new GlideCircleTransform(PersonIconActivity.this))
                    .into(icon);
            File file = new File(result.getImage().getCompressPath());
            if (filelist.size() == 0) {
                filelist.add(file);
            } else {
                filelist.set(0, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(PersonIconActivity.this, "拍照失败，请注意权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                getPhoto().onEnableCompress(compressConfig, true);
                getPhoto().onPickFromCaptureWithCrop(imageUri, cropOptions);
                break;
            case R.id.selectpic:
                getPhoto().onEnableCompress(compressConfig, true);
                getPhoto().onPickFromDocumentsWithCrop(imageUri, cropOptions);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.savepic:
                savePic();

                break;
            default:
                ;
        }
    }

    private  void savePic(){
        if (filelist.size() > 0) {
            dialog.show();
            OkHttpUtils.post(HttpApi.uploadurl)
                    .tag(this)
                    .params("token", LoginUtils.getToken())
                    .params("user_id", LoginUtils.getUserId())
                    .params("policy", HttpApi.policy)
                    .params("signature", HttpApi.signature)
                    .addFileParams("file", filelist)
                    .execute(new AbsCallback<Object>() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws Exception {
                            dialog.dismiss();
                            return null;
                        }

                        @Override
                        public void onResponse(boolean isFromCache, Object o, Request request, @Nullable Response response) {
                            dialog.dismiss();
                            try {
                                String string = response.body().string();
                                Log.e(TAG, "onResponse: " + string);
                                JSONObject jsonObject = new JSONObject(string);
                                String code = jsonObject.getString("message");
                                if (code.equals("ok")) {
                                    Toast.makeText(getApplicationContext(), "头像更改成功！", Toast.LENGTH_SHORT).show();
                                    String url = jsonObject.getString("url");
                                    filelist.clear();
                                    SystemUtil.setSharedString("user_head", url);
                                    SystemUtil.setSharedBoolean("changeHead", true);
                                } else {
                                    Toast.makeText(getApplicationContext(), "头像更改失败！", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                            super.onError(isFromCache, call, response, e);
                            dialog.dismiss();
                            Toast.makeText(SystemUtil.getContext(), "头像更改失败！请检查网络连接！", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                            super.upProgress(currentSize, totalSize, progress, networkSpeed);
                            dialog.dismiss();
                        }
                    });
        } else {
            showShortToast("请选择新头像");
        }

    }
}
