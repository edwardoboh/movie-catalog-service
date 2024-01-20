package com.edwardoboh.moviemicro.moviecatalogservice.model;

import java.util.List;

public record UserMovieRatingEntity(List<MovieRatingEntity> ratings) {
}
/*
public class UserMovieRatingEntity {
    private List<MovieRatingEntity> ratings;

    public UserMovieRatingEntity() {}

    public UserMovieRatingEntity(List<MovieRatingEntity> ratings) {
        this.ratings = ratings;
    }

    public List<MovieRatingEntity> getRatings() {
        return this.ratings;
    }

    public void setRatings(List<MovieRatingEntity> ratings){
        this.ratings = ratings;
    }
}

*/