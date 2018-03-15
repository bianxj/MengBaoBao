package com.doumengmeng.doctor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.doumengmeng.doctor.activity.MainActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/3/15.
 */

public class UpdateSuperScriptReceiver extends BroadcastReceiver {

    public final static String ACTION_UPDATE_SCRIPT = "script";

    private WeakReference<MainActivity> weakReference;

    public UpdateSuperScriptReceiver(MainActivity activity) {
        this.weakReference = new WeakReference<MainActivity>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ( ACTION_UPDATE_SCRIPT.equals(intent.getAction()) ){
            weakReference.get().updateSuperScript();
        }
    }
}
