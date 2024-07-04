package com.myorg.os.entity.dto.response.inventory;

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
