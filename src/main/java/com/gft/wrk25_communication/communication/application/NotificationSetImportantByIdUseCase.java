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
            log.error("El parámetro 'important' es null para la notificación con ID: {}", notificationId.id());
            throw new IllegalArgumentException("important is null");
        }

        log.info("Marcando notificación con ID: {} como {}", notificationId.id(), important ? "importante" : "no importante");

        if (!notificationRepository.existsById(notificationId)) {
            log.warn("No se encontró la notificación con ID: {}", notificationId.id());
            throw new NotificationNotFoundException(notificationId);
        }

        notificationRepository.setImportant(notificationId, important);

        log.info("Notificación con ID: {} actualizada exitosamente como {}", notificationId.id(), important ? "importante" : "no importante");
    }
}
