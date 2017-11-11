package com.yidu.sevensecondmall.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/23 0023.
 */
public class TimeFormatUtils {
    private static String timeStyleA = "yyyy.MM.dd";
    private static String timeStyleB = "yy/MM/dd";
    private static String timeStyleC = "yyyy-MM-dd HH:mm:ss";
    private static String timeStyleD = "yyyy-MM-dd";

    public static String format(long time){
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat(timeStyleA);
        String format = sd.format(date);
        return format;
    }

    public static String formatAll(long time){
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat(timeStyleB);
        String format = sd.format(date);
        return format;
    }

    public static String formatC(long time){
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat(timeStyleC);
        String format = sd.format(date);
        return format;
    }

    public static String formatD(long time){
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat(timeStyleD);
        String format = sd.format(date);
        return format;
    }

    /*
    * 将时间转换为时间戳
    */
    public static String dateToStamp(String s) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }
}
