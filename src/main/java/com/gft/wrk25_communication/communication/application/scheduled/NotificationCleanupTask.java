package com.gft.wrk25_communication.communication.application.scheduled;

import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NotificationCleanupTask {

    private final NotificationRepository notificationRepository;

    public void deleteOldNotifications() {
        notificationRepository.deleteOldNotifications();
    }

}
