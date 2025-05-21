package com.gft.wrk25_communication.communication.infrastructure.messaging.producer;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LowStockNotificationProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private LowStockNotificationProducer publisher;

    @Test
    void testPublish() {

        NotificationDTO notificationDTO = new NotificationDTO(
                Instancio.create(UUID.class),
                Instancio.create(LocalDateTime.class),
                Instancio.create(UUID.class),
                Instancio.create(String.class),
                Instancio.create(Boolean.class)
        );

        publisher.publish(notificationDTO);

        ArgumentCaptor<String> exchangeCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<NotificationDTO> notificationDTOCaptor = ArgumentCaptor.forClass(NotificationDTO.class);

        verify(rabbitTemplate,times(1)).convertAndSend(
                exchangeCaptor.capture(),
                routingKeyCaptor.capture(),
                notificationDTOCaptor.capture()
        );

        assertEquals(notificationDTO, notificationDTOCaptor.getValue());
    }

}
