package com.gft.wrk25_communication.communication.infrastructure.config;

import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.notification.NotificationId;
import com.gft.wrk25_communication.communication.infrastructure.error.ErrorResponse;
import com.gft.wrk25_communication.communication.infrastructure.exception.NotificationNotFoundException;
import com.gft.wrk25_communication.communication.infrastructure.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler customExceptionHandler;

    @BeforeEach
    void setUp() {
        customExceptionHandler = new CustomExceptionHandler();
    }

    @Test
    void testHandleGlobalException() {
        ResponseEntity<ErrorResponse> response = customExceptionHandler.handleGlobalException(new RuntimeException());

        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorResponse.getError(), response.getBody().getError());
    }

    @Test
    void testHandleNotificationNotFoundException() {

        NotificationId notificationId = new NotificationId();

        ResponseEntity<ErrorResponse> response = customExceptionHandler
                .handleNotificationNotFoundException(new NotificationNotFoundException(notificationId));

        ErrorResponse errorResponse = new ErrorResponse("Notification with id " + notificationId.id() + " not found");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorResponse.getError(), response.getBody().getError());
    }

    @Test
    void testHandleIllegalArgumentException() {

        ResponseEntity<ErrorResponse> response = customExceptionHandler
                .handleIllegalArgumentException(new IllegalArgumentException());

        ErrorResponse errorResponse = new ErrorResponse("An argument passed is either in a wrong format or null");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorResponse.getError(), response.getBody().getError());

    }

    @Test
    void testHandleHttpMessageNotReadableExceptionException() {

        ResponseEntity<ErrorResponse> response = customExceptionHandler
                .handleHttpMessageNotReadableExceptionException(new HttpMessageNotReadableException(""));

        ErrorResponse errorResponse = new ErrorResponse("The body of the request must not be empty");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorResponse.getError(), response.getBody().getError());
    }

    @Test
    void testHandleProductNotFoundException() {

        ProductId productId = new ProductId(1L);

        ResponseEntity<ErrorResponse> response = customExceptionHandler
                .handleProductNotFoundException(new ProductNotFoundException(productId));

        ErrorResponse errorResponse = new ErrorResponse("The product " + productId.id() + " was not found");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorResponse.getError(), response.getBody().getError());
    }
}