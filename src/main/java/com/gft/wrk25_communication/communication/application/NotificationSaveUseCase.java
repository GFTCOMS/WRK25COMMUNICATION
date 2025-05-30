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
        log.info("Guardando notificación para el usuario con ID: {}", notification.getUserId().userId());

        Notification notificationSaved = notificationRepository.save(notification);

        log.info("Notificación guardada con ID: {}", notificationSaved.getId().id());

        eventPublisher.publishNotificationCreatedEvent(
                new NotificationCreatedEvent(notificationSaved)
        );

        log.info("Evento NotificationCreatedEvent publicado para notificación ID: {}", notificationSaved.getId().id());
    }
}
