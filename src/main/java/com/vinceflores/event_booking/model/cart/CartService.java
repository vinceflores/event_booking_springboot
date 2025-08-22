package com.vinceflores.event_booking.model.cart;

import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.events.EventService;
import com.vinceflores.event_booking.model.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartService {
    private final EventService eventService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart createCart(User user) {
         return cartRepository.save(Cart.builder().user(user).build());
    }

    public Cart getCartByUser(User user) {
        return cartRepository.findCartByUser(user).orElse(null);
    }

    public Cart getCart(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart addToCart(int id, AddToCartDTO dto) {
        Event e = eventService.findById(dto.getCartItem().getEventId());
        if(e == null) { return null; }
        Cart cart = getCart(id);
        if (cart != null) {
            List<CartItem> cartItems = cart.getCartItems();
            CartItem cartItem  = CartItem.builder()
                    .quantity(dto.getCartItem().getQuantity())
                    .event(e)
                    .cart(cart)
                    .price(e.getPrice() * dto.getCartItem().getQuantity())
                    .build();

            cartItemRepository.save(cartItem);

            cartItems.add(cartItem);
            cart.setCartItems(cartItems);

            return cartRepository.save(cart);
        }
        return null;
    }

    public Cart removeFromCart(int id, int eventId) {
        Cart cart =  cartRepository.findById(id).orElse(null) ;
        if(cart != null) {
            cart.getCartItems()
                    .removeIf(item -> item.getId() == eventId);
           return cartRepository.save(cart);
        }
        return null;
    }

    public Cart updateCart(int id, Cart cart){
        Cart oldCart = getCart(id);
        if (oldCart != null) {
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }
}
