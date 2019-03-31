package com.mythosapps.date15;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationManagerCompat;

//public class DateUpdateService extends JobIntentService {
public class DateUpdateService extends IntentService {

    private static final int DATE15_NOTIFICATION_ID = 4711;

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1017;

    public DateUpdateService() {
        super("DateUpdateServiceThread");
    }

    /* nur bei JobIntentService
    //static void enqueueWork(Context context, Intent work) {
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, DateUpdateService.class, JOB_ID, work);
    }
    */

    @Override
    //protected void onHandleWork(@NonNull Intent intent) {
    protected void onHandleIntent(@NonNull Intent intent) {

        // Gets data from the incoming Intent
        //String dataString = workIntent.getDataString();

        System.out.println("xyz.date15.alarm.rings");

        Notification notification = NotificationBuilder.buildForToday(this);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(DATE15_NOTIFICATION_ID, notification);

        System.out.println("xyz.date15.alarm.rings.notification sent");
    }

//    public DateUpdateService(String name) {
    //        super(name);
    // }

}

