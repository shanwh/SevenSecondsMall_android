package com.yidu.sevensecondmall.bean.OrderMessage;

import android.os.Parcel;
import android.os.Parcelable;

import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.TypeFactory;
import com.yidu.sevensecondmall.views.adapter.MuliTypeAdapter.Visitable;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class AddressBean implements Visitable, Parcelable {
    /**
     * address_id : 361
     * user_id : 2590
     * consignee : 肖田良1
     * email :
     * country : 0
     * province : 28240
     * city : 29400
     * district : 29371
     * twon : 0
     * address : 宝安中心地铁站
     * zipcode :
     * mobile : 13432296614
     * is_default : 0
     * is_pickup : 0
     */

    private String address_id;
    private String user_id;
    private String consignee;
    private String email;
    private String country;
    private String province;
    private String city;
    private String district;
    private String twon;
    private String address;
    private String zipcode;
    private String mobile;
    private String is_default;
    private String is_pickup;
    private String mergeName;

    public String getMergeName() {
        return mergeName;
    }

    public void setMergeName(String mergeName) {
        this.mergeName = mergeName;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTwon() {
        return twon;
    }

    public void setTwon(String twon) {
        this.twon = twon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getIs_pickup() {
        return is_pickup;
    }

    public void setIs_pickup(String is_pickup) {
        this.is_pickup = is_pickup;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address_id);
        dest.writeString(this.user_id);
        dest.writeString(this.consignee);
        dest.writeString(this.email);
        dest.writeString(this.country);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.twon);
        dest.writeString(this.address);
        dest.writeString(this.zipcode);
        dest.writeString(this.mobile);
        dest.writeString(this.is_default);
        dest.writeString(this.is_pickup);
        dest.writeString(this.mergeName);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.address_id = in.readString();
        this.user_id = in.readString();
        this.consignee = in.readString();
        this.email = in.readString();
        this.country = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.twon = in.readString();
        this.address = in.readString();
        this.zipcode = in.readString();
        this.mobile = in.readString();
        this.is_default = in.readString();
        this.is_pickup = in.readString();
        this.mergeName = in.readString();
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
