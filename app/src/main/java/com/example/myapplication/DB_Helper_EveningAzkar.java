package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DB_Helper_EveningAzkar extends SQLiteOpenHelper {
    public static String DataBaseName = "EveningAzkarDataBase.db";
    SQLiteDatabase AzkarDateBase;

    AzkarData ad = new AzkarData();

    public DB_Helper_EveningAzkar(@Nullable Context context) {
        super(context, DataBaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_evening_azkar(" +
                "col_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "col_zekir TEXT NOT NULL," +
                "col_counts INTEGER," +
                "col_category CHAR(1))");

        for (int i = 0; i < ad.size_E(); i++) {
            ContentValues cv = new ContentValues();
            cv.put("col_zekir", ad.getZekir_E(i).get_zekir());
            cv.put("col_counts", ad.getZekir_E(i).get_counts());
            cv.put("col_category", (byte) String.valueOf(ad.getZekir_E(i).get_category()).charAt(0));
            db.insert("tbl_evening_azkar", null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_evening_azkar");
        onCreate(db);
    }

    public Cursor getData(){
        AzkarDateBase = this.getWritableDatabase();
        Cursor cursor = AzkarDateBase.rawQuery("SELECT * FROM tbl_evening_azkar", null);
        return cursor;
    }
}
