package com.vinceflores.event_booking.model.order;

import com.vinceflores.event_booking.model.booking.Booking;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
    private int id;

    private List<Booking> bookings;

    private double totalPrice;
    public static List<OrdersDTO> toList(List<Order> orders) {
        List<OrdersDTO> ordersDTOs = new ArrayList<>();
        for (Order order : orders) {
            ordersDTOs.add(new OrdersDTO( order.getId(), order.getBookings(), order.getTotalPrice()));
        }
        return ordersDTOs;
    }
}
