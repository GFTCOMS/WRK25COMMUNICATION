package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.domain.event.EventPublisher;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSaveUseCase {

    private final NotificationRepository notificationRepository;
    private final EventPublisher eventPublisher;

    public void execute(Notification notification) {
        log.info("Saving notification for user with ID: {}", notification.getUserId().userId());

        Notification notificationSaved = notificationRepository.save(notification);

        log.info("Notification saved with ID: {}", notificationSaved.getId().id());

        eventPublisher.publishNotificationCreatedEvent(
                new NotificationCreatedEvent(notificationSaved)
        );

        log.info("Event NotificationCreatedEvent published for notification ID: {}", notificationSaved.getId().id());
    }
}
