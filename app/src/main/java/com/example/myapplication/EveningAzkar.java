package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.myapplication.databinding.ActivityEveningAzkarBinding;

import java.util.ArrayList;

public class EveningAzkar extends AppCompatActivity {

    DatabaseHelper myDB;
    EveningAzkarAdapter adapter;
    RecyclerView rv;
    ArrayList<String> lst_zekir;
    ArrayList<Integer> lst_counts;
    private ActivityEveningAzkarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEveningAzkarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.id_eve_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myDB = new DatabaseHelper(this);
        lst_zekir = new ArrayList<>();
        lst_counts = new ArrayList<>();
        adapter = new EveningAzkarAdapter(this, lst_zekir,lst_counts);
        rv = findViewById(R.id.id_eve_recyclerview);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    private void displayData() {
        lst_zekir.clear();
        lst_counts.clear();

        Cursor cursor = myDB.getEveningAzkar();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                lst_zekir.add(cursor.getString(cursor.getColumnIndexOrThrow("col_zekir")));
                lst_counts.add(cursor.getInt(cursor.getColumnIndexOrThrow("col_counts")));
            }
            cursor.close();
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