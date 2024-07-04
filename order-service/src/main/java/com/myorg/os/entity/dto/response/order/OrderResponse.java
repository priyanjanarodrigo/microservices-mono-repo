package com.myorg.os.entity.dto.response.order;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderResponse(
    UUID id, UUID orderNumber, BigDecimal total, List<OrderLineItemResponse> lineItems) {

}
