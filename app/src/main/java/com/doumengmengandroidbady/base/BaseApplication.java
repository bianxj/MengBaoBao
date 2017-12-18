package com.doumengmengandroidbady.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.doumengmengandroidbady.entity.UserData;
import com.doumengmengandroidbady.util.MLog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 12:55
 */

public class BaseApplication extends Application {

    private static BaseApplication application;

    private static MLog log;

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
        builder.setInner(true);
        builder.setLogDirName("log");
        builder.setSaveDay(5);
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

    public void clearUserData(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
    }

    public void saveUserData(UserData userData){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UserData.ACCOUNT,userData.getAccount());
        editor.putString(UserData.PWD,userData.getPwd());
        editor.commit();
    }

    public UserData getUserData(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String account = preferences.getString(UserData.ACCOUNT,null);
        String pwd = preferences.getString(UserData.PWD,null);

        if (TextUtils.isEmpty(account)){
            return null;
        }

        if (TextUtils.isEmpty(pwd)){
            return null;
        }

        UserData data = new UserData(account,pwd);
        return data;
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
