package com.doumengmeng.customer.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者: 边贤君
 * 描述: 系统权限辅助类
 * 创建日期: 2018/1/23 14:06
 */
public class PermissionUtil {

    public enum PermissionType{
        PERMISSION_GRANTED,
        PERMISSION_DENIED,
        PERMISSION_ALWAYS_DENIED
    }

    private static final Map<String,Integer> requestCodeMap;

    static {
        requestCodeMap = new HashMap<>();
        requestCodeMap.put(Manifest.permission.CAMERA,0x31);
        requestCodeMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,0x32);
        requestCodeMap.put(Manifest.permission.READ_PHONE_STATE,0x33);
        requestCodeMap.put(Manifest.permission.ACCESS_FINE_LOCATION,0x34);
    }

    public static boolean checkPermissionAndRequest(final Activity activity, String permission) {
        if (requestCodeMap.containsKey(permission)) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){
                    activity.requestPermissions(new String[]{permission},requestCodeMap.get(permission));
                    return false;
                } else {
                    return true;
                }
//                try{
//                    int oldChecker = PermissionChecker.checkSelfPermission(activity,permission);
//                    int newChecker = activity.checkSelfPermission(permission);
//
//                    if ( newChecker != PackageManager.PERMISSION_GRANTED || oldChecker == PermissionChecker.PERMISSION_DENIED ){
//                        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCodeMap.get(permission));
//                        return PermissionType.PERMISSION_DENIED;
//                    } else if ( oldChecker == PermissionChecker.PERMISSION_GRANTED ){
//                        return PermissionType.PERMISSION_GRANTED;
//                    } else {
//                        return PermissionType.PERMISSION_ALWAYS_DENIED;
//                    }
//                } catch (Exception e){
//                    if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
//                        return PermissionType.PERMISSION_GRANTED;
//                    } else {
//                        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCodeMap.get(permission));
//                        return PermissionType.PERMISSION_DENIED;
//                    }
//                }
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                int grant = PermissionChecker.checkSelfPermission(activity,permission);
//                if ( grant == PermissionChecker.PERMISSION_GRANTED ){
//                    return true;
//                } else if ( grant == PermissionChecker.PERMISSION_DENIED ) {
//                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCodeMap.get(permission));
//                    return false;
//                } else {
//                    denied.alwaysDenied();
//                }
//                if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
//                    return true;
//                } else {
//                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCodeMap.get(permission));
//                    return false;
//                }
//            }
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

    public interface RequestPermissionDenied{
        void alwaysDenied();
    }

}
