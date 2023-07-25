package com.tegogames.twentysecondchallenge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "player_stats.db";
    private static final int DATABASE_VERSION = 6;

    public static final String TABLE_PLAYER_STATS = "player_stats";
    public static final String COLUMN_ID = "id";

    public static final String COLUMN_PLAYER1_STRIKES = "player1_strikes";
    public static final String COLUMN_PLAYER2_STRIKES = "player2_strikes";


    public static final String COLUMN_PLAYER1_NAME = "player1_name";
    public static final String COLUMN_PLAYER2_NAME = "player2_name";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_PLAYER_STATS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLAYER1_NAME + " TEXT NOT NULL, " +
            COLUMN_PLAYER2_NAME + " TEXT NOT NULL, " +
            COLUMN_PLAYER1_STRIKES + " INTEGER NOT NULL ,"+
            COLUMN_PLAYER2_STRIKES + " INTEGER NOT NULL );";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);

    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER_STATS, null, null);
        db.close();
    }

//    public void deleteAllStarc() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_PLAYER_STATS, null, null);
//        db.close();
//    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_STATS);
        onCreate(sqLiteDatabase);
    }



}
