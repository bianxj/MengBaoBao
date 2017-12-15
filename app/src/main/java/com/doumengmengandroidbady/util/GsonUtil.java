package com.doumengmengandroidbady.util;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/12/15.
 */

public class GsonUtil {

    private static GsonUtil util;
    private Gson gson;

    public static GsonUtil getInstance(){
        if ( null == util ){
            util = new GsonUtil();
        }
        return util;
    }

    private GsonUtil(){
        gson = new Gson();
    }

    public Gson getGson(){
        return gson;
    }

}
