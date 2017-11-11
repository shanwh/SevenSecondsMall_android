package com.yidu.sevensecondmall.utils;

import com.yidu.sevensecondmall.bean.Order.AttrsBean;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/24.
 * 规格选择助手
 */
public class SpecHelper {

    private static SpecHelper spechelper;
    private static HashMap<String,AttrsBean.GoodsSpecListBean> specmap = new HashMap<String,AttrsBean.GoodsSpecListBean>();
    private static StringBuffer stringbuffer;
    private static JSONArray trolleyJson;
    private static String province;
    private static String city;
    private static String district;

    public  String getShipping_code() {
        return shipping_code;
    }

    public  void setShipping_code(String shipping_code) {
        SpecHelper.shipping_code = shipping_code;
    }

    public  String getProvince() {
        return province;
    }

    public  void setProvince(String province) {
        SpecHelper.province = province;
    }

    public  String getDistrict() {
        return district;
    }

    public  void setDistrict(String district) {
        SpecHelper.district = district;
    }

    public  String getCity() {
        return city;
    }

    public  void setCity(String city) {
        SpecHelper.city = city;
    }

    private static String shipping_code;


    public StringBuffer getName() {
        return Name;
    }

    public void setName(StringBuffer name) {
        Name = name;
    }

    private static StringBuffer Name;

    public static SpecHelper getInstance(){
        if(spechelper == null){
            spechelper = new SpecHelper();
        }
        return spechelper;
    }

    public void SelectSpec(AttrsBean.GoodsSpecListBean bean){
        specmap.put(bean.getSpec_name(),bean);
    }

    public HashMap<String,AttrsBean.GoodsSpecListBean> getMap(){
        return specmap;
    }

    public boolean Selected(AttrsBean.GoodsSpecListBean bean){
        if(specmap.containsKey(bean.getSpec_name())){
            if(specmap.get(bean.getSpec_name()).getItem().equals(bean.getItem())){
                return true;
            }
        }
        return false;
    }

    public void setStringbuffer(StringBuffer buffer){
        stringbuffer = buffer;
    }

    public StringBuffer getStringbuffer(){
        return stringbuffer;
    }

    public void clearData(){
        if(stringbuffer !=null){
            stringbuffer.delete(0,stringbuffer.length());
        }
        if(Name != null){
            Name.delete(0,Name.length());
        }
        if(specmap!=null){
            specmap.clear();
        }
    }

    public void clearSelect(){
        if(stringbuffer !=null){
            stringbuffer.delete(0,stringbuffer.length());
        }
    }

    public void setJson(JSONArray array){
        this.trolleyJson = array;
    }

    public JSONArray getJson(){
        return trolleyJson;
    }

    public void clear(){
        city = "";
        province = "";
        district = "";
        shipping_code = "";
    }







}
