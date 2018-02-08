package com.doumengmengandroidbady.base;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doumengmengandroidbady.util.MyDialog;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 15:53
 */

public class BaseActivity extends Activity {

    protected static final int REQUEST_AGREEMENT = 0x01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getInstance().getMLog().debug("onCreate:"+this.getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getInstance().getMLog().debug("onResume:"+this.getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseApplication.getInstance().getMLog().debug("onPause:"+this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().getMLog().debug("onDestroy:"+this.getClass().getSimpleName());
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

    protected void showPromptDialog(String message){
        MyDialog.showPromptDialog(this,message,null);
    }

}
