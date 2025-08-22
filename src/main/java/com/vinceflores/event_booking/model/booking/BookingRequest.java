package com.vinceflores.event_booking.model.booking;

import com.vinceflores.event_booking.model.events.Event;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class BookingRequest {
    private Event event;
    private int quantity = 1;
    private double totalPrice;
}
