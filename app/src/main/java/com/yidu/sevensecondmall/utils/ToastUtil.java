package com.yidu.sevensecondmall.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class ToastUtil {

    private static Toast toast;

    public static final void showToast(Context context,String str){
        if (toast == null){
            toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
        }else {
            toast.setText(str);
        }
        toast.show();
    }
    public static final void showToast(Context context,String str,boolean isLongToast){
        if (isLongToast) {
            if (toast == null) {
                toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            } else {
                toast.setText(str);
            }
            toast.show();
        }else {
         showToast(context,str);
        }
    }

    public static final void showToastInThread(final Context context, final String str, final boolean isLongToast){

            if (toast == null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        toast = Toast.makeText(context, str,isLongToast? Toast.LENGTH_LONG:Toast.LENGTH_SHORT);
                    }
                });

            } else {
                toast.setText(str);
            }
            toast.show();
    }
}
