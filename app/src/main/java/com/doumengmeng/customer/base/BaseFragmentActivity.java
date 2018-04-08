package com.doumengmeng.customer.base;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 15:54
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);
    }

    protected void startActivity(Class<? extends Activity> act){
        Intent intent = new Intent(this,act);
        startActivity(intent);
    }

    protected void stopTask(AsyncTask task){
        if ( task != null ){
            if ( AsyncTask.Status.FINISHED != task.getStatus() ){
                task.cancel(false);
            }
        }
    }

}
