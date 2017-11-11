package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/3/30.
 */
public class LoginEvent {
    public int founctionTag;
    public int page;
    public String id;
    public String price;

    public LoginEvent(int founctionTag) {
        this.founctionTag = founctionTag;
    }

    public LoginEvent(int functionTag, int page) {
        this.founctionTag = functionTag;
        this.page = page;
    }

    public LoginEvent(int functionTag, String id, String price) {
        this.founctionTag = functionTag;
        this.id = id;
        this.price = price;
    }
}
