package com.gft.wrk25_communication.communication.infrastructure.event;

import com.gft.wrk25_communication.communication.domain.event.EventPublisher;
import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.domain.event.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisherImpl implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishNotificationCreatedEvent(NotificationCreatedEvent notificationCreatedEvent) {
        applicationEventPublisher.publishEvent(notificationCreatedEvent);
    }

    @Override
    public void publishUserDeletedEvent(UserDeletedEvent userDeletedEvent) {
        applicationEventPublisher.publishEvent(userDeletedEvent);
    }

}
