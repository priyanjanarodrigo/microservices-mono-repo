package com.myorg.os.controller;

import static com.myorg.os.util.Constants.ORDER_BASE_URI;

import com.myorg.os.entity.dto.request.order.OrderRequest;
import com.myorg.os.entity.dto.response.order.OrderResponse;
import com.myorg.os.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ORDER_BASE_URI)
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest,
      HttpServletRequest httpServletRequest) {
    OrderResponse orderResponse = orderService.placeOrder(orderRequest);
    return ResponseEntity
        .created(URI.create(httpServletRequest.getRequestURI() + "/" + orderResponse.getId()))
        .body(orderResponse);
  }
}
