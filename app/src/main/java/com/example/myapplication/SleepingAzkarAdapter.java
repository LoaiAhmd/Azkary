package com.example.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SleepingAzkarAdapter extends RecyclerView.Adapter<SleepingAzkarAdapter.ViewHolder> {
    Context context;
    ArrayList<String> lst_zekir;
    ArrayList<Integer> lst_counts;

    public SleepingAzkarAdapter( Context context, ArrayList<String> lst_zekir, ArrayList<Integer> lst_counts) {
        this.context = context;
        this.lst_zekir = lst_zekir;
        this.lst_counts = lst_counts;
    }

    @NonNull
    @Override
    public @NotNull SleepingAzkarAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zekr, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SleepingAzkarAdapter.ViewHolder holder, int position) {
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
                lst_counts.remove(adapterPos);
                lst_zekir.remove(adapterPos);
                notifyItemRemoved(adapterPos);
                if(lst_zekir.isEmpty()) ((AppCompatActivity) context).finish();
            }
        });

        holder.img_copy_btn.setOnClickListener(v -> {
            copyToClipboard(context, holder.txtv_zekir.getText().toString());
            Toast.makeText(context, "تم نسخ الذكر", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lst_zekir.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtv_zekir;
        public Button btn_counts;
        ImageButton img_copy_btn;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtv_zekir = itemView.findViewById(R.id.id_zekr_box);
            btn_counts = itemView.findViewById(R.id.id_count_btn);
            img_copy_btn = itemView.findViewById(R.id.id_copy_btn);
        }
    }

    private void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("zekr", text);
        clipboard.setPrimaryClip(clip);
    }
}
