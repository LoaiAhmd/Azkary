package com.example.myapplication;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RemoteViews;
import io.paperdb.Paper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private static int currentIndex = 0;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Paper.init(context);
        SharedPreferences sp_status = context.getApplicationContext().getSharedPreferences("CheckedAzkar", context.MODE_PRIVATE);

        ArrayList<String> lst_full_azkar = Paper.book().read("full_azkar_list",new ArrayList<>());
        ArrayList<String> lst_checked_azkar = new ArrayList<>();

        String widget_zekir;
        boolean isChecked;

        for (String zekir: lst_full_azkar) {
            if(sp_status.getBoolean(zekir, false))
                lst_checked_azkar.add(zekir);
        }

        if(!lst_checked_azkar.isEmpty()){
            currentIndex = (currentIndex >= lst_checked_azkar.size()) ? 0 : currentIndex;
            widget_zekir = lst_checked_azkar.get(currentIndex);
        }
        else
            widget_zekir = " اذكر الله " ;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.id_widget_txtv, widget_zekir);

        Intent intent = new Intent(context, Widget.class);
        intent.setAction("Change_Zekir");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        views.setOnClickPendingIntent(R.id.id_refresh_zekir, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if("Change_Zekir".equals(intent.getAction())){
            currentIndex++;
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, Widget.class);
            int[] appWidgetIds = widgetManager.getAppWidgetIds(thisWidget);

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, widgetManager, appWidgetId);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Intent intent = new Intent(context, WidgetAzkarPage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.id_widget_layout, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}