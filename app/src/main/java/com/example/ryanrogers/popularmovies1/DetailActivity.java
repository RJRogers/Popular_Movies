package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ryanrogers.popularmovies1.MovieContract.Movies.KEY_MOVIE_ORIGINAL_TITLE;


public class DetailActivity extends ActionBarActivity {

    public static final String URL_PATH = "http://image.tmdb.org/t/p/w185";
    public static final String TRAILER_URL_PATH = "http://img.youtube.com/vi/";
    public static final String LOG_TAG = "myLogs";
    public static final String TRAILER_URL_PATH_END = "/0.jpg";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    static List<String> movieReviewStrings = new ArrayList<>();
    static List<String> movieReviewAuthor = new ArrayList<>();


    static List<String> movieTrailers = new ArrayList<>();
    static List<String> movieTrailersYouTube = new ArrayList<>();

    static List<Trailer> trailers = new ArrayList<>();






    private static Call<Reviews> callTwo;
    private static List <MovieReviewDetail>  movieReviewDetails;
    private static MovieReview movieReview;
    private static Reviews reviews;
    private static Call<Videos> callThree;
    public static List<Videos.VideoResults> videoList;
    public static Videos videoResults;
    public static String videoUrlLink;
    public static String youtubeVideoLink;

    public static List<Movie> movieFavs = new ArrayList<>();



