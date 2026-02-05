package com.xyz.booking.movie.controller;

import com.xyz.booking.movie.dto.MovieRequest;
import com.xyz.booking.movie.dto.MovieResponse;
import com.xyz.booking.movie.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @RequestBody MovieRequest request
    ) {
        return ResponseEntity.ok(
                movieService.createMovie(request)
        );
    }


    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(
                movieService.getAllMovies()
        );
    }
}
