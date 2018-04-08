package com.doumengmeng.customer.base;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.doumengmeng.customer.R;

/**
 * Created by Administrator on 2017/12/5.
 */

public class BaseCrashHandler implements Thread.UncaughtExceptionHandler {

    private static BaseCrashHandler handler;

    public static BaseCrashHandler getInstance(){
        if ( null == handler ){
            handler = new BaseCrashHandler();
        }
        return handler;
    }

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context context;
    private BaseCrashHandler(){}

    public void init(Context context){
        this.context = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if ( !processException(e) && mDefaultHandler != null ){
            mDefaultHandler.uncaughtException(t,e);
        }
    }

    private boolean processException(Throwable e){
        if ( null == e ) {
            return false;
        }
        ((BaseApplication)context.getApplicationContext()).getMLog().error(e);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, R.string.error_exit_message,Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        System.exit(0);
        return true;
    }

}
