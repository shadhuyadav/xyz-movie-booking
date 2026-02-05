package com.xyz.booking.show.service.impl;

import com.xyz.booking.common.exception.BusinessException;
import com.xyz.booking.seat.model.SeatInventory;
import com.xyz.booking.seat.repository.SeatInventoryRepository;
import com.xyz.booking.show.dto.ShowRequest;
import com.xyz.booking.show.model.Show;
import com.xyz.booking.show.repository.ShowRepository;
import com.xyz.booking.show.service.ShowService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final SeatInventoryRepository seatInventoryRepository;

    public ShowServiceImpl(
            ShowRepository showRepository,
            SeatInventoryRepository seatInventoryRepository
    ) {
        this.showRepository = showRepository;
        this.seatInventoryRepository = seatInventoryRepository;
    }

    @Override
    @Transactional
    public Long createShow(ShowRequest request) {

        validate(request);


        showRepository
                .findByScreenIdAndShowDateAndStartTime(
                        request.screenId(),
                        request.showDate(),
                        request.startTime()
                )
                .ifPresent(show -> {
                    throw new BusinessException(
                            "Show already exists for this screen and time"
                    );
                });


        Show show = new Show(
                request.movieId(),
                request.screenId(),
                request.showDate(),
                request.startTime(),
                request.basePrice()
        );

        showRepository.save(show);


        List<SeatInventory> seats = generateSeatInventory(show.getId());

        seatInventoryRepository.saveAll(seats);

        return show.getId();
    }


    private List<SeatInventory> generateSeatInventory(Long showId) {

        return java.util.stream.IntStream.rangeClosed(1, 10)
                .boxed()
                .flatMap(row ->
                        java.util.stream.IntStream.rangeClosed(1, 10)
                                .mapToObj(col ->
                                        new SeatInventory(
                                                showId,
                                                (char) ('A' + row - 1) + String.valueOf(col)
                                        )
                                )
                )
                .toList();
    }

    private void validate(ShowRequest request) {

        if (request.movieId() == null)
            throw new BusinessException("MovieId is required");

        if (request.screenId() == null)
            throw new BusinessException("ScreenId is required");

        if (request.showDate() == null)
            throw new BusinessException("Show date is required");

        if (request.startTime() == null)
            throw new BusinessException("Show time is required");

        if (request.basePrice() <= 0)
            throw new BusinessException("Base price must be positive");
    }
}
