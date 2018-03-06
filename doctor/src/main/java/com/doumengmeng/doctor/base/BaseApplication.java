package com.doumengmeng.doctor.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.doumengmeng.doctor.activity.GuideActivity;
import com.doumengmeng.doctor.util.MLog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/2/26.
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
        initPush();
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

    public void initPush(){
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,"5a6e8ad4f43e4803e300008d","Umeng",UMConfigure.DEVICE_TYPE_PHONE,"5aa0b627542b2f3d0a297401addd6ee1");

//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
//
//        //注册推送服务 每次调用register都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//            }
//        });
//
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    public MLog getMLog(){
        return log;
    }

    private void initImageLoader(){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(configImageLoader());
        clearImageLoaderCache();
    }

    private ImageLoaderConfiguration.Builder builder;

    private ImageLoaderConfiguration configImageLoader(){
        if ( builder == null ){
            builder = new ImageLoaderConfiguration.Builder(this);
            builder .denyCacheImageMultipleSizesInMemory()
                    .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024)
                    .memoryCacheSizePercentage(13)
                    .diskCache(new UnlimitedDiskCache(getDiskCacheDir()))
                    .diskCacheFileCount(100)
                    .diskCacheSize(50 * 1024 * 1024)
                    .writeDebugLogs();
        }
        return builder.build();
    }

    public void removeImageFromImageLoader(String url){
        MemoryCacheUtils.removeFromCache(url,ImageLoader.getInstance().getMemoryCache());
        DiskCacheUtils.removeFromCache(url,ImageLoader.getInstance().getDiskCache());
    }

    private void clearImageLoaderCache(){
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }

    public DisplayImageOptions.Builder defaultDisplayImage(){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        return builder;
    }

    private final static String IMAGE_CACHE_DIR = "image";
    private File getDiskCacheDir(){
        File file = new File(getFilesDir()+File.separator+IMAGE_CACHE_DIR);
        if ( !file.exists() || !file.isDirectory() ) {
            if ( !file.mkdirs() ){
                //TODO
                BaseApplication.getInstance().getMLog().error("目录创建失败");
            }
        }
        return file;
    }

    private void initCrashHandler(){
        BaseCrashHandler.getInstance().init(this);
    }


    private final static String PERSON_DIR = "person";
    private final static String EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator + "doc_dmm";
    private final static String PERSON_HEAD_IMG = "headimg.jpg";
    public String getPersonHeadImgPath(){
//        String dirPath = getFilesDir().getPath()+File.separator+PERSON_DIR;
        String dirPath = EXTERNAL_STORAGE_DIR+File.separator+PERSON_DIR;
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ){
            if ( !dir.mkdirs() ){
                //TODO
                BaseApplication.getInstance().getMLog().error("目录创建失败");
            }
        }
        return dirPath + File.separator + PERSON_HEAD_IMG;
    }

    private final static String PERSON_CROP_HEAD_IMG = "cropheadimg.jpg";

    public String getHeadCropPath(){
        return EXTERNAL_STORAGE_DIR+File.separator+PERSON_DIR+File.separator+PERSON_CROP_HEAD_IMG;
    }

    public File getHeadCropFile(){
        File file = new File(getHeadCropPath());
        if ( file.exists() ){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void skipToGuide(Context context){
        //TODO
//        clearImageLoaderCache();
//        clearUserData();
        Intent intent = new Intent(context, GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
