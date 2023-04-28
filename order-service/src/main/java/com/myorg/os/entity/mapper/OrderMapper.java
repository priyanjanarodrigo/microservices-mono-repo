package com.myorg.os.entity.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import com.myorg.os.entity.dto.response.order.OrderResponse;
import com.myorg.os.entity.model.Order;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class OrderMapper {

  public static OrderResponse mapOrderAndListOfOrderLineItemsToOderResponsesToOrderResponse(
      Order order, List<OrderLineItemResponse> orderLineItemResponses) {
    return OrderResponse.builder()
        .id(order.getId())
        .orderNumber(order.getOrderNumber())
        .lineItems(orderLineItemResponses)
        .build();
  }
}
