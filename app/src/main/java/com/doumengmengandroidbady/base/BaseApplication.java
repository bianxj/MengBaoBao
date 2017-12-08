package com.doumengmengandroidbady.base;

import android.app.Application;

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

    private MLog log;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initMLog();
        initCrashHandler();
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

}
