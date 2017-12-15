package com.doumengmengandroidbady.util;

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

}
