package com.vinceflores.event_booking.model.events;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class CreateEventDTO {

    @Column(length = 20, nullable = false)
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private EventDelivery eventDelivery = EventDelivery.INPERSON;

    private String location;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private double price = 0;

    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    private LocalDateTime createdAt = LocalDateTime.now();


}
