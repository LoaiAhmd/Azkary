package com.example.myapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = context.getSharedPreferences("notifications", Context.MODE_PRIVATE);
        boolean notifications = sp.getBoolean("notifications", false);

        String action = intent.getAction();
        int hour_mor = sp.getInt("hour_morning", 12);
        int minute_mor = sp.getInt("minute_morning", 0);

        int hour_eve = sp.getInt("hour_evening", 12);
        int minute_eve = sp.getInt("minute_evening", 0);

        if ("MORNING_AZKAR".equals(action)) {
            showMorningNotification(context);
            rescheduleAlarm(context, "MORNING_AZKAR", hour_mor, minute_mor);
        } else if ("EVENING_AZKAR".equals(action)) {
            showEveningNotification(context);
            rescheduleAlarm(context, "EVENING_AZKAR", hour_eve, minute_eve);
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

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat.from(context).notify(1, builder.build());
    }

    private void rescheduleAlarm(Context context, String action, int hour, int minute) {
        Calendar next = Calendar.getInstance();
        next.add(Calendar.DAY_OF_YEAR, 1);
        next.set(Calendar.HOUR_OF_DAY, hour);
        next.set(Calendar.MINUTE, minute);
        next.set(Calendar.SECOND, 0);
        next.set(Calendar.MILLISECOND, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                action.equals("MORNING_AZKAR") ? 0 : 1,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, next.getTimeInMillis(), pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, next.getTimeInMillis(), pendingIntent);
        }

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

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        NotificationManagerCompat.from(context).notify(2, builder.build());

    }
}