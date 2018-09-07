package com.doumengmeng.customer.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.doumengmeng.customer.activity.GuideActivity;
import com.doumengmeng.customer.activity.LoadingActivity;
import com.doumengmeng.customer.entity.LoginInfo;
import com.doumengmeng.customer.entity.RoleType;
import com.doumengmeng.customer.request.entity.InputUserInfo;
import com.doumengmeng.customer.response.InitConfigureResponse;
import com.doumengmeng.customer.response.entity.DayList;
import com.doumengmeng.customer.response.entity.ParentInfo;
import com.doumengmeng.customer.response.entity.UserData;
import com.doumengmeng.customer.util.GsonUtil;
import com.doumengmeng.customer.util.MLog;
import com.doumengmeng.customer.util.NotificationUtil;
import com.doumengmeng.customer.util.SharedPreferencesUtil;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 12:55
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
        initPush();
        application = this;
    }

    private void initMLog(){
        MLog.Builder builder = new MLog.Builder(this);
        builder.setDebug(false);
        builder.setInner(false);
        builder.setLogDirName("/log");
        builder.setSaveDay(5);
        builder.setSaveLog(false);
        builder.setShow(true);
        builder.setClose(true);
        log = builder.build();
    }

    public static BaseApplication getInstance(){
        return application;
    }

    public void initPush(){
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this,"5a6e8ad4f43e4803e300008d","Umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

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

    private final static String TABLE_BANNER = "banner";
    private final static String COLUMN_BANNER_DATA = "banner_data";

    public void saveBannerData(List<InitConfigureResponse.Banner> banners){
        SharedPreferencesUtil.saveString(this,TABLE_BANNER,COLUMN_BANNER_DATA,GsonUtil.getInstance().toJson(banners));
    }

    public List<InitConfigureResponse.Banner> loadBannerData(){
        List<InitConfigureResponse.Banner> banners;
        String value = SharedPreferencesUtil.loadString(this,TABLE_BANNER,COLUMN_BANNER_DATA,null);
        if ( value == null ){
            banners = new ArrayList<>();
        } else {
            banners = GsonUtil.getInstance().fromJson(value,new TypeToken<List<InitConfigureResponse.Banner>>(){}.getType());
        }
        return banners;
    }

    //----------------------------------------------------------------------------------------------
    private final static String TABLE_USER = "user";
    private final static String COLUMN_MAIN_PAGE = "main_page";
    private final static String COLUMN_LOGIN = "login";
    private final static String COLUMN_USER = "user";
    private final static String COLUMN_PARENT = "parent";
    private final static String COLUMN_REGISTER_SESSION = "register_vc";
    private final static String COLUMN_FORGET_SESSION = "forget_vc";

    private final static String COLUMN_DAY_LIST = "day_list";

    public final static String COLUMN_CORRENT_DAY = "corrent_day";
    public final static String COLUMN_CORRENT_MONTH = "corrent_month";
    public final static String COLUMN_CURRENT_DAY = "current_day";

    public final static String COLUMN_NOTIFICATION_DATA = "notification_data";
    public final static String[] COLUMN_NOTIFICATION_NAME = {
            "first_notification",
            "second_notification"
    };

    public void saveNotificationData(InitConfigureResponse.NotificationData data){
        InitConfigureResponse.NotificationData history = loadNotificationData();
        if ( !data.equals(history) ){
            SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_NOTIFICATION_DATA,GsonUtil.getInstance().toJson(data));
            for (String key:COLUMN_NOTIFICATION_NAME){
                saveNotificationStatus(key,false);
            }
        }
    }

    public void saveNotificationStatus(String name,boolean isShowed){
        SharedPreferencesUtil.saveBoolean(this,TABLE_USER,name,isShowed);
    }

    public  boolean loadNotificationStatus(String name){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_USER,name,false);
    }

    public InitConfigureResponse.NotificationData loadNotificationData(){
        String data = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_NOTIFICATION_DATA,null);
        if ( data != null ){
            return GsonUtil.getInstance().fromJson(data, InitConfigureResponse.NotificationData.class);
        }
        return null;
    }

    public void clearUserData(){
        userData = null;
        parentInfo = null;
        loginInfo = null;
        SharedPreferencesUtil.clearTable(this,TABLE_USER);
        NotificationUtil.clearReserve(this);
    }
    public final static String COLUMN_CURRENT_MONTH = "current_month";

    private LoginInfo loginInfo;
    private UserData userData;
    private ParentInfo parentInfo;


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

    public String getMainPage(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_MAIN_PAGE,null);
    }

    public void saveMainPage(String pageName){
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_MAIN_PAGE,pageName);
    }

    public void removeMainPage(){
        SharedPreferencesUtil.deleteColumn(this,TABLE_USER,COLUMN_MAIN_PAGE);
    }

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

    public void saveBabyInfo(InputUserInfo.BabyInfo babyInfo){
        UserData userData = getUserData();
        userData.setTruename(babyInfo.getTrueName());
        userData.setSex(babyInfo.getSex());
        userData.setAccountmobile(babyInfo.getAccountMobile());
        userData.setBirthday(babyInfo.getBirthday());
        userData.setPregnancyweeks(babyInfo.getPregnancyWeeks());
        userData.setPregnancydays(babyInfo.getPregnancyDays());
        userData.setBornheight(babyInfo.getBornHeight());
        userData.setBornweight(babyInfo.getBornWeight());
        userData.setPregnancies(babyInfo.getPregnancies());
        userData.setBirthtimes(babyInfo.getBirthTimes());
        userData.setMumbearage(babyInfo.getMumBearAge());
        userData.setBorntype(babyInfo.getBornType());
        userData.setDeliverymethods(babyInfo.getDeliveryMethods());
        userData.setAssistedreproductive(babyInfo.getAssistedReproductive());
        userData.setBirthinjury(babyInfo.getBirthInjury());
//        userData.setNeonatalasphyxia(babyInfo.getNeonatalAsphyxia());
//        userData.setIntracranialhemorrhage(babyInfo.getIntracranialHemorrhage());
        userData.setHereditaryhistory(babyInfo.getHereditaryHistory());
        userData.setHereditaryhistorydesc(babyInfo.getHereditaryHistoryDesc());
        userData.setAllergichistory(babyInfo.getAllergicHistory());
        userData.setAllergichistorydesc(babyInfo.getAllergicHistoryDesc());
        userData.setPasthistory(babyInfo.getPastHistory());
        userData.setPasthistoryother(babyInfo.getPastHistoryOther());

        saveUserData(userData);
    }

    public void saveUserData(UserData userData){
        this.userData = userData;
        String data = GsonUtil.getInstance().toJson(userData);
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_USER,data);
    }

    public UserData getUserData(){
        if ( userData == null ) {
            String data = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_USER,null);
            if (data != null) {
                userData = GsonUtil.getInstance().fromJson(data, UserData.class);
            }
        }
        return userData;
    }

    public void saveParentInfo(InputUserInfo.ParentInfo parentInfo){
        ParentInfo info = getParentInfo();
        if ( info == null ){
            info = new ParentInfo();
        }
        info.setDadBMI(parentInfo.getDadBMI());
        info.setDadEducation(parentInfo.getDadEducation());
        info.setDadWeight(parentInfo.getDadWeight());
        info.setDadHeight(parentInfo.getDadHeight());
        info.setDadName(parentInfo.getDadName());

        info.setMumEducation(parentInfo.getMumEducation());
        info.setMumBMI(parentInfo.getMumBMI());
        info.setMumWeight(parentInfo.getMumWeight());
        info.setMumHeight(parentInfo.getMumHeight());
        info.setMumName(parentInfo.getMumName());

        saveParentInfo(info);
    }

    public void saveParentInfo(ParentInfo parentInfo){
        if ( parentInfo == null ){
            SharedPreferencesUtil.deleteColumn(this,TABLE_USER,COLUMN_PARENT);
            return;
        }
        this.parentInfo = parentInfo;
        String data = GsonUtil.getInstance().toJson(parentInfo);
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_PARENT,data);
    }

    public ParentInfo getParentInfo(){
        if ( parentInfo == null ) {
            String data = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_PARENT,null);
            if (data != null) {
                parentInfo = GsonUtil.getInstance().fromJson(data, ParentInfo.class);
            }
        }
        return parentInfo;
    }

    public void saveRegisterSession(String registerVc){
        SharedPreferencesUtil.saveString(this,TABLE_USER, COLUMN_REGISTER_SESSION,registerVc);
    }

    public String getRegisterSession(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER, COLUMN_REGISTER_SESSION,null);
    }

    public void saveForgetSession(String forgetVc){
        SharedPreferencesUtil.saveString(this,TABLE_USER, COLUMN_FORGET_SESSION,forgetVc);
    }

    public String getForgetSession(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER, COLUMN_FORGET_SESSION,null);
    }

    public void saveDayList(DayList dayList){
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_DAY_LIST,GsonUtil.getInstance().toJson(dayList));
    }

    public DayList getDayList(){
        DayList list = null;
        String value = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_DAY_LIST,null);
        if ( value != null ){
            list = GsonUtil.getInstance().fromJson(value,DayList.class);
        }
        return list;
    }

    public boolean isUpperThan37Month(){
        DayList list = getDayList();
        if ( list == null || !list.notNull() ){
            return false;
        }
        int month = Integer.parseInt(list.getCurrentMonth());
        int day = Integer.parseInt(list.getCurrentDay());
        return month >= 37;
    }

    public int getRecordTimes(){
        if ( TextUtils.isEmpty(userData.getRecordtimes()) ){
            return 0;
        }
        return Integer.parseInt(userData.getRecordtimes());
    }

    public void setRecordTimes(String recordTimes){
        userData.setRecordtimes(recordTimes);
        saveUserData(userData);
    }

    public void minusRecordTimes(){
        int recordTimes = Integer.parseInt(userData.getRecordtimes());
        recordTimes--;
        userData.setRecordtimes(recordTimes+"");
        saveUserData(userData);
    }

    public void addRecordTimes(int count){
        int recordTimes = Integer.parseInt(userData.getRecordtimes());
        recordTimes+=count;
        userData.setRecordtimes(recordTimes+"");
        saveUserData(userData);
    }

    public void addRecordTimes(){
        addRecordTimes(1);
//        int recordTimes = Integer.parseInt(userData.getRecordtimes());
//        recordTimes++;
//        userData.setRecordtimes(recordTimes+"");
//        saveUserData(userData);
    }

    public void payRoleType(){
        RoleType roleType = getRoleType();
//        UserData userData = getUserData();
        if ( roleType == RoleType.FREE_NET_USER ){
            setRoleType(RoleType.PAY_NET_USER);
        } else if ( roleType == RoleType.FREE_HOSPITAL_USER ){
            setRoleType(RoleType.PAY_HOSPITAL_USER);
        }
    }

    //----------------------------------------------------------------------------------------------
    private final static String TABLE_SEARCH_HISTORY = "history";
    private final static String COLUMN_HISTORY = "histroy_value";
    public void saveSearchHistory(JSONArray array){
        SharedPreferencesUtil.saveString(this,TABLE_SEARCH_HISTORY,COLUMN_HISTORY,array.toString());
    }

    public JSONArray getSearchHistory(){
        String value = SharedPreferencesUtil.loadString(this,TABLE_SEARCH_HISTORY,COLUMN_HISTORY,null);
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
        SharedPreferencesUtil.deleteColumn(this,TABLE_SEARCH_HISTORY,COLUMN_HISTORY);
    }


    //----------------------------------------------------------------------------------------------
    private final static String TABLE_CONFIG = "config";
    private final static String COLUMN_IS_FIRST = "isFirstLogin";
    private final static String COLUMN_IS_ABNORMAL_EXIT = "isAbnormalExit";
    private final static String COLUMN_FEATURE_VERSION = "featureVersion";
    public boolean isFistLogin(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_CONFIG,COLUMN_IS_FIRST,true);
    }

    public void saveLoginCount(){
        SharedPreferencesUtil.saveBoolean(this,TABLE_CONFIG,COLUMN_IS_FIRST,false);
    }

    public boolean isAbnormalExit(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_CONFIG,COLUMN_IS_ABNORMAL_EXIT,false);
    }

    public void saveAbnormalExit(boolean isAbnormalExit){
        SharedPreferencesUtil.saveBoolean(this,TABLE_CONFIG,COLUMN_IS_ABNORMAL_EXIT,isAbnormalExit);
    }

    public void saveFeatureVersion(String version){
        SharedPreferencesUtil.saveString(this,TABLE_CONFIG,COLUMN_FEATURE_VERSION,version);
    }

    public String loadFeatureVersion(){
        return SharedPreferencesUtil.loadString(this,TABLE_CONFIG,COLUMN_FEATURE_VERSION,"");
    }

    public boolean isPay(){
        return RoleType.PAY_HOSPITAL_USER == getRoleType() || RoleType.PAY_NET_USER == getRoleType();
    }

    private void setRoleType(RoleType roleType){
        UserData userData = getUserData();
        if ( roleType == RoleType.PAY_NET_USER ){
            userData.setRoletype("1");
        } else if ( roleType == RoleType.FREE_HOSPITAL_USER ){
            userData.setRoletype("3");
        } else if ( roleType == RoleType.PAY_HOSPITAL_USER ){
            userData.setRoletype("2");
        } else {
            userData.setRoletype("0");
        }
        saveUserData(userData);
    }

    public RoleType getRoleType(){
        UserData data = getUserData();
        String roleType = data.getRoletype();
        if ( "3".equals(roleType) ) {
            return RoleType.FREE_HOSPITAL_USER;
        } else if ( "1".equals(roleType) ){
            return RoleType.PAY_NET_USER;
        } else if ( "2".equals(roleType) ){
            return RoleType.PAY_HOSPITAL_USER;
        } else {
            return RoleType.FREE_NET_USER;
        }
//        return RoleType.FREE_NET_USER;
    }

    private final static String PERSON_DIR = "person";
    private final static String EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator + "doumegmeng";
    private final static String PERSON_HEAD_IMG = "headimg.jpg";

    private File getHeadImgDir(){
        File dir = new File(getExternalCacheDir()+File.separator+PERSON_DIR);
        if ( !dir.exists() || !dir.isDirectory() ){
            if ( !dir.mkdirs() ){
                //TODO
                BaseApplication.getInstance().getMLog().error("目录创建失败");
            }
        }
        return dir;
    }

    public String getPersonHeadImgPath(){
//        String dirPath = getFilesDir().getPath()+File.separator+PERSON_DIR;
        File dir = getHeadImgDir();
        return dir.getPath() + File.separator + PERSON_HEAD_IMG;
    }

    private final static String PERSON_CROP_HEAD_IMG = "cropheadimg.jpg";

    public String getHeadCropPath(){
        File dir = getHeadImgDir();
        return dir.getPath()+File.separator+PERSON_CROP_HEAD_IMG;
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

    private final static String PICTURE_DIR = "picture";
    public String getUploadPicture(){
        String dirPath = getFilesDir().getPath()+File.separator+PICTURE_DIR;
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ){
            if ( !dir.mkdirs() ){
                //TODO
                BaseApplication.getInstance().getMLog().error("目录创建失败");
            }
        }
        return dirPath + File.separator + "picture_" + System.currentTimeMillis() + ".jpg";
    }

    public void clearUploadPicture(){
        String dirPath = getFilesDir().getPath()+File.separator+PICTURE_DIR;
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if ( files != null ){
            for (File file : files){
                file.delete();
            }
        }
    }

    private DisplayMetrics display;
    public DisplayMetrics getDisplayInfo(){
        if ( display == null ) {
            display = getResources().getDisplayMetrics();
        }
        return display;
    }

    public void skipToGuide(Context context){
        activities.clear();
        clearImageLoaderCache();
        clearUserData();
        Intent intent = new Intent(context, GuideActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void skipToLoading(Context context){
//        Context context = builder.getWeakReference().get();
        Intent intent = new Intent(context, LoadingActivity.class);
        intent.putExtra(LoadingActivity.IN_PARAM_AUTO_LOGIN,true);
        context.startActivity(intent);
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
