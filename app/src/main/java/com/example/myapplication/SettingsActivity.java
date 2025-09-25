package com.example.myapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean nightMode;
    private boolean notifications;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TextView clk_morningAzkar, clk_eveningAzkar;
    private Switch swch_morningAzkar, swch_eveningAzkar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.id_settings_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sp = getSharedPreferences("notifications", MODE_PRIVATE);
        editor = sp.edit();

        // allowing notifications
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        darkMode();

        swch_morningAzkar = findViewById(R.id.id_switch_clock_morning_azkar);
        clk_morningAzkar = findViewById(R.id.id_clock_morning_azkar);
        createMorningAzkarNotificationChannel();

        boolean morningEnabled = sp.getBoolean("morning_azkar_enabled", false);
        swch_morningAzkar.setChecked(morningEnabled);
        clk_morningAzkar.setEnabled(morningEnabled);

        String saved_mor_Time = sp.getString("set_morning_alarm", getString(R.string.string_select_time));
        clk_morningAzkar.setText(saved_mor_Time);

        swch_morningAzkar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("morning_azkar_enabled", isChecked).apply();
            clk_morningAzkar.setEnabled(isChecked);
            if (!isChecked) {
                disableAlarmMorningAzkar();

            }
        });
        clk_morningAzkar.setOnClickListener(v -> {
            setAlarmMorningAzkar();
        });

        swch_eveningAzkar = findViewById(R.id.id_switch_clock_evening_azkar);
        clk_eveningAzkar = findViewById(R.id.id_clock_evening_azkar);
        createEveningAzkarNotificationChannel();

        boolean eveningAzkarEnabled = sp.getBoolean("evening_azkar_enabled", false);
        swch_eveningAzkar.setChecked(eveningAzkarEnabled);
        clk_eveningAzkar.setEnabled(eveningAzkarEnabled);

        String saved_eve_Time = sp.getString("set_evening_alarm", getString(R.string.string_select_time));
        clk_eveningAzkar.setText(saved_eve_Time);
        swch_eveningAzkar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editor.putBoolean("evening_azkar_enabled", isChecked);
            editor.apply();
            clk_eveningAzkar.setEnabled(isChecked);
            if (!isChecked) {
                disableAlarmEveningAzkar();
            }
        });
        clk_eveningAzkar.setOnClickListener(v -> {
            setAlarmEveningAzkar();
        });
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    notifications = true;
                } else {
                    Toast.makeText(this, R.string.string_notifications_denied, Toast.LENGTH_SHORT).show();
                    swch_morningAzkar.setChecked(false);
                    swch_eveningAzkar.setChecked(false);
                    editor.putBoolean("morning_azkar_enabled", false);
                    editor.putBoolean("evening_azkar_enabled", false);
                    editor.apply();
                }
            });

    private void createMorningAzkarNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = this.getString(R.string.string_channel_morning_azkar);
            String desc = this.getString(R.string.string_notification_morning_azkar);
            int imp = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("channelid_morningazkar", name, imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createEveningAzkarNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = this.getString(R.string.string_channel_evening_azkar);
            String desc = this.getString(R.string.string_notification_evening_azkar);
            int imp = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("channelid_eveningazkar", name, imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarmMorningAzkar() {
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setMinute(0)
                .setTitleText(R.string.string_select_alarm)
                .build();

        timePicker.show(getSupportFragmentManager(), "channelid_morningazkar");
        timePicker.addOnPositiveButtonClickListener(view -> {
            String ampm = (timePicker.getHour() >= 12) ? "PM" : "AM";
            int displayHour = (timePicker.getHour() > 12) ? timePicker.getHour() - 12 : timePicker.getHour();

            String time = String.format("%02d:%02d %s", displayHour, timePicker.getMinute(), ampm);
            editor.putString("set_morning_alarm", time);
            editor.apply();
            clk_morningAzkar.setText(time);

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            editor.putInt("hour_morning", timePicker.getHour()).apply();
            editor.putInt("minute_morning", timePicker.getMinute()).apply();
            enableAlarmMorningAzkar();

        });
    }

    private void setAlarmEveningAzkar() {
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setMinute(0)
                .setTitleText("Select Alarm")
                .build();

        timePicker.show(getSupportFragmentManager(), "channelid_eveningazkar");
        timePicker.addOnPositiveButtonClickListener(view -> {
            String ampm = (timePicker.getHour() >= 12) ? "PM" : "AM";
            int displayHour = (timePicker.getHour() > 12) ? timePicker.getHour() - 12 : timePicker.getHour();

            String time = String.format("%02d:%02d %s", displayHour, timePicker.getMinute(), ampm);
            editor.putString("set_evening_alarm", time);
            editor.apply();
            clk_eveningAzkar.setText(time);

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            editor.putInt("hour_evening", timePicker.getHour()).apply();
            editor.putInt("minute_evening", timePicker.getMinute()).apply();
            enableAlarmEveningAzkar();

        });
    }

    private void enableAlarmMorningAzkar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!am.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("MORNING_AZKAR");
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }
        Toast.makeText(this, R.string.string_alarm_morning_azkar_enabled, Toast.LENGTH_SHORT).show();
    }

    private void enableAlarmEveningAzkar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!am.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("EVENING_AZKAR");
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }
        Toast.makeText(this, R.string.string_alarm_evening_azkar_enabled, Toast.LENGTH_SHORT).show();
    }

    private void disableAlarmMorningAzkar() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, R.string.string_alarm_morning_azkar_disabled, Toast.LENGTH_SHORT).show();
    }

    private void disableAlarmEveningAzkar() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, R.string.string_alarm_evening_azkar_disabled, Toast.LENGTH_SHORT).show();
    }

    private void darkMode() {
        Switch swch_mode = findViewById(R.id.id_switch_dark_mode);
        sp = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        nightMode = sp.getBoolean("night", false);
        editor = sp.edit();

        if (nightMode) {
            swch_mode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        swch_mode.setOnClickListener(v -> {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean("night", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean("night", true);
            }
            editor.apply();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}