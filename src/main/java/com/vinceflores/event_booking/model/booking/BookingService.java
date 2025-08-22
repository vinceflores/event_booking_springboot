package com.vinceflores.event_booking.model.booking;

import com.vinceflores.event_booking.controllers.BookingController;
import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.events.EventRepository;
import com.vinceflores.event_booking.model.users.User;
import com.vinceflores.event_booking.model.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<Booking> findAllByUser(User user){
//        return bookingRepository.findAllBookingsByUser(user);
        return bookingRepository.findByUser(user);
    }


    public Booking createBooking(BookingRequest bookingRequest, Principal principal) {
        Optional<User> user = userRepository.findByEmail(principal.getName());
        if(user.isEmpty()) { return null; }
        Booking booking = Booking.builder()
                .event(bookingRequest.getEvent())
                .user(user.get())
                .totalPrice(bookingRequest.getTotalPrice())
                .quantity(bookingRequest.getQuantity())
                .build();
        bookingRepository.save(booking);
        return booking;
    }

    public Booking CancelBooking(int id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) { return null; }
        booking.get().setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking.get());
    }

}
