package com.yidu.sevensecondmall.bean.Order;

/**
 * Created by Administrator on 2017/4/1.
 */
public class goodCategoryBean {

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    private boolean isChoose;

    /**
     * id : 21
     * name : 厨房电器
     * mobile_name : 厨房电器
     * parent_id : 2
     * parent_id_path : 0_2_21
     * level : 2
     * sort_order : 50
     * is_show : 1
     * image : /Public/upload/category/2016/04-02/56ffa28b12f4f.jpg
     * is_hot : 0
     * cat_group : 0
     * commission_rate : 0
     */

    private int id;
    private String name;
    private String mobile_name;
    private int parent_id;
    private String parent_id_path;
    private int level;
    private int sort_order;
    private int is_show;
    private String image;
    private int is_hot;
    private int cat_group;
    private int commission_rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_name() {
        return mobile_name;
    }

    public void setMobile_name(String mobile_name) {
        this.mobile_name = mobile_name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_id_path() {
        return parent_id_path;
    }

    public void setParent_id_path(String parent_id_path) {
        this.parent_id_path = parent_id_path;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getCat_group() {
        return cat_group;
    }

    public void setCat_group(int cat_group) {
        this.cat_group = cat_group;
    }

    public int getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(int commission_rate) {
        this.commission_rate = commission_rate;
    }
}
