package com.vinceflores.event_booking.model.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.order.Order;
import com.vinceflores.event_booking.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id" , nullable = true)
    private Event event;

    @Builder.Default
    private int quantity = 1;

    @Builder.Default
    private BookingStatus status = BookingStatus.CONFIRMED;

    @Column(nullable = false)
    private double totalPrice;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        foreignKey = @ForeignKey(name = "fk_user_booking", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE"),
        nullable = true
    )
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_orders_booking"))
    @JsonIgnore
    private Order orders;
}
