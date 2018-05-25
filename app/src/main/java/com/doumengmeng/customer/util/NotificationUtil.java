package com.doumengmeng.customer.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.doumengmeng.customer.R;
import com.doumengmeng.customer.activity.GuideActivity;
import com.doumengmeng.customer.base.BaseApplication;

/**
 * Created by Administrator on 2018/1/23.
 */

public class NotificationUtil {

    public final static String NOTIFICATION_ACTION = "com.doumengmeng.customer.show.notification";
    public final static String REQUEST_CODE = "requestCode";
    private static final String channelId = "TEST";

    public static void clearReserve(Context context){
        Intent intent = new Intent(NOTIFICATION_ACTION);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        for (int i = 0;i<BaseApplication.COLUMN_NOTIFICATION_NAME.length;i++){
            PendingIntent sender = PendingIntent.getBroadcast(context.getApplicationContext(),i,intent,0);
            manager.cancel(sender);
        }
    }

    public static void reserveNotification(Context context,long time , int requestCode){
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(NOTIFICATION_ACTION);
        intent.putExtra(REQUEST_CODE,requestCode);
        PendingIntent sender = PendingIntent.getBroadcast(context.getApplicationContext(),requestCode,intent,0);
        manager.set(AlarmManager.RTC_WAKEUP,time,sender);
    }

    public static void showNotification(Context context , String title , String content){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, GuideActivity.class);
        PendingIntent sender = PendingIntent.getActivity(context, 1, intent, 0);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_notification );
        if( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId);
            builder.setContentTitle(title);
            builder.setContentText(content);

            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
            style.setBigContentTitle(title);
            style.bigText(content);

            builder.setStyle(style);
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            builder.setLargeIcon(icon);
            builder.setSmallIcon(R.drawable.ic_action_bar);

            //这句是重点
            builder.setContentIntent(sender);
            builder.setFullScreenIntent(null, true);
            builder.setAutoCancel(true);
            manager.notify(3,builder.build());
        } else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setLargeIcon(icon);
            builder.setSmallIcon(R.drawable.ic_action_bar);
            builder.setContentTitle(title);
            builder.setContentText(content);

            Notification.BigTextStyle style = new Notification.BigTextStyle();
            style.setBigContentTitle(title);
            style.bigText(content);

            builder.setStyle(style);

            builder.setContentIntent(sender);
            manager.notify(3,builder.build());
        }
    }

    public static boolean isNotificationEnable(){
        NotificationManagerCompat manager = NotificationManagerCompat.from(BaseApplication.getInstance());
        return manager.areNotificationsEnabled();
    }

}
