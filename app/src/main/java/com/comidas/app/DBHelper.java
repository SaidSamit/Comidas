package com.comidas.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Comidas.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodContract.FoodEntry.TABLE_NAME + " (" +
                    FoodContract.FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FoodContract.FoodEntry.COLUMN_NAME + " TEXT," +
                    FoodContract.FoodEntry.COLUMN_DESCRIPTION + " TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME);
        onCreate(db);
    }
}
