package com.yidu.sevensecondmall.utils;

import android.util.Log;

import com.yidu.sevensecondmall.bean.Order.AttrsBean;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/22.
 */
public class TextUtil {

    //解析组合属性转换为itemid
    public static String transToAttrslist(String key,List<AttrsBean.GoodsSpecListBean> list){
        String allstr = "";
        String[] str = key.split("_");
        for(int i = 0; i< str.length;i++){
            for(int j = 0;j < list.size();j++){
                if(str[i].equals(String.valueOf(list.get(j).getItem_id()))){
                    allstr = allstr + list.get(j).getItem();
                }
            }
        }
        return allstr;
    }


    //解析组合属性,返回数组
    public static String[] transToAttrs(String key){
        String[] str = key.split("_");
        return str;
    }

    //重新将数组组合回属性
    public static String tranBack(HashMap<String,AttrsBean.GoodsSpecListBean> map,List<String> sort){
        String str = "";
        if(map.size() != 0){
            for(int i = 0;i < sort.size();i++){
                for(Map.Entry<String,AttrsBean.GoodsSpecListBean>item : map.entrySet()){
                    if(item.getKey().equals(sort.get(i))){
                        if(i == 0){
                            str = str + item.getValue().getItem_id();
                        }else {
                            str = str +"_" +item.getValue().getItem_id();
                        }
                    }
                }
            }
        }
        return str;
    }



    //转换为[]字串
    public static String tranToSpec(StringBuffer spec){
        if (spec == null || spec.length() == 0) {
            return "";
        }
        String str = "[";
        String[] array = spec.toString().split("_");
        for(int i = 0;i<array.length;i++){
            if(i == array.length-1){
                str = str+array[i]+"]";
            }else {
                str = str+array[i]+",";
            }

        }
        return str;
    }

    //url 的encode
    public static String UrlEnCode(String url){
        if (url == null || url.equals("")) {
            Log.e("toURLEncoded error: ",  url);
            return "";
        }

        try
        {
            String str = new String(url.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
            Log.e("toURLEncoded error:+url", localException+"");
        }

        return "";
    }


}
