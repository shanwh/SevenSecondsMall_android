package com.yidu.sevensecondmall.bean.OrderMessage;

import com.yidu.sevensecondmall.bean.Order.CartlistBean;
import com.yidu.sevensecondmall.bean.Order.carPriceBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class Cart2Bean {

    private ArrayList<AddressBean> addressList = new ArrayList<>();
    private ArrayList<ShippingListBean> shippingList = new ArrayList<>();
    private ArrayList<CartlistBean.CartListBean> cartList = new ArrayList<>();
    private TotalPriceBean totalPriceBean ;
    private ArrayList<CouponBean> couponList = new ArrayList<>();
    private UserInfo userInfo ;
    private carPriceBean pricebean;

    public ArrayList<AddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<AddressBean> addressList) {
        this.addressList = addressList;
    }

    public ArrayList<ShippingListBean> getShippingList() {
        return shippingList;
    }

    public void setShippingList(ArrayList<ShippingListBean> shippingList) {
        this.shippingList = shippingList;
    }

    public ArrayList<CartlistBean.CartListBean> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<CartlistBean.CartListBean> cartList) {
        this.cartList = cartList;
    }



    public ArrayList<CouponBean> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<CouponBean> couponList) {
        this.couponList = couponList;
    }

    public TotalPriceBean getTotalPriceBean() {
        return totalPriceBean;
    }

    public void setTotalPriceBean(TotalPriceBean totalPriceBean) {
        this.totalPriceBean = totalPriceBean;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public carPriceBean getPricebean() {
        return pricebean;
    }

    public void setPricebean(carPriceBean pricebean) {
        this.pricebean = pricebean;
    }

}
