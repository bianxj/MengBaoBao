package com.doumengmeng.customer.util;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return format.format(System.currentTimeMillis());
    }

    public static float formulaBMI(float weight,float height){
        return (Math.round((weight * 10000) / (height*height)*100))/100f;
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

//    /**
//     * 01天算法月龄
//     * @param calendarBirth
//     * @param calendarNow
//     * @return
//     */
//    private static int[] getNeturalAge01(Calendar calendarBirth,Calendar calendarNow) {
//        int diffMonths = 0;
//        int diffDays = 0;
//        Calendar calBirthday = Calendar.getInstance();
//        calBirthday.setTimeInMillis(calendarBirth.getTimeInMillis());
//        calendarNow.set(Calendar.HOUR_OF_DAY, 0);
//        calendarNow.set(Calendar.MINUTE, 0);
//        calendarNow.set(Calendar.SECOND, 0);
//        int dayOfBirth = calBirthday.get(Calendar.DAY_OF_MONTH);
//        int dayOfNow = calendarNow.get(Calendar.DAY_OF_MONTH);
//        if (dayOfBirth <= dayOfNow) {
//            diffMonths = getMonthsOfAge(calBirthday, calendarNow);
//            diffDays = dayOfNow - dayOfBirth;
//        }else{
//            if(isEndOfMonth(calBirthday)){//判断出生日期是否是月末
//                if (isEndOfMonth(calendarNow)) {//判断今天是否是月末
//                    diffMonths = getMonthsOfAge(calBirthday, calendarNow);
//                }else{
//                    diffMonths = getMonthsOfAge(calBirthday, calendarNow) - 1;
//                    diffDays = dayOfNow;
//                }
//            }else{
//                if (isEndOfMonth(calendarNow)) {//判断今天是否是月末
//                    diffMonths = getMonthsOfAge(calBirthday, calendarNow);
//                }else{
//                    diffMonths = getMonthsOfAge(calBirthday, calendarNow) - 1;
//                    calBirthday.add(Calendar.MONTH, diffMonths);
//                    long diff = calulateDayDiff(calBirthday,calendarNow);
//                    diffDays = Integer.parseInt(String.valueOf(diff));
//                }
//            }
//        }
//        diffDays ++;
//        return new int[] {diffMonths, diffDays};
//    }
//
//    private static long calulateDayDiff(Calendar calendarEarlier,Calendar calendarLater){
//        long dayDiff = 0;
//        long time1 = calendarEarlier.getTimeInMillis();
//        long time2 = calendarLater.getTimeInMillis();
//        dayDiff = (time2 - time1)/1000/3600/24;
//        return dayDiff;
//    }
//
//
//    /**
//     * 获取两个日历的月份之差
//     *
//     * @param calendarBirth
//     * @param calendarNow
//     * @return
//     */
//    private static int getMonthsOfAge(Calendar calendarBirth,Calendar calendarNow) {
//        return (calendarNow.get(Calendar.YEAR) - calendarBirth.get(Calendar.YEAR))* 12+ calendarNow.get(Calendar.MONTH) - calendarBirth.get(Calendar.MONTH);
//    }
//
//
//    /**
//     * 判断这一天是否是月底
//     *
//     * @param calendar
//     * @return
//     */
//    private static boolean isEndOfMonth(Calendar calendar) {
//        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        return dayOfMonth == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//    }

}
