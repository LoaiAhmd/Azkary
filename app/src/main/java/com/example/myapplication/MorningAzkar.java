package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.databinding.ActivityMorningAzkarBinding;

import java.util.ArrayList;
import java.util.Vector;

public class MorningAzkar extends AppCompatActivity {

    DB_Helper_MorningAzkar myDB;
    MorningAzkarAdapter adapter;
    RecyclerView rv;
    ArrayList<String> lst_zekir;
    ArrayList<Integer> lst_counts;
    private ActivityMorningAzkarBinding binding;

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

        binding = ActivityMorningAzkarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myDB = new DB_Helper_MorningAzkar(this);
        lst_zekir = new ArrayList<>();
        lst_counts = new ArrayList<>();
        adapter = new MorningAzkarAdapter(this, lst_zekir, lst_counts);
        rv = findViewById(R.id.id_recyclerView);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        displayData();
    }

    private void displayData() {
        lst_zekir.clear();
        lst_counts.clear();

        Cursor cursor = myDB.getData();
        if(cursor.getCount() == 0){
            Toast.makeText(MorningAzkar.this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                lst_zekir.add(cursor.getString(cursor.getColumnIndex("col_zekir")));
                lst_counts.add(cursor.getInt(cursor.getColumnIndex("col_counts")));
            }
            //adapter.notifyDataSetChanged();  // ✅ refresh RecyclerView
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
