package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public interface IEventOrderTag {
    int SHOW_PAY_TYPE_DETAIL = 0;

    int SEND_PASSWORD = 1;

    int WECHAT_PAY_SUCCESS  = 2;

    int WECHAT_PAY_FAILURE = 3;

    int WECHAT_PAY_CANCEL = 4;
}
