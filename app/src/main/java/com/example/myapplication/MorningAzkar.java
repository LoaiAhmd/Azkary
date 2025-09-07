package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.myapplication.databinding.ActivityMorningAzkarBinding;

import java.util.Vector;

public class MorningAzkar extends AppCompatActivity {

    Vector<Zekir_> listAzkar = new Vector<>();;
    DB_Helper_MorningAzkar dbHelperMorningAzkar;
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

        dbHelperMorningAzkar = new DB_Helper_MorningAzkar(MorningAzkar.this);
        TextView txtv_zekirBox = findViewById(R.id.id_zekr_box);

        for (int i = 0; i < AzkarData.Vz_Morning.size(); i++){
            listAzkar.add(new Zekir_(AzkarData.Vz_Morning.get(i).get_zekir(),
                    AzkarData.Vz_Morning.get(i).get_counts(),
                    AzkarData.Vz_Morning.get(i).get_category()));

            dbHelperMorningAzkar.addAzkar(listAzkar.get(i).get_zekir(),
                    listAzkar.get(i).get_counts(),
                    listAzkar.get(i).get_category());

            txtv_zekirBox.setText(listAzkar.get(i).get_zekir().toString());
        }

        Cursor cursor = dbHelperMorningAzkar.fetchAll();
        MorningAzkarAdapter adapter = new MorningAzkarAdapter(cursor);
        binding.idRecyclerView.setAdapter(adapter);
        binding.idRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
