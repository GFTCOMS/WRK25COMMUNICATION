package com.gft.wrk25_communication.communication.e2etest;

import com.gft.wrk25_communication.communication.application.dto.NotificationDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductStockChangedNotificationDTO;
import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.infrastructure.entity.NotificationEntity;
import com.gft.wrk25_communication.communication.infrastructure.messaging.consumer.ProductChangedNotificationReceiver;
import com.gft.wrk25_communication.communication.infrastructure.repository.NotificationEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class ProductChangedNotificationE2EIT {

    @Autowired
    private ProductChangedNotificationReceiver receiver;

    @MockitoBean
    private ApiClient apiClient;

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private NotificationEntityRepository notificationEntityRepository;

    @Test
    void testRecieveNotification() {

        long productIdValue = 12345L;
        ProductStockChangedNotificationDTO incomingDto = new ProductStockChangedNotificationDTO(productIdValue, 5);
        ProductId productId = new ProductId(productIdValue);

        ProductDTO productDTO = new ProductDTO(
                productIdValue,
                "Mocked Product",
                new ProductDTO.InventoryData(10),
                new BigDecimal("100.00")
        );

        UserId user1 = new UserId(UUID.randomUUID());
        UserId user2 = new UserId(UUID.randomUUID());
        List<UserId> users = List.of(user1, user2);

        when(apiClient.getProductById(productId)).thenReturn(productDTO);
        when(apiClient.getUsersThatHaveProductInFavorites(productId)).thenReturn(users);

        receiver.receive(incomingDto);

        ArgumentCaptor<NotificationDTO> captor = ArgumentCaptor.forClass(NotificationDTO.class);
        verify(rabbitTemplate, times(users.size()))
                .convertAndSend(eq("coms"), eq("coms.notification"), captor.capture());

        NotificationDTO notificationDTO = captor.getValue();

        Optional<NotificationEntity> entitySaved = notificationEntityRepository.findById(notificationDTO.id());

        assertThat(entitySaved).isPresent();

        NotificationEntity entity = entitySaved.get();

        NotificationDTO persistedDTO = new NotificationDTO(
                        entity.getId(),
                entity.getCreatedAt(),
                entity.getUserId(),
                entity.getMessage(),
                entity.isImportant()
                );

        assertEquals(notificationDTO.id(), persistedDTO.id(), "El ID debe coincidir");
        assertEquals(notificationDTO.userId(), persistedDTO.userId(), "El User ID debe coincidir");
        assertEquals(notificationDTO.message(), persistedDTO.message(), "El mensaje debe coincidir");
        assertEquals(notificationDTO.createdAt().withNano(0), persistedDTO.createdAt().withNano(0), "La fecha debe coincidir (ignorando nanos)");
        assertEquals(notificationDTO.important(), persistedDTO.important());

    }
}
