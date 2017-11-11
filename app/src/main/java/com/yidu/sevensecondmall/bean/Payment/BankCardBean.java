package com.yidu.sevensecondmall.bean.Payment;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public class BankCardBean implements Visitable{
    /**
     * id : 1
     * cardnumber : 6214837834041000
     * user_id : 2600
     * cardtype : CMB
     * bankname : 招商银行
     * username : 肖天亮
     * idtype :
     * idnumber :
     * mobile : 13533731794
     * cardpasswd : 123456
     * createtime : 1496647136
     * nickname : 13533731794
     */

    private String id;
    private String cardnumber;
    private String user_id;
    private String cardtype;
    private String bankname;
    private String username;
    private String idtype;
    private String idnumber;
    private String mobile;
    private String cardpasswd;
    private String createtime;
    private String nickname;
    private String imgurl;

    public String getImgUrl() {
        return imgurl;
    }

    public void setImgUrl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCardpasswd() {
        return cardpasswd;
    }

    public void setCardpasswd(String cardpasswd) {
        this.cardpasswd = cardpasswd;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
