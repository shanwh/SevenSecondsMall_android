package com.yidu.sevensecondmall.bean.OrderMessage;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class TotalPriceBean {

    /**
     * total_fee : 16166.76
     * cut_fee : 541.94
     * num : 5
     */

    private double total_fee;
    private double cut_fee;
    private int num;
    private String weight;
    private double all_shipping_price;

    public double getAll_shipping_price() {
        return all_shipping_price;
    }

    public void setAll_shipping_price(double all_shipping_price) {
        this.all_shipping_price = all_shipping_price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    public double getCut_fee() {
        return cut_fee;
    }

    public void setCut_fee(double cut_fee) {
        this.cut_fee = cut_fee;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
