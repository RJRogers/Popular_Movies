package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Reviews {
    @SerializedName("reviews")
    @Expose
    private MovieReview reviews;

    public Reviews() {
    }


    public MovieReview getReviews() {
        return reviews;
    }

    public void setReviews(MovieReview reviews) {
        this.reviews = reviews;
    }
}