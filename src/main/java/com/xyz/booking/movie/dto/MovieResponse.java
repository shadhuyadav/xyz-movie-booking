package com.xyz.booking.movie.dto;


public record MovieResponse(
        Long id,
        String title,
        String language,
        String genre
) {}

