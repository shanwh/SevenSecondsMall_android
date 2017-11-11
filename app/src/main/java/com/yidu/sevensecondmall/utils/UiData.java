package com.yidu.sevensecondmall.utils;

import com.yidu.sevensecondmall.bean.Order.SkuBean;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/29.
 */
public class UiData {
    //存放计算结果
    Map<String, SkuBean> result;

    public Map<String, SkuBean> getResult() {
        return result;
    }

    public void setResult(Map<String, SkuBean> result) {
        this.result = result;
    }
}
