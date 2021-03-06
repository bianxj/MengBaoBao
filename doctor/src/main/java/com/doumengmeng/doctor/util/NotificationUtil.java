package com.doumengmeng.doctor.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.doumengmeng.doctor.R;

/**
 * Created by Administrator on 2018/1/23.
 */

public class NotificationUtil {

    private static final String channelId = "TEST";

    public static void showNotification(Context context){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification.Builder builder = null;

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId);
            builder.setContentTitle("NotificationTitle");
            builder.setContentText("NotificationContent");
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            builder.setSmallIcon(R.mipmap.ic_launcher);

//            Intent obj = new Intent(context, MainActivity.class);
//            PendingIntent pIntent = PendingIntent.getActivity(context, 1, obj, 0);
//            builder.setContentIntent(pIntent);
            //这句是重点
            builder.setFullScreenIntent(null, true);
            builder.setAutoCancel(true);
            manager.notify(1,builder.build());
        } else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("NotificationTitle");
            builder.setContentText("NotificationContent");
            manager.notify(1,builder.build());
        }
    }

//    public static boolean isNotificationEnable(){
//        NotificationManagerCompat manager = NotificationManagerCompat.from(BaseApplication.getInstance());
//        return manager.areNotificationsEnabled();
//    }

}
