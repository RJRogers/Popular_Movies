package com.example.ryanrogers.popularmovies1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.ryanrogers.popularmovies1.MovieContract.Movies.MOVIE_TABLE_NAME;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

public class MovieContentProvider extends ContentProvider {


    public static final int MOVIE = 100;
    public static final int MOVIE_WITH_ID = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDatabaseHelper mMovieDatabaseHelper;

    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //create to match to movies table
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.MOVIE_PATH, MOVIE);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.MOVIE_PATH + "/#", MOVIE_WITH_ID);

        return uriMatcher;


    }



    @Override
    public boolean onCreate() {

        Context context = getContext();
        mMovieDatabaseHelper = new MovieDatabaseHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mMovieDatabaseHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch(match) {
            case MOVIE:
                retCursor = db.query(MOVIE_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Uknown uri: " + uri);




        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    
    @Override
    public Uri insert( Uri uri, ContentValues values) {

        final SQLiteDatabase db = mMovieDatabaseHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        Uri returnUri = null;

        switch(match) {
            case MOVIE:
                long id = db.insert(MOVIE_TABLE_NAME, null, values);
                if ( id > 0 ) {

                    returnUri = ContentUris.withAppendedId(MovieContract.BASE_CONTENT_URI, id);

                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }




    //do not need to implement these as requirements are only to insert, not delete or update

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
