package com.gft.wrk25_communication.communication.application.event;

import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;

public interface NotificationEventPublisher {

    void publishNotificationCreatedEvent(NotificationCreatedEvent notificationCreatedEvent);

}
