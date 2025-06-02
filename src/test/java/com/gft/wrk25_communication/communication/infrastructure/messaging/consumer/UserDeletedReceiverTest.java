package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationDeleteByUserIdUseCase;
import com.gft.wrk25_communication.communication.application.dto.UserDeletedDTO;
import com.gft.wrk25_communication.communication.domain.UserId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserDeletedReceiverTest {

    @Mock
    NotificationDeleteByUserIdUseCase notificationDeleteByUserIdUseCase;

    @InjectMocks
    UserDeletedReceiver userDeletedReceiver;

    @Test
    void testReceive() {

        UserId userId = Instancio.create(UserId.class);

        userDeletedReceiver.receive(new UserDeletedDTO(userId.userId()));

        verify(notificationDeleteByUserIdUseCase, times(1)).execute(userId);
    }
}