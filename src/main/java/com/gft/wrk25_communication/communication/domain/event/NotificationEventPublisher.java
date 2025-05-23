package com.gft.wrk25_communication.communication.domain.event;

public interface NotificationEventPublisher {

    void publishNotificationCreatedEvent(NotificationCreatedEvent notificationCreatedEvent);

}
