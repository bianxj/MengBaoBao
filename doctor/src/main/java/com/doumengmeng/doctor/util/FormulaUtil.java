package com.doumengmeng.doctor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2018/1/5.
 */

public class FormulaUtil {

    public static String getDoubleDigit(int number){
        return (number<10)?"0"+number:number+"";
    }

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
        }
//        else if ( (difference / day) > 0 ){
//            return difference / day + "天";
//        }
        else if ( (difference / hour) > 0 ){
            if ( difference / hour > 48 ) {
                return "48小时";
            }
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

    public static String getHeightResultString(int heightResult){
        switch (heightResult){
            case -3:
                return "下（生长迟缓）";
            case -2:
                return "中下";
            case -1:
            case 0:
            case 1:
                return "中";
            case 2:
                return "中上";
            case 3:
                return "上";
            default:
                return "上";
        }
    }
    public static String getHwResultString(int hwResult){
        switch (hwResult){
            case -3:
            case -2:
                return "下（消瘦）";
            case -1:
            case 0:
            case 1:
                return "中（正常）";
            case 2:
                return "中上（超重）";
            case 3:
                return "上（肥胖）";
            case 4:
                return "上（重度肥胖）";
            default:
                return "中（正常）";
        }
    }

    public static String getWeightResultString(int weightResult){
        switch (weightResult){
            case -3:
                return "下（低体重）";
            case -2:
                return "中下";
            case -1:
            case 0:
            case 1:
                return "中";
            case 2:
                return "中上";
            case 3:
                return "上";
            default:
                return "上";
        }
    }

    public static String getFeatureResultString(int featureResult){
        switch (featureResult){
            case 0:
                return "正常";
            case -1:
                return "异常";
            default:
                return "可疑";
        }
    }

}
