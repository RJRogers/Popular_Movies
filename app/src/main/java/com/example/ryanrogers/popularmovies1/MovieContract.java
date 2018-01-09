package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import android.net.Uri;
import android.provider.BaseColumns;



public class MovieContract {



    public static final String AUTHORITY = "com.example.ryanrogers.popularmovies1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final String MOVIE_PATH = "movie";



    public static class Movies implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(MOVIE_PATH).build();


        // Table Name
        public static final String MOVIE_TABLE_NAME = "movies";


        //Movie Columns
        public static final String KEY_MOVIE_ID = "id";
        public static final String KEY_MOVIE_TEXT = "movieId";
        public static final String KEY_MOVIE_POSTER = "poster";
        public static final String KEY_MOVIE_RELEASE_DATE = "release";
        public static final String KEY_MOVIE_RATING = "rating";
        public static final String KEY_MOVIE_OVERVIEW = "overview";
        public static final String KEY_MOVIE_ORIGINAL_TITLE = "originalTitle";


    }






}
