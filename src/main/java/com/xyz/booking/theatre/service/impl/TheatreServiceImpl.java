package com.xyz.booking.theatre.service.impl;

import com.xyz.booking.common.exception.BusinessException;
import com.xyz.booking.common.exception.ResourceNotFoundException;
import com.xyz.booking.theatre.dto.*;
import com.xyz.booking.theatre.model.Screen;
import com.xyz.booking.theatre.model.Theatre;
import com.xyz.booking.theatre.repository.ScreenRepository;
import com.xyz.booking.theatre.repository.TheatreRepository;
import com.xyz.booking.theatre.service.TheatreService;
import org.springframework.stereotype.Service;

@Service
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    public TheatreServiceImpl(
            TheatreRepository theatreRepository,
            ScreenRepository screenRepository
    ) {
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
    }

    @Override
    public TheatreResponse createTheatre(TheatreRequest request) {

        if (request.name() == null || request.name().isBlank()) {
            throw new BusinessException("Theatre name is required");
        }
        if (request.city() == null || request.city().isBlank()) {
            throw new BusinessException("City is required");
        }

        Theatre theatre =
                theatreRepository.save(
                        new Theatre(request.name(), request.city())
                );

        return new TheatreResponse(
                theatre.getId(),
                theatre.getName(),
                theatre.getCity()
        );
    }

    @Override
    public ScreenResponse createScreen(ScreenRequest request) {

        Theatre theatre = theatreRepository
                .findById(request.theatreId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found")
                );

        Screen screen =
                screenRepository.save(
                        new Screen(theatre.getId(), request.name())
                );

        return new ScreenResponse(
                screen.getId(),
                screen.getTheatreId(),
                screen.getName()
        );
    }
}
