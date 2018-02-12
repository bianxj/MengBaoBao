package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.base.BaseApplication;
import com.doumengmengandroidbady.db.DaoManager;
import com.doumengmengandroidbady.db.DoctorDao;
import com.doumengmengandroidbady.db.FeatureDao;
import com.doumengmengandroidbady.db.GrowthDao;
import com.doumengmengandroidbady.db.HospitalDao;
import com.doumengmengandroidbady.db.MengClassDao;
import com.doumengmengandroidbady.entity.LoginInfo;
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.ResponseErrorCode;
import com.doumengmengandroidbady.request.task.LoginTask;
import com.doumengmengandroidbady.response.InitConfigureResponse;
import com.doumengmengandroidbady.response.UpdateContentResponse;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.util.AppUtil;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: Loading界面
 *       首先检测WIFI是否打开
 *       而后检测版本更新
 *       最后获取初始化数据
 * 创建日期: 2018/1/8 10:06
 */
public class LoadingActivity extends BaseActivity {

    public final static String IN_PARAM_AUTO_LOGIN = "is_auto_login";

    private RelativeLayout rl_back;
    private TextView tv_loading_percent;
    private ImageView iv_loading_icon;
    private AnimationDrawable drawable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findView();
        loading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTask(checkVersionTask);
        stopTask(initConfigureTask);
        if ( loginTask != null ) {
            stopTask(loginTask.getTask());
        }
        if ( drawable != null ){
            drawable.stop();
        }
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_loading_percent = findViewById(R.id.tv_loading_percent);
        iv_loading_icon = findViewById(R.id.iv_loading_icon);
        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        tv_loading_percent.setText("40%");
        AnimationDrawable drawable = (AnimationDrawable) iv_loading_icon.getDrawable();
        drawable.start();
    }

    private void loading(){
        Intent intent = getIntent();
        boolean isAutoLogin = intent.getBooleanExtra(IN_PARAM_AUTO_LOGIN,false);
        if ( isAutoLogin ){
            login();
        } else {
            checkVersionAndWifi();
        }
    }


    private void checkVersionAndWifi(){
        if ( isWifi() ){
            checkVersion();
        } else {
            MyDialog.showPromptDialog(this, getString(R.string.prompt_no_wifi), new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    checkVersion();
                }
            });
        }
    }

    private boolean isWifi(){
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    private RequestTask checkVersionTask;
    private String serviceVersion;
    private void checkVersion() {
        try {
            checkVersionTask = new RequestTask.Builder(this, checkVersionCallBack)
                    .setUrl(UrlAddressList.URL_VERSION_FILE)
                    .setType(RequestTask.FILE | RequestTask.LOADING)
                    .setContent(null)
                    .build();
            checkVersionTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private final RequestCallBack checkVersionCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            serviceVersion = null;
        }

        @Override
        public void onError(String result) {
            showPromptDialog(ResponseErrorCode.getErrorMsg(result));
        }

        @Override
        public void onPostExecute(String result) {
            try {
                if (TextUtils.isEmpty(result)){
                    MyDialog.showPromptDialog(LoadingActivity.this, "版本信息获取失败,请检查网络", new MyDialog.PromptDialogCallback() {
                        @Override
                        public void sure() {
                            finish();
                        }
                    });
                } else {
                    serviceVersion = result;
                    String versionName = AppUtil.getVersionName(LoadingActivity.this);
                    if (AppUtil.isNeedUpdate(versionName, result)) {
                        getUpdateInfo();
//                        //TODO 获取版本更新信息
//                        MyDialog.showUpdateDialog(LoadingActivity.this, AppUtil.isForceUpdate(versionName, result), result, "新版本特性\n" +
//                                "1、修复了部分bug\n" +
//                                "2、优化了部分交互设计\n" +
//                                "3、新增了一些功能\n" +
//                                "4、新增了引导页 \n", new MyDialog.UpdateDialogCallback() {
//                            @Override
//                            public void update() {
//                                AppUtil.openSoftwareMarket(LoadingActivity.this);
//                            }
//
//                            @Override
//                            public void cancel() {
//                                initConfigure();
//                            }
//                        });
                    } else {
                        initConfigure();
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    };


    private RequestTask updateInfoTask = null;
    private void getUpdateInfo(){
        try {
            updateInfoTask = new RequestTask.Builder(this,updateInfoCallback)
                    .setUrl(UrlAddressList.URL_UPDATE_INFO)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildUpdateInfo())
                    .build();
            updateInfoTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack updateInfoCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(LoadingActivity.this, ResponseErrorCode.getErrorMsg(result), new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    BaseApplication.getInstance().skipToGuide(LoadingActivity.this);
                }
            });
        }

        @Override
        public void onPostExecute(String result) {
            UpdateContentResponse response = GsonUtil.getInstance().fromJson(result, UpdateContentResponse.class);
            String versionName = null;
            try {
                versionName = AppUtil.getVersionName(LoadingActivity.this);
                MyDialog.showUpdateDialog(LoadingActivity.this, AppUtil.isForceUpdate(versionName, serviceVersion), serviceVersion, convertUpdateContent(response.getResult().getVersionData()), new MyDialog.UpdateDialogCallback() {
                    @Override
                    public void update() {
                        AppUtil.openSoftwareMarket(LoadingActivity.this);
                    }

                    @Override
                    public void cancel() {
                        initConfigure();
                    }
                });
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    };

    private String convertUpdateContent(List<String> list){
        StringBuffer buffer = new StringBuffer("新版本特性\n");
        for (String content:list){
            buffer.append(content+"\n");
        }
        return buffer.toString();
    }

    private Map<String,String> buildUpdateInfo(){
        Map<String,String> map = new HashMap<>();
        map.put(UrlAddressList.PARAM,"");
        return map;
    }

    private RequestTask initConfigureTask = null;
    private void initConfigure(){
        try {
            initConfigureTask = new RequestTask.Builder(this,initConfigureCallback)
                    .setUrl(UrlAddressList.URL_INIT_CONFIGURE)
                    .setType(RequestTask.DEFAULT)
                    .setContent(buildInitConfigureContent())
                    .build();
            initConfigureTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, String> buildInitConfigureContent() {
        UserData userData = BaseApplication.getInstance().getUserData();
        Map<String,String> map = new HashMap<>();
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userData.getUserid());
            map.put(UrlAddressList.PARAM,object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.SESSION_ID,userData.getSessionId());
        return map;
    }

    private final RequestCallBack initConfigureCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public void onError(String result) {
            MyDialog.showPromptDialog(LoadingActivity.this, ResponseErrorCode.getErrorMsg(result), new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    BaseApplication.getInstance().skipToGuide(LoadingActivity.this);
                }
            });
        }

        @Override
        public void onPostExecute(String result) {
            InitConfigureResponse response = GsonUtil.getInstance().fromJson(result,InitConfigureResponse.class);
            new Thread(new DataBaseRunnable(response)).start();
        }
    };

    private LoginTask loginTask = null;
    /**
     * 作者: 边贤君
     * 描述: 登录
     * 日期: 2018/1/8 9:51
     */
    private void login(){
        LoginInfo loginInfo = BaseApplication.getInstance().getLogin();
            try {
                loginTask = new LoginTask(LoadingActivity.this, loginInfo.getAccount(), loginInfo.getPasswd(), new LoginTask.LoginCallBack() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onError(String result) {
                        MyDialog.showPromptDialog(LoadingActivity.this, ResponseErrorCode.getErrorMsg(result), new MyDialog.PromptDialogCallback() {
                            @Override
                            public void sure() {
                                BaseApplication.getInstance().skipToGuide(LoadingActivity.this);
                            }
                        });
                    }

                    @Override
                    public void onPostExecute(String result) {
                        checkVersionAndWifi();
                    }
                },RequestTask.DEFAULT);
                loginTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.rl_back:
                    back();
                break;
            }
        }
    };

    private void back(){
        finish();
    }

    private static class LoadingHandler extends Handler{
        private final static int MESSAGE_JUMP_TO_MAIN = 0x01;
        private final WeakReference<Context> weakReference;

        public LoadingHandler(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == MESSAGE_JUMP_TO_MAIN ) {
                if ( BaseApplication.getInstance().isAbnormalExit() ){
                    Intent intent = new Intent(weakReference.get(), InputInfoActivity.class);
                    weakReference.get().startActivity(intent);
                } else {
                    Intent intent = new Intent(weakReference.get(), MainActivity.class);
                    weakReference.get().startActivity(intent);
                }
            }
        }
    }

    private final Handler handler = new LoadingHandler(this);

    private class DataBaseRunnable implements Runnable{

        private final InitConfigureResponse response;

        public DataBaseRunnable(InitConfigureResponse response) {
            this.response = response;
        }

        @Override
        public void run() {
            DaoManager.getInstance().deleteTable(LoadingActivity.this, DoctorDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, HospitalDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, GrowthDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, MengClassDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, FeatureDao.TABLE_NAME);

            InitConfigureResponse.Result result = response.getResult();

            DaoManager.getInstance().getDaotorDao().saveDoctorList(LoadingActivity.this, result.getDoctorList());
            DaoManager.getInstance().getGrowthDao().saveGrowthList(LoadingActivity.this, result.getGrowthList());
            DaoManager.getInstance().getHospitalDao().saveHospitalList(LoadingActivity.this, result.getHospitalList());
            DaoManager.getInstance().getMengClassDao().saveMengClassList(LoadingActivity.this, result.getMengClassList());
            DaoManager.getInstance().getFeatureDao().saveFeatureList(LoadingActivity.this,result.getFeatureList());
            BaseApplication.getInstance().saveParentInfo(result.getParentInfo());
            BaseApplication.getInstance().saveDayList(result.getDayList());
            handler.sendEmptyMessage(LoadingHandler.MESSAGE_JUMP_TO_MAIN);
        }
    }

}
