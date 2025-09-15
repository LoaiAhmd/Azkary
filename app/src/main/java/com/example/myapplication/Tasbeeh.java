package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import com.example.myapplication.databinding.ActivityTasbeehBinding;
import com.example.myapplication.databinding.ActivityTasbeehBinding;

public class Tasbeeh extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTasbeehBinding binding;

    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTasbeehBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.id_tsb_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sp = getSharedPreferences("tasbeeh", MODE_PRIVATE);

        if (!sp.contains("initialized")) {
            sp.edit().putInt("count", 0).putBoolean("initialized", true).apply();
        }

        Button btn_zero = findViewById(R.id.id_zero_btn);
        Button btn_counter = findViewById(R.id.id_counter_btn);
        EditText edt_counter = findViewById(R.id.id_edt_counter);

        final int[] counter = {sp.getInt("count", 0)};
        edt_counter.setText(String.valueOf(counter[0]));

        btn_counter.setOnClickListener(v -> {
            counter[0]++;
            edt_counter.setText(String.valueOf(counter[0]));
            sp.edit().putInt("count", counter[0]).apply();
        });

        btn_zero.setOnClickListener(v -> {
            counter[0] = 0;
            edt_counter.setText("0");
            sp.edit().putInt("count", 0).apply();
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