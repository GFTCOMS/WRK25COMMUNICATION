package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import com.gft.wrk25_communication.communication.infrastructure.exception.NotificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationDeleteByIdUseCase {

    private final NotificationRepository notificationRepository;

    public void execute(NotificationId notificationId) {

        if (!notificationRepository.existsById(notificationId)) {
            throw new NotificationNotFoundException(notificationId);
        }

        notificationRepository.deleteById(notificationId);
    }

}
