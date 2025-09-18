package com.example.myapplication;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

        this.sp_status = context.getSharedPreferences("CheckedAzkar", context.MODE_PRIVATE);
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

        //***************************************************************//

        holder.btn_edit.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this.context, holder.btn_edit);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_popup, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.id_update_btn){
                        holder.updateZekr(position);
                    } else if (item.getItemId() == R.id.id_delete_btn) {
                        holder.deleteZekr(position);
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
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
        public ImageButton btn_edit;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            chkbox_zekir = itemView.findViewById(R.id.id_chkbox_zekir);
            btn_edit = itemView.findViewById(R.id.id_edit_btn);
        }

        private void updateZekr(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            EditText editText = new EditText(context);
            editText.setText(lst_widet_azkar.get(position));

            String old_z = editText.getText().toString();

            builder.setTitle(R.string.string_btn_update);
            builder.setView(editText);
            builder.setCancelable(false);

            builder.setPositiveButton(R.string.string_btn_update, (dialog, var) -> {
                String newzekr = editText.getText().toString().trim();
                if(!newzekr.isEmpty() || !newzekr.equals(lst_widet_azkar.get(position))){
                    lst_widet_azkar.set(position, newzekr);
                    DatabaseHelper myDB = new DatabaseHelper(context);
                    myDB.updateWidgetZekr(old_z, newzekr);
                    WidgetAzkarAdapter.this.notifyDataSetChanged();
                    WidgetAzkarAdapter.this.notifyItemChanged(position);

                    sp_status.edit().putBoolean(newzekr, true).apply();
                }
                else{
                    dialog.cancel();
                }
            });

            builder.setNegativeButton(R.string.string_btn_cancel,  (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        private void deleteZekr(int position) {
            DatabaseHelper myDB = new DatabaseHelper(context);
            Cursor cursor = myDB.getWidgetAzkar();
            position = getAdapterPosition();
            String delete_z = lst_widet_azkar.get(position);
            lst_widet_azkar.remove(position);
            if(position != RecyclerView.NO_POSITION && cursor.moveToPosition(position)){
                int zekrId = myDB.getWidgetZekrId(delete_z);
                int zekrid = cursor.getInt(cursor.getColumnIndexOrThrow(myDB.COL_ID));

                if (zekrId != -1) {
                    myDB.deleteWidgetZekr(zekrId);
                }
                WidgetAzkarAdapter.this.notifyItemRemoved(position);
                WidgetAzkarAdapter.this.notifyItemRangeChanged(position, getItemCount());
            }
            sp_status.edit().putBoolean(delete_z, false).apply();
            updateWidget();
        }
    }
}
