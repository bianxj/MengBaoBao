package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/5.
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
        return new RequestTask.Builder(checkVersionCallBack).build();
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
        public Context getContext() {
            return LoadingActivity.this;
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
            initConfigureTask = new RequestTask.Builder(initConfigureCallback).build();
            initConfigureTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private RequestCallBack initConfigureCallback = new RequestCallBack() {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String getUrl() {

            return UrlAddressList.URL_INIT_CONFIGURE;
        }

        @Override
        public Context getContext() {
            return LoadingActivity.this;
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
        public void onError(String result) {

        }

        @Override
        public void onPostExecute(String result) {
//            if ( isTest ) {
//                startActivity(MainActivity.class);
//            } else {
                try {
                    //TODO
                    DaoManager.getInstance().deleteTable(LoadingActivity.this, DoctorDao.TABLE_NAME);
                    DaoManager.getInstance().deleteTable(LoadingActivity.this, HospitalDao.TABLE_NAME);
                    DaoManager.getInstance().deleteTable(LoadingActivity.this, GrowthDao.TABLE_NAME);
                    DaoManager.getInstance().deleteTable(LoadingActivity.this, MengClassDao.TABLE_NAME);

                    JSONObject object = new JSONObject(result);
                    JSONObject res = object.getJSONObject("result");
                    InitConfigure configure = GsonUtil.getInstance().getGson().fromJson(res.toString(), InitConfigure.class);
                    DaoManager.getInstance().getDaotorDao().saveDoctorList(getContext(), configure.getDoctorList());
                    DaoManager.getInstance().getGrowthDao().saveGrowthList(getContext(), configure.getGrowthList());
                    DaoManager.getInstance().getHospitalDao().saveHospitalList(getContext(), configure.getHospitalList());
                    DaoManager.getInstance().getMengClassDao().saveMengClassList(getContext(), configure.getMengClassList());
                    BaseApplication.getInstance().saveParentInfo(configure.getParentInfo());
                    startActivity(MainActivity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
}
