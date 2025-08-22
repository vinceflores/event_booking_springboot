package com.vinceflores.event_booking.model.booking;

import com.vinceflores.event_booking.model.notification.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingStatusEventListener {
    private final EmailService emailService;

    private String formatSubject(BookingStatusEvent event) {
        return String.format("Booking %s",event.getBookingStatus().toString());
    }

    @EventListener
    public void handle(BookingStatusEvent event) {
        switch(event.getBookingStatus()){
            case CONFIRMED:
                emailService.sendEmail(formatSubject(event), "<p>booking confirmed</p>");
                log.info("Booking status has been confirmed");
                break;
            case CANCELLED:
                emailService.sendEmail( formatSubject(event), "<p>booking cancelled</p>");
                log.info("Booking status has been cancelled");
                break;
            case MISSED:
                emailService.sendEmail(formatSubject(event), "<p>booking missed</p>");
                log.info("Booking status has been missed");
                break;
            default:
                break;
        }
    }
}
