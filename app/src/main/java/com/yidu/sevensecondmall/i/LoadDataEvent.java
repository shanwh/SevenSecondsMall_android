package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/4/11.
 */
public class LoadDataEvent {

    public int functionTag;
    public int page;
    public int tag;
    public boolean isRefresh;

    public LoadDataEvent(int functionTag) {
        this.functionTag = functionTag;
    }

    public LoadDataEvent(int functionTag, int page) {
        this.functionTag = functionTag;
        this.page = page;
    }

    public LoadDataEvent(int functionTag, int page, int tag) {
        this.functionTag = functionTag;
        this.page = page;
        this.tag = tag;
    }

}
