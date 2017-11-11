package com.yidu.sevensecondmall.DAO;

import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;

/**
 * Created by Administrator on 2017/10/23.
 */

public class HttpUtils  {


    public static  void downJson(final  String url,final BaseCallBack baseCallBack ){
        OkHttpClientManager.doOkHttpPost(url)
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {

                    }

                    @Override
                    public void failed(int errorCode, Object data) {

                    }
                });
    }
}
