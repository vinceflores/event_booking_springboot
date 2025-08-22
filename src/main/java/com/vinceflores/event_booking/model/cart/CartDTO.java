package com.vinceflores.event_booking.model.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CartDTO {
    private int id;
    private List<CartItem> events;
    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.events = cart.getCartItems();
    }
}


