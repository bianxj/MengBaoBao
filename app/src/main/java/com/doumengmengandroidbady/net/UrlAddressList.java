package com.doumengmengandroidbady.net;

import java.util.Map;
import java.util.Set;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 19:47
 */

public class UrlAddressList {

    public final static String BASE_URL = "http://192.168.31.112:8080/mbbPhoneServerV2/";

    public final static String URL_GET_VC = BASE_URL + "babyUser.do?method=SendMessage";
//    public final static String URL_REGISTER_CHECT = BASE_URL + "babyUser.do?method=RegisterCheck";
    public final static String URL_REGISTER = BASE_URL + "babyUser.do?method=Register";
    public final static String URL_LOGIN = BASE_URL +  "babyUser.do?method=Login";
    public final static String URL_RESET_PASSWORD_GET_VC = BASE_URL + "babyUser.do?method=SendResetPasswordMessage";
    public final static String URL_RESET_PASSWORD = BASE_URL + "babyUser.do?method=SendResetPasswordMessage";
    public final static String URL_INIT_CONFIGURE = BASE_URL + "system.do?method=InitServerConfigure";

    public static String mergeUrlAndParam(String url , String value){
        return url+"&paramStr="+value;
    }

    public static String mergUrlAndParam(String url, Map<String,String> map){
        StringBuilder builder = new StringBuilder(url);
        Set<String> keys = map.keySet();
        for (String key:keys) {
            builder.append("&"+key+"="+map.get(key));
        }
        return builder.toString();
    }

}
