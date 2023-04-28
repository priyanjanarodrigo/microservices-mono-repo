package com.myorg.os.entity.dto.request.order;

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
@ToString
@NoArgsConstructor
@AllArgsConstructor

public final class OrderLineItemRequest {

  @NotBlank(message = "{order.request.skuCode.notBlank}")
  private String skuCode;

  @NotNull(message = "{order.request.price.notNull}")
  private BigDecimal price;

  @NotNull(message = "{order.request.quantity.notNull}")
  private Integer quantity;
}
