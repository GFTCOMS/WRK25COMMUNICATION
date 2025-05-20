package com.gft.wrk25_communication.communication.infrastructure.event;

import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventHandler {

    @EventListener
    public void handleNotificationCreatedEvent(NotificationCreatedEvent event) {

    }

}
