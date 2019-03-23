package com.mythosapps.date15;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class DateUpdateService extends IntentService {

    public DateUpdateService(){
        super("DateUpdateService");
    }

    public DateUpdateService(String name) {
        super(name);
    }

    @Override
        protected void onHandleIntent(Intent workIntent) {
            // Gets data from the incoming Intent
            String dataString = workIntent.getDataString();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }


        //NotificationManager nMgr = (NotificationManager) .getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

