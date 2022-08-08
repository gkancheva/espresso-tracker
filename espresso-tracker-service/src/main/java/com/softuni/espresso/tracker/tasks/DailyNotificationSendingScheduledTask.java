package com.softuni.espresso.tracker.tasks;

import com.softuni.espresso.tracker.service.mail.SendNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DailyNotificationSendingScheduledTask {

    private final SendNotificationService sendNotificationService;

    @Scheduled(cron = "0 9 * * * *")
    public void statTask() {
        log.info("Scheduled task for sending daily notification started");
        sendNotificationService.sendNotification();
    }

}
