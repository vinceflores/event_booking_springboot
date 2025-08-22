package com.vinceflores.event_booking.controllers;

import com.vinceflores.event_booking.model.booking.Booking;
import com.vinceflores.event_booking.model.booking.BookingDTO;
import com.vinceflores.event_booking.model.booking.BookingService;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.order.Order;
import com.vinceflores.event_booking.model.users.User;
import com.vinceflores.event_booking.model.users.UserDTO;
import com.vinceflores.event_booking.model.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserRepository userRepository;
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        if (user.isPresent()) {
            User u = user.get();
            UserDTO userDTO = new UserDTO( u.getId(), u.getFirstname(), u.getLastname(), u.getEmail());
            return  ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update( @PathVariable int id, @RequestBody UserDTO userDTO){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setFirstname(userDTO.getFirstname());
            u.setLastname(userDTO.getLastname());
            u.setEmail(userDTO.getEmail());
            userRepository.save(u);
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        try {
            userRepository.deleteById(id);
            User u =  userRepository.findById(id).orElse(null);
            if (u != null) {
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok("Deleted");
        }
        catch( IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // get events
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents(Principal principal) {
        // TODO
        return null;
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        if (user.isPresent()) {
            return ResponseEntity.ok(bookingService.findAllByUser(user.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getOrders());
        }
        return ResponseEntity.notFound().build();
    }
}
