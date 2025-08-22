package com.vinceflores.event_booking.model.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "user")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @OneToMany(cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne
    @JsonIgnore
    private User user;

}
