package com.yidu.sevensecondmall.bean.User;

/**
 * Created by Administrator on 2017/3/30.
 */
public class UserBean {
    /**
     * user_id : 2590
     * email :
     * password : 519475228fe35ad067744465c42a19b2
     * sex : 0
     * birthday : 0
     * user_money : 0.00
     * frozen_money : 0.00
     * distribut_money : 0.00
     * pay_points : 100
     * address_id : 0
     * reg_time : 1490145974
     * last_login : 1490867050
     * last_ip :
     * qq :
     * id_number :
     * mobile : 13432296616
     * mobile_validated : 1
     * oauth :
     * openid : null
     * head_pic : null
     * province : 0
     * city : 0
     * district : 0
     * email_validated : 0
     * nickname : 13432296616
     * usertype : 0
     * level : 1
     * discount : 1.00
     * total_amount : 0.00
     * is_lock : 0
     * is_distribut : 1
     * first_leader : 0
     * second_leader : 0
     * third_leader : 0
     * token : 48cbe678a4ff9da92e1a0b8ceabd3b7c
     * mall_id :
     * coupon_count : 0
     * collect_count : 2
     * waitPay : 0
     * waitSend : 0
     * waitReceive : 0
     * order_count : 0
     */

    private int user_id;
    private String email;
    private String password;
    private int sex;
    private int birthday;
    private String user_money;
    private String frozen_money;
    private String distribut_money;
    private int pay_points;
    private int address_id;
    private int reg_time;
    private int last_login;
    private String last_ip;
    private String qq;
    private String id_number;
    private String mobile;
    private int mobile_validated;
    private String oauth;
    private Object openid;
    private String head_pic;
    private int province;
    private int city;
    private int district;
    private int email_validated;
    private String nickname;
    private int usertype;
    private int level;
    private String discount;
    private String total_amount;
    private int is_lock;
    private int is_distribut;
    private int first_leader;
    private int second_leader;
    private int third_leader;
    private String token;
    private String mall_id;
    private int coupon_count;
    private int collect_count;
    private int waitPay;
    private int waitSend;
    private int waitReceive;
    private int waitComment;
    private int order_count;
    private String rebate_points;
    private icon icon;
    private partnerOrderInfo info;

    public void setValidBalanceByUserId(UserBean.valid_balance_by_user_id validBalanceByUserId) {
        this.validBalanceByUserId = validBalanceByUserId;
    }

    private valid_balance_by_user_id validBalanceByUserId;
    private String user_money_pwd;
    private String is_authentication;
    private String realname;
    private String business_apply_status;
    private String vip_valid_period;
    private String ding_coin;
    private String new_level;
    private String shareholder_apply_status;

    public String getShareholder_apply_status() {
        return shareholder_apply_status;
    }

    public void setShareholder_apply_status(String shareholder_apply_status) {
        this.shareholder_apply_status = shareholder_apply_status;
    }

    public String getVip_valid_period() {
        return vip_valid_period;
    }

    public void setVip_valid_period(String vip_valid_period) {
        this.vip_valid_period = vip_valid_period;
    }

    public String getDing_coin() {
        return ding_coin;
    }

    public void setDing_coin(String ding_coin) {
        this.ding_coin = ding_coin;
    }

    public String getNew_level() {
        return new_level;
    }

    public void setNew_level(String new_level) {
        this.new_level = new_level;
    }

    public String getBusiness_apply_status() {
        return business_apply_status;
    }

    public void setBusiness_apply_status(String business_apply_status) {
        this.business_apply_status = business_apply_status;
    }

    public static class partnerOrderInfo{
        /**
         * id : 1
         * user_id : 2602
         * type : partner
         * order_no : P2017070315141353539755
         * order_fee : 100000.00
         * province_id : 1694
         * city_id : 1988
         * area_id : 1992
         * submit_info : {"mobile":13352880671,"realname":"张顺滑","address":"宝安区西乡大道龙洞大厦D1231"}
         * pay_status : 0
         * status : 0
         * pay_code :
         * pay_name :
         */

        private String id;
        private String user_id;
        private String type;
        private String order_no;
        private String order_fee;
        private String province_id;
        private String city_id;
        private String area_id;
        /**
         * mobile : 13352880671
         * realname : 张顺滑
         * address : 宝安区西乡大道龙洞大厦D1231
         */

        private SubmitInfoBean submit_info;
        private String pay_status;
        private String status;
        private String pay_code;
        private String pay_name;



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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getOrder_fee() {
            return order_fee;
        }

