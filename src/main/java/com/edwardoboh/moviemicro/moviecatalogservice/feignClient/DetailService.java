package com.edwardoboh.moviemicro.moviecatalogservice.feignClient;

import com.edwardoboh.moviemicro.moviecatalogservice.model.MovieDetailEntity;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//@FeignClient("movie-details-service")
public interface DetailService {
    @GetMapping("detail/{movieId}")
    public ResponseEntity<Optional<MovieDetailEntity>> getMovieDetails(@PathVariable String movieId);
}
