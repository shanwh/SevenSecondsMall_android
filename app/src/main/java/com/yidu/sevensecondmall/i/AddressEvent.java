package com.yidu.sevensecondmall.i;

/**
 * Created by Administrator on 2017/4/11.
 */
public class AddressEvent {

    public int founctionTag;
    public int id;
    public String name;

    public AddressEvent(int founctionTag, int id, String name) {
        this.founctionTag = founctionTag;
        this.id = id;
        this.name = name;
    }
}