        public void setOrder_fee(String order_fee) {
            this.order_fee = order_fee;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public SubmitInfoBean getSubmit_info() {
            return submit_info;
        }

        public void setSubmit_info(SubmitInfoBean submit_info) {
            this.submit_info = submit_info;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_code() {
            return pay_code;
        }

        public void setPay_code(String pay_code) {
            this.pay_code = pay_code;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public static class SubmitInfoBean {
            private String mobile;
            private String realname;
            private String address;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }

    public static class valid_balance_by_user_id{
        String  valid_balance;

        public String getValid_balance() {
            return valid_balance;
        }

        public void setValid_balance(String valid_balance) {
            this.valid_balance = valid_balance;
        }
    }
    public static class icon {
        boolean vip;
        boolean partner;
        boolean authentication;
        boolean business;

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }

        public boolean isPartner() {
            return partner;
        }

        public void setPartner(boolean partner) {
            this.partner = partner;
        }

        public boolean isAuthentication() {
            return authentication;
        }

        public void setAuthentication(boolean authentication) {
            this.authentication = authentication;
        }

        public boolean isBusiness() {
            return business;
        }

        public void setBusiness(boolean business) {
            this.business = business;
        }
    }

    public String getIs_authentication() {
        return is_authentication;
    }

    public void setIs_authentication(String is_authentication) {
        this.is_authentication = is_authentication;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUser_money_pwd() {
        return user_money_pwd;
    }

    public void setUser_money_pwd(String user_money_pwd) {
        this.user_money_pwd = user_money_pwd;
    }

    public partnerOrderInfo getInfo() {
        return info;
    }

    public void setInfo(partnerOrderInfo info) {
        this.info = info;
    }

    public UserBean.icon getIcon() {
        return icon;
    }
    public UserBean.valid_balance_by_user_id getVaid() {
        return validBalanceByUserId;
    }

    public void setIcon(UserBean.icon icon) {
        this.icon = icon;
    }

    public String getRebate_points() {
        return rebate_points;
    }

    public void setRebate_points(String rebate_points) {
        this.rebate_points = rebate_points;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getFrozen_money() {
        return frozen_money;
    }

    public void setFrozen_money(String frozen_money) {
        this.frozen_money = frozen_money;
    }

    public String getDistribut_money() {
        return distribut_money;
    }

    public void setDistribut_money(String distribut_money) {
        this.distribut_money = distribut_money;
    }

    public int getPay_points() {
        return pay_points;
    }

    public void setPay_points(int pay_points) {
        this.pay_points = pay_points;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getReg_time() {
        return reg_time;
    }

    public void setReg_time(int reg_time) {
        this.reg_time = reg_time;
    }

    public int getLast_login() {
        return last_login;
    }

    public void setLast_login(int last_login) {
        this.last_login = last_login;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMobile_validated() {
        return mobile_validated;
    }

    public void setMobile_validated(int mobile_validated) {
        this.mobile_validated = mobile_validated;
    }

    public String getOauth() {
        return oauth;
    }

    public void setOauth(String oauth) {
        this.oauth = oauth;
    }

    public Object getOpenid() {
        return openid;
    }

    public void setOpenid(Object openid) {
        this.openid = openid;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public int getEmail_validated() {
        return email_validated;
    }

    public void setEmail_validated(int email_validated) {
        this.email_validated = email_validated;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(int is_lock) {
        this.is_lock = is_lock;
    }

    public int getIs_distribut() {
        return is_distribut;
    }

    public void setIs_distribut(int is_distribut) {
        this.is_distribut = is_distribut;
    }

    public int getFirst_leader() {
        return first_leader;
    }

    public void setFirst_leader(int first_leader) {
        this.first_leader = first_leader;
    }

    public int getSecond_leader() {
        return second_leader;
    }

    public void setSecond_leader(int second_leader) {
        this.second_leader = second_leader;
    }

    public int getThird_leader() {
        return third_leader;
    }

    public void setThird_leader(int third_leader) {
        this.third_leader = third_leader;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMall_id() {
        return mall_id;
    }

    public void setMall_id(String mall_id) {
        this.mall_id = mall_id;
    }

    public int getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(int coupon_count) {
        this.coupon_count = coupon_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public int getWaitPay() {
        return waitPay;
    }

    public void setWaitPay(int waitPay) {
        this.waitPay = waitPay;
    }

    public int getWaitSend() {
        return waitSend;
    }

    public void setWaitSend(int waitSend) {
        this.waitSend = waitSend;
    }

    public int getWaitReceive() {
        return waitReceive;
    }

    public void setWaitReceive(int waitReceive) {
        this.waitReceive = waitReceive;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }


    public int getWaitComment() {
        return waitComment;
    }

    public void setWaitComment(int waitComment) {
        this.waitComment = waitComment;
    }
}
