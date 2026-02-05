package com.xyz.booking.browse.service.impl;

import com.xyz.booking.browse.dto.BrowseShowResponse;
import com.xyz.booking.browse.repository.BrowseRepository;
import com.xyz.booking.browse.service.BrowseService;
import com.xyz.booking.common.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BrowseServiceImpl implements BrowseService {

    private final BrowseRepository browseRepository;

    public BrowseServiceImpl(BrowseRepository browseRepository) {
        this.browseRepository = browseRepository;
    }

    @Override
    public List<BrowseShowResponse> browse(
            Long movieId,
            String city,
            LocalDate date
    ) {
        if (movieId == null) {
            throw new BusinessException("movieId is required");
        }
        if (city == null || city.isBlank()) {
            throw new BusinessException("city is required");
        }
        if (date == null) {
            throw new BusinessException("date is required");
        }

        return browseRepository.findShows(
                movieId,
                city,
                Date.valueOf(date)
        );
    }
}