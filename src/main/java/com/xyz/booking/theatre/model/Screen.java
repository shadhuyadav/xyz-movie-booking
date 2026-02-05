package com.xyz.booking.theatre.model;

import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long theatreId;
    private String name;

    protected Screen() {}

    public Screen(Long theatreId, String name) {
        this.theatreId = theatreId;
        this.name = name;
    }

    public Long getId() { return id; }
    public Long getTheatreId() { return theatreId; }
    public String getName() { return name; }
}
