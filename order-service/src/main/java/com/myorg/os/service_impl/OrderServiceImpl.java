package com.myorg.os.service_impl;

import com.myorg.os.entity.dto.request.order.OrderRequest;
import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import com.myorg.os.entity.dto.response.order.OrderResponse;
import com.myorg.os.entity.mapper.OrderMapper;
import com.myorg.os.entity.model.Order;
import com.myorg.os.exception.InternalServerException;
import com.myorg.os.repository.OrderRepository;
import com.myorg.os.service.OrderLineItemService;
import com.myorg.os.service.OrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(value = "orderServiceImpl")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final OrderLineItemService orderLineItemService;

  @Override
  @Transactional
  public OrderResponse placeOrder(OrderRequest orderRequest) {
    try {
      Order orderInitial = Order.builder().orderNumber(UUID.randomUUID()).build();
      Order order = orderRepository.save(orderInitial);

      List<OrderLineItemResponse> orderLineItemResponses = orderLineItemService
          .createOrderLineItems(orderRequest.getLineItems(), order.getId());

      return OrderMapper.mapOrderAndListOfOrderLineItemsToOderResponsesToOrderResponse(
          order, orderLineItemResponses);
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new InternalServerException(
          "Unexpected error occurred while creating an order. " + exception.getMessage());
    }
  }
}
