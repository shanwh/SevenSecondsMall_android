package com.yidu.sevensecondmall.utils;


import com.yidu.sevensecondmall.SevenSecondApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dell on 2017/1/20.
 */

public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
         Request request = chain.request().newBuilder()
                     .addHeader("appversion", AppUtils.getVersionName(SevenSecondApplication.getInstance()))
                 .addHeader("os","android")
//                 .addHeader("channelid",WhApplication.getInstansce().getChannelId())
//                 .addHeader("subchannelid",WhApplication.getInstansce().getSubChannelId())
                 //.header("version", AppUtils.getVersionCode(WhApplication.getInstansce())+"")
                 .build();
        addSign(request);
        //Request request = chain.request().newBuilder().addHeader("User-Agent","123").addHeader("Cookie","456").build();
        Response response =  chain.proceed(request);
        return response;
    }


    private void addSign(Request request){
        if(request.method().equals("GET")){
            String url = request.url().toString();
        }else if(request.method().equals("POST")){
            RequestBody body =  request.body();
        }
    }
}
