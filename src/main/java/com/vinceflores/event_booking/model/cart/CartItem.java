package com.vinceflores.event_booking.model.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinceflores.event_booking.model.events.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "fk2tvar3qo0xslg5i13jx9evbpy"))
    @JsonIgnore
    private Event event;
    private int quantity;
    @Builder.ObtainVia(method = "calculatePrice")
    private double price;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    public  double calculatePrice() {
        return quantity * event.getPrice();
    }

    public CartItem(Event event, int quantity) {
        this.event = event;
        this.quantity = quantity;
        this.price = calculatePrice();
    }
}
