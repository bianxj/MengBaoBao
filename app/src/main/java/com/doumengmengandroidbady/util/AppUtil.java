package com.doumengmengandroidbady.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.doumengmengandroidbady.base.BaseApplication;

/**
 * Created by Administrator on 2018/1/2.
 */

public class AppUtil {

    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
        return info.versionName;
    }

    public static void openPrimession(Context context){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BaseApplication.getInstance().getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}
