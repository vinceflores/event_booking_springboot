package com.vinceflores.event_booking.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinceflores.event_booking.model.booking.Booking;
import com.vinceflores.event_booking.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;


    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_order", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE"))
    @JsonIgnore
    private User user;
}
