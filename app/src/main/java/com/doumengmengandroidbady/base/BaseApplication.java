package com.doumengmengandroidbady.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MLog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 12:55
 */

public class BaseApplication extends Application {

    private static BaseApplication application;

    private static MLog log;

    private UserData userData;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initMLog();
        initCrashHandler();
        application = this;
    }

    private void initMLog(){
        MLog.Builder builder = new MLog.Builder(this);
        builder.setDebug(true);
        builder.setInner(false);
        builder.setLogDirName("/log");
        builder.setSaveDay(5);
        builder.setSaveLog(true);
        builder.setShow(true);
        log = builder.build();
    }

    public static BaseApplication getInstance(){
        return application;
    }

    public MLog getMLog(){
        return log;
    }

    private void initImageLoader(){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(configImageLoader());
    }

    private ImageLoaderConfiguration configImageLoader(){
        ImageLoaderConfiguration loader =
                new ImageLoaderConfiguration.Builder(this)
                        .denyCacheImageMultipleSizesInMemory()
                        .diskCache(new UnlimitedDiskCache(getDiskCacheDir()))
                        .diskCacheFileCount(100)
                        .diskCacheSize(50 * 1024 * 1025)
                        .writeDebugLogs()
                        .build();
        return loader;
    }

    private final static String IMAGE_CACHE_DIR = "image";
    private File getDiskCacheDir(){
        File file = new File(getFilesDir()+File.separator+IMAGE_CACHE_DIR);
        file.mkdirs();
        return file;
    }

    private void initCrashHandler(){
        BaseCrashHandler.getInstance().init(this);
    }

    public void saveVerificationCode(String verification){
        SharedPreferences preferences = getSharedPreferences("temp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("verification",verification);
        editor.commit();
    }

    public String getVerificationCode(){
        SharedPreferences preferences = getSharedPreferences("temp", Context.MODE_PRIVATE);
        return preferences.getString("verification",null);
    }

    public void clearUserData(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
    }

    public void saveUserData(UserData userData){
        this.userData = userData;
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String data = GsonUtil.getInstance().getGson().toJson(userData);
        editor.putString("user",data);
        editor.commit();
    }

    public UserData getUserData(){
        if ( userData == null ) {
            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            String data = preferences.getString("user", null);
            if (data != null) {
                userData = GsonUtil.getInstance().getGson().fromJson(data, UserData.class);
            }
        }
        return userData;
    }

    public void saveSearchHistory(JSONArray array){
        SharedPreferences preferences = getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("histroy_value",array.toString());
        editor.commit();
    }

    public JSONArray getSearchHistory(){
        SharedPreferences preferences = getSharedPreferences("history",Context.MODE_PRIVATE);
        String value = preferences.getString("histroy_value",null);
        JSONArray array = null;
        if ( value != null ) {
            try {
                array = new JSONArray(value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    public void clearSearchHistory(){
        SharedPreferences preferences = getSharedPreferences("history",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("histroy_value").commit();
    }

    public boolean isPay(){
        return true;
    }

    public boolean isFistLogin(){
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        return preferences.getBoolean("isFirstLogin",true);
    }

    public void saveLoginCount(){
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFistLogin",false);
        editor.commit();
    }

}
