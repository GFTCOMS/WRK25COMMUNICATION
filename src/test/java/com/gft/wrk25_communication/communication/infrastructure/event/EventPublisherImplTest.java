package com.gft.wrk25_communication.communication.infrastructure.event;

import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.event.NotificationCreatedEvent;
import com.gft.wrk25_communication.communication.domain.event.UserDeletedEvent;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventPublisherImplTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private EventPublisherImpl eventPublisherImpl;

    private final NotificationFactory notificationFactory = new NotificationFactory();

    @Test
    void testPublishNotificationCreatedEvent() {

        Notification notification = notificationFactory.reinstantiate(
                new NotificationId(),
                Instancio.create(LocalDateTime.class),
                new UserId(UUID.randomUUID()),
                Instancio.create(String.class),
                Instancio.create(Boolean.class)
        );

        NotificationCreatedEvent notificationCreatedEvent = new NotificationCreatedEvent(notification);

        eventPublisherImpl.publishNotificationCreatedEvent(notificationCreatedEvent);

        verify(applicationEventPublisher, times(1)).publishEvent(notificationCreatedEvent);
    }

    @Test
    void testPublishUserDeletedEvent() {

        UserId userId = Instancio.create(UserId.class);

        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(userId);

        eventPublisherImpl.publishUserDeletedEvent(userDeletedEvent);

        verify(applicationEventPublisher, times(1)).publishEvent(userDeletedEvent);
    }
}