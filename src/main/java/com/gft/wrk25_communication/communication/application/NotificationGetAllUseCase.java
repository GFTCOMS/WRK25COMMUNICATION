package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationGetAllUseCase {

    private final NotificationRepository notificationRepository;

    public List<Notification> execute(UserId userId) {
        return notificationRepository.findAllByUserId(userId);
    }

}
