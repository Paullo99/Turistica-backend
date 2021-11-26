package pl.turistica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.turistica.config.EmailConfiguration;

@Service
public class EmailService {

    @Autowired
    EmailConfiguration emailConfiguration;

    public void sendMessage(String to, String subject, String text) {
        JavaMailSender javaMailSender = emailConfiguration.getJavaMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("turistica.company@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
