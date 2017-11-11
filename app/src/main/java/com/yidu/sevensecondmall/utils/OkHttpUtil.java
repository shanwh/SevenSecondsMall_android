package com.yidu.sevensecondmall.utils;

import android.os.Handler;
import android.util.Log;

import com.se7en.utils.SystemUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp的工具类
 */
public class OkHttpUtil {

    private static OkHttpClient okHttpClient;
    private static Handler handler = new Handler();

    public static void initOkHttp() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(1000,TimeUnit.SECONDS);
        httpBuilder.addInterceptor(new RequestInterceptor());
//        if(BuildConfig.DEBUG){
            httpBuilder.addInterceptor(loggingInterceptor);
//        }
        httpBuilder.readTimeout(1000,TimeUnit.SECONDS);
        httpBuilder.writeTimeout(1000,TimeUnit.SECONDS);
        okHttpClient = httpBuilder.build();
    }


    /**
     * 下载json
     *
     * @param url
     */
    public static void downJSON(final String url, final OnDownDataListener onDownDataListener) {
        Request request = new Request.Builder()
                .url(SignUtils.sign(url))
                .build();
        //Log.e("request url",url);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownDataListener != null) {
                            onDownDataListener.onFailure(url, e.getMessage());
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();
                //Log.e("response",str);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onDownDataListener != null) {
                            onDownDataListener.onResponse(url, str);
                        }
                    }
                });
            }
        });
    }


    /**
     * 同步get请求 -- 让子类调用
     *
     * @return
     */
    public static Response downResponse(String url) {

        Request request = new Request.Builder()
                .url(SignUtils.sign(url))
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post提交表单
     */
    public static void postSubmitForm(final String url, Map<String, String> params, final OnDownDataListener onDownDataListener) {

        SignUtils.sign(params);
        if (params.size() > 0) {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                builder.add(key, value);
            }
            builder.add("unique_id", SystemUtil.getSharedString("deviceId"));
            FormBody formBody = builder.build();
            Log.d("传输数据", formBody + "");
            final Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (onDownDataListener != null) {
                                onDownDataListener.onFailure(url, e.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String str = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (onDownDataListener != null) {
                                onDownDataListener.onResponse(url, str);
                            }
                        }
                    });
                }
            });
        }
    }


    public interface OnDownDataListener {
        void onResponse(String url, String json) ;

        void onFailure(String url, String error);
    }

    /**
     * 图片加载
     */

    public static byte[] getImage(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");   //设置请求方法为GET
        conn.setReadTimeout(5 * 1000);    //设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据
        byte[] data = readInputStream(inputStream);     //获得图片的二进制数据
        return data;

    }

    /*
    * 从数据流中获得数据
    */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();

    }

//    /**
//     * 上传图片
//     */
//    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    static String imgID = null;
//    public static String uploadimg(String URL,Map<String,String> parpams,List<String> mImgUrls,UpImageCallBack callBack,String Type) {
//        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if(Type.equals("SFYZ")){
//            for (int i = 0; i < mImgUrls.size(); i++) {
//                File f = new File(mImgUrls.get(i));
//                if(null!=f){
//                    if (i == 0) {
//                        builder.addFormDataPart("imga", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//                    } else if (i == 1) {
//                        builder.addFormDataPart("imgb", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//                    } else if (i == 2) {
//                        builder.addFormDataPart("imgc", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//                    }
//                }
//            }
//
//        }else if(Type.equals("TWTP")){
//            for (int i = 0; i < mImgUrls.size(); i++) {
//                File f = new File(mImgUrls.get(i));
//                if (f != null) {
//                    builder.addFormDataPart("imgfile", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
//                }
//
//            }
//        }
//
//        // 添加其它信息  遍历map中所有参数到builder
//        SignUtils.sign(parpams);
//        for (String key : parpams.keySet()) {
//            builder.addFormDataPart(key, parpams.get(key));
//        }
//
//        MultipartBody requestBody = builder.build();
//        //构建请求
//        Request request = new Request.Builder()
//                .url(URL)//地址
//                .post(requestBody)//添加请求体
//                .build();
//        try {
//            Response response=  okHttpClient.newCall(request).execute();
//            String json = response.body().string();
//            if(null!=callBack){
//                callBack.reponse(json);
//            }
//            Log.i("json",json);
//            JSONObject obj = null;
//            try {
//                obj = new JSONObject(json);
//                imgID = obj.getString("imageid");
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return null;
//            }
//            Log.i("上传成功", "URL地址" + imgID);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  imgID;
//    }
//    public interface UpImageCallBack{
//        void reponse(String json);
//    }
}
