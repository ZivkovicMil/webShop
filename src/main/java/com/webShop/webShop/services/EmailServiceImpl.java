package com.webShop.webShop.services;

import com.webShop.webShop.models.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendRegistrationEmail(String userEmail, String token) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setTo(userEmail);
            messageHelper.setSubject("Confirm registration");
            messageHelper.setText("http://localhost:8080/api/auth/" + token);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        log.info("Email with registration token is sent to user with email:" + userEmail);
    }

    @Override
    @Async
    public void sendChangePasswordEmail(String email, String token) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setTo(email);
            messageHelper.setSubject("Change password link");
            messageHelper.setText("http://localhost:8080/api/auth/forgotPassword/" + token);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        log.info("Email with change password token is sent to user with email:" + email);
    }

    @Override
    @Async
    public void sendOrderDetailsEmail(String email, OrderDTO orderDTO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
            messageHelper.setTo(email);
            messageHelper.setSubject("Order details");
            messageHelper.setText(orderDTO.getOrder_id() + " is confirmed");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        log.info("Email with order details is sent to user with email: " + email);
    }
}
