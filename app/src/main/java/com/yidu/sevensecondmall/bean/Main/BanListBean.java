package com.yidu.sevensecondmall.bean.Main;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
public class BanListBean implements Visitable {

    private ArrayList<BanBean> list = new ArrayList<>();
    private ArrayList<TitleBean> titleBeens = new ArrayList<>();

    public ArrayList<TitleBean> getTitleBeens() {
        return titleBeens;
    }

    public void setTitleBeens(ArrayList<TitleBean> titleBeens) {
        this.titleBeens = titleBeens;
    }

    public ArrayList<BanBean> getList() {
        return list;
    }

    public void setList(ArrayList<BanBean> list) {
        this.list = list;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
