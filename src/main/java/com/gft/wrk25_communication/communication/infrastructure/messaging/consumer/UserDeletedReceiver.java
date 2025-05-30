package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationDeleteByUserIdUseCase;
import com.gft.wrk25_communication.communication.domain.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDeletedReceiver {

    private final NotificationDeleteByUserIdUseCase notificationDeleteByUserIdUseCase;

    @RabbitListener(queues = "${queue.user.deleted}")
    public void receive(UserId userId) {
        log.info("User deleted received : userId={}",
                userId.userId());

        notificationDeleteByUserIdUseCase.execute(userId);
    }

}
