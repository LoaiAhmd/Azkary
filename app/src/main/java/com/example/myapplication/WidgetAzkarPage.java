package com.example.myapplication;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.paperdb.Paper;

import java.util.ArrayList;

public class WidgetAzkarPage extends AppCompatActivity {

    DatabaseHelper myDB;
    WidgetAzkarAdapter adapter;
    RecyclerView rv;
    FloatingActionButton fab;
    ArrayList<String> lst_widget_azkar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_azkar_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.id_widget_azkar_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.id_toolbar_widget_page_layout);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        myDB = new DatabaseHelper(this);
        lst_widget_azkar = new ArrayList<>();
        adapter = new WidgetAzkarAdapter(this, lst_widget_azkar);
        rv = findViewById(R.id.id_wid_recyclerView);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fab = findViewById(R.id.id_fab_widget_azkar_page);
        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            EditText edt_newzekr = new EditText(this);
            builder.setTitle(R.string.string_title_add_new_zekir);
            builder.setView(edt_newzekr);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.string_btn_add_new_zekir, (dialog, which) -> {
                String newzekr = edt_newzekr.getText().toString().trim();
                if(!newzekr.isEmpty()){
                    myDB.insertNewWidgetZekr(newzekr);

                    SharedPreferences sp_status = getSharedPreferences("CheckedAzkar", MODE_PRIVATE);
                    SharedPreferences.Editor status_editor = sp_status.edit();
                    status_editor.putBoolean(newzekr, true);
                    status_editor.apply();

                    displayData();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, R.string.string_toast_added, Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(R.string.string_btn_cancel,  (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        Paper.init(this);
        displayData();

    }

    private void displayData() {
        lst_widget_azkar.clear();

        Cursor cursor = myDB.getWidgetAzkar();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            while (cursor.moveToNext()){
                lst_widget_azkar.add(cursor.getString(cursor.getColumnIndexOrThrow("col_zekir")));
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