package com.myorg.ps.entity.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class ProductRequest {

  @NotBlank(message = "{product.request.name.notBlank}")
  private String name;

  @NotBlank(message = "{product.request.description.notBlank}")
  private String description;

  @NotNull(message = "{product.request.price.notNull}")
  private BigDecimal price;
}
