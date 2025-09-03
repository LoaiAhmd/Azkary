package com.example.myapplication;

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

public class MorningAzkarAdapter extends RecyclerView.Adapter<MorningAzkarAdapter.ViewHolder> {

    List<Zekir_> azkarList = new ArrayList<>();

    public MorningAzkarAdapter(List<Zekir_> azkarList) {
        this.azkarList = azkarList;
    }

    @NonNull
    @Override
    public @NotNull MorningAzkarAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zekr, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MorningAzkarAdapter.ViewHolder holder, int position) {
        holder.txtv_zekrBox.setText(azkarList.get(position).get_zekir());
        holder.btn_count.setText(String.valueOf(azkarList.get(position).get_counts()));
    }

    @Override
    public int getItemCount() {
        return azkarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtv_zekrBox;
        Button btn_count;
        ImageButton btn_copy;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtv_zekrBox = itemView.findViewById(R.id.id_zekr_box);
            btn_count = itemView.findViewById(R.id.id_count_btn);
            btn_copy = itemView.findViewById(R.id.id_copy_btn);
        }
    }
}
