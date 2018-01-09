package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;
    public static final String MOVIE_DB_URL_PATH = "http://image.tmdb.org/t/p/w185";



    public MovieAdapter(Context context, List<Movie> movies){
        super(context, R.layout.list_image_item,movies);

        this.movies = movies;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        if(convertView == null){
            //inflate layout
         convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_image_item, parent, false);
        }

        ImageView newImageView = (ImageView)convertView.findViewById(R.id.imageView);

        String urlPath = MOVIE_DB_URL_PATH + movies.get(position).poster_path;


        //Use Picasso to load image
        Picasso
                .with(getContext())
                .load(urlPath)
                .fit()
                .centerCrop()
                .into(newImageView);

        return convertView;




    }
}
