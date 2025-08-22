package com.vinceflores.event_booking.controllers;

import com.vinceflores.event_booking.model.order.Order;
import com.vinceflores.event_booking.model.order.OrderRepository;
import com.vinceflores.event_booking.model.order.OrdersDTO;
import com.vinceflores.event_booking.model.users.User;
import com.vinceflores.event_booking.model.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping ("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderRepository orderRepository;;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<OrdersDTO>> getOrdersByUser(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) { return ResponseEntity.notFound().build(); }
        List<Order> orders = orderRepository.findAllByUser(user);
         return ResponseEntity.ok(OrdersDTO.toList(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersDTO> getOrderById(@PathVariable Integer id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null) { return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok(new OrdersDTO(order.getId(), order.getBookings(), order.getTotalPrice()));
    }

}
