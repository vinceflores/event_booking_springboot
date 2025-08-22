package com.vinceflores.event_booking.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
 public class AddToCartDTO {
    private Integer id;
    private CartItemDTO cartItem;
}

