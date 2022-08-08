package com.softuni.espresso.tracker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class MailProperties {

    @Value("${espresso-tracker-service.mail.host}")
    private String host;

    @Value("${espresso-tracker-service.mail.port}")
    private String port;

    @Value("${espresso-tracker-service.mail.default-sender-address}")
    private String defaultSender;

}
