package com.vinceflores.event_booking.model.booking;

import com.vinceflores.event_booking.model.events.Event;
import com.vinceflores.event_booking.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
//    Booking findBookingByUserId(Long userId);
     List<Booking> findByUser(User user);
     List<Booking> findByEvent(Event event);
}
