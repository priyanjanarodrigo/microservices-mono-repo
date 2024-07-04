package com.myorg.os.entity.dto.response.order;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OrderLineItemResponse(String skuCode, BigDecimal price, Integer quantity,
                                    BigDecimal unitTotal) {

}
