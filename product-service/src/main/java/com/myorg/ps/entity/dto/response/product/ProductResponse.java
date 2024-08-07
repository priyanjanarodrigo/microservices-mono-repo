package com.myorg.ps.entity.dto.response.product;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductResponse(
    String id,
    String name,
    String description,
    BigDecimal price,
    String skuCode
) {

}
