package com.vinceflores.event_booking.controllers;

import com.vinceflores.event_booking.model.booking.*;
import com.vinceflores.event_booking.model.cart.*;
import com.vinceflores.event_booking.model.order.Order;
import com.vinceflores.event_booking.model.order.OrderRepository;
import com.vinceflores.event_booking.model.order.OrderService;
import com.vinceflores.event_booking.model.users.User;
import com.vinceflores.event_booking.model.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final BookingService bookingService;
    private final CartRepository cartRepository;
    private final OrderService orderService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(Principal principal) {
        var user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(user != null){
          Cart cart = cartService.getCartByUser(user);
          if(cart == null){
              System.out.println("cart created");
              cart = cartService.createCart(user);
          }
          return ResponseEntity.ok().body( new CartDTO(cart) );
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CartDTO> addToCart(@RequestBody(required = true) AddToCartDTO dto) {
        Cart cart = cartService.addToCart(dto.getId(), dto);

       if(cart != null) {
           return ResponseEntity.ok(new CartDTO(cart));
       }
       return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/remove/{eventId}")
    public ResponseEntity<CartDTO> removeFromCart(@PathVariable("id") int id, @PathVariable("eventId") int eventId ) {
        Cart cart =  cartService.removeFromCart(id, eventId);

        if(cart != null) {
              return ResponseEntity.ok(new CartDTO(cart));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<Void> checkout(@PathVariable Integer id, Principal principal) {
        Cart  cart = cartService.getCart(id);
        if (cart == null) {
            return ResponseEntity.badRequest().build();
        }
        List<CartItem> cartItems = cart.getCartItems();
        double totalPrice = 0;
        List<Booking> bookings = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Booking b = bookingService.createBooking(
                    new BookingRequest(cartItem.getEvent(), cartItem.getQuantity(), cartItem.getPrice()),
                    principal
            );
            if (b != null) {
                totalPrice += b.getTotalPrice();
                bookings.add(b);
            }
        }
        // Publish CheckoutEvent
        eventPublisher.publishEvent(new BookingStatusEvent(this, principal.getName(), BookingStatus.CONFIRMED, bookings));

        cart.getCartItems().clear();
        cartRepository.save(cart);
        orderService.createOrder( totalPrice, bookings,cart.getUser());

        return ResponseEntity.ok().build();
    }

}

