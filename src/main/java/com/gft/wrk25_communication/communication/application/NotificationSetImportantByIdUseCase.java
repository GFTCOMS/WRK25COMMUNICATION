package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSetImportantByIdUseCase {

    private final NotificationRepository notificationRepository;

    public void execute(NotificationId notificationId) {
        notificationRepository.setAsImportant(notificationId);
    }

}
