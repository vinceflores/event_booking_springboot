package com.vinceflores.event_booking.model.notification;

import com.resend.Resend;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class NotificationConfig {
    @Value("${resend.api.key}")
    private String resendApiKey;

    @Bean
    public Resend resend(){
        return new Resend(this.resendApiKey);
    }
}
