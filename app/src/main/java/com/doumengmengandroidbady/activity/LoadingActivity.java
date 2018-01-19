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
import com.doumengmengandroidbady.response.InitConfigure;
import com.doumengmengandroidbady.response.UserData;
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

    private final static boolean isTest = true;

    private RelativeLayout rl_back;
    private TextView tv_loading_percent;
    private ImageView iv_loading_icon;
    private AnimationDrawable drawable;

    private RequestTask checkVersionTask;

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

        AnimationDrawable drawable = (AnimationDrawable) iv_loading_icon.getDrawable();
        drawable.start();
    }

    private void loading(){
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

    private void checkVersion(){
        if ( isTest ){
            initConfigure();
        } else {
            try {
                checkVersionTask = buildCheckVersionTask();
                checkVersionTask.execute();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private RequestTask buildCheckVersionTask() throws Throwable {
        return new RequestTask.Builder(this,checkVersionCallBack).build();
    }

    private RequestCallBack checkVersionCallBack = new RequestCallBack() {
        @Override
        public void onPreExecute() {
            //TODO
        }

        @Override
        public String getUrl() {
            //TODO
            return null;
        }

        @Override
        public Map<String, String> getContent() {
            return null;
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

        @Override
        public String type() {
            return null;
        }
    };

    private RequestTask initConfigureTask = null;
    private void initConfigure(){
        try {
            initConfigureTask = new RequestTask.Builder(this,initConfigureCallback).build();
            initConfigureTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack initConfigureCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {}

        @Override
        public String getUrl() {
            return UrlAddressList.URL_INIT_CONFIGURE;
        }


        @Override
        public Map<String, String> getContent() {
            UserData userData = BaseApplication.getInstance().getUserData();
            Map<String,String> map = new HashMap<>();
            JSONObject object = new JSONObject();
            try {
                object.put("userId",userData.getUserid());
                map.put("paramStr",object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put("sesId",userData.getSessionId());
            return map;
        }

        @Override
        public void onError(String result) {}

        @Override
        public void onPostExecute(String result) {
//            if ( isTest ) {
//                startActivity(MainActivity.class);
//            } else {
                new Thread(new DataBaseRunnable(result)).start();
//            }
        }

        @Override
        public String type() {
            return JSON;
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
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
        private WeakReference<Context> weakReference;

        public LoadingHandler(Context context) {
            this.weakReference = new WeakReference<Context>(context);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == MESSAGE_JUMP_TO_MAIN ) {
                Intent intent = new Intent(weakReference.get(),MainActivity.class);
                weakReference.get().startActivity(intent);
            }
        }
    }

    private Handler handler = new LoadingHandler(this);

    private class DataBaseRunnable implements Runnable{

        private String result;

        public DataBaseRunnable(String result) {
            this.result = result;
        }

        @Override
        public void run() {
            DaoManager.getInstance().deleteTable(LoadingActivity.this, DoctorDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, HospitalDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, GrowthDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, MengClassDao.TABLE_NAME);
            DaoManager.getInstance().deleteTable(LoadingActivity.this, FeatureDao.TABLE_NAME);

            JSONObject object = null;
            try {
                object = new JSONObject(result);
                JSONObject res = object.getJSONObject("result");
                InitConfigure configure = GsonUtil.getInstance().getGson().fromJson(res.toString(), InitConfigure.class);
                DaoManager.getInstance().getDaotorDao().saveDoctorList(LoadingActivity.this, configure.getDoctorList());
                DaoManager.getInstance().getGrowthDao().saveGrowthList(LoadingActivity.this, configure.getGrowthList());
                DaoManager.getInstance().getHospitalDao().saveHospitalList(LoadingActivity.this, configure.getHospitalList());
                DaoManager.getInstance().getMengClassDao().saveMengClassList(LoadingActivity.this, configure.getMengClassList());
                DaoManager.getInstance().getFeatureDao().saveFeatureList(LoadingActivity.this,configure.getFeatureList());
                BaseApplication.getInstance().saveParentInfo(configure.getParentInfo());
                BaseApplication.getInstance().saveDayList(configure.getDayList());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(LoadingHandler.MESSAGE_JUMP_TO_MAIN);
        }
    }

}
