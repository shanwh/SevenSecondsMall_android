package com.yidu.sevensecondmall.bean.Main;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class GameBean {

    /**
     * code : 1
     * message : 成功
     * result : {"banner":[{"src":"http://shop.qimiaolife.cn/Public/images/daxiong_game/banner1.png","href":"##"}],"gameList":[{"src":"http://shop.qimiaolife.cn/Public/images/daxiong_game/game1.png","title":"捕鱼来了","desc":"\u201c捕鱼来了\u201d全新概念捕鱼玩法，多种娱乐游戏场景画面，倍率、特殊鱼等满足玩家爆金游戏体验！","gameCode":"PTG0004","hot":"8975"},{"src":"http://shop.qimiaolife.cn/Public/images/daxiong_game/game2.png","title":"欢乐30秒","desc":"\u201c欢乐30秒\u201d又称将相和或龙虎斗，是非常受欢迎的扑克游戏，以玩家下注的形式出现，玩家不参与摸牌，一切由系统自动操控，玩家下注即可。","gameCode":"PTG0019","hot":"2975"},{"src":"http://shop.qimiaolife.cn/Public/images/daxiong_game/game3.png","title":"水果机","desc":"\u201c水果机\u201d是一款街机风格休闲游戏，游戏的玩法非常的简单，只要你轻轻的按下水果按钮进行下注压分，然后选择开始转灯，如果转灯停留到所押的选项上则按赔率赢取比分。","gameCode":"PTG0001","hot":"23975"},{"src":"http://shop.qimiaolife.cn/Public/images/daxiong_game/game4.png","title":"港式赛马","desc":"\u201c港式赛马\u201d游戏是基于真实的港式赛马创新的游戏新玩法。这款游戏多年前在街机厅凭借着出色的耐玩性，一直保持着高人气的状态，没有体验过的玩家就赶紧试试吧！","gameCode":"PTG0018","hot":"345456"}]}
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
        private List<BannerBean> banner;
        private List<GameListBean> gameList;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<GameListBean> getGameList() {
            return gameList;
        }

        public void setGameList(List<GameListBean> gameList) {
            this.gameList = gameList;
        }

        public static class BannerBean {
            /**
             * src : http://shop.qimiaolife.cn/Public/images/daxiong_game/banner1.png
             * href : ##
             */

            private String src;
            private String href;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public static class GameListBean {
            /**
             * src : http://shop.qimiaolife.cn/Public/images/daxiong_game/game1.png
             * title : 捕鱼来了
             * desc : “捕鱼来了”全新概念捕鱼玩法，多种娱乐游戏场景画面，倍率、特殊鱼等满足玩家爆金游戏体验！
             * gameCode : PTG0004
             * hot : 8975
             */

            private String src;
            private String title;
            private String desc;
            private String gameCode;
            private String hot;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getGameCode() {
                return gameCode;
            }

            public void setGameCode(String gameCode) {
                this.gameCode = gameCode;
            }

            public String getHot() {
                return hot;
            }

            public void setHot(String hot) {
                this.hot = hot;
            }
        }
    }
}
