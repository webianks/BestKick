package com.webianks.test.bestkick.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KickDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "kick.db";
    private static final int DB_VERSION = 2;


    public KickDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_KICK = "CREATE TABLE " + KickContract.KickEntry.TABLE_NAME
                + "(" + KickContract.KickEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KickContract.KickEntry.KICK_SL_NUMBER + " INTEGER NOT NULL ,"
                + KickContract.KickEntry.KICK_AMT_PLEDGED + " INTEGER NOT NULL,"
                + KickContract.KickEntry.KICK_BLURB + " VARCHAR(255) ,"
                + KickContract.KickEntry.KICK_BY + " VARCHAR(255) ,"
                + KickContract.KickEntry.KICK_COUNTRY + " VARCHAR (255) ,"
                + KickContract.KickEntry.KICK_CURRENCY + " VARCHAR (255) ,"
                + KickContract.KickEntry.KICK_END_TIME + " VARCHAR (255) ,"
                + KickContract.KickEntry.KICK_TITLE + " VARCHAR (255) ,"
                + KickContract.KickEntry.KICK_LOCATION + " VARCHAR (255) ,"
                + KickContract.KickEntry.KICK_PERCENTAGE_FUNDED + " INTEGER ,"
                + KickContract.KickEntry.KICK_BACKERS + " VARCHAR(255) ,"
                + KickContract.KickEntry.KICK_STATE + " VARCHAR(255) ,"
                + KickContract.KickEntry.KICK_URL + " VARCHAR(255) ,"
                + KickContract.KickEntry.KICK_TYPE + " VARCHAR (255));";

        sqLiteDatabase.execSQL(SQL_CREATE_KICK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KickContract.KickEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

}
