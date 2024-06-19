package com.myorg.ps.entity.dto.response.product;

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
public final class ProductResponse {

  private String id;

  private String name;

  private String description;

  private BigDecimal price;

  private String skuCode;
}
