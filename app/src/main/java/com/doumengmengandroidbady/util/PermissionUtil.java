package com.doumengmengandroidbady.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2017/12/14.
 */

public class PermissionUtil {

    public static final int GRANTED = 0x01;
    public static final int DENIED = 0x02;
    public static final int DENIED_NOT_SHOW = 0x03;

    public static int requestPermission(Activity activity,String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int state = ActivityCompat.checkSelfPermission(activity,permission);
            if ( state == PackageManager.PERMISSION_DENIED){
                if ( ActivityCompat.shouldShowRequestPermissionRationale(activity,permission) ){
                    return DENIED;
                } else {
                    return DENIED_NOT_SHOW;
                }
            }
        }
        return GRANTED;
    }

}
