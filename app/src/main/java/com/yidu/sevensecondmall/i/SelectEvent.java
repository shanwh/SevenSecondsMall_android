package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/3/21.
 */
public class SelectEvent {
    public int founctionTag;

    public int position;

    public SelectEvent(int founctionTag) {
        this.founctionTag = founctionTag;
    }

    public SelectEvent(int founctionTag, int position) {
        this.founctionTag = founctionTag;
        this.position = position;
    }
}
