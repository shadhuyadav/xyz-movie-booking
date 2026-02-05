package com.xyz.booking.show.controller;

import com.xyz.booking.show.dto.ShowRequest;
import com.xyz.booking.show.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<String> createShow(
            @RequestBody ShowRequest request
    ) {
        Long showId = showService.createShow(request);
        return ResponseEntity.ok("Show created with ID: " + showId);
    }
}
