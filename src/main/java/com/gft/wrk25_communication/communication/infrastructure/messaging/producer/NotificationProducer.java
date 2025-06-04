package com.gft.wrk25_communication.communication.infrastructure.messaging.producer;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${exchange.coms}")
    private String exchange;

    @Value("${routing-key.coms}")
    private String routingKey;

    public void publish(NotificationDTO notification) {
        log.info("Publishing notification on RabbitMQ. Exchange: {}, RoutingKey: {}, Notification: {}",
                exchange, routingKey, notification);

        rabbitTemplate.convertAndSend(exchange, routingKey, notification);
    }
}
