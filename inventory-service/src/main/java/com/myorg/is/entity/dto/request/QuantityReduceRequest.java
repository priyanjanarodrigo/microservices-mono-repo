package com.myorg.is.entity.dto.request;

import com.myorg.is.validation.FullUpdateValidation;
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
    @NotBlank(message = "{inventory.quantityReduceRequest.skuCode.notBlank}", groups = FullUpdateValidation.class)
    String skuCode,
    @NotNull(message = "{inventory.quantityReduceRequest.reducedQuantity.notNull}", groups = FullUpdateValidation.class)
    @Min(value = 1, message = "{inventory.quantityReduceRequest.reducedQuantity.min}", groups = FullUpdateValidation.class)
    Integer reducedQuantity
) {

}
