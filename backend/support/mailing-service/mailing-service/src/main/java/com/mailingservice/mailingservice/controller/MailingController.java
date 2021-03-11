package com.mailingservice.mailingservice.controller;

import com.mailingservice.mailingservice.service.MailService;
import com.mailingservice.mailingservice.model.NotificationEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailingController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendmail")
    public String sendMail(@RequestBody NotificationEmail body){
        mailService.sendMail(body);
        return "Mail Sent";
    }
}
