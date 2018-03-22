package com.doumengmeng.doctor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/5.
 */

public class FormulaUtil {

    public static String getCurrentTime(){
        return getSimpleDateFormat().format(System.currentTimeMillis());
    }

    public static float formulaBMI(float weight,float height){
        return (Math.round((weight * 10000) / (height*height)*100))/100f;
    }

    public static String getTimeDifference(String lastTimeString){
        long currentTime = System.currentTimeMillis();
        long lastTime = 0;
        try {
            lastTime = getSimpleDateFormat().parse(lastTimeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long day = 24 * 60 * 60 * 1000;
        long hour = 60 * 60 * 1000;
        long minute = 60 * 1000;

        long difference = lastTime - currentTime;
        if ( difference < 0 ){
            return null;
        } else if ( (difference / day) > 0 ){
            return difference / day + "天";
        } else if ( (difference / hour) > 0 ){
            return difference / hour + "小时";
        } else {
            return difference / minute + "分钟";
        }
    }

    private static SimpleDateFormat format;
    private static SimpleDateFormat getSimpleDateFormat(){
        if ( format == null ){
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        }
        return format;
    }

}
