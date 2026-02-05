package com.xyz.booking.movie.service;

import com.xyz.booking.movie.dto.MovieRequest;
import com.xyz.booking.movie.dto.MovieResponse;

import java.util.List;

public interface MovieService {

    MovieResponse createMovie(MovieRequest request);

    List<MovieResponse> getAllMovies();
}
