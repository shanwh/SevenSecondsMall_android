package com.yidu.sevensecondmall.bean.OrderMessage;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/4/1 0001.
 */
public class ReturnGoodsInfo {
    /**
     * id : 15
     * order_id : 806
     * order_sn : C328920561273783
     * goods_id : 92
     * type : 0
     * reason : 垃圾
     * imgs : ["a:1:{i:0;s:0:\"\";}"]
     * addtime : 1491030597
     * status : 0
     * remark : 
     * user_id : 2594
     * spec_key : 
     * goods_name : 泊泉雅男士女士香水50ml 淡香清新持久留香 淡雅香氛诱惑 正品
     */

    private String id;
    private String order_id;
    private String order_sn;
    private String goods_id;
    private String type;
    private String reason;
    private String addtime;
    private String status;
    private String remark;
    private String user_id;
    private String spec_key;
    private String goods_name;
    private ArrayList<String> imgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSpec_key() {
        return spec_key;
    }

    public void setSpec_key(String spec_key) {
        this.spec_key = spec_key;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }
}
