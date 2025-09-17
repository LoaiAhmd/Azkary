package com.example.myapplication;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.paperdb.Paper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WidgetAzkarAdapter extends RecyclerView.Adapter<WidgetAzkarAdapter.ViewHolder>{

    public SharedPreferences sp_status;
    Context context;
    ArrayList<String> lst_widet_azkar;

    public WidgetAzkarAdapter(Context context, ArrayList<String> lst_widet_azkar) {
        this.context = context;
        this.lst_widet_azkar = lst_widet_azkar;
    }

    @NonNull
    @Override
    public @NotNull WidgetAzkarAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_widget_azkar, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String zekir = lst_widet_azkar.get(position);

        SharedPreferences sp_status = context.getSharedPreferences("CheckedAzkar", context.MODE_PRIVATE);
        boolean checked = sp_status.getBoolean(zekir, false);

        holder.chkbox_zekir.setOnCheckedChangeListener(null);

        holder.chkbox_zekir.setText(zekir);
        holder.chkbox_zekir.setChecked(checked);

        holder.chkbox_zekir.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor status_editor = sp_status.edit();
            status_editor.putBoolean(zekir, isChecked);
            status_editor.apply();

            updateWidget();
        });

        Paper.book().write("full_azkar_list", lst_widet_azkar);
    }

    public void updataAdapter(String newzekr){
        lst_widet_azkar.add(newzekr);
        SharedPreferences.Editor editor = sp_status.edit();
        editor.putBoolean(newzekr, true);
        editor.commit();

        updateWidget();
    }
    private void updateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, Widget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.id_widget_txtv);
            for (int appWidgetId : appWidgetIds) {
                Widget.updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lst_widet_azkar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox chkbox_zekir;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            chkbox_zekir = itemView.findViewById(R.id.id_chkbox_zekir);
        }
    }
}
