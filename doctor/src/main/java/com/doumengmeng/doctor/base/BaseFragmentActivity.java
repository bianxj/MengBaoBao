package com.doumengmeng.doctor.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2018/2/26.
 */

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
    }
}
