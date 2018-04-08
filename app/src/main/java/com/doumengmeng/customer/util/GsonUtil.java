package com.doumengmeng.customer.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/12/15.
 */

public class GsonUtil {

    private static GsonUtil util;
    private final Gson gson;

    public static GsonUtil getInstance(){
        if ( null == util ){
            util = new GsonUtil();
        }
        return util;
    }

    private GsonUtil(){
        gson = new Gson();
    }

//    public Gson getGson(){
//        return gson;
//    }

    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json,typeOfT);
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json,classOfT);
    }

    public String toJson(Object object){
        return gson.toJson(object);
    }

}
