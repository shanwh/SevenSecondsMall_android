package com.yidu.sevensecondmall.bean.User;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/1 0001.
 */
public class InviteInfo {
    private int totalPage;
    private ArrayList<Visitable> list;
    private String totalCount;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<Visitable> getList() {
        return list;
    }

    public void setList(ArrayList<Visitable> list) {
        this.list = list;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
