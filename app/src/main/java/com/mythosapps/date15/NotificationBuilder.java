package com.mythosapps.date15;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class NotificationBuilder {

    static final List<Integer> dateIcons = new ArrayList<>();
    static {
        dateIcons.add(R.drawable.ic_date_1);
        dateIcons.add(R.drawable.ic_date_2);
        dateIcons.add(R.drawable.ic_date_3);
        dateIcons.add(R.drawable.ic_date_4);
        dateIcons.add(R.drawable.ic_date_5);
        dateIcons.add(R.drawable.ic_date_6);
        dateIcons.add(R.drawable.ic_date_7);
        dateIcons.add(R.drawable.ic_date_8);
        dateIcons.add(R.drawable.ic_date_9);
        dateIcons.add(R.drawable.ic_date_10);
        dateIcons.add(R.drawable.ic_date_11);
        dateIcons.add(R.drawable.ic_date_12);
        dateIcons.add(R.drawable.ic_date_13);
        dateIcons.add(R.drawable.ic_date_14);
        dateIcons.add(R.drawable.ic_date_15);
        dateIcons.add(R.drawable.ic_date_16);
        dateIcons.add(R.drawable.ic_date_17);
        dateIcons.add(R.drawable.ic_date_18);
        dateIcons.add(R.drawable.ic_date_19);
        dateIcons.add(R.drawable.ic_date_20);
        dateIcons.add(R.drawable.ic_date_21);
        dateIcons.add(R.drawable.ic_date_22);
        dateIcons.add(R.drawable.ic_date_23);
        dateIcons.add(R.drawable.ic_date_24);
        dateIcons.add(R.drawable.ic_date_25);
        dateIcons.add(R.drawable.ic_date_26);
        dateIcons.add(R.drawable.ic_date_27);
        dateIcons.add(R.drawable.ic_date_28);
        dateIcons.add(R.drawable.ic_date_29);
        dateIcons.add(R.drawable.ic_date_30);
        dateIcons.add(R.drawable.ic_date_31);
    }

    static NotificationCompat.Builder builder;

    static NotificationChannel channel;

    public static Notification buildForToday(Context context) {

        Calendar cal = GregorianCalendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //int day = cal.get(Calendar.MINUTE);// only fo debugging !!!!!
        //!if (day > 30) {
        //!    day = day-30;
        //!}
        int todaysDateIcon = dateIcons.get(day-1); // index zero-based

        System.out.println("xyz.date15.alarm.rings.notification " + day);

        if (builder == null) {
             builder = new NotificationCompat.Builder(context, Context.NOTIFICATION_SERVICE)
                    .setSmallIcon(todaysDateIcon)
                    .setContentTitle("Date in Status Bar4711")
                    .setContentText(day < 10 ? "0"+day : ""+day)
                    .setOnlyAlertOnce(true)
                    //.setShowWhen(false)
                    //.setExtras()
                    .setOngoing(true)
                    .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
        } else {
            builder.setSmallIcon(todaysDateIcon);
            builder.setContentText(""+day);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // ab Android Oreo mit Channel:

            if (channel == null) {
                channel = new NotificationChannel("date15_channel", "date15_channel", NotificationManager.IMPORTANCE_HIGH);
            }
            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.createNotificationChannel(channel);
            builder.setChannelId("date15_channel");
        }
        return builder.build();
    }
}
