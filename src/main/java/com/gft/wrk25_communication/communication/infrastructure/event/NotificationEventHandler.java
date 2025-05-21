package com.gft.wrk25_communication.communication.infrastructure.event;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.infrastructure.messaging.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventHandler {

    private final NotificationProducer notificationProducer;

    @EventListener
    public void handleNotificationCreatedEvent(NotificationCreatedEvent event) {

        NotificationDTO notificationDTO = new NotificationDTO(
                event.notification().getId().id(),
                event.notification().getCreatedAt(),
                event.notification().getUserId().id(),
                event.notification().getMessage(),
                event.notification().isImportant()
        );

        notificationProducer.publish(notificationDTO);
    }

}
