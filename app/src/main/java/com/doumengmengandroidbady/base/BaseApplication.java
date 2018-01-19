package com.doumengmengandroidbady.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import com.doumengmengandroidbady.entity.RoleType;
import com.doumengmengandroidbady.request.entity.InputUserInfo;
import com.doumengmengandroidbady.response.DayList;
import com.doumengmengandroidbady.response.ParentInfo;
import com.doumengmengandroidbady.response.UserData;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MLog;
import com.doumengmengandroidbady.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
                        .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024))
                        .memoryCacheSize(2 * 1024 * 1024)
                        .memoryCacheSizePercentage(13)
                        .diskCache(new UnlimitedDiskCache(getDiskCacheDir()))
                        .diskCacheFileCount(100)
                        .diskCacheSize(50 * 1024 * 1024)
                        .writeDebugLogs()
                        .build();
        return loader;
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


    //----------------------------------------------------------------------------------------------
    public final static String TABLE_USER = "user";
    public final static String COLUMN_USER = "user";
    public final static String COLUMN_PARENT = "parent";
    public final static String COLUMN_REGISTER_VC = "register_vc";
    public final static String COLUMN_FORGET_VC = "forget_vc";

    public final static String COLUMN_DAY_LIST = "day_list";

    public final static String COLUMN_CORRENT_DAY = "corrent_day";
    public final static String COLUMN_CORRENT_MONTH = "corrent_month";
    public final static String COLUMN_CURRENT_DAY = "current_day";
    public final static String COLUMN_CURRENT_MONTH = "current_month";

    private UserData userData;
    private ParentInfo parentInfo;
    public void clearUserData(){
        SharedPreferencesUtil.clearTable(this,TABLE_USER);
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
        userData.setNeonatalasphyxia(babyInfo.getNeonatalAsphyxia());
        userData.setIntracranialhemorrhage(babyInfo.getIntracranialHemorrhage());
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
        String data = GsonUtil.getInstance().getGson().toJson(userData);
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_USER,data);
    }

    public UserData getUserData(){
        if ( userData == null ) {
            String data = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_USER,null);
            if (data != null) {
                userData = GsonUtil.getInstance().getGson().fromJson(data, UserData.class);
            }
        }
        return userData;
    }

    public void saveParentInfo(InputUserInfo.ParentInfo parentInfo){
        ParentInfo info = getParentInfo();
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
        this.parentInfo = parentInfo;
        String data = GsonUtil.getInstance().getGson().toJson(parentInfo);
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_PARENT,data);
    }

    public ParentInfo getParentInfo(){
        if ( parentInfo == null ) {
            String data = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_PARENT,null);
            if (data != null) {
                parentInfo = GsonUtil.getInstance().getGson().fromJson(data, ParentInfo.class);
            }
        }
        return parentInfo;
    }

    public void saveRegisterVc(String registerVc){
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_REGISTER_VC,registerVc);
    }

    public String getRegisterVc(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_REGISTER_VC,null);
    }

    public void saveForgetVc(String forgetVc){
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_FORGET_VC,forgetVc);
    }

    public String getForgetVc(){
        return SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_FORGET_VC,null);
    }

    public void saveDayList(DayList dayList){
        SharedPreferencesUtil.saveString(this,TABLE_USER,COLUMN_DAY_LIST,GsonUtil.getInstance().getGson().toJson(dayList));
    }

    public DayList getDayList(){
        DayList list = null;
        String value = SharedPreferencesUtil.loadString(this,TABLE_USER,COLUMN_DAY_LIST,null);
        if ( value != null ){
            list = GsonUtil.getInstance().getGson().fromJson(value,DayList.class);
        }
        return list;
    }

    public void minusRecordTimes(){
        int recordTimes = Integer.parseInt(userData.getRecordtimes());
        recordTimes--;
        userData.setRecordtimes(recordTimes+"");
        saveUserData(userData);
    }

    public void addRecordTimes(){
        int recordTimes = Integer.parseInt(userData.getRecordtimes());
        recordTimes++;
        userData.setRecordtimes(recordTimes+"");
        saveUserData(userData);
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
    public final static String TABLE_CONFIG = "config";
    public final static String COLUMN_IS_FIRST = "isFirstLogin";
    public final static String COLUMN_IS_ABNORMAL_EXIT = "isAbnormalExit";
    public boolean isFistLogin(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_CONFIG,COLUMN_IS_FIRST,false);
    }

    public void saveLoginCount(){
        SharedPreferencesUtil.saveBoolean(this,TABLE_CONFIG,COLUMN_IS_FIRST,false);
    }

    public boolean isAbnormalExit(){
        return SharedPreferencesUtil.loadBoolean(this,TABLE_CONFIG,COLUMN_IS_FIRST,false);
    }

    public void saveAbnormalExit(boolean isAbnormalExit){
        SharedPreferencesUtil.saveBoolean(this,TABLE_CONFIG,COLUMN_IS_ABNORMAL_EXIT,isAbnormalExit);
    }

    public boolean isPay(){
        if ( RoleType.PAY_HOSPITAL_USER == getRoleType() || RoleType.PAY_NET_USER == getRoleType() ){
            return true;
        }
        return false;
    }

    public RoleType getRoleType(){
        UserData data = getUserData();
        String roleType = data.getRoletype();
//        if ( "3".equals(roleType) ) {
//            return RoleType.FREE_HOSPITAL_USER;
//        } else if ( "1".equals(roleType) ){
//            return RoleType.PAY_NET_USER;
//        } else if ( "2".equals(roleType) ){
//            return RoleType.PAY_HOSPITAL_USER;
//        } else {
//            return RoleType.FREE_NET_USER;
//        }
        return RoleType.PAY_HOSPITAL_USER;
    }

    public final static String PERSON_DIR = "person";
    public final static String PERSON_HEAD_IMG = "headimg.jpg";
    public String getPersonHeadImgPath(){
        String dirPath = getFilesDir().getPath()+File.separator+PERSON_DIR;
//        String dirPath = Environment.getExternalStorageDirectory().getPath()+File.separator+PERSON_DIR;
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ){
            dir.mkdirs();
        }
        return dirPath + File.separator + PERSON_HEAD_IMG;
    }

    public final static String PICTURE_DIR = "picture";
    public String getUploadPicture(){
        String dirPath = getFilesDir().getPath()+File.separator+PICTURE_DIR;
        File dir = new File(dirPath);
        if ( !dir.exists() || !dir.isDirectory() ){
            dir.mkdirs();
        }
        return dirPath + File.separator + "picture_" + System.currentTimeMillis();
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

}
