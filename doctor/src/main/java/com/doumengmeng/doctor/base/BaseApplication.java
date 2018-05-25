package com.doumengmeng.doctor.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.doumengmeng.doctor.activity.LoginActivity;
import com.doumengmeng.doctor.entity.LoginInfo;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.util.FileUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MLog;
import com.doumengmeng.doctor.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class BaseApplication extends Application {

    private static BaseApplication application;
    private List<Activity> activities = new ArrayList<>();

    private static MLog log;


    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        initMLog();
        initCrashHandler();
        initUMeng();
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
        builder.setClose(false);
        log = builder.build();
    }

    public static BaseApplication getInstance(){
        return application;
    }

    public void initUMeng(){
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,"5aefba74f29d9835520000f7","Umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
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

    /**-------------------------------------------SharedPerference Start---------------------------------------------------*/
    private final static String TABLE_GUIDE = "guide";
    private final static String COLUMN_GUIDE_STATE = "state";

    public void saveGuideState(){
        SharedPreferencesUtil.saveBoolean(this,TABLE_GUIDE,COLUMN_GUIDE_STATE,true);
    }

    public boolean loadGuideState(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_GUIDE,COLUMN_GUIDE_STATE,false);
    }

    private final static String TABLE_USER = "user";

    private final static String COLUMN_USER_DATA = "user_data";
    private final static String COLUMN_SESSION_ID = "session_id";
    private final static String COLUMN_REGISTER_SESSION = "register_vc";
    private final static String COLUMN_FORGET_SESSION = "forget_vc";
    private final static String COLUMN_IS_ABNORMAL_EXIT = "is_abnormal_exit";
    private final static String COLUMN_IS_TO_EXAMINE = "is_to_examine";
    private final static String COLUMN_LOGIN = "login";
    private final static String COLUMN_MESSAGE_COUNT = "message_count";
    private final static String COLUMN_ASSESSMENT_COUNT = "assessment_count";
    private final static String COLUMN_MAINTAIN = "maintion";

    public boolean hasAccountData(){
        LoginInfo loginInfo = getLogin();
        if ( loginInfo != null ){
            if (!TextUtils.isEmpty(loginInfo.getAccount())
                    && !TextUtils.isEmpty(loginInfo.getPasswd())){
                return true;
            }
        }
        return false;
    }

    public void saveAssessmentCount(int count){
        SharedPreferencesUtil.saveInteger(this,TABLE_USER,COLUMN_ASSESSMENT_COUNT,count);
    }

    public int getAssessmentCount(){
        return SharedPreferencesUtil.loadInteger(this,TABLE_USER,COLUMN_ASSESSMENT_COUNT,0);
    }

    public void saveMessageCount(int count){
        SharedPreferencesUtil.saveInteger(this,TABLE_USER,COLUMN_MESSAGE_COUNT,count);
    }

    public int getMessageCount(){
        return SharedPreferencesUtil.loadInteger(this,TABLE_USER,COLUMN_MESSAGE_COUNT,0);
    }

    private UserData userData;
    public void clearUserData(){
        userData = null;
        loginInfo = null;
        SharedPreferencesUtil.clearTable(this,TABLE_USER);
    }

    public void saveUserData(UserData userData){
        this.userData = userData;
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_USER_DATA, GsonUtil.getInstance().toJson(userData));
    }

    public UserData getUserData(){
        if ( userData == null ) {
            String content = SharedPreferencesUtil.loadString(this, TABLE_USER, COLUMN_USER_DATA, "");
            if (TextUtils.isEmpty(content)) {
                return null;
            }
            userData = GsonUtil.getInstance().fromJson(content,UserData.class);
        }
        return userData;
    }

    public void saveRegisterSession(String registerSession){
        SharedPreferencesUtil.saveString(this,TABLE_USER, COLUMN_REGISTER_SESSION,registerSession);
    }

    public String getRegisterSession(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER, COLUMN_REGISTER_SESSION,"");
    }

    public void saveForgetSession(String forgetSession){
        SharedPreferencesUtil.saveString(this,TABLE_USER, COLUMN_FORGET_SESSION,forgetSession);
    }

    public String getForgetSession(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER, COLUMN_FORGET_SESSION,"");
    }

    public String getSessionId(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_SESSION_ID,"");
    }

    public void saveSessionId(String sessionId){
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_SESSION_ID,sessionId);
    }

    public boolean isAbnormalExit(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_USER,COLUMN_IS_ABNORMAL_EXIT,true);
    }

    public void saveAbnormalExit(boolean isAbnormalExit){
        SharedPreferencesUtil.saveBoolean(this,TABLE_USER,COLUMN_IS_ABNORMAL_EXIT,isAbnormalExit);
    }

    public boolean isToExamine(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_USER,COLUMN_IS_TO_EXAMINE,true);
    }

    public void saveToExamine(boolean isToExamine){
        SharedPreferencesUtil.saveBoolean(this,TABLE_USER,COLUMN_IS_TO_EXAMINE,isToExamine);
    }

    private LoginInfo loginInfo;
    public LoginInfo getLogin(){
        if ( loginInfo == null ) {
            String data = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_LOGIN,null);
            if (data != null) {
                loginInfo = GsonUtil.getInstance().fromJson(data, LoginInfo.class);
            }
        }
        return loginInfo;
    }

    public void saveLogin(String account , String passwd){
        if ( loginInfo == null ){
            loginInfo = new LoginInfo();
        }
        loginInfo.setAccount(account);
        loginInfo.setPasswd(passwd);
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_LOGIN,GsonUtil.getInstance().toJson(loginInfo));
    }

    public String getMaintain(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_MAINTAIN,"");
    }

    public void saveMaintain(String maintain){
        if ( TextUtils.isEmpty(maintain) ){
            return;
        }
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_MAINTAIN,maintain);
    }

    /**-------------------------------------------SharedPerference End---------------------------------------------------*/

    private final static String EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator + "doc_dmm";
    private final static String PERSON_DIR = "person";
    private final static String PERSON_HEAD_IMG = "headimg.jpg";
    private final static String PERSON_CROP_HEAD_IMG = "cropheadimg.jpg";

    private final static String PERSON_CERTIFICATE_DIR = "certificate";
    private final static String PERSON_CERTIFICATE_ONE = "certificate_one.jpg";
    private final static String PERSON_CERTIFICATE_TWO = "certificate_two.jpg";

    private File getCertificateDir(){
        String dirPath = getFilesDir().getPath()+File.separator+PERSON_CERTIFICATE_DIR;
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ){
            if ( !dir.mkdirs() ){
                BaseApplication.getInstance().getMLog().error("目录创建失败");
            }
        }
        return dir;
    }

    public String getCertificateOnePath(){
        File dir = getCertificateDir();
        return dir.getPath() + File.separator + PERSON_CERTIFICATE_ONE;
    }

    public String getCertificateTwoPath(){
        File dir = getCertificateDir();
        return dir.getPath() + File.separator + PERSON_CERTIFICATE_TWO;
    }

    private File getDoctorHeadImgDir(){
        String dirPath = getExternalCacheDir().getPath()+File.separator+PERSON_DIR;
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ){
            if ( !dir.mkdirs() ){
                BaseApplication.getInstance().getMLog().error("目录创建失败");
            }
        }
        return dir;
    }

    public String getDoctorHeadImgPath(){
        File dir = getDoctorHeadImgDir();
        return dir.getPath() + File.separator + PERSON_HEAD_IMG;
    }

    public String getHeadCropPath(){
        File dir = getDoctorHeadImgDir();
        return dir.getPath()+File.separator+PERSON_CROP_HEAD_IMG;
    }

    public String getHeadImgPath(){
        File dir = getDoctorHeadImgDir();
        return dir.getPath()+File.separator+PERSON_HEAD_IMG;
    }

    public File getHeadCropFile() throws IOException {
        return FileUtil.getIntance().createNewFile(getHeadCropPath());
    }

    public void clearHeadFile(){
        FileUtil.getIntance().deleteFile(getHeadImgPath());
        FileUtil.getIntance().deleteFile(getHeadCropPath());
    }

    public void skipToGuide(Activity context){
        finishApp(context);
        activities.clear();
        clearImageLoaderCache();
        clearUserData();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public void finishApp(Activity currentActivity){
        for (Activity activity:activities){
            if ( !activity.equals(currentActivity) ){
                activity.finish();
            }
        }
        currentActivity.finish();
    }

}
