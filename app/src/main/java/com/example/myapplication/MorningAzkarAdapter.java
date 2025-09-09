package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MorningAzkarAdapter extends RecyclerView.Adapter<MorningAzkarAdapter.ViewHolder>{

    public Cursor cursor;
    private Context context;
    private ArrayList<String> lst_zekir;
    private ArrayList<Integer> lst_counts;

    public MorningAzkarAdapter(Context context, ArrayList lst_zekir, ArrayList lst_counts) {
        this.context = context;
        this.lst_zekir = lst_zekir;
        this.lst_counts = lst_counts;
    }

    @NonNull
    @Override
    public @NotNull MorningAzkarAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zekr,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MorningAzkarAdapter.ViewHolder holder, int position) {
        holder.txtv_zekir.setText(lst_zekir.get(position));
        holder.btn_counts.setText(String.valueOf(lst_counts.get(position)));

        holder.btn_counts.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (adapterPos == RecyclerView.NO_POSITION) return;

            int newCount = lst_counts.get(adapterPos) - 1;

            if (newCount > 0) {
                lst_counts.set(adapterPos, newCount);
                notifyItemChanged(adapterPos);
            } else {
                // remove from all lists
                lst_counts.remove(adapterPos);
                lst_zekir.remove(adapterPos);
                notifyItemRemoved(adapterPos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lst_zekir.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_zekir;
        public Button btn_counts;
        public CardView card;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtv_zekir = itemView.findViewById(R.id.id_zekr_box);
            btn_counts = itemView.findViewById(R.id.id_count_btn);
            card = itemView.findViewById(R.id.id_cardview);
        }
    }
}
