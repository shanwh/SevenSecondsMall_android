package com.yidu.sevensecondmall.utils;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import java.io.File;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;


/**
 * Created by zhuang on 16/6/7.
 */
public final class AppUtils {



    public static int getVersionCode(Context context){
        try{
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = info.versionCode;
            return versionCode;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static String getVersionName(Context context){
        try{
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String versionCode = info.versionName;
            return versionCode;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public static String getMeta(Context context,String key){
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.getString(key);
            return msg;
        }catch (Exception e){
            return "";
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void backgroundAlpha(Activity activity,float bgAlpha)
    {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
       lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    public static void install(Context context, File apk) {
        if (apk == null || !apk.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apk.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static boolean isAppInstalled(Context context,String packageName){
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        }catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            //e.printStackTrace();
        }
        if(packageInfo ==null){
            //System.out.println("没有安装");
            return false;
        }else{
            //System.out.println("已经安装");
            return true;
        }
    }


    public static void callPhone(Context context,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        context.startActivity(intent);
    }


    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getStatusBarHeight(Context context){

        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, statusBarHeight = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            statusBarHeight = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return statusBarHeight;

    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

//    public static String getImageUrl(String relativeUrl){
//        if(TextUtils.isEmpty(relativeUrl))
//            return null;
//        if(relativeUrl.startsWith("http")){
//            return relativeUrl;
//        }
//        return Contants.RESOURCE_DOMAIN + relativeUrl;
//    }
//
//    public static boolean isLogin(){
//        String jsonUser = PreferencesUtils.getString(WhApplication.getInstansce(),Contants.PreferenceKey.USER_ID,"");
//        return !TextUtils.isEmpty(jsonUser);
//    }
//
//    public static String getUserToken(){
//        String token = PreferencesUtils.getString(WhApplication.getInstansce(),Contants.PreferenceKey.USER_TOKEN,"");
//        return token;
//    }
//
//    public static int getTime(){
//        int time =  PreferencesUtils.getInt(WhApplication.getInstansce(),AppUtils.getUserId(),-2);
//        return  time;
//    }
//    public static int getMaxTime(){
//        int maxTime = PreferencesUtils.getInt(WhApplication.getInstansce(),Contants.PreferenceKey.MAXTIME+"",60);
//        return maxTime;
//    }
//    public static int getCurrentPress(){
//        int currentProgress  =  PreferencesUtils.getInt(WhApplication.getInstansce(),Contants.PreferenceKey.PROGRESS,1);
//        return  currentProgress;
//    }
//    public static String getUserId(){
//        String user_id = PreferencesUtils.getString(WhApplication.getInstansce(),Contants.PreferenceKey.USER_ID,"");
//        return user_id;
//    }
//
//    public static String getUserName(){
//        return PreferencesUtils.getString(WhApplication.getInstansce(),Contants.PreferenceKey.USER_NAME,"");
//    }
//
//    public static String getRoomId(){
//        return PreferencesUtils.getString(WhApplication.getInstansce(),Contants.PreferenceKey.ROOM_ID,"");
//    }
//
//    public static String getRoomPic(){
//        String pic = PreferencesUtils.getString(WhApplication.getInstansce(),Contants.PreferenceKey.ROOM_PIC,"");
//        if(TextUtils.isEmpty(pic)){
//            return getAvator(getUserId());
//        }else{
//            return getImageUrl(pic);
//        }
//    }
//
//    public static void setLogin(LoginUserModel.DataBean.LoginUserBean model){
//        PreferencesUtils.putString(WhApplication.getInstansce(), Contants.PreferenceKey.USER_ID,model.getUser_id());
//        PreferencesUtils.putString(WhApplication.getInstansce(),Contants.PreferenceKey.USER_TOKEN,model.getToken());
//        PreferencesUtils.putString(WhApplication.getInstansce(), Contants.PreferenceKey.ROOM_PIC,model.getRoompic());
//        PreferencesUtils.putString(WhApplication.getInstansce(),Contants.PreferenceKey.USER_NAME,model.getUser_name());
//        PreferencesUtils.putString(WhApplication.getInstansce(),Contants.PreferenceKey.ROOM_ID,model.getRoom_id());
//        EventBus.getDefault().post(new Event(Event.ACTION.LOGIN_COMPLTETE));
//        JPushInterface.setAliasAndTags(WhApplication.getInstansce(),model.getUser_id(),null,null);
//
//    }
//
//    public static String getAvator(String userid){
////
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
//        //return "http://uc.youyulive.com/avatar.php?uid="+userid+"&size=small&time="+sdf.format(new Date());
//        return  Contants.SERVER_ADDRESS + "/user/avatar?userid="+userid+"&size=small";
//    }
    public static  String getIcon(String userid){
        return "http://uc.youyulive.com/avatar.php?uid="+userid+"&size=small";
    }
    public static void toggleKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void copyText(Context context,String text){
        ClipboardManager cm =(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //将文本数据复制到剪贴板
        cm.setText(text);
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public static String getShareTitle(){
        String[] titles = new String[]{"来啊，直播啊，反正有大把时光！","最美不是下雨天，是有你的直播间！","有情饮水饱，直播玩到老！"};
        return titles[new Random().nextInt(titles.length)];
    }

    public static String getShareContent(String nick){
        return "["+nick+"]正在直播间等你撩";
    }


//    public static void jumpLogin(){
//        final Activity activity = AppManager.getAppManager().currentActivity();
//        ViewUtils.makeConfirm(activity, "你还没有登录", "取消", "去登录", new ViewUtils.ButtonCallback() {
//            @Override
//            public void onPositive(Dialog d) {
//
//            }
//
//            @Override
//            public void onNegative(Dialog d) {
//                activity.startActivity(new Intent(activity, LoginActivity.class).putExtra("is_jump_main",false));
//            }
//        }).show();
//
//    }


    public static boolean isShouldHideInput(View v, MotionEvent event) {
        int[] l = { 0, 0 };
        v.getLocationInWindow(l);
        int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                + v.getWidth();
        if ( event.getY() > top && event.getY() < bottom) {
            // 点击EditText的事件，忽略它。
            return false;
        } else {
            return true;
        }
    }
    private static  long currentTime = 0;
    public  static  boolean isTouch(){
        long time = System.currentTimeMillis();
        if(time - currentTime < 500){
            return  false;
        }
        currentTime = time ;
        return true;
    }

}
