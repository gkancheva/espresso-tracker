package com.softuni.espresso.tracker.service.mail;

import com.softuni.espresso.tracker.config.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class MailService {

    private final MailProperties mailProperties;

    public void sendEmail(String toEmail, String subject, String messageBody) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailProperties.getHost());
        properties.setProperty("mail.smtp.port", mailProperties.getPort());
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getDefaultSender()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setContent(messageBody, "text/html");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
