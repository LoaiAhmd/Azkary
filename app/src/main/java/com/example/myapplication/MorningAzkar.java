package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ActivityMorningAzkarBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MorningAzkar extends AppCompatActivity {

    private ActivityMorningAzkarBinding binding;
    List<Zekir_> azkarList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
/*
        azkarList.add(new Zekir_("بسم الله الرحمن الرحيم"));
        azkarList.add(new Zekir_("الله أكبر"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));
        azkarList.add(new Zekir_("الحمد لله"));

 */

        // ViewBinding
        binding = ActivityMorningAzkarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Toolbar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        MorningAzkarAdapter morningAzkarAdapter = new MorningAzkarAdapter(azkarList);

        RecyclerView rvNumbers = findViewById(R.id.id_recyclerView);
        rvNumbers.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvNumbers.setAdapter(morningAzkarAdapter);

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
