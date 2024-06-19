package com.myorg.is.service_impl;

import static com.myorg.is.entity.mapper.DtoToEntityMapper.mapInventoryPatchRequestToExistingInventory;
import static com.myorg.is.entity.mapper.DtoToEntityMapper.maptInventoryRequestToInventory;
import static com.myorg.is.entity.mapper.EntityToDtoMapper.mapInventoryToInventoryResponse;
import static java.util.Objects.isNull;
import static java.util.UUID.fromString;

import com.myorg.is.entity.dto.request.InventoryPatchRequest;
import com.myorg.is.entity.dto.request.InventoryRequest;
import com.myorg.is.entity.dto.request.InventoryResponse;
import com.myorg.is.entity.model.Inventory;
import com.myorg.is.exception.EmptyPatchRequestException;
import com.myorg.is.exception.GeneralClientDataException;
import com.myorg.is.exception.ResourceNotFoundException;
import com.myorg.is.repository.InventoryRepository;
import com.myorg.is.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service(value = "inventoryServiceImpl")
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;

  @Override
  public boolean isInStock(String skuCode) {
    return false;
  }

  @Override
  public boolean isExistingSkuCode(String skuCode) {
    return inventoryRepository.existsBySkuCode(skuCode);
  }

  @Override
  public InventoryResponse saveInventoryRecord(InventoryRequest inventoryRequest) {
    if (isExistingSkuCode(inventoryRequest.skuCode())) {
      throw new GeneralClientDataException(
          "Provided skuCode: " + inventoryRequest.skuCode() + ", already exists");
    }

    return mapInventoryToInventoryResponse(
        saveInventoryEntity(maptInventoryRequestToInventory(inventoryRequest)));
  }

  @Override
  public InventoryResponse findInventoryRecordById(String id) {
    return mapInventoryToInventoryResponse(findInventoryEntityRecordByID(id));
  }

  @Override
  public InventoryResponse patchInventoryRecord(String id,
      InventoryPatchRequest inventoryPatchRequest) {
    validatePatchRequest(inventoryPatchRequest);
    Inventory inventoryRecord = findInventoryEntityRecordByID(id);

    return mapInventoryToInventoryResponse(
        saveInventoryEntity(mapInventoryPatchRequestToExistingInventory(inventoryRecord,
            inventoryPatchRequest)));
  }

  private Inventory findInventoryEntityRecordByID(String id) {
    return inventoryRepository.findById(fromString(id)).orElseThrow(
        () -> new ResourceNotFoundException("Inventory record not found for the id: " + id));
  }

  private Inventory saveInventoryEntity(Inventory inventory) {
    return inventoryRepository.save(inventory);
  }

  private void validatePatchRequest(InventoryPatchRequest inventoryPatchRequest) {
    if (isNull(inventoryPatchRequest.skuCode()) && isNull(inventoryPatchRequest.reorderLevel())
        && isNull(inventoryPatchRequest.availableQuantity())) {
      throw new EmptyPatchRequestException(
          "Patch request must contain at least one parameter to update");
    }
  }
}
