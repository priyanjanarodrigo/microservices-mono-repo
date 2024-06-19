package com.myorg.is.entity.dto.request;

import com.myorg.is.annotation.NotBlankIfPresent;
import jakarta.validation.constraints.Min;

public record InventoryPatchRequest(
    @NotBlankIfPresent String skuCode,
    @Min(value = 0, message = "{inventory.request.reorderLevel.min}") Integer reorderLevel,
    @Min(value = 0, message = "{inventory.request.availableQuantity.min}") Integer availableQuantity
) {

}
