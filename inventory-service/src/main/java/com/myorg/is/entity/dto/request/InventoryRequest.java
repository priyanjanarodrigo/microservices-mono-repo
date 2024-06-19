package com.myorg.is.entity.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record InventoryRequest(
    @NotBlank(message = "{inventory.request.skuCode.notBlank}") String skuCode,
    @NotNull(message = "{inventory.request.reorderLevel.notNull}")
    @Min(value = 0, message = "{inventory.request.reorderLevel.min}") Integer reorderLevel,
    @NotNull(message = "{inventory.request.availableQuantity.notNull}")
    @Min(value = 0, message = "{inventory.request.availableQuantity.min}") Integer availableQuantity
) {

}
