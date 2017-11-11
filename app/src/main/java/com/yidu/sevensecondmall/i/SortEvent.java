package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/4/11.
 */
public class SortEvent {

    public int founctionTag;
    public int id;
    public String keyWork;
    public SortEvent(int founctionTag, int id) {
        this.founctionTag = founctionTag;
        this.id = id;
    }
    public SortEvent(int founctionTag, int id, String keyWork) {
        this.founctionTag = founctionTag;
        this.id = id;
        this.keyWork = keyWork;
    }
}
