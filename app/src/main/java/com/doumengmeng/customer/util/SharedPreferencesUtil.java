package com.doumengmeng.customer.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/5.
 */

public class SharedPreferencesUtil {

    public static void saveBoolean(Context context,String tableName,String key,boolean value){
        SharedPreferences preferences = context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean loadBoolean(Context context,String tableName,String key,boolean defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
        return preferences.getBoolean(key,defaultValue);
    }

    public static void saveString(Context context,String tableName ,String key, String value){
        SharedPreferences preferences = context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String loadString(Context context,String tableName,String key,String defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
        return preferences.getString(key,defaultValue);
    }

    public static void deleteColumn(Context context,String tableName,String columnName){
        SharedPreferences preferences = context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(columnName);
        editor.commit();
    }

    public static void clearTable(Context context,String tableName){
        SharedPreferences preferences = context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
