package com.gft.wrk25_communication.communication.infrastructure.event;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationEventHandlerTest {

    @InjectMocks
    private NotificationEventHandler notificationEventHandler;

    @Test
    void testHandleNotificationCreatedEvent() {

        NotificationFactory notificationFactory = new NotificationFactory();

        Notification notification = notificationFactory.reinstantiate(
                new NotificationId(),
                Instancio.create(LocalDateTime.class),
                new UserId(UUID.randomUUID()),
                Instancio.create(String.class),
                Instancio.create(Boolean.class)
        );

        notificationEventHandler.handleNotificationCreatedEvent(new NotificationCreatedEvent(notification));

        fail();

        //
    }
}