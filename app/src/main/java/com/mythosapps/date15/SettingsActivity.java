package com.mythosapps.date15;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.service.notification.StatusBarNotification;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        Intent intent = new Intent(this, DateUpdateAlarmReceiver.class);
        PendingIntent myPendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 1000, myPendingIntent);  //set repeating every minute

        System.out.println("xyz.date15.alarm.set");
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || NotificationPreferenceFragment.class.getName().equals(fragmentName);
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {

        private static final int DATE15_NOTIFICATION_ID = 4711;
        private static final String DATE15_PACKAGE = "com.mythosapps.date15";

        Notification notification;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            final SwitchPreference onOffDateNotification = (SwitchPreference) findPreference("notifications_new_message");

            NotificationManager nMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            StatusBarNotification[] activeNotifications = nMgr.getActiveNotifications();
            boolean isNotificationActive = false;
            if (activeNotifications != null && activeNotifications.length > 0) {
                for (StatusBarNotification activeNotification : activeNotifications) {
                    //String msg = "" + notification.getPackageName() + "#" + notification.getId();
                    //Toast.makeText(getContext(),"Found: " + msg,Toast.LENGTH_SHORT).show();
                    if (DATE15_PACKAGE.equals(activeNotification.getPackageName()) && DATE15_NOTIFICATION_ID == activeNotification.getId()) {
                        isNotificationActive = true;
                        notification = activeNotification.getNotification();
                        break;
                    }
                }
            }

            // SwitchPreference preference change listener
            onOffDateNotification.setChecked(isNotificationActive);
            onOffDateNotification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if (onOffDateNotification.isChecked()) {
                        // Checked the switch programmatically
                        onOffDateNotification.setChecked(false);

                        Toast.makeText(getContext(), "Off", Toast.LENGTH_SHORT).show();

                        if (notification != null) {
                            NotificationManager nMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            nMgr.cancel(DATE15_NOTIFICATION_ID);
                            notification = null;
                        }
                    } else {
                        // Unchecked the switch programmatically
                        onOffDateNotification.setChecked(true);

                        notification = NotificationBuilder.buildForToday(getContext());

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                        notificationManager.notify(DATE15_NOTIFICATION_ID, notification);
                        Toast.makeText(getContext(), "On", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
