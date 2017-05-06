package com.webianks.test.bestkick.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class KickProvider extends ContentProvider {

    static final int BEST_KICK_PROJECT = 100;
    static final int BEST_KICK_PROJECT_WITH_SERIAL = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final String kick_project_selection_with_serial =
            KickContract.KickEntry.TABLE_NAME +
                    "." + KickContract.KickEntry.KICK_SL_NUMBER + " = ? ";

    private KickDbHelper kickDbHelper;


    @Override
    public boolean onCreate() {
        kickDbHelper = new KickDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {

            case BEST_KICK_PROJECT:
                retCursor = kickDbHelper.getReadableDatabase().query(
                        KickContract.KickEntry.TABLE_NAME,
                        projection, selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case BEST_KICK_PROJECT_WITH_SERIAL:
                retCursor = getProjectDetailBySerialNumber(uri, projection, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    private Cursor getProjectDetailBySerialNumber(Uri uri, String[] projection, String sortOrder) {

        String serial = KickContract.KickEntry.getDetailById(uri);

        String selection = kick_project_selection_with_serial;
        String[] selectionArgs = new String[]{serial};

        return kickDbHelper.getReadableDatabase().query(
                KickContract.KickEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case BEST_KICK_PROJECT:
                return KickContract.KickEntry.CONTENT_TYPE;

            case BEST_KICK_PROJECT_WITH_SERIAL:
                return KickContract.KickEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = kickDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (selection == null) selection = "1";

        switch (match) {
            case BEST_KICK_PROJECT:
                rowsDeleted = db.delete(KickContract.KickEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri = " + uri);
        }

        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, ContentValues[] values) {

        final SQLiteDatabase db = kickDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BEST_KICK_PROJECT:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(KickContract.KickEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            default:
                return super.bulkInsert(uri, values);
        }
    }


    static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = KickContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, KickContract.BEST_KICK_PATH, BEST_KICK_PROJECT);
        uriMatcher.addURI(authority, KickContract.BEST_KICK_PATH + "/*", BEST_KICK_PROJECT_WITH_SERIAL);

        return uriMatcher;
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        kickDbHelper.close();
        super.shutdown();
    }

}
