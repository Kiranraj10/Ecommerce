package com.ecommerce.backend.ecommerce_practice.service;

import com.ecommerce.backend.ecommerce_practice.exception.EmailFailureException;
import com.ecommerce.backend.ecommerce_practice.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender JavaMailSender;
    @Value("${email.add}")
    private String addMail;

    public EmailService(org.springframework.mail.javamail.JavaMailSender javaMailSender) {
        JavaMailSender = javaMailSender;
    }

    private SimpleMailMessage makeMailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(addMail);
        return message;
    }

    public void sendVerificationEmail(VerificationToken token) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(token.getLocalUser().getEmail());
        message.setSubject("Verify your email address");
        message.setText("Please click the link below to verify your email address:\n" +
                "http://localhost:8080/verify?token=" + token.getToken());
        try{
            JavaMailSender.send(message);
        }catch (MailException e){
            throw new EmailFailureException();
        }
    }
}
