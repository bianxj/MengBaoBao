package com.doumengmeng.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.doumengmeng.doctor.base.BaseActivity;
import com.doumengmeng.doctor.base.BaseApplication;

/**
 * Created by Administrator on 2018/5/14.
 */

public class LauncherActivity extends BaseActivity {

    private  Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!BaseApplication.getInstance().isAbnormalExit()
                        && !BaseApplication.getInstance().isToExamine()){
                    Intent intent = new Intent(LauncherActivity.this,LoadingActivity.class);
                    intent.putExtra(LoadingActivity.IN_PARAM_AUTO_LOGIN,true);
                    startActivity(intent);
                } else {
                    if (BaseApplication.getInstance().loadGuideState()) {
                        Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LauncherActivity.this, GuideActivity.class);
                        startActivity(intent);
                    }
                }
                finish();
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
    }
}
