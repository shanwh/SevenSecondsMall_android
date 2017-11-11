package com.yidu.sevensecondmall.DAO;

import com.yidu.sevensecondmall.i.BaseCallBack;
import com.yidu.sevensecondmall.networks.OkHttpClientManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/21.
 */
public class LiveDAO {

    /**
     * 获取弹幕信息
     */
    public static void  getBarrageContent(final BaseCallBack baseCallBack){

        OkHttpClientManager.doOkHttpPost("")
                .addCode("id","")
                .LogGetURL()
                .setBaseCallBack(new BaseCallBack() {
                    @Override
                    public void success(Object data) {
                        try{
                            JSONObject obj = new JSONObject(data.toString());

                            baseCallBack.success(data);
                        }catch (JSONException e){
                            e.printStackTrace();
                            baseCallBack.failed(0 ,"失败");
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data) {
                        baseCallBack.failed(errorCode,data);
                    }
                });
    };

}
