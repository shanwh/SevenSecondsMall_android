package com.yidu.sevensecondmall.bean.Others;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public class CommentBean implements Visitable{
    /**
     * comment_id : 133
     * goods_id : 1
     * email : www.99soubao.com
     * username : 嫦娥
     * content : 晒单给大家看看.我刚买的.
     * deliver_rank : 5
     * add_time : 1457746625
     * ip_address : 127.0.0.1
     * is_show : 1
     * parent_id : 0
     * user_id : 1
     * img : ["http://www.sevenshop.com/Public/upload/goods/2016/03-09/56e01a54a2c6d.jpg","http://www.sevenshop.com/Public/upload/goods/2016/03-09/56e01a54bcc53.jpg","http://www.sevenshop.com/Public/upload/goods/2016/03-09/56e01a54de5a9.jpg"]
     * order_id : 1
     * goods_rank : 2
     * service_rank : 1
     */

    private int comment_id;
    private int goods_id;
    private String email;
    private String username;
    private String content;
    private int deliver_rank;
    private int add_time;
    private String ip_address;
    private int is_show;
    private int parent_id;
    private int user_id;
    private int order_id;
    private int goods_rank;
    private int service_rank;
    private List<String> img;
    private String head_pic;

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDeliver_rank() {
        return deliver_rank;
    }

    public void setDeliver_rank(int deliver_rank) {
        this.deliver_rank = deliver_rank;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getGoods_rank() {
        return goods_rank;
    }

    public void setGoods_rank(int goods_rank) {
        this.goods_rank = goods_rank;
    }

    public int getService_rank() {
        return service_rank;
    }

    public void setService_rank(int service_rank) {
        this.service_rank = service_rank;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
