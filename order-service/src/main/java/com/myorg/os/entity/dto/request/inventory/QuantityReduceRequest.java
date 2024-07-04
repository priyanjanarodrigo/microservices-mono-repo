package com.myorg.os.entity.dto.request.inventory;

import lombok.Builder;

/**
 * QuantityReduceRequest record.
 *
 * @param skuCode         SkuCode of the product.
 * @param reducedQuantity Quantity to be reduced from the inventory.
 */
@Builder
public record QuantityReduceRequest(
    String skuCode,
    Integer reducedQuantity
) {

}
