package com.yidu.sevensecondmall.bean.Distribution;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class MyJurisdictionBean implements Visitable{
    /**
     * key : recommend_rebate_direct
     * value : 1600
     * name : 直推会员获得多少返利
     * company : ￥
     */

    private String key;
    private String value;
    private String name;
    private String company;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
