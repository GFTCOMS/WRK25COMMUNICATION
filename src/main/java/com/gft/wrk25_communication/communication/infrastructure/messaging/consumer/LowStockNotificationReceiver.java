package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.dto.LowStockNotification;
import com.gft.wrk25_communication.communication.infrastructure.messaging.producer.LowStockNotificationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LowStockNotificationReceiver {

    private final LowStockNotificationPublisher lowStockNotificationPublisher;

    @RabbitListener(queues = "${queue.product.stock.low}")
    public void receive(LowStockNotification notification) {
        lowStockNotificationPublisher.publish(notification); // reenviar si quieres
    }
}
