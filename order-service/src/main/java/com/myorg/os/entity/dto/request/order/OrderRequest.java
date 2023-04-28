package com.myorg.os.entity.dto.request.order;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class OrderRequest {

  @Valid
  private List<OrderLineItemRequest> lineItems;
}
