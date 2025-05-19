package com.gft.wrk25_communication.communication.infrastructure.event;

import com.gft.wrk25_communication.communication.application.event.NotificationEventPublisher;
import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventPublisherImpl implements NotificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishNotificationCreatedEvent(NotificationCreatedEvent notificationCreatedEvent) {
        applicationEventPublisher.publishEvent(notificationCreatedEvent);
    }

}
