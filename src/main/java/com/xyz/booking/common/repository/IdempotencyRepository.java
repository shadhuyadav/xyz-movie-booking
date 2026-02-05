package com.xyz.booking.common.repository;

import com.xyz.booking.common.model.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyRepository
        extends JpaRepository<IdempotencyKey, Long> {

    Optional<IdempotencyKey> findByIdemKeyAndApiName(
            String idemKey,
            String apiName
    );
}
