package com.vinceflores.event_booking.model.notification;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService {
   private final Resend resend;

   public void  sendEmail(String subject, String body){
       CreateEmailOptions params = CreateEmailOptions.builder()
               .from("Acme <onboarding@resend.dev>")
               .to("delivered@resend.dev")
               .subject(subject)
               .html("<div>" + body + "</div>")
               .build();

       try {
           CreateEmailResponse data = resend.emails().send(params);
       } catch (ResendException e) {
           log.error(e.getMessage());
       }

   }
}
