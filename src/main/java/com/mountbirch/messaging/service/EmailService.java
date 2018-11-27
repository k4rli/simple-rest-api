package com.mountbirch.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Kaur Laanemäe on 22/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    private String emailFrom;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, @Value("${spring.mail.sender}") String emailFrom) {
        this.javaMailSender = javaMailSender;
        this.emailFrom = emailFrom;
    }

    public void sendEmailMessage(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        mimeMessage.setContent(text, "text/html");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(emailFrom);
        javaMailSender.send(mimeMessage);
    }
}
