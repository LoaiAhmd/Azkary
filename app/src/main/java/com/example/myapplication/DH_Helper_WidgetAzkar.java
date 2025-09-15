package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DH_Helper_WidgetAzkar extends SQLiteOpenHelper {
    public static String DataBaseName = "WidgetAzkarDataBase.db";
    SQLiteDatabase AzkarDataBase;
    AzkarData ad = new AzkarData();
    public DH_Helper_WidgetAzkar(@Nullable Context context){
        super(context, DataBaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_widget_azkar(" +
                "col_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "col_zekir TEXT NOT NULL)");

        for (int i = 0; i < ad.size_W(); i++) {
            ContentValues cv = new ContentValues();
            cv.put("col_zekir", ad.getZekir_W(i).get_zekir());
            db.insert("tbl_widget_azkar", null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_widget_azkar");
        onCreate(db);
    }

    public Cursor getData(){
        AzkarDataBase = this.getWritableDatabase();
        Cursor cursor = AzkarDataBase.rawQuery("SELECT * FROM tbl_widget_azkar", null);
        return cursor;
    }
}
