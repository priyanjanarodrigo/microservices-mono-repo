package com.myorg.is.entity.mapper;

import static java.util.Objects.nonNull;

import com.myorg.is.entity.dto.request.InventoryPatchRequest;
import com.myorg.is.entity.dto.request.InventoryRequest;
import com.myorg.is.entity.model.Inventory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * DtoToEntityMapper class: Contains utility methods required to map DTOs in to entity objects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DtoToEntityMapper {

  /**
   * Maps InventoryRequest in to Inventory and returns it.
   *
   * @param inventoryRequest InventoryRequest object.
   * @return Inventory.
   */
  public static Inventory maptInventoryRequestToInventory(InventoryRequest inventoryRequest) {
    return Inventory.builder()
        .skuCode(inventoryRequest.skuCode())
        .reorderLevel(inventoryRequest.reorderLevel())
        .availableQuantity(inventoryRequest.availableQuantity())
        .build();
  }

  /**
   * Maps InventoryPatchRequest object in to an existing Inventory object. Only the values from
   * inventory patch request which are not null or empty are replaced with the corresponding values
   * of Inventory object. (excluding the inventory id)
   *
   * @param inventory             Existing Inventory object.
   * @param inventoryPatchRequest InventoryPatchRequest object.
   * @return Inventory.
   */
  public static Inventory mapInventoryPatchRequestToExistingInventory(
      Inventory inventory, InventoryPatchRequest inventoryPatchRequest) {
    if (nonNull(inventoryPatchRequest.skuCode())) {
      inventory.setSkuCode(inventoryPatchRequest.skuCode());
    }

    if (nonNull(inventoryPatchRequest.reorderLevel())) {
      inventory.setReorderLevel(inventoryPatchRequest.reorderLevel());
    }

    if (nonNull(inventoryPatchRequest.availableQuantity())) {
      inventory.setAvailableQuantity(inventoryPatchRequest.availableQuantity());
    }
    return inventory;
  }
}
