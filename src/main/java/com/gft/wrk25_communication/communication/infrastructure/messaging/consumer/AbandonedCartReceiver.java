package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.AbandonedCartDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AbandonedCartReceiver {

    private final NotificationSaveUseCase notificationSaveUseCase;
    private final NotificationFactory factory;

    @RabbitListener(queues = "${queue.cart.abandoned}")
    public void handleMessage(AbandonedCartDTO dto) {
        log.info("Mensaje de carrito abandonado recibido para usuario {}", dto.userId());

        Notification notification = factory.createAbandonedCartNotification(
                new UserId(dto.userId().id())
        );

        notificationSaveUseCase.execute(notification);

    }

}