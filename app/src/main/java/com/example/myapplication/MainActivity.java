package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.shape.MaterialShapeDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        boolean nightMode = sp.getBoolean("night", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_main);

        Button morning_azkar_btn = findViewById(R.id.id_morning_btn);
        morning_azkar_btn.setOnClickListener(v -> {
            Intent morning_intent = new Intent(MainActivity.this, MorningAzkar.class);
            startActivity(morning_intent);
        });

        Button evening_azkar_btn = findViewById(R.id.id_evening_btn);
        evening_azkar_btn.setOnClickListener(v -> {
            Intent evening_intent = new Intent(MainActivity.this, EveningAzkar.class);
            startActivity(evening_intent);
        });

        Button tasbeeh_btn = findViewById(R.id.id_sleeping_azkar_btn);
        tasbeeh_btn.setOnClickListener(v -> {
            Intent tasbeeh_intent = new Intent(MainActivity.this, SleepingAzkar.class);
            startActivity(tasbeeh_intent);
        });

        ImageButton settings = findViewById(R.id.id_settings);
        settings.setOnClickListener(v -> {
            Intent settings_intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings_intent);
        });

        Button nav_TasbeehPage = findViewById(R.id.id_nav_tasbeeh_page);
        nav_TasbeehPage.setOnClickListener(item -> {
            Intent homePage_intent = new Intent(MainActivity.this, Tasbeeh.class);
            startActivity(homePage_intent);
        });

        Button nav_widgetAzkarPage = findViewById(R.id.id_nav_widget_page);
        nav_widgetAzkarPage.setOnClickListener(item -> {
            Intent widgetAzkarPage_intent = new Intent(MainActivity.this, WidgetAzkarPage.class);
            startActivity(widgetAzkarPage_intent);
        });

    }
}