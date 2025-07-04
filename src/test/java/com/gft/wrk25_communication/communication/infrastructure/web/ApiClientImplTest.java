package com.gft.wrk25_communication.communication.infrastructure.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientImplTest {

    private MockWebServer mockWebServer;

    private ApiClientImpl apiClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
        apiClient = new ApiClientImpl(webClient);
    }

    @AfterEach
    void shutDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void testGetUsersThatHaveProductInFavorites() throws Exception {

        List<UUID> users = List.of(UUID.randomUUID(), UUID.randomUUID());

        String response = objectMapper.writeValueAsString(users);

        mockWebServer.enqueue(new MockResponse()
                .setBody(response)
                .setHeader("Content-Type", "application/json"));

        List<UserId> actualResponse = apiClient.getUsersThatHaveProductInFavorites(new ProductId(1L));
        assertEquals(users.stream().map(UserId::new).toList(), actualResponse);
    }

    @Test
    void testGetUsersThatHaveProductInFavoritesUsersNull() throws Exception {

        List<UUID> users = null;

        String response = objectMapper.writeValueAsString(users);

        mockWebServer.enqueue(new MockResponse()
                .setBody(response)
                .setHeader("Content-Type", "application/json"));

        List<UserId> actualResponse = apiClient.getUsersThatHaveProductInFavorites(new ProductId(1L));
        assertEquals(List.of(), actualResponse);
    }

    @Test
    void testGetUsersThatHaveProductInFavoritesReturnsNone() throws Exception {

        List<UUID> users = List.of();

        String response = objectMapper.writeValueAsString(users);

        mockWebServer.enqueue(new MockResponse()
                .setBody(response)
                .setHeader("Content-Type", "application/json"));

        List<UserId> actualResponse = apiClient.getUsersThatHaveProductInFavorites(new ProductId(1L));
        assertTrue(actualResponse.isEmpty());
    }

    @Test
    void testGetProductById() throws Exception {

        ProductDTO product = Instancio.create(ProductDTO.class);

        String response = objectMapper.writeValueAsString(product);

        mockWebServer.enqueue(new MockResponse()
                .setBody(response)
                .setHeader("Content-Type", "application/json"));

        ProductDTO actualResponse = apiClient.getProductById(new ProductId(product.id()));

        assertEquals(product, actualResponse);
    }

    @Test
    void testGetProductByIdReturnsNull() {

        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json"));

        ProductDTO actualResponse = apiClient.getProductById(new ProductId(1L));

        assertNull(actualResponse);
    }

    @Test
    void deleteUserDeletedCart() throws Exception {

        UserId userId = Instancio.create(UserId.class);

        mockWebServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 204 No Content"));

        apiClient.deleteUserDeletedCart(userId);

        String requestPath = mockWebServer.takeRequest().getPath();

        assertNotNull(requestPath);
        assertTrue(requestPath.contains(userId.userId().toString()));
    }

    @Test
    void deleteUserDeletedCart5xxError() throws Exception {

        UserId userId = Instancio.create(UserId.class);

        mockWebServer.enqueue(new MockResponse()
                        .setStatus("HTTP/1.1 500 Internal Server Error"));

        apiClient.deleteUserDeletedCart(userId);

        String requestPath = mockWebServer.takeRequest().getPath();

        assertNotNull(requestPath);
        assertTrue(requestPath.contains(userId.userId().toString()));
    }

    @Test
    void deleteUserDeletedCart4xxError() throws Exception {

        UserId userId = Instancio.create(UserId.class);

        mockWebServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 400 Bad Request"));

        apiClient.deleteUserDeletedCart(userId);

        String requestPath = mockWebServer.takeRequest().getPath();

        assertNotNull(requestPath);
        assertTrue(requestPath.contains(userId.userId().toString()));
    }

    @Test
    void deleteUserDeletedCartNotFoundError() throws Exception {

        UserId userId = Instancio.create(UserId.class);

        mockWebServer.enqueue(new MockResponse()
                        .setStatus("HTTP/1.1 404 Not Found"));

        apiClient.deleteUserDeletedCart(userId);

        String requestPath = mockWebServer.takeRequest().getPath();

        assertNotNull(requestPath);
        assertTrue(requestPath.contains(userId.userId().toString()));
    }

}