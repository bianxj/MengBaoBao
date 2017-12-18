package com.doumengmengandroidbady.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doumengmengandroidbady.R;
import com.doumengmengandroidbady.base.BaseActivity;
import com.doumengmengandroidbady.request.RequestCallBack;
import com.doumengmengandroidbady.request.RequestTask;
import com.doumengmengandroidbady.util.MyDialog;

/**
 * Created by Administrator on 2017/12/5.
 */
public class LoadingActivity extends BaseActivity {

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
        configView();
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
    }

    private void configView(){
//        AnimationDrawable drawable = (AnimationDrawable) iv_loading_icon.getDrawable();
//        drawable.start();
        RotateAnimation set = (RotateAnimation) AnimationUtils.loadAnimation(LoadingActivity.this,R.anim.loading_rotate);
        set.setDuration(1500);
        set.setRepeatMode(Animation.RESTART);
        set.setRepeatCount(Animation.INFINITE);
        set.setInterpolator(new LinearInterpolator());
        iv_loading_icon.startAnimation(set);
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
        try {
            checkVersionTask = buildCheckVersionTask();
            checkVersionTask.execute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
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
        public void onError(String result) {
            //TODO
        }

        @Override
        public void onPostExecute(String result) {
            //TODO
            startActivity(MainActivity.class);
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
