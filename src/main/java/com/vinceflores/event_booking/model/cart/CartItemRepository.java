package com.vinceflores.event_booking.model.cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository  extends JpaRepository<CartItem, Integer> {
}
