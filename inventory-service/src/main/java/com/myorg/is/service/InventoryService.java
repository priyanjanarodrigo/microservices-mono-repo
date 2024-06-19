package com.myorg.is.service;

import com.myorg.is.entity.dto.request.InventoryPatchRequest;
import com.myorg.is.entity.dto.request.InventoryRequest;
import com.myorg.is.entity.dto.request.InventoryResponse;

public interface InventoryService {

  boolean isInStock(String skuCode);

  boolean isExistingSkuCode(String skuCode);

  InventoryResponse saveInventoryRecord(InventoryRequest inventoryRequest);

  InventoryResponse findInventoryRecordById(String id);

  InventoryResponse patchInventoryRecord(String id, InventoryPatchRequest inventoryPatchRequest);
}
