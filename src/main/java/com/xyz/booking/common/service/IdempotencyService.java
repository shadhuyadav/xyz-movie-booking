package com.xyz.booking.common.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.booking.common.model.IdempotencyKey;
import com.xyz.booking.common.repository.IdempotencyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdempotencyService {

    private final IdempotencyRepository repository;
    private final ObjectMapper objectMapper;

    public IdempotencyService(
            IdempotencyRepository repository,
            ObjectMapper objectMapper
    ) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public <T> Optional<T> getResponse(
            String key,
            String api,
            Class<T> clazz
    ) {
        return repository
                .findByIdemKeyAndApiName(key, api)
                .map(entity -> {
                    try {
                        return objectMapper.treeToValue(
                                entity.getResponse(),
                                clazz
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public void saveResponse(
            String key,
            String api,
            Object response
    ) {
        try {
            JsonNode jsonNode = objectMapper.valueToTree(response);
            repository.save(
                    new IdempotencyKey(key, api, jsonNode)
            );
        } catch (Exception ignored) {

        }
    }
}