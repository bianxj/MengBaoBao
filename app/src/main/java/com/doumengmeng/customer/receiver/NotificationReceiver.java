package com.doumengmeng.customer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.doumengmeng.customer.base.BaseApplication;
import com.doumengmeng.customer.response.InitConfigureResponse;
import com.doumengmeng.customer.util.NotificationUtil;

/**
 * Created by Administrator on 2018/5/8.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (NotificationUtil.NOTIFICATION_ACTION.equals(intent.getAction()) ){
            InitConfigureResponse.NotificationData data = BaseApplication.getInstance().loadNotificationData();
            NotificationUtil.showNotification(context,data.getNoticeTitle(),data.getNoticeContent());

            int requestCode = intent.getIntExtra(NotificationUtil.REQUEST_CODE,-1);
            if ( requestCode != -1 ){
                BaseApplication.getInstance().saveNotificationStatus(BaseApplication.COLUMN_NOTIFICATION_NAME[requestCode],true);
            }
        }
    }
}
