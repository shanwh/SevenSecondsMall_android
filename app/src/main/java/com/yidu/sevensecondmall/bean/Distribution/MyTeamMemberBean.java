package com.yidu.sevensecondmall.bean.Distribution;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class MyTeamMemberBean implements Visitable{
    /**
     * user_id : 2600
     * nickname : 13533731794
     * head_pic : /Public/images/head.jpg
     * level : 3
     * count : null
     */


    private String user_id;
    private String nickname;
    private String head_pic;
    private String level;
    private String memberjointime;
    private String invite_rebate;
    private String consumption_rebate;

    public String getMemberjointime() {
        return memberjointime;
    }

    public void setMemberjointime(String memberjointime) {
        this.memberjointime = memberjointime;
    }

    public String getInvite_rebate() {
        return invite_rebate;
    }

    public void setInvite_rebate(String invite_rebate) {
        this.invite_rebate = invite_rebate;
    }

    public String getConsumption_rebate() {
        return consumption_rebate;
    }

    public void setConsumption_rebate(String consumption_rebate) {
        this.consumption_rebate = consumption_rebate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
