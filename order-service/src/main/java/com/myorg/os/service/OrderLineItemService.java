package com.myorg.os.service;

import com.myorg.os.entity.dto.request.order.OrderLineItemRequest;
import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import java.util.List;
import java.util.UUID;

public interface OrderLineItemService {

  List<OrderLineItemResponse> createOrderLineItems(
      List<OrderLineItemRequest> orderLineItemRequests, UUID orderId);
}
