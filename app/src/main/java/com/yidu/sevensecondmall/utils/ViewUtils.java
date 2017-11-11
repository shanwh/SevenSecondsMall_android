package com.yidu.sevensecondmall.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yidu.sevensecondmall.R;
import com.yidu.sevensecondmall.SevenSecondApplication;
import com.yidu.sevensecondmall.views.widget.DensityUtils;
import com.yidu.sevensecondmall.views.widget.tagview.FlowLayout;

import net.qiujuer.genius.kit.handler.Run;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/7.
 * view工具
 */
public class ViewUtils {


    //截屏函数
    public static File CutSreen(View v,Context context){
        View view = v.getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        //测一下
        int currentVersion = Build.VERSION.SDK_INT;
        File dir = new File(Environment.getExternalStorageDirectory() + "/my_image/");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir,System.currentTimeMillis() + ".jpg");
        Uri uri;
//        if(currentVersion >24){
//            context.getExternalCacheDir();
//            uri = FileProvider.getUriForFile(context, "com.yidu.sevensecondmall.fileprovider", file);
//        }else {
            uri = Uri.fromFile(file);
//        }
        writeToFile(bitmap,uri);
        return file;
    }

    public static void writeToFile(Bitmap bitmap, Uri imageUri) {
        if (bitmap == null) return;
        File file = new File(imageUri.getPath());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            bos.flush();
            fos.flush();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) try {
                fos.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    //截图分享
//    public static void CutScreenShare(View v, Activity context, String text,  boolean img){
//        File file = CutSreen(v.getRootView(),(Context)context);
////        UMImage image = new UMImage((Context)context,file);
//
//
//    }


//    public static void manThreadToast(final String msg){
//        Run.onUiAsync(new Action() {
//            @Override
//            public void call() {
//                Toast.makeText(SevenSecondApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    public static void toast(String msg){
        Toast.makeText(SevenSecondApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();

    }

    public static void toast(int resId){
        Toast.makeText(SevenSecondApplication.getInstance(),resId,Toast.LENGTH_SHORT).show();
    }

    public static Dialog makeDialogBottom(Context context, View view){
        final Dialog dialog = new Dialog(context, R.style.DialogBottom);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.x = 0;
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        windowParams.gravity = Gravity.BOTTOM;
        //设置window的布局参数
        window.setAttributes(windowParams);
        // window.setBackgroundDrawableResource(R.drawable.alert_dialog_background);

        // 显示的大小是contentView 的大小
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
    public static Dialog makeDialogCenter(Context context,View view){
        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.9);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);
//        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    /**
     *
     * @param context
     * @param msg
     * @param buttonText 按钮文字，默认是"确定"
     * @param callback 点击确定的回掉，没有传null
     * @return
     */
    public static Dialog makeAlert(Context context,String title,String msg,String buttonText,final OKButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(callback == null) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_alert,null);

        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
        TextView  alertTitle = (TextView) view.findViewById(R.id.alertTitle);
        if(title == null){
            alertTitle.setVisibility(View.GONE);
        }else{
            alertTitle.setText(title);
        }
        tvMsg.setText(msg);

        if(!TextUtils.isEmpty(buttonText)){
            tvOk.setText(buttonText);
        }


        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (callback != null) {
                    callback.onPositive(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.7);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);
        return dialog;
    }


    /**
     *
     * @param context
     * @param msg
     * @param positiveText 确定按钮文字，默认"确定"
     * @param negativeText 取消按钮文字，默认"取消"
     * @param callback 按钮点击回掉
     * @return
     */
    public static Dialog makeConfirm(Context context,String msg , String positiveText,String negativeText,final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_confirm,null);

        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);

        tvMsg.setText(msg);

        if(!TextUtils.isEmpty(positiveText)){
            tvOk.setText(positiveText);
        }
        if(!TextUtils.isEmpty(negativeText)){
            tvCancel.setText(negativeText);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onPositive(dialog);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);
        return dialog;
    }
    public static Dialog  pop_img(Context context,String img_url ,final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);

        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.layout_pop_image,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_instructions);
        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
        if(img_url != null){
            imageView.setImageURI(Uri.parse(img_url));
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;
        windowParams.height = FlowLayout.LayoutParams.MATCH_PARENT;

        window.setAttributes(windowParams);
        return dialog;
    }

    public static Dialog makeUpdate(Context context,String title,String msg , String positiveText,String negativeText,boolean is_must,final ButtonCallback callback){
        final Dialog dialog = new Dialog(context,R.style.Dialog);
        if(!is_must) {
            dialog.setCanceledOnTouchOutside(true);
        }else{
            dialog.setCanceledOnTouchOutside(false);
        }
        View view = View.inflate(context,R.layout.dialog_update,null);

        TextView tvTitle = (TextView)view.findViewById(R.id.tv_title);
        TextView tvMsg  = (TextView)view.findViewById(R.id.tv_msg);
        TextView tvOk = (TextView)view.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);

        tvTitle.setText(title);
        tvMsg.setText(msg);

        if(!TextUtils.isEmpty(positiveText)){
            tvOk.setText(positiveText);
        }
        if(!TextUtils.isEmpty(negativeText)){
            tvCancel.setText(negativeText);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onPositive(dialog);
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(callback!=null){
                    callback.onNegative(dialog);
                }
            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int width = (int)(window.getWindowManager().getDefaultDisplay().getWidth()*0.8);
        windowParams.x = 0;
        windowParams.width = width;

        window.setAttributes(windowParams);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }



    public interface  OKButtonCallback {

        void onPositive(Dialog d) ;
    }

    public interface  ButtonCallback{
        void onPositive(Dialog d);
        void onNegative(Dialog d);
    }

    public static Dialog makeChoice(Context context,final String[] items , final OnChoiceClickListener listener){
        final Dialog dialog = new Dialog(context, R.style.DialogBottom);
        final int padding = DensityUtils.dip2px(context,15);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_choice,null);
        final LinearLayout llItems = (LinearLayout)view.findViewById(R.id.ll_items);
        TextView tvCannel  = (TextView)view.findViewById(R.id.tv_cannel);

        if(items!=null){
            for (int i = 0;i<items.length;i++){
                TextView tv = new TextView(context);
                tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setPadding(padding,padding,padding,padding);
                tv.setGravity(Gravity.CENTER);
                tv.setText(items[i]);
                tv.setBackgroundResource(R.drawable.item_selector);
                final int index = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if(listener!=null){
                            listener.onClick(index,items[index]);
                        }
                    }
                });

                llItems.addView(tv);
            }
        }

        tvCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.x = 0;
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        windowParams.gravity = Gravity.BOTTOM;
        //设置window的布局参数
        window.setAttributes(windowParams);
        // window.setBackgroundDrawableResource(R.drawable.alert_dialog_background);

        // 显示的大小是contentView 的大小
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    public interface OnChoiceClickListener{
        void onClick(int index,String item);
    }


    public static void takePhoto(Context context, final TakePhotoListener listener){
        final Dialog dialog = new Dialog(context, R.style.DialogBottom);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(context,R.layout.dialog_choose_photo,null);
        TextView tvPhoto = (TextView)view.findViewById(R.id.tv_photo);
        TextView tvCamera = (TextView)view.findViewById(R.id.tv_camera);
        TextView tvCannel  = (TextView)view.findViewById(R.id.tv_cannel);
        tvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener!=null){
                    listener.takeByPhoto();
                }
            }
        });
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(listener!=null){
                    listener.takeByCamera();
                }
            }
        });
        tvCannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.x = 0;
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        windowParams.gravity = Gravity.BOTTOM;
        //设置window的布局参数
        window.setAttributes(windowParams);
        // window.setBackgroundDrawableResource(R.drawable.alert_dialog_background);

        // 显示的大小是contentView 的大小
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    public interface TakePhotoListener{
        void takeByPhoto();
        void takeByCamera();
    }

    public interface ReportUserListener{
        void select(Dialog dialog,int i);
    }
