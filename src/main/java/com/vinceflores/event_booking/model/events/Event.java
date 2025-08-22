package com.vinceflores.event_booking.model.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinceflores.event_booking.model.booking.Booking;
import com.vinceflores.event_booking.model.cart.Cart;
import com.vinceflores.event_booking.model.cart.CartItem;
import com.vinceflores.event_booking.model.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, nullable = false)
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private EventDelivery eventDelivery = EventDelivery.INPERSON;

    private String location;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private double price = 0;

    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    private LocalDateTime createdAt = LocalDateTime.now();

//    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    @OneToMany(mappedBy = "event", orphanRemoval = false)
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<CartItem> cartItems;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
