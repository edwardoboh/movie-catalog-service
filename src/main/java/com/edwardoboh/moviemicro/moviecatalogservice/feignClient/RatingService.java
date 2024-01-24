package com.edwardoboh.moviemicro.moviecatalogservice.feignClient;

import com.edwardoboh.moviemicro.moviecatalogservice.model.UserMovieRatingEntity;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient("movie-rating-service")
public interface RatingService {
    @GetMapping("rating/user/{userId}")
    public ResponseEntity<UserMovieRatingEntity> getUserMovieRating (@PathVariable String userId);
}
