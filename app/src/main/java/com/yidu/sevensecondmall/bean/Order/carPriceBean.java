package com.yidu.sevensecondmall.bean.Order;

/**
 * Created by Administrator on 2017/5/22.
 */
public class carPriceBean {

    /**
     * cut_fee : 1196
     * total_price : 23232
     */

    private int cut_fee;
    private double total_price;
    private String weight;
    private int num;

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    private String shipping_price;



    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }



    public int getCut_fee() {
        return cut_fee;
    }

    public void setCut_fee(int cut_fee) {
        this.cut_fee = cut_fee;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
