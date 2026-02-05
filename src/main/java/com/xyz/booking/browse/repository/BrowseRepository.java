package com.xyz.booking.browse.repository;

import com.xyz.booking.browse.dto.BrowseShowResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BrowseRepository {

    private final JdbcTemplate jdbcTemplate;

    public BrowseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BrowseShowResponse> findShows(
            Long movieId,
            String city,
            Date showDate
    ) {
        String sql = """
            SELECT
                t.name AS theatreName,
                s.name AS screenName,
                sh.start_time AS startTime,
                COUNT(si.id) FILTER (WHERE si.status = 'AVAILABLE') AS availableSeats
            FROM shows sh
            JOIN screens s ON sh.screen_id = s.id
            JOIN theatres t ON s.theatre_id = t.id
            LEFT JOIN seat_inventory si ON sh.id = si.show_id
            WHERE sh.movie_id = ?
              AND t.city = ?
              AND sh.show_date = ?
            GROUP BY t.name, s.name, sh.start_time
            ORDER BY sh.start_time
        """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new BrowseShowResponse(
                                rs.getString("theatreName"),
                                rs.getString("screenName"),
                                rs.getTime("startTime").toLocalTime(),
                                rs.getInt("availableSeats")
                        ),
                movieId,
                city,
                showDate
        );
    }
}