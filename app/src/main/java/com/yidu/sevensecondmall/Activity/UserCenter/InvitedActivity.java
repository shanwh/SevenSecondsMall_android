package com.yidu.sevensecondmall.Activity.UserCenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;
import com.yidu.sevensecondmall.Activity.BaseActivity;
import com.yidu.sevensecondmall.DAO.UserDAO;
import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.ViewUtils;
import com.yidu.sevensecondmall.utils.onekeyshare.OnekeyShare;
import com.yidu.sevensecondmall.utils.onekeyshare.OnekeyShareTheme;
import com.yidu.sevensecondmall.views.widget.iconfonts.IconFontTextView;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public class InvitedActivity extends BaseActivity {
    @BindView(R.id.back)
    IconFontTextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //    @BindView(R.id.if_tv_add)
//    IconFontTextView ifTvAdd;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    //    @BindView(R.id.rv)
//    RecyclerView rv;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;

    private String reg_url;

    @Override
    protected int setViewId() {
        return R.layout.activity_invited;
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
        toolbarTitle.setText("我的邀请");
        toolbarRight.setText("分享");
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ContextCompat.checkSelfPermission(InvitedActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                        ActivityCompat.requestPermissions(InvitedActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                SCANNIN_GREQUEST_CODE);
                    } else {
                        //有权限，直接拍照
                        File file = ViewUtils.CutSreen(toolbarRight.getRootView(), InvitedActivity.this);
                        Log.e(TAG, "onClick: " + file.getAbsolutePath());
                        showShare(InvitedActivity.this, null, false, file.getAbsolutePath(), reg_url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {
        UserDAO.inviteUser(new BaseCallBack() {
            @Override
            public void success(Object data) {
                try {
                    JSONObject content = new JSONObject(data.toString());
                    if (content.has("inviterinfo")) {
                        JSONObject inviterinfo = content.getJSONObject("inviterinfo");
                        String invited_nums = inviterinfo.getString("invited_nums");
                        tvNum.setText(invited_nums);
                        reg_url = "http://" + inviterinfo.getString("reg_url");
                        Bitmap bitmap = EncodingUtils.createQRCode(reg_url, 500, 500, null);
                        iv.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                showShortToast(data + "");
            }
        });
    }

    private static final String TAG = "InvitedActivity";

    @OnClick({R.id.back, R.id.if_tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.if_tv_add:
                try {
                    if (ContextCompat.checkSelfPermission(InvitedActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                        ActivityCompat.requestPermissions(InvitedActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                SCANNIN_GREQUEST_CODE);
                    } else {
                        //有权限，直接拍照
                        File file = ViewUtils.CutSreen(view.getRootView(), InvitedActivity.this);
                        Log.e(TAG, "onClick: " + file.getAbsolutePath());
                        showShare(this, null, false, file.getAbsolutePath(), reg_url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    /**
     * 演示调用ShareSDK执行分享
     *
     * @param context
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     * @param showContentEdit 是否显示编辑页
     */
    public static void showShare(Context context, String platformToShare, boolean showContentEdit, String img, String reg_url
//                                 String shareTitle, String shareTip, String shareIcon
    ) {
        OnekeyShare oks = new OnekeyShare();
        oks.setSilent(!showContentEdit);
        if (platformToShare != null) {
            oks.setPlatform(platformToShare);
        }
        //ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();
        // 在自动授权时可以禁用SSO方式
        oks.disableSSOWhenAuthorize();
        //oks.setAddress("12345678901"); //分享短信的号码和邮件的地址
        oks.setTitle("DING网分享");
        oks.setText("DING网分享");
        oks.setTitleUrl(reg_url);
        oks.setImagePath(img);  //分享sdcard目录下的图片
//        oks.setImageUrl(shareIcon);
        oks.setUrl(reg_url); //微信不绕过审核分享链接
//        oks.setFilePath("");  //filePath用于视频分享
//        oks.setComment(context.getString(R.string.app_share_comment)); //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        oks.setSite("ShareSDK");  //QZone分享完之后返回应用时提示框上显示的名称
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("分享商品!");
        oks.setLatitude(23.169f);
        oks.setLongitude(112.908f);


        // 启动分享
        oks.show(context);
    }


    PopupWindow popupWindow;

    private void showPopupWindow(View view, Bitmap bitmap) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_code_window, null);
        // 设置按钮的点击事件

        ImageView iv = (ImageView) contentView.findViewById(R.id.iv);
        iv.setImageBitmap(bitmap);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.alertdiag_bg));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.showAtLocation(tvTitle, Gravity.CENTER, 0, 0);
            }
        });
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

}
