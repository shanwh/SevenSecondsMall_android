package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ActionEvent {
    public int founctionTag;
    public int page;

    public ActionEvent(int founctionTag) {
        this.founctionTag = founctionTag;
    }

    public ActionEvent(int functionTag, int page) {
        this.founctionTag = functionTag;
        this.page = page;
    }
}
