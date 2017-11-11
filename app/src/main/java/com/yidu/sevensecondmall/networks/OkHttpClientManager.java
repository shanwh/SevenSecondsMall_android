package com.yidu.sevensecondmall.networks;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.i.IEventTag;
import com.yidu.sevensecondmall.i.LoginEvent;
import com.yidu.sevensecondmall.utils.LoginUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/6/28.
 */
public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;


    private static final String TAG = "OkHttpClientManager";

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());


    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    private void asyncGet(final String url, final BaseCallBack responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        mOkHttpClient.newCall(request).enqueue(getCallback(responseCallback));
    }

    private void asyncPost(final String url, final RequestBody requestBody, final BaseCallBack responseCallback) {
        try {
            long l = requestBody.contentLength();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(getCallback(responseCallback));
    }

    private Callback getCallback(final BaseCallBack responseCallback) {
        return new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "failed: requestNewsList" + e.getMessage());
                        e.printStackTrace();
                        responseCallback.failed(call.hashCode(), e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
//                Log.e(TAG, "response:" + response.toString());
//                Log.e(TAG, "response:" + response.body().toString());
//                Log.e(TAG, "response:" + responseStr);
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    final int code = jsonObject.getInt("code");
                    String content = "";
                    if (code == 0) {
                        if (!jsonObject.isNull("result")) {
                            content = jsonObject.getString("result");
                            final String finalContent = content;
                            mDelivery.post(new Runnable() {
                                @Override
                                public void run() {
                                    responseCallback.success(finalContent);
                                }
                            });
                        } else {
                            mDelivery.post(new Runnable() {
                                @Override
                                public void run() {
                                    responseCallback.success("");
                                }
                            });
                        }
                    } else {
                        final String message = jsonObject.getString("message");
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(SystemUtil.getContext(),message+" 返回错误code："+code,Toast.LENGTH_SHORT).show();
                                if (code == 1006 || code == 1005) {
                                    if (code == 1006){
                                        LoginUtils.setIsLogin(false);
                                    }
                                    EventBus.getDefault().post(new LoginEvent(IEventTag.AUTO_LOGIN));
                                }
                                responseCallback.failed(code, message);
                            }
                        });
                    }
                } catch (JSONException e) {
                    responseCallback.failed(0, "数据异常");
                    e.printStackTrace();
                }
            }
        };
    }

    public static void doOkHttpGet(final String url, final BaseCallBack responseCallback) {
        getInstance().asyncGet(url, responseCallback);
    }

    public static HttpBuilder doOkHttpGet(final String router) {
        HttpBuilder builder = new HttpBuilder(router);
        builder.addCode("unique_id", SystemUtil.getSharedString("deviceId"));
        return builder;
    }

    public static void doOkHttpPost(final String url, final RequestBody requestBody, final BaseCallBack responseCallback) {
        getInstance().asyncPost(url, requestBody, responseCallback);
    }

    public static HttpBuilder doOkHttpPost(final String router) {
        HttpBuilder builder = new HttpBuilder(router);
        builder.addCode("unique_id", SystemUtil.getSharedString("deviceId"));

        return builder;
    }

    public static HttpBuilder doOkHttpPostWithToken(final String router) {
        return doOkHttpPostWithToken(router, true);
    }

    public static HttpBuilder doOkHttpPostWithToken(final String router, final boolean forceNeedToken) {

        HttpBuilder builder = new HttpBuilder(router);
        if (forceNeedToken) {//强制需要token
            builder.addCode("token", LoginUtils.getToken());
//            builder.addCode("user_id", LoginUtils.getUserId());
//            if(LoginUtils.getDeviceid() != null&&!LoginUtils.getDeviceid().equals("")){
//                unique_id = LoginUtils.getDeviceid();
//            }else {
//                unique_id = "unionid001";
//                LoginUtils.setDeviceid(unique_id);
//            }
            builder.addCode("unique_id", SystemUtil.getSharedString("deviceId"));
            builder.setHasToken(true);
        } else {
            //判断token是否为空
            boolean tokenIsEmpty = (LoginUtils.getToken().isEmpty() || LoginUtils.getToken().equals(""));
            if (!tokenIsEmpty) {//可传可不传token
                builder.addCode("token", LoginUtils.getToken());
//                builder.addCode("user_id", LoginUtils.getUserId());
                if (SystemUtil.getSharedString("deviceId") != null) {
                    builder.addCode("unique_id", SystemUtil.getSharedString("deviceId"));
                } else {
                    builder.addCode("unique_id", "unionid001");
                }
                builder.setHasToken(true);
            }
        }

        return builder;
    }

    public static HttpBuilderMulti doOkHttpPostWithTokenMulti(final String router) {
        return doOkHttpPostWithTokenMulti(router, true);
    }

    public static HttpBuilderMulti doOkHttpPostWithTokenMulti(final String router, final boolean forceNeedToken) {

        HttpBuilderMulti builder = new HttpBuilderMulti(router);
        if (forceNeedToken) {//强制需要token
            builder.addCode("token", LoginUtils.getToken());
//            builder.addCode("user_id", LoginUtils.getUserId());
//            if(LoginUtils.getDeviceid() != null&&!LoginUtils.getDeviceid().equals("")){
//                unique_id = LoginUtils.getDeviceid();
//            }else {
//                unique_id = "unionid001";
//                LoginUtils.setDeviceid(unique_id);
//            }
            builder.addCode("unique_id", SystemUtil.getSharedString("deviceId"));
            builder.setHasToken(true);
        } else {
            //判断token是否为空
            boolean tokenIsEmpty = (LoginUtils.getToken().isEmpty() || LoginUtils.getToken().equals(""));
            if (!tokenIsEmpty) {//可传可不传token
                builder.addCode("token", LoginUtils.getToken());
//                builder.addCode("user_id", LoginUtils.getUserId());
                if (SystemUtil.getSharedString("deviceId") != null) {
                    builder.addCode("unique_id", SystemUtil.getSharedString("deviceId"));
                } else {
                    builder.addCode("unique_id", "unionid001");
                }
                builder.setHasToken(true);
            }
        }

        return builder;
    }

}
