package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AzkarDataBase.db";
    public static final int DATABASE_VERSION = 3;

    // Table names and columns
    public static final String TBL_MORNING_AZKAR = "tbl_morning_azkar";
    public static final String TBL_EVENING_AZKAR = "tbl_evening_azkar";
    public static final String TBL_WIDGET_AZKAR = "tbl_widget_azkar";
    public static final String COL_ID = "col_id";
    public static final String COL_ZEKIR = "col_zekir";
    public static final String COL_COUNTS = "col_counts";
    public static final String COL_CATEGORY = "col_category";

    private final AzkarData ad = new AzkarData();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Morning Azkar Table
        db.execSQL("CREATE TABLE " + TBL_MORNING_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL," +
                COL_COUNTS + " INTEGER," +
                COL_CATEGORY + " CHAR(1))");

        // Insert initial data for Morning Azkar
        for (int i = 0; i < ad.size_M(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_M(i).get_zekir());
            cv.put(COL_COUNTS, ad.getZekir_M(i).get_counts());
            cv.put(COL_CATEGORY, (byte) String.valueOf(ad.getZekir_M(i).get_category()).charAt(0));
            db.insert(TBL_MORNING_AZKAR, null, cv);
        }

        // Create Evening Azkar Table
        db.execSQL("CREATE TABLE " + TBL_EVENING_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL," +
                COL_COUNTS + " INTEGER," +
                COL_CATEGORY + " CHAR(1))");

        // Insert initial data for Evening Azkar
        for (int i = 0; i < ad.size_E(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_E(i).get_zekir());
            cv.put(COL_COUNTS, ad.getZekir_E(i).get_counts());
            cv.put(COL_CATEGORY, (byte) String.valueOf(ad.getZekir_E(i).get_category()).charAt(0));
            db.insert(TBL_EVENING_AZKAR, null, cv);
        }

        // Create Widget Azkar Table
        db.execSQL("CREATE TABLE " + TBL_WIDGET_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL)");

        // Insert initial data for Widget Azkar
        for (int i = 0; i < ad.size_W(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_W(i).get_zekir());
            db.insert(TBL_WIDGET_AZKAR, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MORNING_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_EVENING_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WIDGET_AZKAR);
        onCreate(db);
    }

    public Cursor getMorningAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_MORNING_AZKAR, null);
    }

    public Cursor getEveningAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_EVENING_AZKAR, null);
    }

    public Cursor getWidgetAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_WIDGET_AZKAR, null);
    }
}