package com.myorg.is.entity.mapper;

import com.myorg.is.entity.dto.request.InventoryResponse;
import com.myorg.is.entity.model.Inventory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * EntityToDtoMapper class: Contains utility methods required to map entity objects in to DTOs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityToDtoMapper {

  /**
   * Maps Inventory in to InventoryResponse and returns it.
   *
   * @param inventory Inventory object.
   * @return InventoryResponse.
   */
  public static InventoryResponse mapInventoryToInventoryResponse(Inventory inventory) {
    return InventoryResponse.builder()
        .id(inventory.getId())
        .skuCode(inventory.getSkuCode())
        .reorderLevel(inventory.getReorderLevel())
        .availableQuantity(inventory.getAvailableQuantity())
        .build();
  }
}
