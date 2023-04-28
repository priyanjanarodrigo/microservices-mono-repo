package com.myorg.os.service_impl;

import static com.myorg.os.entity.mapper.OrderLineItemMapper.mapListOfOrderLineItemRequestsToListOfOrderLineItems;

import com.myorg.os.entity.dto.request.order.OrderLineItemRequest;
import com.myorg.os.entity.dto.response.order.OrderLineItemResponse;
import com.myorg.os.entity.mapper.OrderLineItemMapper;
import com.myorg.os.entity.model.OrderLineItem;
import com.myorg.os.exception.InternalServerException;
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
  public List<OrderLineItemResponse> createOrderLineItems(
      List<OrderLineItemRequest> orderLineItemRequests, UUID orderId) {

    try {
      List<OrderLineItem> orderLineItems = mapListOfOrderLineItemRequestsToListOfOrderLineItems(
          orderLineItemRequests, orderId);
      return OrderLineItemMapper.mapListOfOrderLineItemsToListOfOrderLineItemResponses(
          this.orderLineItemRepository.saveAll(orderLineItems));
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new InternalServerException(
          "Unexpected error occurred while creating order line items. " + exception.getMessage());
    }
  }
}
