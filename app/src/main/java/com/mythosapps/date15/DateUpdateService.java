package com.mythosapps.date15;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class DateUpdateService extends IntentService {

    private static final int DATE15_NOTIFICATION_ID = 4711;

    public DateUpdateService() {
        super("DateUpdateService");
    }

    public DateUpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        //String dataString = workIntent.getDataString();

        System.out.println("xyz.date15.alarm.rings");

        Notification notification = NotificationBuilder.buildForToday(this);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(DATE15_NOTIFICATION_ID, notification);

        System.out.println("xyz.date15.alarm.rings.notification sent");
    }
}

