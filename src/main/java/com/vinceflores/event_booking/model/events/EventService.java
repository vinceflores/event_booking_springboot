package com.vinceflores.event_booking.model.events;

import com.vinceflores.event_booking.model.booking.Booking;
import com.vinceflores.event_booking.model.booking.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    public Event findById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteEventKeepBookings(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Detach bookings
        List<Booking> bookings = bookingRepository.findByEvent(event);
        for (Booking booking : bookings) {
            booking.setEvent(null);
        }
        bookingRepository.saveAll(bookings);

        // Now delete the event
        eventRepository.delete(event);
    }
}
