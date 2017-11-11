package com.yidu.sevensecondmall.networks;

import android.content.Intent;
import android.util.Log;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.LoginUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;

/**
 * Created by Administrator on 2016/8/12.
 */
public class HttpBuilder {
    private FormBody.Builder mBuilder = new FormBody.Builder();
    private Map<String, Object> map = new HashMap<>();
    private BaseCallBack baseCallBack;
    private String router;
    private boolean hasToken;

    private final String TAG = "RequestURL";

    public HttpBuilder(String router){
        this.router = router;
        hasToken = false;
    }

    public HttpBuilder addCode(String name, Object value){
        map.put(name, value);
        if (value == null){
            mBuilder.add(name, "");
        }else {
            mBuilder.add(name, value.toString());
        }
        return this;
    }

    public void setHasToken(boolean hasToken){
        this.hasToken = hasToken;
    }

    private FormBody getFormBody() {
//        Gson gson = new Gson();
//        String params = gson.toJson(map);
//
//        String signData = HttpApi.getSignString("sig");
//        mBuilder.add("sig", signData);

        return mBuilder.build();
    }

    public void setBaseCallBack(BaseCallBack callBack){
        setBaseCallBack(callBack, true);
    }
    public void setBaseCallBack(BaseCallBack callBack, boolean showLogin){
        this.baseCallBack = callBack;
        //修改
        if(hasToken){
            //弹出登录框
            if(LoginUtils.isLogin()){
                onPost();
            }else if (showLogin) {
                Intent intent = new Intent(SystemUtil.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                SystemUtil.getContext().startActivity(intent);
            } else {
                onPost();
            }
        }else{
            onPost();
        }
    }

    private void onPost(){
        String url = HttpApi.getRouterURL(router);
        OkHttpClientManager.doOkHttpPost(url, getFormBody(), baseCallBack);
        LogGetURL();
    }

    public void onGetCallback(BaseCallBack callBack){
        this.baseCallBack = callBack;
        String url = HttpApi.getRouterURL(router) + "?";
        for(Map.Entry<String, Object> item : map.entrySet()){
            url += item.getKey() + "=" + item.getValue().toString() + "&";
        }
        url = url.substring(0, url.length()-1);
        Log.e("onGetCallback",url);
        OkHttpClientManager.doOkHttpGet(url, baseCallBack);
    }

    public HttpBuilder LogGetURL(){
        String url = HttpApi.getRouterURL(router) + "?";
        try {
            for(Map.Entry<String, Object> item : map.entrySet()){
                if (item.getValue()!=null) {
                    url += item.getKey() + "=" + item.getValue().toString() + "&";
                }else {
                    url += item.getKey() + "=" + item.getValue()+" " + "&";
                }
            }
            url = url.substring(0, url.length()-1);
            Log.i(TAG, url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
}
