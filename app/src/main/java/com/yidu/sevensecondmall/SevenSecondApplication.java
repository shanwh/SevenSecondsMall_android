package com.yidu.sevensecondmall;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.alivc.player.AccessKey;
import com.alivc.player.AccessKeyCallback;
import com.alivc.player.AliVcMediaPlayer;
import com.duanqu.qupai.jni.ApplicationGlue;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheEntity;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.mob.MobSDK;
import com.se7en.utils.DeviceUtils;
import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.WelcomeActivity;
import com.yidu.sevensecondmall.DAO.PositionDao;
import com.yidu.sevensecondmall.utils.AssetsDatabaseManager;
import com.yidu.sevensecondmall.utils.OkHttpUtil;
import com.yidu.sevensecondmall.utils.ThreadTask;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;


/**
 * Created by Administrator on 2017/3/9.
 */
public class SevenSecondApplication extends Application implements Thread.UncaughtExceptionHandler{
    String TAG = "com.yidu.kingoffootball.SevenSecondApplication";

    private static SevenSecondApplication instance;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        DeviceUtils.setContext(this);
        SystemUtil.setContext(SevenSecondApplication.this);
        OkHttpUtil.initOkHttp();
        Fresco.initialize(this);
        initDb();
        MobSDK.init(this,"1dc7c72f41ab1");
        SMSSDK.initSDK(this,"1dc7c72f41ab1","cce730f7b06fd9944a09add6325ed4a8");
        initUpLoad();
        initVideo();
        initJpush();
        instance = this;

        System.loadLibrary("gnustl_shared");
//        System.loadLibrary("ijkffmpeg");//目前使用微博的ijkffmpeg会出现1K再换wifi不重连的情况
        System.loadLibrary("qupai-media-thirdparty");
//        System.loadLibrary("alivc-media-jni");
        System.loadLibrary("qupai-media-jni");
        ApplicationGlue.initialize(this);
    }



    public static SevenSecondApplication getInstance(){
        return instance;
    }



    private void initDb() {
        ThreadTask.getInstance().executorDBThread(new Runnable() {
            @Override
            public void run() {
                AssetsDatabaseManager.initManager(getApplicationContext());
                PositionDao.getInstance().queryProvinceList();
            }
        }, ThreadTask.ThreadPeriod.PERIOD_HIGHT);
    }



//    //第三方登录配置,资料给予后修改
//    {
//        PlatformConfig.setQQZone("1105762861", "vn2IRrr6Pofx8gd0");
//        PlatformConfig.setSinaWeibo("1083045738", "85817fc9145b52e425e286bc934974bd");
//        PlatformConfig.setWeixin("wx7d83655139c3e2e8", "f7a3c8e144dba1ad36be1d491ddda178");
//        Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
//    }

    private void initJpush() {
        JPushInterface.init(this);
        JPushInterface.requestPermission(this);
    }


    private void initUpLoad() {
        OkHttpUtils.init(this);
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkHttpUtils.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkHttpUtils")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认就是Default,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy0216/
                    .setCacheMode(CacheMode.DEFAULT)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    .setCookieStore(new PersistentCookieStore());     //cookie持久化存储，如果cookie不过期，则一直有效
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVideo(){
        AliVcMediaPlayer.init(getApplicationContext(), "", new AccessKeyCallback() {
            public AccessKey getAccessToken() {
                return new AccessKey("LTAIpuMS5HbYs1Lw", "VhDWt5EfBD9wXmdkl0ZCUA0x9FGrFs");
            }
        });
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        restart();
    }

    private void restart(){
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //退出程序
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                restartIntent); // 1秒钟后重启应用
        System.exit(0);
    }


}
