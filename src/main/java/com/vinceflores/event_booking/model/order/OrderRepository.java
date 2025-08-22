package com.vinceflores.event_booking.model.order;

import com.vinceflores.event_booking.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
}
