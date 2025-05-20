package com.gft.wrk25_communication.communication.infrastructure.messaging.producer;

import com.gft.wrk25_communication.communication.application.dto.LowStockNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LowStockNotificationPublisherTest {

    private RabbitTemplate rabbitTemplate;
    private LowStockNotificationPublisher publisher;

    @BeforeEach
    void initData() {
        rabbitTemplate = mock(RabbitTemplate.class);
        publisher = new LowStockNotificationPublisher(rabbitTemplate, "user", "notification");
    }

    @Test
    void testPublish() {
        UUID id = UUID.randomUUID();
        Long productId = 123L;
        Integer stock = 5;

        LowStockNotification notification = new LowStockNotification(id, productId, stock);

        publisher.publish(notification);

        ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
        verify(rabbitTemplate, times(1)).convertAndSend(eq("user"), eq("notification"), messageCaptor.capture());

        assertEquals(notification, messageCaptor.getValue());
    }
}
