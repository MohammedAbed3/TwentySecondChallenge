package com.tegogames.twentysecondchallenge.Database;

import static com.tegogames.twentysecondchallenge.Database.DatabaseHelper.COLUMN_PLAYER2_NAME;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseOperations {


    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] allColumns = {DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_PLAYER1_NAME,COLUMN_PLAYER2_NAME, DatabaseHelper.COLUMN_PLAYER1_STRIKES, DatabaseHelper.COLUMN_PLAYER2_STRIKES};

    public DataBaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);


    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addPlayers(String player1Name, String player2Name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PLAYER1_NAME, player1Name);
        values.put(DatabaseHelper.COLUMN_PLAYER2_NAME, player2Name);
        values.put(DatabaseHelper.COLUMN_PLAYER1_STRIKES, 0);
        values.put(DatabaseHelper.COLUMN_PLAYER2_STRIKES, 0);
//



        long r =    db.insert(DatabaseHelper.TABLE_PLAYER_STATS, null, values);
        if (r ==-1){
            Log.d("errr","fellef");
        }else {
            Log.d("errr","gooood");

        }

    }











//    public void addPlayersStrike(int strike1,int strike2) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_PLAYER1_STRIKES, strike1);
//        values.put(DatabaseHelper.COLUMN_PLAYER2_STRIKES, strike2);
//
//
//
//
//        long r =    db.insert(DatabaseHelper.TABLE_PLAYER_STATS, null, values);
//        if (r ==-1){
//            Log.d("errr","fellef");
//        }else {
//            Log.d("errr","gooood");
//
//        }
//
//    }

    public void addPlayersStrikes(String player1Name, int player1Strikes, String player2Name, int player2Strikes) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PLAYER1_NAME, player1Name);
        values.put(DatabaseHelper.COLUMN_PLAYER1_STRIKES, player1Strikes);
        values.put(DatabaseHelper.COLUMN_PLAYER2_NAME, player2Name);
        values.put(DatabaseHelper.COLUMN_PLAYER2_STRIKES, player2Strikes);

        long r = db.insert(DatabaseHelper.TABLE_PLAYER_STATS, null, values);
        if (r == -1) {
            Log.d("errr", "فشل في إضافة البيانات");
        } else {
            Log.d("errr", "تم إضافة البيانات بنجاح"+player1Strikes);
        }
    }

//    public int[] getPlayersStrikes() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        int[] strikes = new int[2];
//
//        String[] projection = {
//                DatabaseHelper.COLUMN_PLAYER1_STRIKES,
//                DatabaseHelper.COLUMN_PLAYER2_STRIKES
//        };
//
//        Cursor cursor = db.query(
//                DatabaseHelper.TABLE_PLAYER_STATS,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        if (cursor.moveToFirst()) {
//            strikes[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PLAYER1_STRIKES));
//            strikes[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PLAYER2_STRIKES));
//        }
//
//        cursor.close();
//        Log.d("hhhhhh",strikes[0]+"");
//
//        return strikes;
//    }


    public List<PlayerModel> getAllPlayerStats() {
        List<PlayerModel> playerStats = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER_STATS, null,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PlayerModel playerStat = cursorToPlayerStat(cursor);
            playerStats.add(playerStat);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return playerStats;
    }
    @SuppressLint("Range")
    private PlayerModel cursorToPlayerStat(Cursor cursor) {
        PlayerModel playerStat = new PlayerModel();
        playerStat.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
        playerStat.setPlayer1Name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER1_NAME)));
        playerStat.setPlayer2Name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER2_NAME)));
        playerStat.setStrikes1(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER1_STRIKES)));
        playerStat.setStrikes2(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER2_STRIKES)));
        return playerStat;
    }


    public List<String> getFirstAndSecondPlayerNames() {
        List<String> playerNames = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_PLAYER1_NAME, DatabaseHelper.COLUMN_PLAYER2_NAME};
        String sortOrder = DatabaseHelper.COLUMN_ID + " DESC"; // ترتيب عكسي بناءً على العمود COLUMN_ID (أحدث سجلات أولاً)
        String limit = "2"; // ستعرض آخر اسمين فقط

        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER_STATS, projection,
                null, null, null, null, sortOrder, limit);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String player1Name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER1_NAME));
                @SuppressLint("Range") String player2Name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER2_NAME));

                playerNames.add(player1Name);
                playerNames.add(player2Name);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return playerNames;
    }
    @SuppressLint("Range")
    public   List<Integer> getPlayers1Strikes() {
        List<Integer> player1Strikes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_PLAYER1_STRIKES};
        String sortOrder = DatabaseHelper.COLUMN_ID + " DESC"; // ترتيب عكسي بناءً على العمود COLUMN_ID (أحدث سجلات أولاً)
        String limit = "1"; // ستعرض سترايك واحد فقط
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER_STATS, projection,
                null, null, null, null, sortOrder, limit);

        if (cursor.moveToFirst()) {
           int player1Strikes1 = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER1_STRIKES));
            player1Strikes.add(player1Strikes1);
        }

        cursor.close();
        db.close();

        return player1Strikes;
    }
    @SuppressLint("Range")

    public int getTotalPlayer1Strikes() {
        int totalPlayer1Strikes = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"SUM(" + DatabaseHelper.COLUMN_PLAYER1_STRIKES + ")"};
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER_STATS, projection,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            totalPlayer1Strikes = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return totalPlayer1Strikes;
    }

    public List<Integer> getPlayers2Strikes() {
        List<Integer> playerStrikes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseHelper.COLUMN_PLAYER2_STRIKES};
        String sortOrder = DatabaseHelper.COLUMN_ID + " DESC"; // ترتيب عكسي بناءً على العمود COLUMN_ID (أحدث سجلات أولاً)
        String limit = "1"; // ستعرض آخر اسمين فقط
        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER_STATS, projection,
                null, null, null, null, sortOrder, limit);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int player2Strikes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER2_STRIKES));
                playerStrikes.add(player2Strikes);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return playerStrikes;
    }
    @SuppressLint("Range")
    public List<Integer> getPlayersStrikes() {
        List<Integer> playerStrikes = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper.COLUMN_PLAYER1_STRIKES, DatabaseHelper.COLUMN_PLAYER2_STRIKES};

        Cursor cursor = db.query(DatabaseHelper.TABLE_PLAYER_STATS, projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            // إضافة عدد السترايكات للاعب الأول
           int player1Strikes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER1_STRIKES));
            playerStrikes.add(player1Strikes);

            // إضافة عدد السترايكات للاعب الثاني
            int player2Strikes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYER2_STRIKES));
            playerStrikes.add(player2Strikes);
        }

        cursor.close();
        db.close();

        return playerStrikes;
    }


    public void deleteAllDataInColumn() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String column = DatabaseHelper.COLUMN_PLAYER1_STRIKES;
        String column2 = DatabaseHelper.COLUMN_PLAYER2_STRIKES;
        db.delete(DatabaseHelper.TABLE_PLAYER_STATS, column , null);
        db.delete(DatabaseHelper.TABLE_PLAYER_STATS, column2 , null);
        db.close();
    }


}
