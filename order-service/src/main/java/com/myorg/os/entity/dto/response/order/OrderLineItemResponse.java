package com.myorg.os.entity.dto.response.order;

import java.math.BigDecimal;
import java.util.UUID;
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
public class OrderLineItemResponse {

  private UUID orderLineItemId;

  private String skuCode;

  private BigDecimal price;

  private Integer quantity;

  private UUID orderId;
}
