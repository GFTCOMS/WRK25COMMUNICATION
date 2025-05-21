package com.gft.wrk25_communication.communication.infrastructure.messaging.producer;

import com.gft.wrk25_communication.communication.application.dto.LowStockNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LowStockNotificationPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public LowStockNotificationPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${exchange.user}") String exchange,
            @Value("${routing-key.product.stock.low}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void publish(LowStockNotification notification) {
        rabbitTemplate.convertAndSend(exchange, routingKey, notification);
    }
}

