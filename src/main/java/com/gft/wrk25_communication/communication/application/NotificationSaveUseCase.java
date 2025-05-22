package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.domain.event.NotificationEventPublisher;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSaveUseCase {

    private final NotificationRepository notificationRepository;
    private final NotificationEventPublisher notificationEventPublisher;

    public void execute(Notification notification) {

        Notification notificationSaved = notificationRepository.save(notification);

        notificationEventPublisher.publishNotificationCreatedEvent(
                new NotificationCreatedEvent(notificationSaved)
        );
    }

}
