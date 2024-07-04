package com.myorg.ps.entity.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductRequest(
    @NotBlank(message = "{product.request.name.notBlank}") String name,
    @NotBlank(message = "{product.request.description.notBlank}") String description,
    @NotNull(message = "{product.request.price.notNull}") BigDecimal price,
    @NotBlank(message = "{product.request.skuCode.notBlank}") String skuCode
) {

}
