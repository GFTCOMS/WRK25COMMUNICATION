package com.gft.wrk25_communication.communication.application;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.event.EventPublisher;
import com.gft.wrk25_communication.communication.domain.event.UserDeletedEvent;
import com.gft.wrk25_communication.communication.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationDeleteByUserIdUseCase {

    private final NotificationRepository notificationRepository;
    private final EventPublisher eventPublisher;

    public void execute(UserId userId) {

        notificationRepository.deleteAllByUserId(userId);

        eventPublisher.publishUserDeletedEvent(new UserDeletedEvent(userId));
    }

}
