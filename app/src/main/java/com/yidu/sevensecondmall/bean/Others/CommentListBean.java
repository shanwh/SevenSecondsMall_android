package com.yidu.sevensecondmall.bean.Others;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */
public class CommentListBean {

    /**
     * list : [{"comment_id":"67","goods_id":"1","email":"www.tp-sh0p.cn","username":"蒙娜丽莎","content":"不错,买回来老公很喜欢","deliver_rank":"5","add_time":"1457746449","ip_address":"127.0.0.1","is_show":"1","parent_id":"0","user_id":"1","img":"","order_id":"1","goods_rank":"5","service_rank":"3"}]
     * all_count : 3
     * good_count : 1
     * commonly_count : 2
     * bad_count : 0
     * img_count : 0
     */

    private String all_count;
    private String good_count;
    private String commonly_count;
    private String bad_count;
    private String img_count;
    /**
     * comment_id : 67
     * goods_id : 1
     * email : www.tp-sh0p.cn
     * username : 蒙娜丽莎
     * content : 不错,买回来老公很喜欢
     * deliver_rank : 5
     * add_time : 1457746449
     * ip_address : 127.0.0.1
     * is_show : 1
     * parent_id : 0
     * user_id : 1
     * img :
     * order_id : 1
     * goods_rank : 5
     * service_rank : 3
     */

    private List<CommentBean> list;

    public String getAll_count() {
        return all_count;
    }

    public void setAll_count(String all_count) {
        this.all_count = all_count;
    }

    public String getGood_count() {
        return good_count;
    }

    public void setGood_count(String good_count) {
        this.good_count = good_count;
    }

    public String getCommonly_count() {
        return commonly_count;
    }

    public void setCommonly_count(String commonly_count) {
        this.commonly_count = commonly_count;
    }

    public String getBad_count() {
        return bad_count;
    }

    public void setBad_count(String bad_count) {
        this.bad_count = bad_count;
    }

    public String getImg_count() {
        return img_count;
    }

    public void setImg_count(String img_count) {
        this.img_count = img_count;
    }

    public List<CommentBean> getList() {
        return list;
    }

    public void setList(List<CommentBean> list) {
        this.list = list;
    }

}
