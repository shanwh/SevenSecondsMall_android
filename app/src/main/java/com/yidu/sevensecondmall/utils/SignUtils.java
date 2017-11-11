package com.yidu.sevensecondmall.utils;


import java.security.MessageDigest;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dell on 2017/2/8.
 * request请求签名工具
 */

public final class SignUtils {

//    public static final String API_SECRET ="ZGkoRZ9DxFwsAnXvuLappDMQL4XnPSKY";
    public static final String API_SECRET ="";

    public static void sign(Map<String,String> params){

        StringBuilder sb = new StringBuilder();
        if(params == null){
            params = new HashMap<>();
        }

        if(params.size() >0 ) {
            Map<String, String> sortMap = new TreeMap<>(Collator.getInstance(Locale.ENGLISH));
            sortMap.putAll(params);

            for (String s : sortMap.values()) {
                sb.append(s);
            }
        }

        sb.append(API_SECRET);
        params.put("signature",MD5(sb.toString().toLowerCase()));
    }

    public static String sign(String url){
        StringBuilder sb = new StringBuilder();
        Map<String,String> maps = parseURL(url);

        if(maps.size() >0 ) {
            Map<String, String> sortMap = new TreeMap<>(Collator.getInstance(Locale.ENGLISH));
            sortMap.putAll(maps);

            for (String s : sortMap.values()) {
                sb.append(s);
            }
        }

        sb.append(API_SECRET);
        if(maps.size()>0) {
            return url + "&signature=" + MD5(sb.toString().toLowerCase());
        }else{
            return url + "?signature=" + MD5(sb.toString().toLowerCase());
        }
    }


    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    private static Map<String, String> parseURL(String URL)
    {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit=null;

        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null)
        {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit)
        {
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");

            //解析出键值
            if(arrSplitEqual.length>1)
            {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            }
            else
            {
                if(arrSplitEqual[0]!="")
                {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL)
    {
        String strAllParam=null;
        String[] arrSplit=null;

       // strURL=strURL.trim().toLowerCase();

        arrSplit=strURL.split("[?]");
        if(strURL.length()>1)
        {
            if(arrSplit.length>1)
            {
                if(arrSplit[1]!=null)
                {
                    strAllParam=arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    // MD5加密，32位
    public static String MD5(String plainText) {
        // 返回字符串
        String md5Str = null;
        // 操作字符串

        try {
            StringBuffer buf = new StringBuffer();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
            md5.update(plainText.getBytes());
            // 计算出摘要,完成哈希计算。
            byte b[] = md5.digest();
            int i;

            for (int offset = 0; offset < b.length; offset++) {

                i = b[offset];

                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                // 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
                buf.append(Integer.toHexString(i));
            }

            // 32位的加密
            md5Str = buf.toString();

            // 16位的加密
            // md5Str = buf.toString().md5Strstring(8,24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

}
