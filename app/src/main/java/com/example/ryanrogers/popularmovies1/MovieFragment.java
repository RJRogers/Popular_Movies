package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ryanrogers.popularmovies1.DetailActivity.DetailFragment.getAllMovies;
import static com.example.ryanrogers.popularmovies1.DetailActivity.mAdapter;


public class MovieFragment extends Fragment {


    private static final String API_KEY = "d32ff9db6f9b7de8af6ecd4145ebb47e";
    public static final String LOG_TAG = "myLogs";
    private ArrayList<Movie> itemsTwo;
    private Movie movie;
    private MovieAdapter movieAdapter;
    ArrayList<Movie> newMovies;
    ArrayList<Movie> movieFavs;




    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("myArrayListTwo", newMovies);
    }


    public void getMovieFavs(){
        movieFavs = getAllMovies(getContext());
        movieAdapter.clear();
        movieAdapter.addAll(movieFavs);
    }




    public void getMovie(String sortBy){
        Call<Movie> call;
        call = ApiManager.getService().getQuestions(sortBy, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Response<Movie> response) {
                Log.d(LOG_TAG, "Reached here");
                if (!response.isSuccess()) {
                    Log.d(LOG_TAG, "No Success");
                }

                Log.d(LOG_TAG, "Reached here");
                movie = response.body();

                itemsTwo = movie.getItems();
                movieAdapter.clear();
                movieAdapter.addAll(itemsTwo);

                Log.d(LOG_TAG, "Response is" + " " + response.code());

            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("getMovie throwable: ", t.getMessage());

            }
        });


    }



    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getMovie("popularity.desc");
        setRetainInstance(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            getMovie("popularity.desc");
            return true;
        }
        if(id == R.id.action_highest_rated){
            getMovie("vote_count.desc");

            return true;
        }

        if(id == R.id.favorite){
            getMovieFavs();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GridView gridView;
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_main, container, false);


        if(savedInstanceState == null || !savedInstanceState.containsKey("myArrayListTwo")){
            newMovies = new ArrayList<>();
        }

        else{
            newMovies = savedInstanceState.getParcelableArrayList("myArrayListTwo");
        }


        //Create GridView
        gridView = (GridView) rootView.findViewById(R.id.grid_view);

        //Create Adapter
        movieAdapter = new MovieAdapter(getActivity(), newMovies);

        //Set Adapter to GridView
        gridView.setAdapter(movieAdapter);

        //set click listener for items
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = movieAdapter.getItem(i);

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("movie", movie);
                startActivity(intent);
            }
        });



        return rootView;



    }



}