package com.edwardoboh.moviemicro.moviecatalogservice.controllers;

import com.edwardoboh.moviemicro.moviecatalogservice.model.MovieCatalogEntity;
import com.edwardoboh.moviemicro.moviecatalogservice.model.MovieDetailEntity;
import com.edwardoboh.moviemicro.moviecatalogservice.model.MovieRatingEntity;
import com.edwardoboh.moviemicro.moviecatalogservice.model.UserMovieRatingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    WebClient.Builder webclientBuilder;

//    @Autowired
//    DetailService detailService;
//
//    @Autowired
//    RatingService ratingService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<MovieCatalogEntity>> fetchCatalog(@PathVariable("userId") String userId) {
        List<MovieRatingEntity> movieRatingList;
//            ResponseEntity<MovieRatingEntity[]> responseEntity =
//                    restTemplate.exchange(
//                            "http://localhost:3003/rating/user/" + userId,
//                            HttpMethod.GET,
//                            null,
//                            MovieRatingEntity[].class
//                    );
//            if (!userMovieRating .getStatusCode().is2xxSuccessful())
//                throw new Exception("Fetching Rating information from Rating Service has failed");

            UserMovieRatingEntity userMovieRating = restTemplate
                    .getForObject("http://movie-rating-service/rating/user/" + userId, UserMovieRatingEntity.class);
//        UserMovieRatingEntity userMovieRating = ratingService.getUserMovieRating(userId).getBody();
        movieRatingList = userMovieRating.ratings();

        List<MovieCatalogEntity> catalogEntities = movieRatingList.stream().map(rating -> {
                MovieDetailEntity movieDetail = restTemplate.getForObject(
                        "http://movie-details-service/detail/"+rating.getMovieId(),
                        MovieDetailEntity.class
                );
//            MovieDetailEntity movieDetail = detailService.getMovieDetails(rating.getMovieId()).getBody().get();
            return new MovieCatalogEntity(
                    movieDetail.getName(),
                    movieDetail.getDescription(),
                    rating.getRating()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(catalogEntities);
    }

//    USING WEB CLIENT FOR API CALLS TO OTHER SERVICES
    /*
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

     */
}
