package com.myorg.os.entity.mapper;

import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import com.myorg.os.entity.dto.response.order.OrderResponse;
import com.myorg.os.entity.model.Order;
import com.myorg.os.entity.model.OrderLineItem;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * EntityToDtoMapper class: Responsible for mapping entities to DTOs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityToDtoMapper {

  /**
   * Maps an OrderLineItem to an OrderLineItemResponse and returns it.
   *
   * @param orderLineItem OrderLineItem object to be mapped.
   * @return OrderLineItemResponse
   */
  public static OrderLineItemResponse mapOrderLineItemToOrderLineItemResponse(
      OrderLineItem orderLineItem) {
    return OrderLineItemResponse.builder()
        .skuCode(orderLineItem.getSkuCode())
        .price(orderLineItem.getPrice())
        .quantity(orderLineItem.getQuantity())
        .unitTotal(orderLineItem.getUnitTotal())
        .build();
  }

  /**
   * Maps a list of OrderLineItem to a list of OrderLineItemResponse and returns the list.
   *
   * @param orderLineItems List of OrderLineItem objects to be mapped.
   * @return List<OrderLineItemResponse>
   */
  public static List<OrderLineItemResponse> mapOrderLineItemsListToOrderLineItemResponseList(
      List<OrderLineItem> orderLineItems) {
    return orderLineItems.stream()
        .map(EntityToDtoMapper::mapOrderLineItemToOrderLineItemResponse)
        .toList();
  }

  /**
   * Maps an Order and a list of OrderLineItems to an OrderResponse and returns it.
   *
   * @param order          Order object to be mapped.
   * @param orderLineItems List of OrderLineItem objects to be mapped.
   * @return OrderResponse
   */
  public static OrderResponse mapOrderAndOrderLineItemsToOrderResponse(
      Order order, List<OrderLineItem> orderLineItems) {
    return OrderResponse.builder()
        .id(order.getId())
        .orderNumber(order.getOrderNumber())
        .total(order.getTotal())
        .lineItems(mapOrderLineItemsListToOrderLineItemResponseList(orderLineItems))
        .build();
  }
}
