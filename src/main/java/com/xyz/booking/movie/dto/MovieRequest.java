package com.xyz.booking.movie.dto;

public record MovieRequest(
        String title,
        String language,
        String genre
) {}
