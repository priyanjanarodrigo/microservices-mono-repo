package com.myorg.is.entity.dto.request;

import jakarta.validation.Valid;
import java.util.List;

public record SkuCodeBasedQuantityReduceRequest(
    @Valid List<QuantityReduceRequest> quantityReduceRequests
) {

}
