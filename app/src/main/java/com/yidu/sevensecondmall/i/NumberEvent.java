package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/3/31 0031.
 */
public class NumberEvent {
    public String number;
    public int founctionTag;

    public NumberEvent(int founctionTag, String number) {
        this.founctionTag = founctionTag;
        this.number = number;
    }
}
