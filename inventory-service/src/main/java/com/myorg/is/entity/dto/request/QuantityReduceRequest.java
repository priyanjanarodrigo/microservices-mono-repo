package com.myorg.is.entity.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * QuantityReduceRequest record.
 *
 * @param skuCode         SkuCode of the product.
 * @param reducedQuantity Quantity to be reduced from the inventory.
 */
public record QuantityReduceRequest(
    @NotBlank(message = "{inventory.quantityReduceRequest.skuCode.notBlank}")
    String skuCode,
    @NotNull(message = "{inventory.quantityReduceRequest.reducedQuantity.notNull}")
    @Min(value = 1, message = "{inventory.quantityReduceRequest.reducedQuantity.min}")
    Integer reducedQuantity
) {

}
