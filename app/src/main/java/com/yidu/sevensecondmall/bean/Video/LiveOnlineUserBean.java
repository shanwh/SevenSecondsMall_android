package com.yidu.sevensecondmall.bean.Video;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23 0023.
 */
public class LiveOnlineUserBean {
    /**
     * TotalUserNumber : 0
     * OnlineUserInfo : {"LiveStreamOnlineUserNumInfo":[{"Time":"2017-08-23T06:44:00Z","StreamUrl":"rtmp://live.qimiaolife.cn/userlive15/6ec05ecbfc","UserNumber":0}]}
     * RequestId : 0F4ECA8B-5460-4C92-B33D-61653F4BDA5A
     */

    private int TotalUserNumber;
    private OnlineUserInfoBean OnlineUserInfo;
    private String RequestId;

    public int getTotalUserNumber() {
        return TotalUserNumber;
    }

    public void setTotalUserNumber(int TotalUserNumber) {
        this.TotalUserNumber = TotalUserNumber;
    }

    public OnlineUserInfoBean getOnlineUserInfo() {
        return OnlineUserInfo;
    }

    public void setOnlineUserInfo(OnlineUserInfoBean OnlineUserInfo) {
        this.OnlineUserInfo = OnlineUserInfo;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public static class OnlineUserInfoBean {
        /**
         * Time : 2017-08-23T06:44:00Z
         * StreamUrl : rtmp://live.qimiaolife.cn/userlive15/6ec05ecbfc
         * UserNumber : 0
         */

        private List<LiveStreamOnlineUserNumInfoBean> LiveStreamOnlineUserNumInfo;

        public List<LiveStreamOnlineUserNumInfoBean> getLiveStreamOnlineUserNumInfo() {
            return LiveStreamOnlineUserNumInfo;
        }

        public void setLiveStreamOnlineUserNumInfo(List<LiveStreamOnlineUserNumInfoBean> LiveStreamOnlineUserNumInfo) {
            this.LiveStreamOnlineUserNumInfo = LiveStreamOnlineUserNumInfo;
        }

        public static class LiveStreamOnlineUserNumInfoBean {
            private String Time;
            private String StreamUrl;
            private int UserNumber;

            public String getTime() {
                return Time;
            }

            public void setTime(String Time) {
                this.Time = Time;
            }

            public String getStreamUrl() {
                return StreamUrl;
            }

            public void setStreamUrl(String StreamUrl) {
                this.StreamUrl = StreamUrl;
            }

            public int getUserNumber() {
                return UserNumber;
            }

            public void setUserNumber(int UserNumber) {
                this.UserNumber = UserNumber;
            }
        }
    }
}
