package com.yidu.sevensecondmall.bean.Main;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class BannerBean {
    /**
     * ad_name : 首页banner轮播1
     * ad_code : http://www.sevenshop.com/Public/upload/banner/2015/11-04/563a014600063.jpg
     */

    private List<BannerlistBean> bannerlist;

    public List<BannerlistBean> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(List<BannerlistBean> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public static class BannerlistBean {
        private String ad_name;
        private String ad_code;

        public String getAd_name() {
            return ad_name;
        }

        public void setAd_name(String ad_name) {
            this.ad_name = ad_name;
        }

        public String getAd_code() {
            return ad_code;
        }

        public void setAd_code(String ad_code) {
            this.ad_code = ad_code;
        }
    }
}
