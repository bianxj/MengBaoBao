package com.doumengmeng.doctor.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FormatCheckUtil {

    public static boolean isChinese(String chinese){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m = p.matcher(chinese);
        return m.matches();
//        return true;
    }

    public static boolean isPhone(String phone){
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isLetterDigit(String string){
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    public static boolean isSameString(String s1,String s2){
        if (TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2)){
            return true;
        }
        if ( !TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2) ){
            if ( s1.equals(s2) ){
                return true;
            }
        }
        return false;
    }

    public static boolean isOnlyPointNumber(String number) {//保留两位小数正则
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
