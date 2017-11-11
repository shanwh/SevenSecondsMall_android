package com.yidu.sevensecondmall.bean.Order;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class ShipDataBean implements Visitable{

    private String time;
    private String ftime;
    String shipContext;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    @Override
    public String toString() {
        return "ShipDataBean{" +
                "time='" + time + '\'' +
                ", ftime='" + ftime + '\'' +
                ", shipContext='" + shipContext + '\'' +
                '}';
    }

    public String getContext() {
        return shipContext;
    }

    public void setContext(String shipContext) {
        this.shipContext = shipContext;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
