package com.xyz.booking.seat.repository;

import com.xyz.booking.seat.model.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {

    Optional<SeatInventory> findByShowIdAndSeatId(Long showId, String seatId);

    List<SeatInventory> findByShowIdAndSeatIdIn(Long showId, List<String> seatIds);
}
