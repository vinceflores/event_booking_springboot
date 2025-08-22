package com.vinceflores.event_booking.model.cart;

import com.vinceflores.event_booking.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
    Optional<Cart> findCartByUser(User user);
}
