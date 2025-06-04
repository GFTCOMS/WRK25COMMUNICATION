package com.gft.wrk25_communication.communication.infrastructure.web;

import com.gft.wrk25_communication.communication.application.web.ApiClient;
import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiClientImpl implements ApiClient {

    @Value("${users.url}")
    private String usersUrl;

    @Value("${products.url}")
    private String productsUrl;

    @Value(" ${cart.url}")
    private String cartUrl;

    @Value("${users.product.find.id}")
    private String findUsersFromProductIdEndpoint;

    @Value("${products.find.id}")
    private String findProductFromIdEndpoint;

    @Value("${cart.user.deleted}")
    private String deleteUsersFromCartEndpoint;

    private final WebClient webClient;

    @Override
    public List<UserId> getUsersThatHaveProductInFavorites(ProductId productId) {

        List<UUID> users = webClient.get()
                .uri(usersUrl + findUsersFromProductIdEndpoint + productId.id())
                .retrieve()
                .bodyToFlux(UUID.class)
                .collectList()
                .block();

        if (users.isEmpty()) {
            log.error("Users not found with product id {}", productId.id());
            return List.of();
        }

        log.info("{} users found", users.size());

        return users.stream().map(UserId::new).toList();
    }

    @Override
    public ProductDTO getProductById(ProductId productId) {
        ProductDTO productDTO = webClient.get()
                .uri(productsUrl + findProductFromIdEndpoint + "/" + productId.id())
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();

        if (productDTO == null) {
            log.error("Product not found with id {}", productId.id());
            return null;
        }

        log.info("Product found with id {}", productDTO.id());
        return productDTO;
    }

    @Override
    public void deleteUserDeletedCart(UserId userId) {

        log.info("Deleting cart for user with id {}", userId);

        webClient.delete()
                .uri(cartUrl + deleteUsersFromCartEndpoint + userId.userId().toString())
                .exchangeToMono(response -> {
                    HttpStatusCode status = response.statusCode();
                    if (status == HttpStatus.NOT_FOUND) {
                        log.warn("Cart not found for user id {}. Skipping deletion.", userId);
                    } else if (status.is4xxClientError() || status.is5xxServerError()) {
                        log.error("Error while deleting cart for user id {}. Status: {}", userId, status);
                    } else {
                        log.info("Successfully deleted cart for user id {}", userId);
                    }
                    return Mono.empty();
                }).block();
    }

}
