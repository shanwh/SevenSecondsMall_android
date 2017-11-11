package com.yidu.sevensecondmall.bean.Distribution;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class TeamInfo {
    /**
     * id : 3
     * user_id : 2600
     * name : amy的团队
     * logo : /Public/upload/goods/2016/01-13/5695b2f14616a.jpg
     * team_id : 1003
     * createtime : 1494569195
     * city_id : 1988
     * members : 22
     * extension_team_nums : 1
     * sales_volume : 100
     * sales_rebate : 20
     * status : audit
     * userinfo : {"user_id":"2600","nickname":"13533731794","head_pic":"/Public/images/head.jpg"}
     * cityinfo : {"id":"1988","parent_id":"1964","shortname":"深圳","name":"深圳市","mergename":"中国,广东省,深圳市","level":"2","pinyin":"shenzhen","code":"0755","zip":"518035","first":"S","lng":"114.085947","lat":"22.547"}
     */

    private String id;
    private String user_id;
    private String name;
    private String logo;
    private String team_id;
    private String createtime;
    private String city_id;
    private String members;
    private String extension_team_nums;
    private String sales_volume;
    private String sales_rebate;
    private String status;
    /**
     * user_id : 2600
     * nickname : 13533731794
     * head_pic : /Public/images/head.jpg
     */

    private UserinfoBean userinfo;
    /**
     * id : 1988
     * parent_id : 1964
     * shortname : 深圳
     * name : 深圳市
     * mergename : 中国,广东省,深圳市
     * level : 2
     * pinyin : shenzhen
     * code : 0755
     * zip : 518035
     * first : S
     * lng : 114.085947
     * lat : 22.547
     */

    private CityinfoBean cityinfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getExtension_team_nums() {
        return extension_team_nums;
    }

    public void setExtension_team_nums(String extension_team_nums) {
        this.extension_team_nums = extension_team_nums;
    }

    public String getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(String sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getSales_rebate() {
        return sales_rebate;
    }

    public void setSales_rebate(String sales_rebate) {
        this.sales_rebate = sales_rebate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public CityinfoBean getCityinfo() {
        return cityinfo;
    }

    public void setCityinfo(CityinfoBean cityinfo) {
        this.cityinfo = cityinfo;
    }

    public static class UserinfoBean {
        private String user_id;
        private String nickname;
        private String head_pic;

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
    }

    public static class CityinfoBean {
        private String id;
        private String parent_id;
        private String shortname;
        private String name;
        private String mergename;
        private String level;
        private String pinyin;
        private String code;
        private String zip;
        private String first;
        private String lng;
        private String lat;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMergename() {
            return mergename;
        }

        public void setMergename(String mergename) {
            this.mergename = mergename;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
