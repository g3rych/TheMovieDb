package com.example.error.themoviedb.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import com.example.error.themoviedb.data.DBContract.MoviesEntry;
import com.example.error.themoviedb.data.DBContract.TrailersEntry;



public class MoviesProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.example.error.themoviedb.provider";

    public static final String URL = "content://" + "com.example.error.themoviedb.provider";
    public static final Uri CONTENT_URI = Uri.parse(URL);


    public static final Uri MOVIE_PATH = Uri.withAppendedPath(CONTENT_URI,MoviesEntry.TABLE_NAME);
    public static final Uri TRAILER_PATH = Uri.withAppendedPath(CONTENT_URI,TrailersEntry.TABLE_NAME);



    private static final int ALLMOVIES = 100;
    private static final int SINGLEMOVIE = 101;

    private static final int INSERTMOVIE = 150;

    private static final int TRAILERS = 200;
    private static final int INSERTTRAILER = 250;

    private static UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(CONTENT_AUTHORITY,MoviesEntry.TABLE_NAME,ALLMOVIES);
        sUriMatcher.addURI(CONTENT_AUTHORITY,MoviesEntry.TABLE_NAME + "/#",SINGLEMOVIE);

        sUriMatcher.addURI(CONTENT_AUTHORITY,TrailersEntry.TABLE_NAME,TRAILERS);
    }


    private SQLiteDatabase db;

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        switch (sUriMatcher.match(uri)) {

            case ALLMOVIES :
                id = db.insert(MoviesEntry.TABLE_NAME,null,values);
                return ContentUris.withAppendedId(MOVIE_PATH,id);

            case TRAILERS :

                id = db.insert(TrailersEntry.TABLE_NAME,null,values);
                return ContentUris.withAppendedId(TRAILER_PATH,id);

            default:throw new SQLiteException("failed to insert into " + uri.toString());
        }

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
        switch (sUriMatcher.match(uri)) {
            case ALLMOVIES :
                return db.query(MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null);
            case SINGLEMOVIE :
                return db.query(MoviesEntry.TABLE_NAME,projection,
                        selection,selectionArgs,
                        null,null,null);
            case TRAILERS :
                return db.query(TrailersEntry.TABLE_NAME,projection,
                        selection,selectionArgs,
                        null,null,null);
            default: throw new UnsupportedOperationException("Unknow uri " + uri.toString());
        }
    }

    @Override
    public boolean onCreate() {
        DBHelper helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        return (db != null);
    }
}
