package com.doumengmeng.doctor.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Administrator on 2018/3/13.
 */

public abstract class BaseTimeFragmentActivity extends BaseFragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();
        registerTimeReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterTimeReceiver();
    }

    private void registerTimeReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(timeReceiver, filter);
    }

    private void unregisterTimeReceiver(){
        unregisterReceiver(timeReceiver);
    }

    public abstract void minuteCallBack();

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            minuteCallBack();
        }
    };

}
