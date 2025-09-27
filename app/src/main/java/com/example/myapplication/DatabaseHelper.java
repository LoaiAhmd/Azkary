package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AzkarDataBase.db";
    public static final int DATABASE_VERSION = 18;

    public static final String TBL_PRAYING_AZKAR = "tbl_praying_azkar";
    public static final String TBL_MORNING_AZKAR = "tbl_morning_azkar";
    public static final String TBL_EVENING_AZKAR = "tbl_evening_azkar";
    public static final String TBL_WIDGET_AZKAR = "tbl_widget_azkar";
    public static final String TBL_SLEEPING_AZKAR = "tbl_sleeping_azkar";
    public static final String TBL_WAKING_AZKAR = "tbl_waking_azkar";
    public static final String COL_ID = "col_id";
    public static final String COL_ZEKIR = "col_zekir";
    public static final String COL_NOTE = "col_note";
    public static final String COL_COUNTS = "col_counts";
    public static final String COL_CATEGORY = "col_category";

    private final AzkarData ad = new AzkarData();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //---------------- PRAYING AZKAR TABLE ----------------//
        Create_Praying_Azkar_Table(db);

        //---------------- MORNING AZKAR TABLE ----------------//
        Create_Morning_Azkar_Table(db);

        //---------------- EVENING AZKAR TABLE ----------------//
        Create_Evening_Azkar_Table(db);

        //---------------- WIDGET AZKAR TABLE ----------------//
        Create_Widget_Azkar_Table(db);

        //---------------- SLEEPING AZKAR TABLE ----------------//
        Create_Sleeping_Azkar_Table(db);

        //---------------- WAkING AZKAR TABLE ----------------//
        Create_Waking_Azkar_Table(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_PRAYING_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MORNING_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_EVENING_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WIDGET_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_SLEEPING_AZKAR);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_WAKING_AZKAR);
        onCreate(db);
    }

    private void Create_Praying_Azkar_Table(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_PRAYING_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NOTE + " TEXT NOT NULL," +
                COL_ZEKIR + " TEXT NOT NULL," +
                COL_COUNTS + " INTEGER," +
                COL_CATEGORY + " CHAR(1))");

        for (int i = 0; i < ad.size_P(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_NOTE, ad.getZekir_P(i).get_note());
            cv.put(COL_ZEKIR, ad.getZekir_P(i).get_zekir());
            cv.put(COL_COUNTS, ad.getZekir_P(i).get_counts());
            cv.put(COL_CATEGORY, (byte) String.valueOf(ad.getZekir_P(i).get_category()).charAt(0));
            db.insert(TBL_PRAYING_AZKAR, null, cv);
        }
    }
    public Cursor getPrayingAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_PRAYING_AZKAR, null);
    }

    public void Create_Morning_Azkar_Table(SQLiteDatabase db){
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
    }
    public Cursor getMorningAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_MORNING_AZKAR, null);
    }

    public void Create_Evening_Azkar_Table(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TBL_EVENING_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL," +
                COL_COUNTS + " INTEGER," +
                COL_CATEGORY + " CHAR(1))");

        for (int i = 0; i < ad.size_E(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_E(i).get_zekir());
            cv.put(COL_COUNTS, ad.getZekir_E(i).get_counts());
            cv.put(COL_CATEGORY, (byte) String.valueOf(ad.getZekir_E(i).get_category()).charAt(0));
            db.insert(TBL_EVENING_AZKAR, null, cv);
        }
    }
    public Cursor getEveningAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_EVENING_AZKAR, null);
    }



    public void Create_Widget_Azkar_Table(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TBL_WIDGET_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL)");

        for (int i = 0; i < ad.size_W(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_W(i).get_zekir());
            db.insert(TBL_WIDGET_AZKAR, null, cv);
        }
    }
        public Cursor getWidgetAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_WIDGET_AZKAR, null);
    }

    public void insertNewWidgetZekr(String newZekr){
        SQLiteDatabase db = this.getWritableDatabase();
        ad.add_new_widget_zekr(newZekr);
        ContentValues cv = new ContentValues();
        cv.put(COL_ZEKIR, newZekr);
        db.insert(TBL_WIDGET_AZKAR, null, cv);
        db.close();
    }

    public void updateWidgetZekr(String old_z, String new_z){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "UPDATE " + TBL_WIDGET_AZKAR +
                        " SET " + COL_ZEKIR + " = '" + new_z + "'" +
                        " WHERE " + COL_ZEKIR + " = '" + old_z + "';");
        db.close();
    }

    public void deleteWidgetZekr(int delete_z_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_WIDGET_AZKAR, COL_ID + " = ?", new String[]{String.valueOf(delete_z_id)});
        db.close();
    }

    public int getWidgetZekrId(String delete_Z) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TBL_WIDGET_AZKAR, new String[]{COL_ID},
                COL_ZEKIR + " = ?", new String[]{delete_Z},
                null, null, null);

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
        }
        cursor.close();
        db.close();
        return id;
    }



    public void Create_Sleeping_Azkar_Table(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TBL_SLEEPING_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL," +
                COL_COUNTS + " INTEGER," +
                COL_CATEGORY + " CHAR(1))");

        for (int i = 0; i < ad.size_S(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_S(i).get_zekir());
            cv.put(COL_COUNTS, ad.getZekir_S(i).get_counts());
            cv.put(COL_CATEGORY, (byte) String.valueOf(ad.getZekir_S(i).get_category()).charAt(0));
            db.insert(TBL_SLEEPING_AZKAR, null, cv);
        }
    }
    public Cursor getSleepingAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_SLEEPING_AZKAR, null);
    }


    private void Create_Waking_Azkar_Table(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_WAKING_AZKAR + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ZEKIR + " TEXT NOT NULL," +
                COL_COUNTS + " INTEGER," +
                COL_CATEGORY + " CHAR(1))");

        for (int i = 0; i < ad.size_K(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_ZEKIR, ad.getZekir_K(i).get_zekir());
            cv.put(COL_COUNTS, ad.getZekir_K(i).get_counts());
            cv.put(COL_CATEGORY, (byte) String.valueOf(ad.getZekir_K(i).get_category()).charAt(0));
            db.insert(TBL_WAKING_AZKAR, null, cv);
        }
    }

    public Cursor getWakingAzkar() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TBL_WAKING_AZKAR, null);
    }
}