package com.gft.wrk25_communication.communication.application.web;

import com.gft.wrk25_communication.communication.application.dto.ProductDTO;
import com.gft.wrk25_communication.communication.domain.ProductId;
import com.gft.wrk25_communication.communication.domain.UserId;

import java.util.List;

public interface ApiClient {

    List<UserId> getUsersThatHaveProductInFavorites(ProductId productId);

    ProductDTO getProductById(ProductId productId);

    void deleteUserDeletedCart(UserId userId);

}
