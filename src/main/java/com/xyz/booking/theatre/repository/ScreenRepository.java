package com.xyz.booking.theatre.repository;

import com.xyz.booking.theatre.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
}
