package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.dto.LowStockNotification;
import com.gft.wrk25_communication.communication.infrastructure.messaging.producer.LowStockNotificationPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LowStockNotificationReceiverTest {

    private LowStockNotificationPublisher publisher;
    private LowStockNotificationReceiver receiver;

    @BeforeEach
    void initData() {
        publisher = mock(LowStockNotificationPublisher.class);
        receiver = new LowStockNotificationReceiver(publisher);
    }

    @Test
    void testReceive() {
        UUID id = UUID.randomUUID();
        LowStockNotification notification = new LowStockNotification(id, 100L, 5);

        receiver.receive(notification);

        ArgumentCaptor<LowStockNotification> captor = ArgumentCaptor.forClass(LowStockNotification.class);
        verify(publisher, times(1)).publish(captor.capture());

        assertEquals(notification, captor.getValue());
    }
}
