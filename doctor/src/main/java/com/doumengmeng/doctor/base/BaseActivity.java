package com.doumengmeng.doctor.base;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2018/2/26.
 */

public class BaseActivity extends Activity {
    protected static final int REQUEST_AGREEMENT = 0x01;

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

    protected void startActivity(Class<? extends Activity> act){
        Intent intent = new Intent(this,act);
        startActivity(intent);
    }

    protected boolean isResponceError(String msg){
//        if (ResponseErrorCode.getErrorMsg())
        return true;
    }

    protected void stopTask(AsyncTask task){
        if ( task != null ){
            if ( AsyncTask.Status.FINISHED != task.getStatus() ){
                task.cancel(false);
            }
        }
    }

    protected void clearHandler(Handler handler){
        if ( handler != null ){
            handler.removeCallbacksAndMessages(null);
        }
    }

    protected void showPromptDialog(String message){
//        MyDialog.showPromptDialog(this,message,null);
    }
}