//    public static  void  reportUser(Context context,final ReportUserListener listener){
//        final Dialog dialog = new Dialog(context, R.style.DialogBottom);
//        dialog.setCanceledOnTouchOutside(true);
//        View view = View.inflate(context,R.layout.dialog_report_user,null);
//        TextView zhengzhi = (TextView) view.findViewById(R.id.zhengzhi);
//        TextView seqing = (TextView) view.findViewById(R.id.seqing);
//        TextView nonage = (TextView) view.findViewById(R.id.nonage);
//        TextView driving = (TextView) view.findViewById(R.id.driving);
//        TextView other = (TextView) view.findViewById(R.id.other);
//        TextView cancel = (TextView) view.findViewById(R.id.cancel);
//        zhengzhi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener!=null){
//                    listener.select(dialog,0);
//                }
//            }
//        });
//        seqing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener!=null){
//                    listener.select(dialog,1);
//                }
//            }
//        });
//        nonage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener!=null){
//                    listener.select(dialog,2);
//                }
//            }
//        });
//        driving.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener!=null){
//                    listener.select(dialog,3);
//                }
//            }
//        });
//        other.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener!=null){
//                    listener.select(dialog,4);
//                }
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener!=null){
//                    dialog.dismiss();
//                }
//            }
//        });
//        dialog.setContentView(view);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams windowParams = window.getAttributes();
//        windowParams.x = 0;
//        int width = (int) (window.getWindowManager().getDefaultDisplay().getWidth() * 0.9);
//        windowParams.width = width;
//        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
//        windowParams.gravity = Gravity.BOTTOM;
//        //设置window的布局参数
//        window.setAttributes(windowParams);
//        // window.setBackgroundDrawableResource(R.drawable.alert_dialog_background);
//        dialog.show();
//    }

}
