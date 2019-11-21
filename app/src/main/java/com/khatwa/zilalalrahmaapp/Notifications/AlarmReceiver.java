package com.khatwa.zilalalrahmaapp.Notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.khatwa.zilalalrahmaapp.R;
import com.khatwa.zilalalrahmaapp.ui.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent intentToRepeat = new Intent(context, MainActivity.class);
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification repeatedNotification = buildLocalNotification(context, pendingIntent).build();
        NotificationHelper.getNotificationManager(context).notify(NotificationHelper.ALARM_TYPE_RTC, repeatedNotification);
    }

    public NotificationCompat.Builder buildLocalNotification(Context context, PendingIntent pendingIntent) {

        return new NotificationCompat.Builder(context, String.valueOf(R.string.NotificationSub))
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle(String.valueOf(R.string.app_name))
                .setAutoCancel(true);
    }
}
