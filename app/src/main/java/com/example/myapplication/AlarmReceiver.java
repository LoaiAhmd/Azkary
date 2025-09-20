package com.example.myapplication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences("notifications", Context.MODE_PRIVATE);
        boolean notifications = sp.getBoolean("notifications", false);

        String action = intent.getAction();

        if ("MORNING_AZKAR".equals(action)) {
            showMorningNotification(context);
        } else if ("EVENING_AZKAR".equals(action)) {
            showEveningNotification(context);
        }
    }

    private void showMorningNotification(Context context) {
        Intent intent = new Intent(context, MorningAzkar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,  PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelid_morningazkar")
                .setSmallIcon(R.drawable.cloudy_day)
                .setContentTitle(context.getString(R.string.string_morning_azkar))
                .setContentText(context.getString(R.string.string_notification_morning_azkar))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat.from(context).notify(1, builder.build());
    }

    private void showEveningNotification(Context context) {
        Intent intent = new Intent(context, EveningAzkar.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 1, intent, PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelid_eveningazkar")
                .setSmallIcon(R.drawable.cloudy_night)
                .setContentTitle(context.getString(R.string.string_evening_azkar))
                .setContentText(context.getString(R.string.string_notification_evening_azkar))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat.from(context).notify(2, builder.build());

    }
}