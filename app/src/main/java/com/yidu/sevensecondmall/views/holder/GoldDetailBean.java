package com.yidu.sevensecondmall.views.holder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class GoldDetailBean {

    /**
     * code : 1
     * message : 成功
     * result : {"gameList":[{"id":"64","note":"游戏消耗","type":"3","ding_coin":"0.0000","user_money":"0.0000","daxiong_coin":"-241.0000","daxiong_username":"3Dingtest5","datetime":"2017-10-27 17:55:10"},{"id":"65","note":"游戏消耗","type":"3","ding_coin":"0.0000","user_money":"0.0000","daxiong_coin":"+175.7500","daxiong_username":"3Dingtest5","datetime":"2017-10-27 17:55:10"},{"id":"66","note":"游戏消耗","type":"3","ding_coin":"0.0000","user_money":"0.0000","daxiong_coin":"-60.0000","daxiong_username":"3Dingtest5","datetime":"2017-10-27 17:55:10"},{"id":"67","note":"游戏消耗","type":"3","ding_coin":"0.0000","user_money":"0.0000","daxiong_coin":"-200.0000","daxiong_username":"3Dingtest5","datetime":"2017-10-27 17:55:10"},{"id":"68","note":"游戏消耗","type":"3","ding_coin":"0.0000","user_money":"0.0000","daxiong_coin":"-983.0000","daxiong_username":"3Dingtest5","datetime":"2017-10-27 17:55:10"}]}
     */

    private int code;

    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<GameListBean> gameList;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        private int pageCount ;
        public List<GameListBean> getGameList() {
            return gameList;
        }

        public void setGameList(List<GameListBean> gameList) {
            this.gameList = gameList;
        }

        public static class GameListBean {
            /**
             * id : 64
             * note : 游戏消耗
             * type : 3
             * ding_coin : 0.0000
             * user_money : 0.0000
             * daxiong_coin : -241.0000
             * daxiong_username : 3Dingtest5
             * datetime : 2017-10-27 17:55:10
             */

            private String id;
            private String note;
            private String type;
            private String ding_coin;
            private String user_money;
            private String daxiong_coin;
            private String daxiong_username;
            private String datetime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDing_coin() {
                return ding_coin;
            }

            public void setDing_coin(String ding_coin) {
                this.ding_coin = ding_coin;
            }

            public String getUser_money() {
                return user_money;
            }

            public void setUser_money(String user_money) {
                this.user_money = user_money;
            }

            public String getDaxiong_coin() {
                return daxiong_coin;
            }

            public void setDaxiong_coin(String daxiong_coin) {
                this.daxiong_coin = daxiong_coin;
            }

            public String getDaxiong_username() {
                return daxiong_username;
            }

            public void setDaxiong_username(String daxiong_username) {
                this.daxiong_username = daxiong_username;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }
        }
    }
}
