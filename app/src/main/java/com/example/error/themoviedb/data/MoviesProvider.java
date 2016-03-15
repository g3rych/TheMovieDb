package com.example.error.themoviedb.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import com.example.error.themoviedb.data.DBContract.MoviesEntry;
import com.example.error.themoviedb.data.DBContract.TrailersEntry;



public class MoviesProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.example.error.themoviedb.provider";

    public static final String URL = "content://" + "com.example.error.themoviedb.provider";
    public static final Uri CONTENT_URI = Uri.parse(URL);


    public static final Uri MOVIE_PATH = Uri.withAppendedPath(CONTENT_URI, MoviesEntry.TABLE_NAME);
    public static final Uri TRAILER_PATH = Uri.withAppendedPath(CONTENT_URI, TrailersEntry.TABLE_NAME);


    private static final int ALLMOVIES = 100;
    private static final int SINGLEMOVIE = 101;


    private static final int TRAILERS = 200;


    private static UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(CONTENT_AUTHORITY, MoviesEntry.TABLE_NAME, ALLMOVIES);
        sUriMatcher.addURI(CONTENT_AUTHORITY, MoviesEntry.TABLE_NAME + "/#", SINGLEMOVIE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, TrailersEntry.TABLE_NAME, TRAILERS);
    }


    private SQLiteDatabase db;

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        switch (sUriMatcher.match(uri)) {
            case ALLMOVIES :
                count = db.delete(MoviesEntry.TABLE_NAME,selection,selectionArgs);
                break;
                default: count = 0;
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        Uri retUri;
            switch (sUriMatcher.match(uri)) {
                case ALLMOVIES:
                    id = db.insertWithOnConflict(MoviesEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    retUri = ContentUris.withAppendedId(MOVIE_PATH, id);
                    break;
                case TRAILERS:
                    id = db.insertWithOnConflict(TrailersEntry.TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
                    retUri = ContentUris.withAppendedId(TRAILER_PATH, id);
                    getContext().getContentResolver().notifyChange(MOVIE_PATH, null);
                    break;
                default:
                    throw new SQLiteException("failed to insert into " + uri.toString());
            }
        getContext().getContentResolver().notifyChange(retUri, null);
        return retUri;
    }



    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ALLMOVIES : return ContentResolver.CURSOR_DIR_BASE_TYPE+"vnd.error.provider." + MoviesEntry.TABLE_NAME;
            case SINGLEMOVIE : return ContentResolver.CURSOR_ITEM_BASE_TYPE+"vnd.error.provider." + MoviesEntry.TABLE_NAME;
            case TRAILERS : return ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.error.provider" + TrailersEntry.TABLE_NAME;
            default: throw  new IllegalArgumentException("unknown uri" + uri.toString());
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case ALLMOVIES:
                retCursor = db.query(MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
                break;
            case SINGLEMOVIE :
                String _id = uri.getLastPathSegment();
                final String rawSQL =
                        "SELECT * "
                        +" FROM " + MoviesEntry.TABLE_NAME
                        +" LEFT JOIN " +TrailersEntry.TABLE_NAME
                        +" ON " + MoviesEntry.TABLE_NAME+"."+MoviesEntry.MOVIE_id+"="
                        +TrailersEntry.TABLE_NAME+"."+TrailersEntry.MOVIE_id
                        +" WHERE " + MoviesEntry.TABLE_NAME + "._id=" + _id +";";
                retCursor = db.rawQuery(rawSQL,null);
                break;
                case TRAILERS :
                retCursor = db.query(TrailersEntry.TABLE_NAME,projection,
                        selection,selectionArgs,
                        null,null,null);
                break;
            default: throw new UnsupportedOperationException("Unknow uri " + uri.toString());
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;

    }

    @Override
    public boolean onCreate() {
        DBHelper helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        return (db != null);
    }
}
