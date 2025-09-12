package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DB_Helper_MorningAzkar extends SQLiteOpenHelper{
    public static String DataBaseName = "MorningAzkarDataBase.db";
    SQLiteDatabase AzkarDataBase;
    AzkarData ad = new AzkarData();
    public DB_Helper_MorningAzkar(@Nullable Context context)
    { super(context, DataBaseName, null, 3);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_morning_azkar (" +
                "col_id INTEGER PRIMARY KEY autoincrement," +
                "col_zekir TEXT NOT NULL," +
                "col_counts INTEGER," +
                "col_category CHAR(1))");

        for (int i = 0; i < ad.size_M(); i++) {
            ContentValues cv = new ContentValues();
            cv.put("col_zekir", ad.getZekir_M(i).get_zekir());
            cv.put("col_counts", ad.getZekir_M(i).get_counts());
            cv.put("col_category", (byte) String.valueOf(ad.getZekir_M(i).get_category()).charAt(0));
            db.insert("tbl_morning_azkar", null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_morning_azkar");
        onCreate(db);
    }

    public void insertIntoDB(String zekir, int counts, char category){
        /*
        AzkarDataBase.execSQL("INSERT INTO tbl_morning_azkar " +
                "(col_zekir, col_counts, col_category) " +
                "values('"+ zekir +"', '"+counts+"', '"+category+"');");*/
        AzkarDataBase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("col_zekir", zekir);
        cv.put("col_counts", counts);
        cv.put("col_category", (byte) category);
        AzkarDataBase.insert("tbl_morning_azkar", null, cv);
    }

    public Cursor getData(){
        AzkarDataBase = this.getWritableDatabase();
        Cursor cursor = AzkarDataBase.rawQuery("SELECT * FROM tbl_morning_azkar", null);
        return cursor;
    }
}