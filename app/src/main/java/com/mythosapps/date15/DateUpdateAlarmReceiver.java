package com.mythosapps.date15;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;

public class DateUpdateAlarmReceiver extends BroadcastReceiver {

    private static final int DATE15_NOTIFICATION_ID = 4711;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("xyz.date15.alarm.rings-on-receiver");

        Notification notification = NotificationBuilder.buildForToday(context);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(DATE15_NOTIFICATION_ID, notification);

        System.out.println("xyz.date15.alarm.rings.notification sent-from-receiver");

    }
}
