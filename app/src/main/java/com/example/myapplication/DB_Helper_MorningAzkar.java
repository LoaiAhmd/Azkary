package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;
import androidx.annotation.Nullable;

import java.util.Vector;

public class DB_Helper_MorningAzkar extends SQLiteOpenHelper {
    private Context context;

    AzkarData azkarData = new AzkarData();

    private static final String DATABASE_NAME = "DB_Azkar.db";
    private static final int VERSION = 1;

    public DB_Helper_MorningAzkar(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                " CREATE TABLE tbl_morning_azkar (" +
                        "id INTEGER PRIMARY KEY," +
                        "zekr TEXT NOT NULL," +
                        "counts INTEGER," +
                        "category CHAR(1) )"
        );

        db.execSQL(
                " CREATE TABLE tbl_evening_azkar (" +
                        "id INTEGER PRIMARY KEY," +
                        "zekr TEXT NOT NULL," +
                        "counts INTEGER," +
                        "category CHAR(1) )"
        );

        db.execSQL(
                " CREATE TABLE tbl_notfications_azkar (" +
                        "id INTEGER PRIMARY KEY," +
                        "zekr TEXT NOT NULL )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_morning_azkar");
        db.execSQL("DROP TABLE IF EXISTS tbl_evening_azkar");
        db.execSQL("DROP TABLE IF EXISTS tbl_notfications_azkar");
        onCreate(db);
    }

    public void add_Azkar(){
        for (Pair<String, Integer> p: azkarData.getVP_Morning()
             ) {

        }
    }
}
