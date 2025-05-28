package com.gft.wrk25_communication.communication.application.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id, String name, InventoryData inventoryData, BigDecimal price) {
    public record InventoryData(Integer stock){}
}
