package com.vinceflores.event_booking.model.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinceflores.event_booking.model.booking.Booking;
import com.vinceflores.event_booking.model.cart.Cart;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.order.Order;
import com.vinceflores.event_booking.model.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
@EqualsAndHashCode(exclude = "cart")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade= CascadeType.ALL)
    private List<Token> token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
