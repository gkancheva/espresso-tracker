package com.softuni.espresso.tracker.service.mail;

import com.softuni.espresso.tracker.repository.CoffeeRepository;
import com.softuni.espresso.tracker.repository.UserRepository;
import com.softuni.espresso.tracker.model.entities.CoffeeEntity;
import com.softuni.espresso.tracker.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendNotificationService {

    private static final String SUBJECT = "New coffees in our bakeries";

    private final UserRepository userRepository;
    private final CoffeeRepository coffeeRepository;
    private final TemplateLoader templateLoader;

    private final MailService mailService;

    public void sendNotification() {
       sendNotification(LocalDate.now().minusDays(1));
    }

    protected void sendNotification(LocalDate date) {
        List<CoffeeEntity> coffees = coffeeRepository
                .findAllByDateCreated(date).stream()
                .sorted(Comparator.comparing(coffee -> coffee.getBakery().getName()))
                .collect(Collectors.toList());

        if (coffees.isEmpty()) {
            return;
        }

        List<UserEntity> users = userRepository.findAll();

        String template = templateLoader.loadMailTemplate();
        template = prepareBody(coffees, template);
        for (UserEntity user : users) {
            template = updateUserInformation(template, user);
            mailService.sendEmail(user.getEmail(), SUBJECT, updateUserInformation(template, user));
        }
    }

    private String updateUserInformation(String template, UserEntity user) {
        return template.replace("${username}", user.getUsername());
    }

    private String prepareBody(List<CoffeeEntity> coffees, String template) {
        String coffeeLine = "<div>Coffee: %s at Bakery: %s</div>";
        StringBuilder sb = new StringBuilder();
        for (CoffeeEntity coffee : coffees) {
            String format = String.format(coffeeLine, coffee.getName(), coffee.getBakery().getName());
            sb.append(format);
        }
        return template.replace("${body}", sb.toString());
    }

}