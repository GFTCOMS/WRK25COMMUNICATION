package com.gft.wrk25_communication.communication.infrastructure.messaging.consumer;

import com.gft.wrk25_communication.communication.application.NotificationSaveUseCase;
import com.gft.wrk25_communication.communication.application.dto.CartProductChangedDTO;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import com.gft.wrk25_communication.communication.domain.notification.Notification;
import com.gft.wrk25_communication.communication.domain.notification.NotificationFactory;
import com.gft.wrk25_communication.communication.infrastructure.exception.ProductNotFoundException;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductInCartChangedNotificationReceiverTest {

    @Mock
    ApiClient apiClient;

    @Mock
    NotificationFactory notificationFactory;

    @Mock
    NotificationSaveUseCase notificationSaveUseCase;

    @InjectMocks
    private ProductInCartChangedNotificationReceiver receiver;

    @Test
    void testReceive() {

        UserId userId = new UserId(UUID.randomUUID());
        ProductDTO product = Instancio.create(ProductDTO.class);
        ProductId productId = new ProductId(product.id());

        CartProductChangedDTO notification = new CartProductChangedDTO(
                userId.userId(),
                product.id()
        );

        Notification notificationToReturn = Instancio.create(Notification.class);

        when(apiClient.getProductById(productId)).thenReturn(product);

        when(notificationFactory.createProductChangedNotification(userId, product)).thenReturn(notificationToReturn);

        receiver.receive(notification);

        verify(apiClient, times(1)).getProductById(productId);
        verify(notificationFactory, times(1)).createProductChangedNotification(userId, product);
        verify(notificationSaveUseCase,times(1)).execute(notificationToReturn);
    }

    @Test
    void testReceiveProductIdNotFound() {

        UserId userId = new UserId(UUID.randomUUID());
        ProductDTO product = Instancio.create(ProductDTO.class);
        ProductId productId = new ProductId(product.id());

        CartProductChangedDTO notification = new CartProductChangedDTO(
                userId.userId(),
                product.id()
        );

        when(apiClient.getProductById(productId)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> receiver.receive(notification));
    }

}