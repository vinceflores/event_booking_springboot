package com.vinceflores.event_booking.model.order;

import com.vinceflores.event_booking.model.booking.Booking;
import com.vinceflores.event_booking.model.booking.BookingRepository;
import com.vinceflores.event_booking.model.booking.BookingService;
import com.vinceflores.event_booking.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public Order createOrder(double totalPrice, List<Booking> bookings, User user) throws IllegalArgumentException {
        Order order = new Order();
        order.setTotalPrice(totalPrice);
        order.setBookings(bookings);
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        for(int i = 0; i<bookings.size(); i++) {
            Booking booking = bookings.get(i);
            booking.setOrders(savedOrder);
        }
        bookingRepository.saveAll(bookings);
        return savedOrder;
    }

}
