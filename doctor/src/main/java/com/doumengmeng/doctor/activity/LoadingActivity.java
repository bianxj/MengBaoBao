package com.doumengmeng.doctor.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmeng.doctor.R;
import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;
import com.doumengmeng.doctor.db.DaoManager;
import com.doumengmeng.doctor.db.FeatureDao;
import com.doumengmeng.doctor.db.HospitalDao;
import com.doumengmeng.doctor.db.NurtureDao;
import com.doumengmeng.doctor.entity.LoginInfo;
import com.doumengmeng.doctor.net.UrlAddressList;
import com.doumengmeng.doctor.request.RequestCallBack;
import com.doumengmeng.doctor.request.RequestTask;
import com.doumengmeng.doctor.request.ResponseErrorCode;
import com.doumengmeng.doctor.request.task.LoginTask;
import com.doumengmeng.doctor.response.InitConfigureResponse;
import com.doumengmeng.doctor.response.UpdateContentResponse;
import com.doumengmeng.doctor.response.entity.UserData;
import com.doumengmeng.doctor.util.AppUtil;
import com.doumengmeng.doctor.util.GsonUtil;
import com.doumengmeng.doctor.util.MyDialog;
import com.doumengmeng.doctor.util.PermissionUtil;

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
    private int percent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findView();
