package com.xyz.booking.browse.controller;

import com.xyz.booking.browse.dto.BrowseShowResponse;
import com.xyz.booking.browse.service.BrowseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/browse")
public class BrowseController {

    private final BrowseService browseService;

    public BrowseController(BrowseService browseService) {
        this.browseService = browseService;
    }

    @GetMapping("/shows")
    public ResponseEntity<List<BrowseShowResponse>> browseShows(
            @RequestParam Long movieId,
            @RequestParam String city,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return ResponseEntity.ok(
                browseService.browse(movieId, city, date)
        );
    }
}
