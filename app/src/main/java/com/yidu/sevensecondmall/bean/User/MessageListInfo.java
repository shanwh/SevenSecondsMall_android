package com.yidu.sevensecondmall.bean.User;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
public class MessageListInfo {
    private String shipTitle;
    private String sysTitle;
    private String actionTitle;

    private String shipTime;
    private String sysTime;
    private String actionTime;

    private int count;
    private int shipCount;
    private int informCount;
    private int actionCount;

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public int getInformCount() {
        return informCount;
    }

    public void setInformCount(int informCount) {
        this.informCount = informCount;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getShipTitle() {
        return shipTitle;
    }

    public void setShipTitle(String shipTitle) {
        this.shipTitle = shipTitle;
    }

    public String getSysTitle() {
        return sysTitle;
    }

    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }
}
