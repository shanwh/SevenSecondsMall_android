package com.yidu.sevensecondmall.bean.Order;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class OrderInfo {

    private ArrayList<Visitable> list = new ArrayList<>();
    private int totalPage;

    public ArrayList<Visitable> getList() {
        return list;
    }

    public void setList(ArrayList<Visitable> list) {
        this.list = list;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
