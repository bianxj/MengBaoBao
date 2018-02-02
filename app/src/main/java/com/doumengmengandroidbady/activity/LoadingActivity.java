package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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
import com.doumengmengandroidbady.net.UrlAddressList;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.request.task.LoginTask;
import com.doumengmengandroidbady.response.entity.UserData;
import com.doumengmengandroidbady.response.InitConfigureResponse;
import com.doumengmengandroidbady.util.GsonUtil;
import com.doumengmengandroidbady.util.MyDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
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

    public final static String IN_PARAM_IS_TIME_OUT = "is_time_out";

    private final static boolean isTest = true;

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
        boolean isTimeOut = intent.getBooleanExtra(IN_PARAM_IS_TIME_OUT,false);
        if ( isTimeOut ){
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
    private void checkVersion(){
        if ( isTest ){
            initConfigure();
        } else {
            try {
                checkVersionTask = new RequestTask.Builder(this,checkVersionCallBack)
//                        .setUrl()
//                        .setType()
//                        .setContent()
                        .build();
                checkVersionTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private final RequestCallBack checkVersionCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            //TODO
        }

        @Override
        public void onError(String result) {
            //TODO
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
            startActivity(MainActivity.class);
        }
    };

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
        public void onError(String result) {}

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
        UserData userData = BaseApplication.getInstance().getUserData();
            try {
                loginTask = new LoginTask(LoadingActivity.this, userData.getAccountmobile(), userData.getPasswd(), new LoginTask.LoginCallBack() {
                    @Override
                    public void onPreExecute() {

                    }

                    @Override
                    public void onError(String result) {
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
