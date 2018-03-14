package com.doumengmeng.doctor.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2018/3/13.
 */

public abstract class BaseTimeFragment extends BaseFragment {

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( hidden ){
            unregisterTimeReceiver();
        } else {
            registerTimeReceiver();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerTimeReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterTimeReceiver();
    }

    private void registerTimeReceiver(){
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_TICK);
            getContext().registerReceiver(timeReceiver, filter);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void unregisterTimeReceiver(){
        try {
            getContext().unregisterReceiver(timeReceiver);
        } catch ( Exception e ){
            e.printStackTrace();
        }
    }

    public abstract void minuteCallBack();

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            minuteCallBack();
        }
    };

}
