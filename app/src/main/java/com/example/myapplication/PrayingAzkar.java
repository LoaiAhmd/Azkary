package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PrayingAzkar extends AppCompatActivity {

    DatabaseHelper myDB;
    PrayingAzkarAdapter adapter;
    RecyclerView rv;
    ArrayList<String> lst_note;
    ArrayList<String> lst_zekir;
    ArrayList<Integer> lst_counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praying_azkar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.id_praying_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.id_pray_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myDB = new DatabaseHelper(this);
        lst_note = new ArrayList<>();
        lst_zekir = new ArrayList<>();
        lst_counts = new ArrayList<>();
        adapter = new PrayingAzkarAdapter(this, lst_note, lst_zekir, lst_counts);
        rv = findViewById(R.id.id_pray_recyclerView);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    private void displayData() {
        lst_note.clear();
        lst_zekir.clear();
        lst_counts.clear();

        Cursor cursor = myDB.getPrayingAzkar();
        if(cursor.getCount() == 0){
            Toast.makeText(PrayingAzkar.this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                lst_note.add(cursor.getString(cursor.getColumnIndex("col_note")));
                lst_zekir.add(cursor.getString(cursor.getColumnIndex("col_zekir")));
                lst_counts.add(cursor.getInt(cursor.getColumnIndex("col_counts")));
            }
        }
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