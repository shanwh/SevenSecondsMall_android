package com.yidu.sevensecondmall.bean.Distribution;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class ExtensionTeamBean implements Visitable{

    /**
     * id : 2
     * name : 神
     * logo : /Public/upload/goods/2016/01-13/5695b2f14616a.jpg
     * createtime : 1494932544
     * members : 1
     * linkman : 测试
     * invite_rebate : 20000
     * consumption_rebate : 0
     */

    private String id;
    private String name;
    private String logo;
    private String createtime;
    private String members;
    private String linkman;
    private String invite_rebate;
    private String consumption_rebate;

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
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
}
