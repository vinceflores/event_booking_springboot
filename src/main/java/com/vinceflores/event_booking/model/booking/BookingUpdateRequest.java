package com.vinceflores.event_booking.model.booking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingUpdateRequest {
    private BookingStatus status;
}
