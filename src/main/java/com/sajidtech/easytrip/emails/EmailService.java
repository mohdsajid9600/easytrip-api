package com.sajidtech.easytrip.emails;

import com.sajidtech.easytrip.dto.response.BookingResponse;
import com.sajidtech.easytrip.enums.TripStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async("taskExecutor")
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Async("taskExecutor")
    public void sendEmailToCustomer(String subject, BookingResponse bookingResponse, TripStatus tripStatus){
        String body = CustomerEmailFormate.getEmailTemplate(
                tripStatus,
                bookingResponse
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(bookingResponse.getCustomerResponse().getEmail());
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}

