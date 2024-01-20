package com.edwardoboh.moviemicro.moviecatalogservice.controllers;

import com.edwardoboh.moviemicro.moviecatalogservice.model.MovieCatalogEntity;
import com.edwardoboh.moviemicro.moviecatalogservice.model.MovieDetailEntity;
import com.edwardoboh.moviemicro.moviecatalogservice.model.UserMovieRatingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webclientBuilder;

    /*
    @GetMapping("/{userId}")
    public ResponseEntity<List<MovieCatalogEntity>> fetchCatalog(@PathVariable("userId") String userId) {
        List<MovieRatingEntity> movieRatingList;
        try{
            ResponseEntity<MovieRatingEntity[]> responseEntity =
                    restTemplate.exchange(
                            "http://localhost:3003/rating/" + userId,
                            HttpMethod.GET,
                            null,
                            MovieRatingEntity[].class
                    );
            if (!responseEntity.getStatusCode().is2xxSuccessful())
                throw new Exception("Fetching Rating information from Rating Service has failed");

            movieRatingList = Arrays.stream(responseEntity.getBody()).toList();

            List<MovieCatalogEntity> catalogEntities = movieRatingList.stream().map(rating -> {
//                MovieDetailEntity movieDetail = restTemplate.getForObject(
//                        "http://localhost:3002/detail/"+rating.getMovieId(),
//                        MovieDetailEntity.class
//                );
                MovieDetailEntity movieDetail = webclientBuilder.build()
                        .get()
                        .uri("http://localhost:3002/detail/"+rating.getMovieId())
                        .retrieve()
                        .bodyToMono(MovieDetailEntity.class)
                        .block();

                return new MovieCatalogEntity(
                        movieDetail.getName(),
                        movieDetail.getDescription(),
                        rating.getRating()
                );
            }).collect(Collectors.toList());

            return ResponseEntity.ok(catalogEntities);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    */

    @GetMapping("/{userId}")
    public ResponseEntity<List<MovieCatalogEntity>> getMovieUserCatalog(@PathVariable String userId) {
        try{
            UserMovieRatingEntity userMovieRating = webclientBuilder.build()
                    .get()
                    .uri("http://localhost:3003/rating/user/" + userId)
                    .retrieve()
                    .bodyToMono(UserMovieRatingEntity.class)
                    .block();

            List<MovieCatalogEntity> movieCatalog = new ArrayList<>();
            if(userMovieRating.ratings() != null){
                movieCatalog = userMovieRating.ratings().stream().map(
                        rating -> {
                            MovieDetailEntity movieDetail = webclientBuilder.build()
                                    .get()
                                    .uri("http://localhost:3002/detail/"+rating.getMovieId())
                                    .retrieve()
                                    .bodyToMono(MovieDetailEntity.class)
                                    .block();
                            if(movieDetail == null) return new MovieCatalogEntity();

                            return new MovieCatalogEntity(
                                    movieDetail.getName(),
                                    movieDetail.getDescription(),
                                    rating.getRating()
                            );
                        }
                ).toList();
            }
            return ResponseEntity.ok(movieCatalog);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
