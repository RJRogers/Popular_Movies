package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class ApiManager {


    public interface MovieDbApi {
        @GET("3/discover/movie")
        Call<Movie> getQuestions(
                @Query("sort_by") String sortKey,
                @Query("api_key") String apiKey
        );

        @GET("/3/movie/{id}?&append_to_response=reviews,videos")
        Call<Reviews> getReviews(
                @Path("id") int id,
                @Query("api_key") String apiKey
        );

        @GET("/3/movie/{id}/videos")
        Call<Videos> getVideos(
                @Path("id") int id,
                @Query("api_key") String apiKey
        );

    }

    private static final String API_BASE_URL_MOVIES = "http://api.themoviedb.org/";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(API_BASE_URL_MOVIES)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final MovieDbApi MOVIE_DB_API = RETROFIT.create(MovieDbApi.class);

    public static MovieDbApi getService(){
        return MOVIE_DB_API;
    }


}
