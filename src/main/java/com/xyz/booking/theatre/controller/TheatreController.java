package com.xyz.booking.theatre.controller;

import com.xyz.booking.theatre.dto.*;
import com.xyz.booking.theatre.service.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @PostMapping
    public ResponseEntity<TheatreResponse> createTheatre(
            @RequestBody TheatreRequest request
    ) {
        return ResponseEntity.ok(
                theatreService.createTheatre(request)
        );
    }

    @PostMapping("/screens")
    public ResponseEntity<ScreenResponse> createScreen(
            @RequestBody ScreenRequest request
    ) {
        return ResponseEntity.ok(
                theatreService.createScreen(request)
        );
    }
}
