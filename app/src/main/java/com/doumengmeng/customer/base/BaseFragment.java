package com.doumengmeng.customer.base;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 19:48
 */

public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseApplication.getInstance().getMLog().debug("onCreateView:"+this.getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getInstance().getMLog().debug("onResume:"+this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        BaseApplication.getInstance().getMLog().debug("onPause:"+this.getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseApplication.getInstance().getMLog().debug("onDestroyView:"+this.getClass().getSimpleName());
    }

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
