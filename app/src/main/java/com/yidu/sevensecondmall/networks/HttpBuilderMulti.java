package com.yidu.sevensecondmall.networks;

import android.content.Intent;
import android.util.Log;

import com.se7en.utils.SystemUtil;
import com.yidu.sevensecondmall.Activity.Login.LoginActivity;
import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.utils.LoginUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/8/12.
 */
public class HttpBuilderMulti {
    private MultipartBody.Builder mBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
    private Map<String, Object> map = new HashMap<>();
    private BaseCallBack baseCallBack;
    private String router;
    private boolean hasToken;

    private final String TAG = "RequestURL";

    public HttpBuilderMulti(String router){
        this.router = router;
        hasToken = false;
    }

    public HttpBuilderMulti addCode(String name, Object value){
        if (value == null){
            mBuilder.addFormDataPart(name, "");
            map.put(name, "");
        }else {
            mBuilder.addFormDataPart(name, value.toString());
            map.put(name, value);
        }
        return this;
    }

    public HttpBuilderMulti addCode(String name, File file, String type){
        if (file == null){
            mBuilder.addFormDataPart(name, "");
            map.put(name, "");
        }else {
            map.put(name, file.getName());
            mBuilder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse(type), file));
//            mBuilder.addFormDataPart(name, file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        }
        return this;
    }

    public void setHasToken(boolean hasToken){
        this.hasToken = hasToken;
    }

    private MultipartBody getFormBody() {
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

    public HttpBuilderMulti LogGetURL(){
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
