package com.xyz.booking.common.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
        name = "idempotency_keys",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idem_key", "api_name"})
)
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idem_key", nullable = false)
    private String idemKey;

    @Column(name = "api_name", nullable = false)
    private String apiName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private JsonNode response;

    protected IdempotencyKey() {}

    public IdempotencyKey(String idemKey, String apiName, JsonNode response) {
        this.idemKey = idemKey;
        this.apiName = apiName;
        this.response = response;
    }

    public JsonNode getResponse() {
        return response;
    }
}