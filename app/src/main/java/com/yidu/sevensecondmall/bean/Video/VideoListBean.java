package com.yidu.sevensecondmall.bean.Video;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class VideoListBean implements Visitable{

    private String head_icon;
    private String name;
    private String title;
    private String relation;
    private String status;
    private String video_url;
    private String num;
    private String fans_num;
    private String WatcherNum;
    private String StoreName;
    private String LikesNum;

    public String getLikesNum() {
        return LikesNum;
    }

    public void setLikesNum(String likesNum) {
        LikesNum = likesNum;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getWatcherNum() {
        return WatcherNum;
    }

    public void setWatcherNum(String watcherNum) {
        WatcherNum = watcherNum;
    }


    public String getFans_num() {
        return fans_num;
    }

    public void setFans_num(String fans_num) {
        this.fans_num = fans_num;
    }

    public String getHead_icon() {
        return head_icon;
    }

    public void setHead_icon(String head_icon) {
        this.head_icon = head_icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
