package com.yidu.sevensecondmall.bean.User;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/6/5 0005.
 */
public class MessageBean implements Visitable{
    /**
     * message_id : 3
     * admin_id : 0
     * message : {"order_img":"\/Public\/upload\/goods\/2016\/01-13\/5695b2f14616a.jpg","order_title":"\u7f8e\u56fd\u56fd\u4f1a\u9152","order_code":"446446456456456477","order_shipping_name":"shunfung","order_shipping_status":2}
     * type : 0
     * category : 2
     * send_time : 1496643079
     * content : {"order_img":"/Public/upload/goods/2016/01-13/5695b2f14616a.jpg","order_title":"美国国会酒","order_code":"446446456456456477","order_shipping_name":"shunfung","order_shipping_status":2}
     */

    private String message_id;
    private String admin_id;
    private String message;
    private String type;
    private String category;
    private String send_time;
    /**
     * order_img : /Public/upload/goods/2016/01-13/5695b2f14616a.jpg
     * order_title : 美国国会酒
     * order_code : 446446456456456477
     * order_shipping_name : shunfung
     * order_shipping_status : 2
     */

    private ContentBean content;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    public static class ContentBean {
        private String order_img;
        private String order_title;
        private String order_code;
        private String order_shipping_name;
        private int order_shipping_status;
        private String act_title;
        private String act_content;



        public String getOrder_img() {
            return order_img;
        }

        public void setOrder_img(String order_img) {
            this.order_img = order_img;
        }

        public String getOrder_title() {
            return order_title;
        }

        public void setOrder_title(String order_title) {
            this.order_title = order_title;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getOrder_shipping_name() {
            return order_shipping_name;
        }

        public void setOrder_shipping_name(String order_shipping_name) {
            this.order_shipping_name = order_shipping_name;
        }

        public int getOrder_shipping_status() {
            return order_shipping_status;
        }

        public void setOrder_shipping_status(int order_shipping_status) {
            this.order_shipping_status = order_shipping_status;
        }
    }

    /**
     * 0待发货
     * 1已发货
     * 2已收货
     */

}
