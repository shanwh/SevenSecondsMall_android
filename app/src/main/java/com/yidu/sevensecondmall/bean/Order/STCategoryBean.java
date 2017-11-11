package com.yidu.sevensecondmall.bean.Order;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class STCategoryBean {
    /**
     * mobile_name : 生活电器
     * image : /Public/upload/category/2016/04-02/56ffa28b12f4f.jpg
     * id : 19
     * level : 2
     * parent_id : 2
     * sub_category : [{"mobile_name":"录音机","image":"","id":125,"level":3,"parent_id":19},{"mobile_name":"饮水机","image":"","id":126,"level":3,"parent_id":19},{"mobile_name":"烫衣机","image":"","id":127,"level":3,"parent_id":19},{"mobile_name":"电风扇","image":"","id":128,"level":3,"parent_id":19},{"mobile_name":"电话机","image":"","id":129,"level":3,"parent_id":19}]
     */

    private String mobile_name;
    private String image;
    private int id;
    private int level;
    private int parent_id;
    /**
     * mobile_name : 录音机
     * image :
     * id : 125
     * level : 3
     * parent_id : 19
     */

    private List<SubCategoryBean> sub_category;

    public String getMobile_name() {
        return mobile_name;
    }

    public void setMobile_name(String mobile_name) {
        this.mobile_name = mobile_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public List<SubCategoryBean> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<SubCategoryBean> sub_category) {
        this.sub_category = sub_category;
    }

    public static class SubCategoryBean {
        private String mobile_name;
        private String image;
        private int id;
        private int level;
        private int parent_id;

        public String getMobile_name() {
            return mobile_name;
        }

        public void setMobile_name(String mobile_name) {
            this.mobile_name = mobile_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
    }
}
