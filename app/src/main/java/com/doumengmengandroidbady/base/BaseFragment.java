package com.doumengmengandroidbady.base;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 19:48
 */

public class BaseFragment extends Fragment {

    protected void startActivity(Class<? extends Activity> act){
        Intent intent = new Intent(getActivity(),act);
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
