package com.myorg.is.entity.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record InventoryResponse(
    UUID id,
    String skuCode,
    Integer reorderLevel,
    Integer availableQuantity
) {

}
