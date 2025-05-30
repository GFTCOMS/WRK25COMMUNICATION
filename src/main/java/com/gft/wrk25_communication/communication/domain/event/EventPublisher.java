package com.gft.wrk25_communication.communication.domain.event;

public interface EventPublisher {

    void publishNotificationCreatedEvent(NotificationCreatedEvent notificationCreatedEvent);

    void publishUserDeletedEvent(UserDeletedEvent userDeletedEvent);

}
