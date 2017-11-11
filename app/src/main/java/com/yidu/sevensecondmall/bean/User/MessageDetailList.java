package com.yidu.sevensecondmall.bean.User;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class MessageDetailList {

    private ArrayList<Visitable> shipList = new ArrayList<>();
    private ArrayList<Visitable> informList = new ArrayList<>();
    private ArrayList<Visitable> actionList = new ArrayList<>();

    public ArrayList<Visitable> getShipList() {
        return shipList;
    }

    public void setShipList(ArrayList<Visitable> shipList) {
        this.shipList = shipList;
    }

    public ArrayList<Visitable> getInformList() {
        return informList;
    }

    public void setInformList(ArrayList<Visitable> informList) {
        this.informList = informList;
    }

    public ArrayList<Visitable> getActionList() {
        return actionList;
    }

    public void setActionList(ArrayList<Visitable> actionList) {
        this.actionList = actionList;
    }
}
