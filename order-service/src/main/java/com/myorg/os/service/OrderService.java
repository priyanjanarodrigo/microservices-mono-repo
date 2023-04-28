package com.myorg.os.service;

import com.myorg.os.entity.dto.request.order.OrderRequest;
import com.myorg.os.entity.dto.response.order.OrderResponse;

public interface OrderService {

  OrderResponse placeOrder(OrderRequest orderRequest);
}
