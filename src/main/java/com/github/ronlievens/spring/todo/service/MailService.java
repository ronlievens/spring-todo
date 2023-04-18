package com.github.ronlievens.spring.todo.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailService {
    private final JavaMailSenderImpl mailSender;

    @Async
    public void send(final String toEmail, final String toName,
                     final String fromEmail, final String fromName,
                     final String subject, final String content) {
        try {
            // Create email
            val mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail, toName));
            mimeMessage.setFrom(new InternetAddress(fromEmail, fromName));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(new String(Base64.getDecoder().decode(content)), "UTF-8", "html");

            // Send notification
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("MessagingException: {}", e.getMessage(), e);
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage(), e);
        }
    }
}
