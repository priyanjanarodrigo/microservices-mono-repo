package com.myorg.is.entity.mapper;

import static java.util.stream.Collectors.toList;

import com.myorg.is.entity.dto.response.inventory.InventoryResponse;
import com.myorg.is.entity.model.Inventory;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * EntityToDtoMapper class: Contains utility methods required to map entity objects in to DTOs.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityToDtoMapper {

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

  /**
   * Maps a list of inventory records in to a list of inventory responses and returns it.
   *
   * @param inventories List of inventory records.
   * @return List<InventoryResponse>
   */
  public static List<InventoryResponse> mapInventoriesInventoryResponses(
      List<Inventory> inventories) {
    return inventories.stream()
        .map(EntityToDtoMapper::mapInventoryToInventoryResponse)
        .collect(toList());
  }
}
