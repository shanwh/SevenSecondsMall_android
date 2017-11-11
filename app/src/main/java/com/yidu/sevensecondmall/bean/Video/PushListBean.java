package com.yidu.sevensecondmall.bean.Video;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/8/23 0023.
 */
public class PushListBean implements Visitable{

    /**
     * PublishTime : 2017-08-23T07:11:05Z
     * StreamName : 4bc4f2c66e
     * PublishUrl : rtmp://live.qimiaolife.cn/userlive15/4bc4f2c66e
     * DomainName : live.qimiaolife.cn
     * AppName : userlive15
     */

    private String PublishTime;
    private String StreamName;
    private String PublishUrl;
    private String DomainName;
    private String AppName;

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String PublishTime) {
        this.PublishTime = PublishTime;
    }

    public String getStreamName() {
        return StreamName;
    }

    public void setStreamName(String StreamName) {
        this.StreamName = StreamName;
    }

    public String getPublishUrl() {
        return PublishUrl;
    }

    public void setPublishUrl(String PublishUrl) {
        this.PublishUrl = PublishUrl;
    }

    public String getDomainName() {
        return DomainName;
    }

    public void setDomainName(String DomainName) {
        this.DomainName = DomainName;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String AppName) {
        this.AppName = AppName;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
