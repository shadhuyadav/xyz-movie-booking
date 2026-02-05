package com.xyz.booking.theatre.repository;

import com.xyz.booking.theatre.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {
}
