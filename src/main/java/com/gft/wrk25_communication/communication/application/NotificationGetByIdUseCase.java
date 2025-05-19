package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationGetByIdUseCase {

    private final NotificationRepository notificationRepository;

    public Optional<Notification> execute(NotificationId notificationId) {
        return notificationRepository.findById(notificationId);
    }

}
