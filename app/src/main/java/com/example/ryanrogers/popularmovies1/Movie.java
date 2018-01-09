package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



public class Movie implements Parcelable {

//this returns all results from the JSON results
    public ArrayList<Movie> getItems() {
        return results;
    }

    public void setItems(ArrayList<Movie> items) {
        this.results= items;
    }

    private ArrayList<Movie> results;


    String id;
    String original_title;
    String overview;
    String poster_path;
    String release_date;
    double vote_average;


    public Movie(){


    };


  public Movie(Parcel in){

      this.id = in.readString();
      this.original_title = in.readString();
      this.poster_path = in.readString();
      this.overview = in.readString();
      this.release_date = in.readString();
      this.vote_average = in.readDouble();

  }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.id);
        dest.writeString(this.original_title);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeDouble(this.vote_average);

    }


    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>(){

        public Movie createFromParcel(Parcel in){
            return new Movie(in);
        }

        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };



    @Override
    public String toString()
    {
        return " id: " + id + " original title: " + original_title + " poster path: " + poster_path + " overview: " + overview + " release date: " + release_date + " vote average: " + vote_average;
    }



}
