package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
            Intent tasbeeh_intent = new Intent(MainActivity.this, MorningAzkar.class);
            startActivity(tasbeeh_intent);
        });

    }
}