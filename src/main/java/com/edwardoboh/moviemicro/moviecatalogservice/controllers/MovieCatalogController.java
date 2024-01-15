package com.edwardoboh.moviemicro.moviecatalogservice.controllers;

import com.edwardoboh.moviemicro.moviecatalogservice.domain.MovieCatalogEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class MovieCatalogController {

    @GetMapping("/{userId}")
    public ResponseEntity<List<MovieCatalogEntity>> fetchCatalog(@PathVariable("userId") String userId) {
        List<MovieCatalogEntity> movies = Collections.singletonList(new MovieCatalogEntity(
                "Fast and Furious",
                "The fast and furious movie sequel",
                7
        ));
        return ResponseEntity.ok(movies);
    }
}
