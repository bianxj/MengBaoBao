package com.doumengmengandroidbady.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FormatCheckUtil {

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

}
