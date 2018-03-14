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
