package com.myorg.os.entity.dto.request.inventory;

import java.util.List;
import lombok.Builder;

@Builder
public record SkuCodeBasedQuantityReduceRequest(
    List<QuantityReduceRequest> quantityReduceRequests
) {

}