//        loading();
//        if ( !BaseApplication.getInstance().isToExamine() || BaseApplication.getInstance().isAbnormalExit() ){
//            startActivity(LoginActivity.class);
//            back();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkExternalStorage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeLoadingPercent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseLoadingPercent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTask(checkVersionTask);
        stopTask(initConfigureTask);
        if ( loginTask != null ) {
            stopTask(loginTask.getTask());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkExternalStorage(){
        if ( PermissionUtil.checkPermissionAndRequest(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ){
            loading();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new PermissionUtil.RequestPermissionSuccess() {
            @Override
            public void success(String permission) {
                BaseApplication.getInstance().initUMeng();
                loading();
            }

            @Override
            public void denied(String permission) {
                checkExternalStorage();
            }

            @Override
            public void alwaysDenied(String permission) {
                MyDialog.showChooseDialog(LoadingActivity.this, "请打开存储权限",R.string.prompt_bt_cancel,R.string.dialog_btn_prompt_go_setting, new MyDialog.ChooseDialogCallback() {
                    @Override
                    public void sure() {
                        AppUtil.openPrimession(LoadingActivity.this);
                    }

                    @Override
                    public void cancel() {
                        back();
                    }
                });
            }
        });
    }

    private void findView(){
        rl_back = findViewById(R.id.rl_back);
        tv_loading_percent = findViewById(R.id.tv_loading_percent);
        iv_loading_icon = findViewById(R.id.iv_loading_icon);

        rl_back.setVisibility(View.GONE);
        initView();
    }

    private void initView(){
        rl_back.setOnClickListener(listener);
        percent = 0;
        tv_loading_percent.setText("0%");
        AnimationDrawable drawable = (AnimationDrawable) iv_loading_icon.getDrawable();
        drawable.start();
    }

    private void updateLoadingPercent(){
        tv_loading_percent.setText(String.format("%d%%",percent));
        percent+=5;
    }

    private void loading(){
        checkVersionAndWifi();
    }

    private void checkVersionAndWifi(){
        if ( isWifi() ){
//            afterCheckVersion();
            checkVersion();
        } else {
            MyDialog.showPromptDialog(this, getString(R.string.prompt_no_wifi),R.string.dialog_btn_go_on, new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    //TODO
//                    afterCheckVersion();
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
                    .setType(RequestTask.FILE)
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
            startLoadingPercent();
            serviceVersion = null;
        }

        @Override
        public void onError(String result) {
            stopLoadingPercent();
            MyDialog.showPromptDialog(LoadingActivity.this, ResponseErrorCode.getErrorMsg(result),null);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                if (TextUtils.isEmpty(result)){
                    stopLoadingPercent();
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
                    } else {
                        afterCheckVersion();
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                stopLoadingPercent();
            }
        }
    };

    private void afterCheckVersion(){
        Intent intent = getIntent();
        boolean isAutoLogin = intent.getBooleanExtra(IN_PARAM_AUTO_LOGIN,false);
        if ( isAutoLogin ){
            login();
        } else {
            initConfigure();
        }
    }


    private RequestTask updateInfoTask = null;
    private void getUpdateInfo(){
        try {
            updateInfoTask = new RequestTask.Builder(this,updateInfoCallback)
                    .setUrl(UrlAddressList.URL_UPDATE_INFO)
                    .setType(RequestTask.JSON)
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
            stopLoadingPercent();
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
                    .setType(RequestTask.JSON)
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
            object.put("doctorId",userData.getDoctorId());
            map.put(UrlAddressList.PARAM,object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put(UrlAddressList.SESSION_ID,BaseApplication.getInstance().getSessionId());
        return map;
    }

    private final RequestCallBack initConfigureCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public void onError(String result) {
            stopLoadingPercent();
            MyDialog.showPromptDialog(LoadingActivity.this, ResponseErrorCode.getErrorMsg(result), new MyDialog.PromptDialogCallback() {
                @Override
                public void sure() {
                    BaseApplication.getInstance().skipToGuide(LoadingActivity.this);
                }
            });
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
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
                        stopLoadingPercent();
                        MyDialog.showPromptDialog(LoadingActivity.this, ResponseErrorCode.getErrorMsg(result), new MyDialog.PromptDialogCallback() {
                            @Override
                            public void sure() {
                                BaseApplication.getInstance().skipToGuide(LoadingActivity.this);
                            }
                        });
                    }

                    @Override
                    public void onPostExecute(String result) {
//                        checkVersionAndWifi();
//                        if (BaseApplication.getInstance().isToExamine()){
                            initConfigure();
//                        } else {
//                            MyDialog.showPromptDialog(LoadingActivity.this, "请等待审核\n" +
//                                    " 审核通过后将会以短信形式通知您", R.string.sure, new MyDialog.PromptDialogCallback() {
//                                @Override
//                                public void sure() {
//
//                                }
//                            });
//                        }
                    }
                },RequestTask.JSON);
                loginTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                stopLoadingPercent();
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
        private final static int MESSAGE_LOADING_PERCENT = 0x02;
        private final static int MESSAGE_LOADING_CANCLE = 0x03;
        private final WeakReference<LoadingActivity> weakReference;

        public LoadingHandler(LoadingActivity context) {
            this.weakReference = new WeakReference<>(context);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == MESSAGE_JUMP_TO_MAIN ) {
                weakReference.get().stopLoadingPercent();
                //TODO
                if ( BaseApplication.getInstance().isAbnormalExit() ){
                    Intent intent = new Intent(weakReference.get(), InputDoctorInfoActivity.class);
                    weakReference.get().startActivity(intent);
                } else {
                    Intent intent = new Intent(weakReference.get(), MainActivity.class);
                    weakReference.get().startActivity(intent);
                }
            } else if ( msg.what == MESSAGE_LOADING_PERCENT) {
                removeMessages(MESSAGE_LOADING_PERCENT);
                if ( weakReference.get().percent < 99 ){
                    weakReference.get().updateLoadingPercent();
                    sendEmptyMessageDelayed(MESSAGE_LOADING_PERCENT,100);
                }
            } else if ( msg.what == MESSAGE_LOADING_CANCLE ) {
                removeMessages(MESSAGE_LOADING_PERCENT);
            }
        }
    }

    private boolean isLoading = false;
    private void startLoadingPercent(){
        isLoading = true;
        handler.sendEmptyMessage(LoadingHandler.MESSAGE_LOADING_PERCENT);
    }

    private void resumeLoadingPercent(){
        if ( isLoading == true ) {
            handler.sendEmptyMessage(LoadingHandler.MESSAGE_LOADING_PERCENT);
        }
    }

    private void pauseLoadingPercent(){
        handler.sendEmptyMessage(LoadingHandler.MESSAGE_LOADING_CANCLE);
    }

    private void stopLoadingPercent(){
        isLoading = false;
        handler.sendEmptyMessage(LoadingHandler.MESSAGE_LOADING_CANCLE);
    }

    private final Handler handler = new LoadingHandler(this);

    private class DataBaseRunnable implements Runnable{
        //TODO
        private final InitConfigureResponse response;

        public DataBaseRunnable(InitConfigureResponse response) {
            this.response = response;
        }

        @Override
        public void run() {
            DaoManager.getInstance().deleteTable(LoadingActivity.this, HospitalDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, FeatureDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, NurtureDao.TABLE_NAME);

            InitConfigureResponse.Result result = response.getResult();

            DaoManager.getInstance().getHospitalDao().saveHospitalList(LoadingActivity.this, result.getHospitalList());
            DaoManager.getInstance().getFeatureDao().saveFeatureList(LoadingActivity.this,result.getFeatureList());
            DaoManager.getInstance().getNurtureDao().saveNurtureList(LoadingActivity.this,result.getNurtureGuideInfoList());

            BaseApplication.getInstance().saveMaintain("");
            if ( TextUtils.isEmpty(result.getNewsCount()) ){
                BaseApplication.getInstance().saveMessageCount(0);
            } else {
                BaseApplication.getInstance().saveMessageCount(Integer.parseInt(result.getNewsCount()));
            }
            handler.sendEmptyMessage(LoadingHandler.MESSAGE_JUMP_TO_MAIN);
        }
    }

}
