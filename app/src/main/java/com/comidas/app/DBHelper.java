package com.comidas.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ComidasDB.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + FoodContract.FoodEntry.TABLE_NAME + " (" +
                FoodContract.FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FoodContract.FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FoodContract.FoodEntry.COLUMN_DESCRIPTION + " TEXT);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME);
        onCreate(db);
    }
}
