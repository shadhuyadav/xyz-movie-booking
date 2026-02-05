package com.xyz.booking.theatre.service;

import com.xyz.booking.theatre.dto.*;

public interface TheatreService {

    TheatreResponse createTheatre(TheatreRequest request);

    ScreenResponse createScreen(ScreenRequest request);
}
