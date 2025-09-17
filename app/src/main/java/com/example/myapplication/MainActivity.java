package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Button tasbeeh_btn = findViewById(R.id.id_tasbeeh_btn);
        tasbeeh_btn.setOnClickListener(v -> {
            Intent tasbeeh_intent = new Intent(MainActivity.this, Tasbeeh.class);
            startActivity(tasbeeh_intent);
        });

        ImageButton settings = findViewById(R.id.id_settings);
        settings.setOnClickListener(v -> {
            Intent settings_intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settings_intent);
        });

        BottomNavigationView nav_homePage = findViewById(R.id.id_nav_home_page);
        nav_homePage.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.id_nav_menu_home_page) {
                Intent homePage_intent = new Intent(MainActivity.this, Tasbeeh.class);
                startActivity(homePage_intent);
            }
            return false;
        });

        BottomNavigationView nav_widgetAzkarPage = findViewById(R.id.id_nav_widget_page);
        nav_widgetAzkarPage.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.id_nav_menu_widget_page) {
                Intent widgetAzkarPage_intent = new Intent(MainActivity.this, WidgetAzkarPage.class);
                startActivity(widgetAzkarPage_intent);
            }
            return false;
        });

    }
}