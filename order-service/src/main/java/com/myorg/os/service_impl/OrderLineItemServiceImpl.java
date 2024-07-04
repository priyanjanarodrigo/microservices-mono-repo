package com.myorg.os.service_impl;

import com.myorg.os.entity.model.OrderLineItem;
import com.myorg.os.repository.OrderLineItemRepository;
import com.myorg.os.service.OrderLineItemService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "orderLineItemServiceImpl")
@RequiredArgsConstructor
public class OrderLineItemServiceImpl implements OrderLineItemService {

  private final OrderLineItemRepository orderLineItemRepository;

  @Override
  public List<OrderLineItem> createOrderLineItems(
      List<OrderLineItem> orderLineItems, UUID orderId) {

    // Assigning the order ID to each order line item
    orderLineItems.forEach(orderLineItem -> orderLineItem.setOrderId(orderId));

    return orderLineItemRepository.saveAll(orderLineItems);
  }
}
