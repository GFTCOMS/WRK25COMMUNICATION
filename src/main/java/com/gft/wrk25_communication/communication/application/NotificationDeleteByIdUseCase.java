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
public class NotificationDeleteByIdUseCase {

    private final NotificationRepository notificationRepository;

    public void execute(NotificationId notificationId) {
        log.info("Trying to eliminate the notification with ID: {}", notificationId.id());

        if (!notificationRepository.existsById(notificationId)) {
            log.warn("Could not found a notification with ID: {}", notificationId.id());
            throw new NotificationNotFoundException(notificationId);
        }

        notificationRepository.deleteById(notificationId);
        log.info("Succesfully eliminated notification with ID: {}", notificationId.id());
    }
}