    static RecyclerView mNumbersList;
    static TrailerAdapter mAdapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }






    public static class DetailFragment extends Fragment {





        public void getVideos(int id){

            callThree = ApiManager.getService().getVideos(id, getContext().getString(R.string.THE_MOVIE_DB_API_TOKEN));
            callThree.enqueue(new Callback<Videos>() {
                @Override
                public void onResponse(Response<Videos> response) {
                    Log.d(LOG_TAG, "Got here");
                    if (!response.isSuccess()) {
                        Log.d(LOG_TAG, "No Success");
                    }



                    Log.d(LOG_TAG, "Got here");

                    videoResults = response.body();
                    videoList = videoResults.getResults();


                    for (int i = 0; i < videoList.size(); i++){
                        videoUrlLink = videoList.get(i).toString();
                        String videoUrlTwo = TRAILER_URL_PATH + videoUrlLink + TRAILER_URL_PATH_END;
                        movieTrailers.add(videoUrlTwo);
                        youtubeVideoLink = YOUTUBE_URL + videoUrlLink;
                        movieTrailersYouTube.add(youtubeVideoLink);

                        Trailer trailer = new Trailer(youtubeVideoLink, videoUrlTwo);
                        trailers.add(trailer);
                        Log.d(LOG_TAG, "This is the trailer image URL " + trailer.getTrailerImageUrl().toString());
                        Log.d(LOG_TAG, "This is the YouTube URL " + trailer.getTrailerYoutubeUrl().toString());

                    }




                }

                @Override
                public void onFailure( Throwable t) {
                    Log.e("getVideos throwable: ", t.getMessage());
                    t.printStackTrace();

                }
            });


        }



        public static boolean checkIfMovieExists(Context context, String movieName){

            Uri uri = MovieContract.Movies.CONTENT_URI;
            String[] mProjection = {KEY_MOVIE_ORIGINAL_TITLE};
            String mSelection = KEY_MOVIE_ORIGINAL_TITLE + "= ?";
            String[] mSelectionArgs = { movieName };



            Cursor cursor = context.getContentResolver().query(uri, mProjection, mSelection, mSelectionArgs,
                    null);

            if(cursor.getCount() == 1 ){

                return true;
            }else {

                return false;
            }


        }






            public static ArrayList<Movie> getAllMovies(Context context){

                ArrayList<Movie> movies = new ArrayList<>();

                Uri uri = MovieContract.Movies.CONTENT_URI;

                Cursor cursor = context.getContentResolver().query(uri, null, null, null,
                        null);




        try{
            if (cursor.moveToFirst()){
                do{
                    //create a movie object
                    Movie newMovie = new Movie();

                    //add new movie id
                    newMovie.id = cursor.getString(cursor.getColumnIndex(MovieContract.Movies.KEY_MOVIE_TEXT));

                    //add new movie poster path
                    newMovie.poster_path = cursor.getString(cursor.getColumnIndex(MovieContract.Movies.KEY_MOVIE_POSTER));

                    //add new movie release date
                    newMovie.release_date = cursor.getString(cursor.getColumnIndex(MovieContract.Movies.KEY_MOVIE_RELEASE_DATE));

                    //add new movie overview text
                    newMovie.overview = cursor.getString(cursor.getColumnIndex(MovieContract.Movies.KEY_MOVIE_OVERVIEW));

                    //add new movie average vote
                    newMovie.vote_average = cursor.getDouble(cursor.getColumnIndex(MovieContract.Movies.KEY_MOVIE_RATING));

                    newMovie.original_title = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_ORIGINAL_TITLE));


                    //add them to the array
                    movies.add(newMovie);



                }while (cursor.moveToNext());
            }
        }catch(Exception e){
            Log.d(LOG_TAG, "Error getting from database");
        }finally{
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return movies;

    }



        public void addMovie(Movie movie){


                ContentValues values = new ContentValues();

                //add using content provider

                //add poster path
               values.put(MovieContract.Movies.KEY_MOVIE_POSTER, movie.poster_path);

                // add text using movie id
               values.put(MovieContract.Movies.KEY_MOVIE_TEXT, movie.id);

                // add movie overview
               values.put(MovieContract.Movies.KEY_MOVIE_OVERVIEW, movie.overview);

                // add vote average
               values.put(MovieContract.Movies.KEY_MOVIE_RATING, movie.vote_average);

                // add release date
               values.put(MovieContract.Movies.KEY_MOVIE_RELEASE_DATE, movie.release_date);

//                //add original title
                values.put(KEY_MOVIE_ORIGINAL_TITLE, movie.original_title);


            Uri uri = getContext().getContentResolver().insert(MovieContract.Movies.CONTENT_URI,values);


        }



        public void getMovieReview(int id){

            callTwo = ApiManager.getService().getReviews(id, getContext().getString(R.string.THE_MOVIE_DB_API_TOKEN));

            callTwo.enqueue(new Callback<Reviews>() {
                                @Override
                                public void onResponse(Response<Reviews> response) {
                                    if (!response.isSuccess()) {
                                        Log.d(LOG_TAG, "No Success");

                                    }

                                    reviews = response.body();
                                    movieReview=reviews.getReviews();
                                    movieReviewDetails = movieReview.getResults();


                                    TextView reviewTextOne = (TextView) getActivity().findViewById(R.id.movieReview);
                                    TextView reviewTextTwo = (TextView) getActivity().findViewById(R.id.movieReviewOne);



                                    for (int i = 0; i < movieReviewDetails.size(); i++) {
                                        movieReviewStrings.add(movieReviewDetails.get(i).getContent());
                                        movieReviewAuthor.add(movieReviewDetails.get(i).getAuthor());

                                    }


                                    if(movieReviewStrings != null && movieReviewStrings.size() > 1 ) {
                                        reviewTextOne.setText("Review" + "\n" + movieReviewAuthor.get(0) + "\n" + movieReviewStrings.get(0) + "\n");
                                        reviewTextTwo.setText("Review" + "\n" + movieReviewAuthor.get(1) + "\n" + movieReviewStrings.get(1) + "\n");
                                    }

                                    else if(movieReviewStrings != null && movieReviewStrings.size() > 0 ){
                                        reviewTextOne.setText("Review" + "\n" + movieReviewAuthor.get(0) + "\n" + movieReviewStrings.get(0) + "\n");

                                    }

                                    else{
                                        reviewTextOne.setText("Review" + "\n" + "No reviews available");

                                    }


                                }

                                @Override
                                public void onFailure( Throwable t) {
                                    Log.e("getMovie throwable: ", t.getMessage());
                                    t.printStackTrace();

                                }
                            }
            );

        }

        //constructor

        public DetailFragment() {
        }


        public void getIntentOnFirstLoad(){


                Intent intent = getActivity().getIntent();
                final Movie movie = intent.getParcelableExtra("movie");
                final String movieId = movie.id;
                int movieReviewId = Integer.parseInt(movieId);
                getVideos(movieReviewId);




        }





        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if(trailers != null){
                trailers.clear();
            }

            getIntentOnFirstLoad();



            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            mNumbersList = (RecyclerView) rootView.findViewById(R.id.rv_numbers);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


            mNumbersList.setLayoutManager(layoutManager);

            mNumbersList.setHasFixedSize(true);

            mAdapter = new TrailerAdapter(getContext(), trailers);

            mNumbersList.setAdapter(mAdapter);


            Intent intent = getActivity().getIntent();

            if(intent != null && intent.hasExtra("movie")){

                final Movie movie = intent.getParcelableExtra("movie");
                final String movieId = movie.id;
                int movieReviewId = Integer.parseInt(movieId);
                final String movieName = movie.original_title;
                final String moviePoster = movie.poster_path;
                final String movieOverview = movie.overview;
                final Double movieRating = movie.vote_average;
                final String movieRelease = movie.release_date;

                System.out.println(movieReviewId);
                getMovieReview(movieReviewId);




                movieReviewStrings.clear();
                movieReviewAuthor.clear();

                ImageView posterDetail = (ImageView) rootView.findViewById(R.id.imageView);
                TextView textView = (TextView) rootView.findViewById(R.id.trailerHeading);
                textView.setText("Trailers");

                final String url = URL_PATH + moviePoster;

                Picasso
                        .with(getContext())
                        .load(url)
                        .fit()
                        .centerCrop()
                        .into(posterDetail);

                ((TextView)rootView.findViewById(R.id.title)).setText(movie.original_title);
                ((TextView)rootView.findViewById(R.id.year)).setText("Release Date \n" + movie.release_date);
                ((TextView)rootView.findViewById(R.id.rating)).setText("Rating \n" + Double.toString(movie.vote_average) + "\n");
                ((TextView)rootView.findViewById(R.id.overview)).setText("Synopsis \n" + movie.overview + "\n");

                FloatingActionButton myFab = (FloatingActionButton)rootView.findViewById(R.id.fab);
                myFab.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {



                        if(checkIfMovieExists(getContext(), movieName)){

                            Toast.makeText(getActivity(), movieName + " is already contained in your favorites", Toast.LENGTH_LONG).show();

                        }

                        else{


                            Toast.makeText(getActivity(), movieName + " - Added to favorites", Toast.LENGTH_LONG).show();

                            //create new movie object
                            Movie newMovie = new Movie();

                            //add poster path url to object
                            newMovie.poster_path = moviePoster;

                            //add id to object
                            newMovie.id = movieId;

                            //add synopsis (overview) to object
                            newMovie.overview = movieOverview;


                            //add release date to object
                            newMovie.release_date = movieRelease;


                            //add rating to object
                            newMovie.vote_average = movieRating;


                            newMovie.original_title = movieName;


                            //print for testing
                            System.out.println(movieId);
//                            System.out.println(url);
                            System.out.println(newMovie.poster_path);


                            //add the movie through content provider
                            addMovie(newMovie);

                        }




                    }
                });





            }





            return rootView;
        }



    }


}