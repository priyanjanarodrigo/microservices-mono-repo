package com.myorg.os.entity.mapper;

import static java.util.stream.Collectors.toList;

import com.myorg.os.entity.dto.request.order.OrderLineItemRequest;
import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import com.myorg.os.entity.model.OrderLineItem;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderLineItemMapper {

  public static List<OrderLineItem> mapListOfOrderLineItemRequestsToListOfOrderLineItems(
      List<OrderLineItemRequest> orderLineItemRequests, UUID orderId) {
    return orderLineItemRequests
        .stream()
        .map(orderLineItemRequest -> mapOrderLineItemRequestToOrderLineItem(
            orderLineItemRequest, orderId))
        .collect(toList());
  }

  public static OrderLineItem mapOrderLineItemRequestToOrderLineItem(
      OrderLineItemRequest orderLineItemRequest, UUID orderId) {
    return OrderLineItem.builder()
        .skuCode(orderLineItemRequest.getSkuCode())
        .price(orderLineItemRequest.getPrice())
        .quantity(orderLineItemRequest.getQuantity())
        .orderId(orderId)
        .build();
  }

  public static List<OrderLineItemResponse> mapListOfOrderLineItemsToListOfOrderLineItemResponses(
      List<OrderLineItem> orderLineItems) {
    return orderLineItems
        .stream()
        .map(OrderLineItemMapper::mapOrderLineItemToOrderLineItemResponse)
        .collect(toList());
  }

  public static OrderLineItemResponse mapOrderLineItemToOrderLineItemResponse(
      OrderLineItem orderLineItem) {
    return OrderLineItemResponse.builder()
        .orderLineItemId(orderLineItem.getOrderLineItemId())
        .skuCode(orderLineItem.getSkuCode())
        .price(orderLineItem.getPrice())
        .quantity(orderLineItem.getQuantity())
        .orderId(orderLineItem.getOrderId())
        .build();
  }
}
