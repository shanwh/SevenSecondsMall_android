package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/3/16.
 */
public class PriceEvent {

    public int founctionTag;
    public boolean change = false;
    public String tag = "";

    public PriceEvent(int founctionTag,boolean change) {
        this.founctionTag = founctionTag;
        this.change = change;
    }

    public PriceEvent(String tag) {
        this.tag = tag;
    }


}
