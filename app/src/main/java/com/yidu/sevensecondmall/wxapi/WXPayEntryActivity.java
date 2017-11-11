package com.yidu.sevensecondmall.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yidu.sevensecondmall.i.IEventOrderTag;
import com.yidu.sevensecondmall.i.WechatPayEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/8/23.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e("pay","WXPayEntryActivity");
//        setContentView(R.layout.pay_result);
//
        IWXAPI api = WXAPIFactory.createWXAPI(this, "wx2a5538052969956e");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
//        Log.e("pay", "### WXPayEntryActivity   onNewIntent");
        super.onNewIntent(intent);
        setIntent(intent);
//        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("pay", "onPayFinish, errCode = " + baseResp.errCode);

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.e("pay", "onPayFinish, getType = " + baseResp.getType());
            Log.e("pay", "onPayFinish, errStr = " + baseResp.errStr);
            Log.e("pay", "onPayFinish, errStr = " + baseResp.openId);

            if(baseResp.errCode == 0){
                EventBus.getDefault().post(new WechatPayEvent(IEventOrderTag.WECHAT_PAY_SUCCESS));
            }else if(baseResp.errCode == -1){
                String tips = "微信支付失败";
                Toast.makeText(getApplicationContext(), tips, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new WechatPayEvent(IEventOrderTag.WECHAT_PAY_FAILURE));
            }else if(baseResp.errCode == -2){
                String tips = "微信支付取消";
                Toast.makeText(getApplicationContext(), tips, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new WechatPayEvent(IEventOrderTag.WECHAT_PAY_CANCEL));
            }
        }
        this.finish();
    }
}
