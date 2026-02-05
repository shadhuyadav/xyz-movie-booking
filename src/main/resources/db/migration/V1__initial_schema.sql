

CREATE TABLE IF NOT EXISTS theatres (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS screens (
                                       id BIGSERIAL PRIMARY KEY,
                                       theatre_id BIGINT NOT NULL,
                                       name VARCHAR(50) NOT NULL,

    CONSTRAINT fk_screen_theatre
    FOREIGN KEY (theatre_id)
    REFERENCES theatres(id)
    ON DELETE CASCADE
    );




CREATE TABLE IF NOT EXISTS movies (
                                      id BIGSERIAL PRIMARY KEY,
                                      title VARCHAR(100) NOT NULL,
    language VARCHAR(30),
    genre VARCHAR(30)
    );




CREATE TABLE IF NOT EXISTS shows (
                                     id BIGSERIAL PRIMARY KEY,
                                     movie_id BIGINT NOT NULL,
                                     screen_id BIGINT NOT NULL,
                                     show_date DATE NOT NULL,
                                     start_time TIME NOT NULL,
                                     base_price INTEGER NOT NULL,

                                     CONSTRAINT uq_show_unique
                                     UNIQUE (screen_id, show_date, start_time),

    CONSTRAINT fk_show_movie
    FOREIGN KEY (movie_id)
    REFERENCES movies(id),

    CONSTRAINT fk_show_screen
    FOREIGN KEY (screen_id)
    REFERENCES screens(id)
    ON DELETE CASCADE
    );

CREATE INDEX idx_show_movie_date
    ON shows (movie_id, show_date);


CREATE TABLE IF NOT EXISTS seat_inventory (
                                              id BIGSERIAL PRIMARY KEY,
                                              show_id BIGINT NOT NULL,
                                              seat_id VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT uq_seat_per_show
    UNIQUE (show_id, seat_id),

    CONSTRAINT fk_seat_show
    FOREIGN KEY (show_id)
    REFERENCES shows(id)
    ON DELETE CASCADE
    );

CREATE INDEX idx_seat_show_status
    ON seat_inventory (show_id, status);




CREATE TABLE IF NOT EXISTS bookings (
                                        id BIGSERIAL PRIMARY KEY,
                                        user_id BIGINT NOT NULL,
                                        show_id BIGINT NOT NULL,
                                        status VARCHAR(20) NOT NULL,
    total_amount NUMERIC(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_booking_show
    FOREIGN KEY (show_id)
    REFERENCES shows(id)
    ON DELETE CASCADE
    );

CREATE INDEX idx_booking_user
    ON bookings (user_id);

CREATE INDEX idx_booking_show
    ON bookings (show_id);




CREATE TABLE IF NOT EXISTS payments (
                                        id BIGSERIAL PRIMARY KEY,
                                        booking_id BIGINT NOT NULL,
                                        amount NUMERIC(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    retry_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(id)
    ON DELETE CASCADE
    );

CREATE INDEX idx_payment_booking
    ON payments (booking_id);




CREATE TABLE IF NOT EXISTS idempotency_keys (
                                                id BIGSERIAL PRIMARY KEY,
                                                idem_key VARCHAR(100) NOT NULL,
    api_name VARCHAR(100) NOT NULL,
    response JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_idempotency
    UNIQUE (idem_key, api_name)
    );

CREATE INDEX idx_idempotency_key
    ON idempotency_keys (idem_key);