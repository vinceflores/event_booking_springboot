package com.vinceflores.event_booking.model.cart;

import com.vinceflores.event_booking.model.events.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDTO  {
    private int eventId;
    private int quantity;
}
