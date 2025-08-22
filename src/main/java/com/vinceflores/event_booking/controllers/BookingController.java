package com.vinceflores.event_booking.controllers;

import com.vinceflores.event_booking.model.booking.*;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.events.EventRepository;
import com.vinceflores.event_booking.model.users.User;
import com.vinceflores.event_booking.model.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<CollectionModel<Booking>> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CollectionModel.of(bookings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Booking>> getBooking(@PathVariable int id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(EntityModel.of(booking));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<Booking>> cancelBooking(@PathVariable int id, Principal principal) {
        Booking booking = bookingService.CancelBooking(id);
        if(booking == null) {
            return ResponseEntity.notFound().build();
        }
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        eventPublisher.publishEvent(new BookingStatusEvent(this, principal.getName(), BookingStatus.CANCELLED, bookings ));

        EntityModel<Booking> bookingModel = EntityModel.of(booking)
                .add(linkTo(methodOn(BookingController.class).getAllBookings()).withRel("findAll"))
                .add(linkTo(methodOn(BookingController.class).getBooking(id)).withRel("findOne"));
        return ResponseEntity.ok(bookingModel);
    }
    // get booking by event
}
