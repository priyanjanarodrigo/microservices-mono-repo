package com.myorg.os.service;

import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import com.myorg.os.entity.model.OrderLineItem;
import java.util.List;
import java.util.UUID;

public interface OrderLineItemService {

  List<OrderLineItem> createOrderLineItems(
      List<OrderLineItem> orderLineItemRequests, UUID orderId);
}
