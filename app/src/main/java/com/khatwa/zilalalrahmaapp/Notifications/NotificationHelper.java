package com.khatwa.zilalalrahmaapp.Notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import java.sql.Time;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationHelper {
    static int ALARM_TYPE_RTC = 100;

    public static void scheduleRepeatingRTCNotification(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.setFirstDayOfWeek(Calendar.FRIDAY);


        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManagerRTC = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        assert alarmManagerRTC != null;
        alarmManagerRTC.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntentRTC);
    }

    static void scheduleRepeatingElapsedNotification(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        int ALARM_TYPE_ELAPSED = 101;
        PendingIntent alarmIntentElapsed = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManagerElapsed = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        assert alarmManagerElapsed != null;
        alarmManagerElapsed.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntentElapsed);
    }

    static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void enableBootReceiver(Context context) {
        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

}
