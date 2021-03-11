package com.mailingservice.mailingservice.service;

import com.mailingservice.mailingservice.exception.SpringException;
import com.mailingservice.mailingservice.model.NotificationEmail;
import com.mailingservice.mailingservice.service.MailContentBuilder;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    // If role is doctor then send a link to docregistration page and then set enable = 1
    @Async
    public void sendMail(NotificationEmail notificationEmail) { // create a mime for java mail sending
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try { // simple try catch
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new SpringException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }
    
}