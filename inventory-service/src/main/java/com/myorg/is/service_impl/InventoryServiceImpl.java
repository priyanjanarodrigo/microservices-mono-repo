package com.myorg.is.service_impl;

import static com.myorg.is.entity.mapper.DtoToEntityMapper.mapInventoryPatchRequestToExistingInventory;
import static com.myorg.is.entity.mapper.DtoToEntityMapper.maptInventoryRequestToInventory;
import static com.myorg.is.entity.mapper.EntityToDtoMapper.mapInventoriesInventoryResponses;
import static com.myorg.is.entity.mapper.EntityToDtoMapper.mapInventoryToInventoryResponse;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.UUID.fromString;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

import com.myorg.is.entity.dto.request.InventoryPatchRequest;
import com.myorg.is.entity.dto.request.InventoryRequest;
import com.myorg.is.entity.dto.request.QuantityReduceRequest;
import com.myorg.is.entity.dto.response.inventory.InventoryResponse;
import com.myorg.is.entity.model.Inventory;
import com.myorg.is.exception.EmptyPatchRequestException;
import com.myorg.is.exception.GeneralClientDataException;
import com.myorg.is.exception.ResourceNotFoundException;
import com.myorg.is.repository.InventoryRepository;
import com.myorg.is.service.InventoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
          "Provided skuCode: " + inventoryRequest.skuCode() + " already exists");
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

    /*
     * To not being able to patch the skuCode, the following conditions should be satisfied:
     *
     *  1. Inventory patch request must contain skuCode and it should not be null
     *  2. The skuCode passed via inventory patch request must not be equal to the existing skuCode
     *     of the same record.
     *  3. The provided skuCode to be patched, must be associated with some other inventory
     *     record.
     * */
    if (nonNull(inventoryPatchRequest.skuCode())
        && !inventoryRecord.getSkuCode().equals(inventoryPatchRequest.skuCode())
        // If it's not the same skuCode associated with this particular record
        && isExistingSkuCode(inventoryPatchRequest.skuCode())) {
      throw new GeneralClientDataException(
          "Provided skuCode: " + inventoryPatchRequest.skuCode()
              + " is already associated with a different product related inventory record. "
              + "Redundant skuCode is not allowed to be patched");
    }

    return mapInventoryToInventoryResponse(
        saveInventoryEntity(mapInventoryPatchRequestToExistingInventory(inventoryRecord,
            inventoryPatchRequest)));
  }

  @Override
  public InventoryResponse findInventoryRecordBySkuCode(String skuCode) {
    return mapInventoryToInventoryResponse(findInventoryEntityRecordBySkuCode(skuCode));
  }

  @Override
  public List<InventoryResponse> findInventoryRecordsBySkuCodes(List<String> skuCodes) {
    List<Inventory> inventoryRecords = inventoryRepository.findBySkuCodes(skuCodes);

    if (inventoryRecords.isEmpty()) {
      throw new ResourceNotFoundException(
          "Inventory records not found for any of the skuCodes: " + skuCodes);
    }

    if (skuCodes.size() > inventoryRecords.size()) {
      skuCodes.removeAll(inventoryRecords.stream().map(Inventory::getSkuCode).toList());
      throw new ResourceNotFoundException(
          "Inventory records not found for some of the skuCodes: " + skuCodes);
    }

    return mapInventoriesInventoryResponses(inventoryRepository.findBySkuCodes(skuCodes));
  }

  @Override
  @Transactional
  public List<InventoryResponse> reduceQuantitiesBySkuCodes(
      List<QuantityReduceRequest> quantityReduceRequests) {
    List<String> skuCodes = quantityReduceRequests.stream().map(QuantityReduceRequest::skuCode)
        .collect(toCollection(ArrayList::new));

    List<Inventory> inventoryRecords = inventoryRepository.findBySkuCodes(skuCodes);

    if (skuCodes.size() > inventoryRecords.size()) {
      skuCodes.removeAll(inventoryRecords.stream().map(Inventory::getSkuCode).toList());
      throw new ResourceNotFoundException(
          "Inventory records not found for some of the skuCodes: " + skuCodes);
    }

    Map<String, Inventory> inventoryMap = inventoryRecords.stream()
        .collect(toMap(Inventory::getSkuCode, inventory -> inventory));

    quantityReduceRequests.forEach(request -> {
      Inventory inventory = inventoryMap.get(request.skuCode());
      inventory.setAvailableQuantity(inventory.getAvailableQuantity() - request.reducedQuantity());
    });

    return mapInventoriesInventoryResponses(inventoryRepository.saveAll(inventoryMap.values()));
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

  private Inventory findInventoryEntityRecordBySkuCode(String skuCode) {
    return inventoryRepository.findBySkuCode(skuCode)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Inventory record not found for the skuCode: " + skuCode));
  }
}
