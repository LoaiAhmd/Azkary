package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import androidx.annotation.Nullable;

import java.util.Vector;

public class DB_Helper_MorningAzkar extends SQLiteOpenHelper{
    private static String DataBaseName = "AzkarDataBase";
    SQLiteDatabase AzkarDataBase;
    public DB_Helper_MorningAzkar(@Nullable Context context) { super(context, DataBaseName, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_morning_azkar (" +
                "col_id INTEGER PRIMARY KEY," +
                "col_zekir TEXT NOT NULL," +
                "col_counts INTEGER," +
                "col_category CHAR(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_morning_azkar");
        onCreate(db);
    }

    public void addAzkar(String zekir, int counts, char category){
        ContentValues row = new ContentValues();
        row.put("col_zekir", zekir);
        row.put("col_counts", counts);
        row.put("col_category", (byte) String.valueOf(category).charAt(0));

        AzkarDataBase = getWritableDatabase();
        AzkarDataBase.insert("tbl_morning_azkar", null, row);
        AzkarDataBase.close();
    }

    public Cursor fetchAll(){
        AzkarDataBase = getReadableDatabase();
        String[] rowDetails = {"col_id", "col_zekir", "col_counts", "col_category"};
        Cursor cursor = AzkarDataBase.query("tbl_morning_azkar", rowDetails, null, null, null, null, null);
        if(cursor != null) cursor.moveToFirst();
        return cursor;
    }
}
