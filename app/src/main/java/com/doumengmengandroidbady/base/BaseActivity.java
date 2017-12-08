package com.doumengmengandroidbady.base;

import android.app.Activity;
import android.content.Intent;

/**
 * 作者:边贤君
 * 描述:
 * 创建日期:2017/12/2 15:53
 */

public class BaseActivity extends Activity {

    public BaseApplication getBaseApplication(){
        return (BaseApplication) getApplication();
    }

    protected void startActivity(Class<? extends Activity> act){
        Intent intent = new Intent(this,act);
        startActivity(intent);
    }

}
