package com.vinceflores.event_booking.model.booking;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
//@AllArgsConstructor
public class BookingStatusEvent extends ApplicationEvent {
        private String Email;
        private BookingStatus bookingStatus;
        private List<Booking> bookings;

        public BookingStatusEvent(Object source , String Email, BookingStatus bookingStatus, List<Booking> bookings) {
            super(source);
            this.Email = Email;
            this.bookingStatus = bookingStatus;
            this.bookings = bookings;
        }

        public BookingStatusEvent(Object source) {
            super(source);
        }
}
