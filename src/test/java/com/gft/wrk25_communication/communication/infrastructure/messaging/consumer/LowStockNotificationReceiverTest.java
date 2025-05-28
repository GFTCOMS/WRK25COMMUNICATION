package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.LowStockNotificationDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LowStockNotificationReceiverTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private NotificationFactory notificationFactory;

    @Mock
    private NotificationSaveUseCase notificationSaveUseCase;

    @InjectMocks
    private LowStockNotificationReceiver receiver;

    @Test
    void testReceive() {

        NotificationFactory workingFactory = new NotificationFactory();

        UserId userId = new UserId(UUID.randomUUID());
        ProductDTO productDTO = Instancio.create(ProductDTO.class);

        LowStockNotificationDTO notification = new LowStockNotificationDTO(userId.id(), productDTO.id(), productDTO.inventoryData().stock());
        Notification notificationToReturn = workingFactory.createLowStockNotification(userId, productDTO);

        when(apiClient.getProductById(new ProductId(productDTO.id()))).thenReturn(productDTO);

        when(notificationFactory.createLowStockNotification(userId, productDTO)).thenReturn(notificationToReturn);

        receiver.receive(notification);

        verify(notificationFactory, times(1)).createLowStockNotification(userId, productDTO);
        verify(notificationSaveUseCase, times(1)).execute(notificationToReturn);
    }

}
