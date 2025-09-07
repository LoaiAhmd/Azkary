package com.example.myapplication;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MorningAzkarAdapter extends RecyclerView.Adapter<MorningAzkarAdapter.ViewHolder>{

    public Cursor cursor;

    public MorningAzkarAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public @NotNull MorningAzkarAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zekr,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MorningAzkarAdapter.ViewHolder holder, int position) {
        if(!cursor.moveToPosition(position)) return;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_zekir;
        public Button btn_counts;
        MorningAzkarAdapter morningAzkarAdapter;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtv_zekir = itemView.findViewById(R.id.id_zekr_box);
            btn_counts = itemView.findViewById(R.id.id_count_btn);

            DB_Helper_MorningAzkar myDB = new DB_Helper_MorningAzkar(itemView.getContext());
        }
    }
}
