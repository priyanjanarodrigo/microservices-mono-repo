package com.myorg.is.service;

import com.myorg.is.entity.dto.request.InventoryPatchRequest;
import com.myorg.is.entity.dto.request.InventoryRequest;
import com.myorg.is.entity.dto.request.QuantityReduceRequest;
import com.myorg.is.entity.dto.response.inventory.InventoryResponse;
import java.util.List;

public interface InventoryService {

  boolean isInStock(String skuCode);

  boolean isExistingSkuCode(String skuCode);

  InventoryResponse saveInventoryRecord(InventoryRequest inventoryRequest);

  InventoryResponse findInventoryRecordById(String id);

  InventoryResponse patchInventoryRecord(String id, InventoryPatchRequest inventoryPatchRequest);

  InventoryResponse findInventoryRecordBySkuCode(String skuCode);

  List<InventoryResponse> findInventoryRecordsBySkuCodes(List<String> skuCodes);

  List<InventoryResponse> reduceQuantitiesBySkuCodes(
      List<QuantityReduceRequest> quantityReduceRequests);
}
