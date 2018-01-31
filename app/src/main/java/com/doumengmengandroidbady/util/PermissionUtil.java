package com.doumengmengandroidbady.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 系统权限辅助类
 * 创建日期: 2018/1/23 14:06
 */
public class PermissionUtil {

    private static final Map<String,Integer> requestCodeMap;

    static {
        requestCodeMap = new HashMap<>();
        requestCodeMap.put(Manifest.permission.CAMERA,0x31);
        requestCodeMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,0x32);
        requestCodeMap.put(Manifest.permission.READ_PHONE_STATE,0x33);
        requestCodeMap.put(Manifest.permission.ACCESS_FINE_LOCATION,0x34);
    }

    public static boolean checkPermissionAndRequest(Activity activity,String permission) {
        if (requestCodeMap.containsKey(permission)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCodeMap.get(permission));
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static void onRequestPermissionsResult(Activity activity,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,@NonNull RequestPermissionSuccess request){
        onRequestPermissionsResult(activity,requestCode,permissions,grantResults,request,true);
    }

    public static void onRequestPermissionsResult(Activity activity,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults,@NonNull RequestPermissionSuccess request,boolean skipToAppInfo){
        if ( requestCodeMap.get(permissions[0]) == requestCode ){
            if ( PackageManager.PERMISSION_GRANTED == grantResults[0] ){
                request.success(permissions[0]);
            } else {
                if ( skipToAppInfo ){
                    if ( !ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions[0]) ){
                        request.alwaysDenied(permissions[0]);
                    } else {
                        request.denied(permissions[0]);
                    }
                }
            }
        }
    }

    public interface RequestPermissionSuccess{
        void success(String permission);
        void denied(String permission);
        void alwaysDenied(String permission);
    }

}
