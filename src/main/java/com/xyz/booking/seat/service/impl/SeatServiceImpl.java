package com.xyz.booking.seat.service.impl;

import com.xyz.booking.common.exception.BusinessException;
import com.xyz.booking.seat.dto.SeatLockRequest;
import com.xyz.booking.seat.model.SeatInventory;
import com.xyz.booking.seat.model.SeatStatus;
import com.xyz.booking.seat.repository.SeatInventoryRepository;
import com.xyz.booking.seat.service.SeatService;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private static final Duration LOCK_TTL = Duration.ofMinutes(7);

    private final SeatInventoryRepository repository;
    private final StringRedisTemplate redisTemplate;

    public SeatServiceImpl(SeatInventoryRepository repository,
                           StringRedisTemplate redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void lockSeats(SeatLockRequest request) {

        validate(request);

        for (String seatId : request.seatIds()) {
            String key = redisKey(request.showId(), seatId);

            Boolean locked = redisTemplate.opsForValue()
                    .setIfAbsent(key, request.userId().toString(), LOCK_TTL);

            if (Boolean.FALSE.equals(locked)) {
                throw new BusinessException("Seat already locked: " + seatId);
            }
        }
    }

    @Override
    @Transactional
    public void confirmSeats(Long showId, Iterable<String> seatIds) {

        List<SeatInventory> seats =
                repository.findByShowIdAndSeatIdIn(showId, (List<String>) seatIds);

        for (SeatInventory seat : seats) {
            if (seat.getStatus() == SeatStatus.BOOKED) {
                throw new BusinessException("Seat already booked");
            }
            seat.markBooked();
        }

        repository.saveAll(seats);

        seatIds.forEach(seatId ->
                redisTemplate.delete(redisKey(showId, seatId)));
    }

    @Override
    public void releaseSeats(Long showId, Iterable<String> seatIds) {
        seatIds.forEach(seatId ->
                redisTemplate.delete(redisKey(showId, seatId)));
    }

    private void validate(SeatLockRequest request) {
        if (request.showId() == null)
            throw new BusinessException("ShowId required");

        if (request.userId() == null)
            throw new BusinessException("UserId required");

        if (request.seatIds() == null || request.seatIds().isEmpty())
            throw new BusinessException("SeatIds required");
    }

    private String redisKey(Long showId, String seatId) {
        return "lock:show:" + showId + ":seat:" + seatId;
    }
}
