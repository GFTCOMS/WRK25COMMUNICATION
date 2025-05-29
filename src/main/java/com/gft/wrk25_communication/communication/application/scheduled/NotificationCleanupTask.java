package com.gft.wrk25_communication.communication.application.scheduled;

import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationCleanupTask {

    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime maxDays = now.minusDays(25);
        notificationRepository.deleteByCreateAtBefore(maxDays);
    }

}
