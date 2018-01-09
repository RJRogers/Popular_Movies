package com.example.ryanrogers.popularmovies1;

/**
 * Created by ryanrogers on 16/09/2017.
 */

public class Trailer {



    String trailerImageUrl;
    String trailerYoutubeUrl;


    public Trailer (String youtubeLink, String imageLink){

        trailerYoutubeUrl = youtubeLink;
        trailerImageUrl = imageLink;
    }


    public String getTrailerImageUrl(){
        return trailerImageUrl;
    }

    public void setTrailerImageUrl(String trailerImageUrl){
        this.trailerImageUrl = trailerImageUrl;
    }

    public String getTrailerYoutubeUrl(){
        return trailerYoutubeUrl;

    }

    public void setTrailerYoutubeUrl(String trailerYoutubeUrl){
        this.trailerYoutubeUrl = trailerYoutubeUrl;

    }


}
