package com.doumengmeng.customer.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class AppUtil {

    private enum TYPE_EQUAL{
        LARGER,
        EQUAL,
        LOWER
    }

    public static String getSystemOsName(){
        return Build.BRAND;
    }

    public static boolean isForceUpdate(String currentVersion , String updateVersion){
        String[] currents = currentVersion.split("\\.");
        String[] updates = updateVersion.split("\\.");
        if ( updates.length == currents.length ){
            for ( int i = 0; i< currents.length-1;i++ ){
                TYPE_EQUAL equal = isUpdate(updates[i],currents[i]);
                if ( equal == TYPE_EQUAL.LARGER ){
                    return true;
                } else if ( equal == TYPE_EQUAL.LOWER ){
                    break;
                }
            }
        } else if ( updates.length > currents.length ){
            return true;
        }
        return false;
    }

    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
        return info.versionName;
    }

    private static TYPE_EQUAL isUpdate(String update,String current){
        int upd = Integer.parseInt(update);
        int cur = Integer.parseInt(current);
        if ( upd > cur ) {
            return TYPE_EQUAL.LARGER;
        } else if ( upd == cur ) {
            return TYPE_EQUAL.EQUAL;
        } else {
            return TYPE_EQUAL.LOWER;
        }
    }

    public static boolean isNeedUpdate(String currentVersion , String updateVersion){
        String[] currents = currentVersion.split("\\.");
        String[] updates = updateVersion.split("\\.");
        if ( updates.length == currents.length ){
            for ( int i = 0; i< currents.length;i++ ){
                TYPE_EQUAL equal = isUpdate(updates[i],currents[i]);
                if ( equal == TYPE_EQUAL.LARGER ){
                    return true;
                } else if ( equal == TYPE_EQUAL.LOWER ){
                    break;
                }
            }
        } else if ( updates.length > currents.length ){
            return true;
        }
        return false;
    }

    public static void openPrimession(Context context){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void openSoftwareMarket(Context context){
        String marketPkg = null;
        if (isAvilible(context, "com.tencent.android.qqdownloader")) {
            marketPkg = "com.tencent.android.qqdownloader";
        }

        try {
            launchAppDetail(context, context.getPackageName(), marketPkg);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                goToSamsungMarket(context,context.getPackageName());
            } catch (Exception e1) {
                e1.printStackTrace();
//                goToSonyMarket(context,context.getPackageName());
            }
        }
    }

    public static void goToSamsungMarket(Context context, String packageName) throws Exception {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.sec.android.app.samsungapps");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean goToSonyMarket(Context context, String appId) {
        Uri uri = Uri.parse("http://m.sonyselect.cn/" + appId);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static void launchAppDetail(Context context, String appPkg, String marketPkg) throws Exception {
//        try {
//            if (TextUtils.isEmpty(appPkg))
//                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<>();// 用于存储所有已安装程序的包名
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public static void hideSoftInput(Context context, Window window){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(),0);
    }

}
