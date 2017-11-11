package com.yidu.sevensecondmall.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.yidu.sevensecondmall.SevenSecondApplication;

/**
 * Created by Administrator on 2017/5/20 0020.
 */
public class PermissionUtil {

    public static String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};

    public static boolean isLacksOfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(
                    SevenSecondApplication.getInstance().getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED;
        }
        return false;
    }


    public static boolean hasPermission(Context context, String permission){
       return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void reqPermission(Activity activity, int code, String[] permissionArray){
        ActivityCompat.requestPermissions(activity, permissionArray, code);
    }
}
