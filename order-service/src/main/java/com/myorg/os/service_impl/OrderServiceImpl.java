package com.myorg.os.service_impl;

import static com.myorg.os.entity.mapper.EntityToDtoMapper.mapOrderAndOrderLineItemsToOrderResponse;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.resolve;

import com.myorg.os.entity.dto.request.inventory.QuantityReduceRequest;
import com.myorg.os.entity.dto.request.inventory.SkuCodeBasedQuantityReduceRequest;
import com.myorg.os.entity.dto.request.order.OrderLineItemRequest;
import com.myorg.os.entity.dto.request.order.OrderRequest;
import com.myorg.os.entity.dto.response.error.ApiErrorResponse;
import com.myorg.os.entity.dto.response.inventory.InventoryResponse;
import com.myorg.os.entity.dto.response.order.OrderResponse;
import com.myorg.os.entity.dto.response.product.ProductResponse;
import com.myorg.os.entity.model.Order;
import com.myorg.os.entity.model.OrderLineItem;
import com.myorg.os.exception.BadRequestException;
import com.myorg.os.exception.InternalServerException;
import com.myorg.os.exception.OutOfStockException;
import com.myorg.os.exception.ResourceNotFoundException;
import com.myorg.os.repository.OrderRepository;
import com.myorg.os.service.OrderLineItemService;
import com.myorg.os.service.OrderService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service(value = "orderServiceImpl")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final OrderLineItemService orderLineItemService;

  private final WebClient webClient;

  @Override
  @Transactional
  public OrderResponse placeOrder(OrderRequest orderRequest) {
    List<String> skuCodes = orderRequest.getLineItems().stream()
        .map(OrderLineItemRequest::getSkuCode).toList();

    log.info("skuCodes received with then request: {}", skuCodes);

    // Checks whether the corresponding product details are available for the given skuCodes.
    List<ProductResponse> productResponses = getProductsFromProductServiceBySkuCodes(skuCodes);

    // TODO: Make this map return directly via the product service response above.
    Map<String, ProductResponse> productsMap = productResponses.stream()
        .collect(toMap(ProductResponse::skuCode, productResponse -> productResponse));

    // Checks whether the corresponding inventory records are available for the given skuCodes.
    List<InventoryResponse> inventoryResponses = getInventoryFromInventoryServiceBySkuCodes(
        skuCodes);

    // TODO: Make this map return directly via the inventory service response above.
    Map<String, InventoryResponse> inventoryMap = inventoryResponses.stream()
        .collect(toMap(InventoryResponse::skuCode, inventoryResponse -> inventoryResponse));

    // Validates the stocks availability for the given order line items.
    validateStocksAvailability(orderRequest, inventoryMap);

    List<OrderLineItem> orderLineItems = orderRequest.getLineItems().stream().map(lineItem -> {
      BigDecimal price = productsMap.get(lineItem.getSkuCode()).price();
      int quantity = lineItem.getQuantity();
      return OrderLineItem.builder()
          .skuCode(lineItem.getSkuCode())
          .price(price)
          .quantity(quantity)
          .unitTotal(price.multiply(valueOf(quantity)))
          .build();
    }).toList();

    // Persisting the order entity to the database.
    Order order = orderRepository.save(Order.builder()
        .orderNumber(UUID.randomUUID()).total(calculateTotal(orderLineItems)).build());

    // Persisting the order line item entities to the database.
    List<OrderLineItem> savedOrderLineItems = orderLineItemService.createOrderLineItems(
        orderLineItems, order.getId());

    // Updating the inventory records depending on the quantity of each ordered item.
    List<InventoryResponse> updatedInventories = reduceOrderedQuantitiesFromInventoryBySkuCodes(
        savedOrderLineItems);

    log.info("updatedInventories: {}", updatedInventories);

    return mapOrderAndOrderLineItemsToOrderResponse(order, savedOrderLineItems);
  }

  /**
   * Updates the inventory records depending on the quantity of each ordered item.
   *
   * @param savedOrderLineItems Saved order line items.
   */
  private List<InventoryResponse> reduceOrderedQuantitiesFromInventoryBySkuCodes(
      List<OrderLineItem> savedOrderLineItems) {
    // Preparing the inventory update request object
    List<QuantityReduceRequest> quantityReduceRequests = savedOrderLineItems.stream()
        .map(orderLineItem -> QuantityReduceRequest.builder()
            .skuCode(orderLineItem.getSkuCode())
            .reducedQuantity(orderLineItem.getQuantity())
            .build())
        .toList();

    SkuCodeBasedQuantityReduceRequest skuCodeBasedQuantityReduceRequest =
        SkuCodeBasedQuantityReduceRequest.builder()
            .quantityReduceRequests(quantityReduceRequests)
            .build();

    return webClient
        .put()
        .uri("http://localhost:8083/api/inventories/skuCodes/reduceQuantities")
        .body(BodyInserters.fromValue(skuCodeBasedQuantityReduceRequest))
        .retrieve()
        .onStatus(
            httpStatus -> requireNonNull(resolve(httpStatus.value())).isError()
            , webClientResponse -> {
              HttpStatusCode httpStatusCode = webClientResponse.statusCode();
              if (httpStatusCode.equals(BAD_REQUEST)) {
                return webClientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(
                        new BadRequestException(errorResponse.message())));
              } else {
                return webClientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(
                        new InternalServerException(errorResponse.message())));
              }
            })
        .bodyToFlux(InventoryResponse.class)
        .collectList()
        .block();
  }

  /**
   * Validates the stocks availability for the given order line items.
   *
   * @param orderRequest Order request object.
   * @param inventoryMap Inventory details map.
   */
  private static void validateStocksAvailability(OrderRequest orderRequest,
      Map<String, InventoryResponse> inventoryMap) {
    Map<String, String> stockUnavailabilityMap = new HashMap<>();

    // Checks whether the required stock is available for each skuCodes.
    orderRequest.getLineItems().forEach(lineItem -> {
      int lineItemQuantity = lineItem.getQuantity();
      int availableQuantity = inventoryMap.get(lineItem.getSkuCode()).availableQuantity();

      if (lineItemQuantity > availableQuantity) {
        stockUnavailabilityMap.put(lineItem.getSkuCode(),
            Math.subtractExact(lineItemQuantity, availableQuantity) + " units are lacking");
      }
    });

    if (!stockUnavailabilityMap.isEmpty()) {
      throw new OutOfStockException(
          "Stocks unavailable for the following item(s): " + stockUnavailabilityMap);
    }
  }

  /**
   * Calculates the total amount of the order line items.
   *
   * @param orderLineItems List of order line items.
   * @return BigDecimal
   */
  private BigDecimal calculateTotal(List<OrderLineItem> orderLineItems) {
    return orderLineItems.stream().map(OrderLineItem::getUnitTotal).reduce(BigDecimal::add)
        .orElse(ZERO);
  }

  /**
   * Fetches the product details from the product service by the given skuCodes.
   *
   * @param skuCodes List of SKU codes.
   * @return List<ProductResponse>
   */
  private List<ProductResponse> getProductsFromProductServiceBySkuCodes(List<String> skuCodes) {
    return webClient
        .get()
        .uri("http://localhost:8080/api/products/skuCodes",
            uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
        .retrieve()
        .onStatus(
            httpStatus -> requireNonNull(resolve(httpStatus.value())).isError()
            , webClientResponse -> {
              HttpStatusCode httpStatusCode = webClientResponse.statusCode();
              if (httpStatusCode.equals(NOT_FOUND)) {
                return webClientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(
                        new ResourceNotFoundException(errorResponse.message())));
              } else {
                return webClientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(
                        new InternalServerException(errorResponse.message())));
              }
            })
        .bodyToFlux(ProductResponse.class)
        .collectList()
        .block();
  }

  /**
   * Fetches the inventory details from the inventory service by the given skuCodes.
   *
   * @param skuCodes List of SKU codes.
   * @return List<InventoryResponse>
   */
  private List<InventoryResponse> getInventoryFromInventoryServiceBySkuCodes(
      List<String> skuCodes) {
    return webClient
        .get()
        .uri("http://localhost:8083/api/inventories/skuCodes",
            uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
        .retrieve()
        .onStatus(
            httpStatus -> requireNonNull(resolve(httpStatus.value())).isError()
            , webClientResponse -> {
              HttpStatusCode httpStatusCode = webClientResponse.statusCode();
              if (httpStatusCode.equals(NOT_FOUND)) {
                return webClientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(
                        new ResourceNotFoundException(errorResponse.message())));
              } else {
                return webClientResponse.bodyToMono(ApiErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(
                        new InternalServerException(errorResponse.message())));
              }
            })
        .bodyToFlux(InventoryResponse.class)
        .collectList()
        .block();
  }
}
