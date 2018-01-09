package com.example.ryanrogers.popularmovies1;

//Code may contain references from Udacity Android Nanodegree
// tutorial exercises see https://classroom.udacity.com/nanodegrees/nd801

import java.util.List;


public class Videos {


    private String id;
    private List<VideoResults> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<VideoResults> getResults() {
        return results;
    }

    public void setResults(List<VideoResults> results) {
        this.results = results;
    }


    public class VideoResults {


        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString(){

            return key;
        }

    }



}
