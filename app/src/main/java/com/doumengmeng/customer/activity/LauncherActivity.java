package com.doumengmeng.customer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.doumengmeng.customer.base.BaseActivity;
import com.doumengmeng.customer.base.BaseApplication;

/**
 * 作者: 边贤君
 * 描述: 启动页
 * 等待1秒后跳转至引导页
 * 创建日期: 2018/2/9 14:47
 */
public class LauncherActivity extends BaseActivity {

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = BaseApplication.getInstance().getDisplayInfo();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this,GuideActivity.class);
                startActivity(intent);
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
