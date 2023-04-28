package com.myorg.os.entity.dto.response.order;


import java.util.List;
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
public final class OrderResponse {

  private UUID id;

  private UUID orderNumber;

  private List<OrderLineItemResponse> lineItems;
}
