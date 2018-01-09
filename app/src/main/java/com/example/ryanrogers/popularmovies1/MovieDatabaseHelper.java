package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.ryanrogers.popularmovies1.MovieContract.Movies.MOVIE_TABLE_NAME;


public class MovieDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 3;

    //constructor

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_MOVIES_TABLE = "CREATE TABLE " + MOVIE_TABLE_NAME +
                "(" +
                MovieContract.Movies.KEY_MOVIE_ID + " INTEGER PRIMARY KEY UNIQUE," +
                MovieContract.Movies.KEY_MOVIE_TEXT + " TEXT, " +
                MovieContract.Movies.KEY_MOVIE_POSTER + " TEXT, " +
                MovieContract.Movies.KEY_MOVIE_OVERVIEW + " TEXT, " +
                MovieContract.Movies.KEY_MOVIE_RATING + " DOUBLE, "  +
                MovieContract.Movies.KEY_MOVIE_RELEASE_DATE + " TEXT, " +
                MovieContract.Movies.KEY_MOVIE_ORIGINAL_TITLE + " TEXT " +
                ")";

        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
            onCreate(db);

    }


}
