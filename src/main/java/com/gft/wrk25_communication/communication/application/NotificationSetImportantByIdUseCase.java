package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import com.gft.wrk25_communication.communication.infrastructure.exception.NotificationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSetImportantByIdUseCase {

    private final NotificationRepository notificationRepository;

    public void execute(NotificationId notificationId, Boolean important) {
        if (important == null) {
            log.error("Parameter important is null on the notification with ID: {}", notificationId.id());
            throw new IllegalArgumentException("important is null");
        }

        log.info("Setting the notification withMarcando notificación con ID: {} as {}", notificationId.id(), important ? "important" : "not important");

        if (!notificationRepository.existsById(notificationId)) {
            log.warn("Could not find the notification with ID: {}", notificationId.id());
            throw new NotificationNotFoundException(notificationId);
        }

        notificationRepository.setImportant(notificationId, important);

        log.info("Notification with ID: {} succesfully updated as {}", notificationId.id(), important ? "important" : "not important");
    }
}
