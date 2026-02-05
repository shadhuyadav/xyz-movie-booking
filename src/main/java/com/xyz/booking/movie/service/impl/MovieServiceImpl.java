package com.xyz.booking.movie.service.impl;

import com.xyz.booking.common.exception.BusinessException;
import com.xyz.booking.movie.dto.MovieRequest;
import com.xyz.booking.movie.dto.MovieResponse;
import com.xyz.booking.movie.model.Movie;
import com.xyz.booking.movie.repository.MovieRepository;
import com.xyz.booking.movie.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieResponse createMovie(MovieRequest request) {

        if (request.title() == null || request.title().isBlank()) {
            throw new BusinessException("Movie title is required");
        }

        Movie movie =
                movieRepository.save(
                        new Movie(
                                request.title(),
                                request.language(),
                                request.genre()
                        )
                );

        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getLanguage(),
                movie.getGenre()
        );
    }

    @Override
    public List<MovieResponse> getAllMovies() {

        return movieRepository.findAll()
                .stream()
                .map(movie ->
                        new MovieResponse(
                                movie.getId(),
                                movie.getTitle(),
                                movie.getLanguage(),
                                movie.getGenre()
                        )
                )
                .toList();
    }
}
