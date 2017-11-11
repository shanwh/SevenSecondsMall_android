package com.yidu.sevensecondmall.bean.Distribution;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public class InviteListBean {

    /**
     * id : 1
     * invite_user_id : 2602
     * user_id : 2601
     * createtime : 1497860723
     * nickname : 13352880671
     * head_pic : /Public/images/head.jpg
     */

    private String id;
    private String invite_user_id;
    private String user_id;
    private String createtime;
    private String nickname;
    private String head_pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvite_user_id() {
        return invite_user_id;
    }

    public void setInvite_user_id(String invite_user_id) {
        this.invite_user_id = invite_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }
}
