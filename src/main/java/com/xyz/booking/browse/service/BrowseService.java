package com.xyz.booking.browse.service;

import com.xyz.booking.browse.dto.BrowseShowResponse;

import java.time.LocalDate;
import java.util.List;

public interface BrowseService {


    List<BrowseShowResponse> browse(
            Long movieId,
            String city,
            LocalDate date
    );
}