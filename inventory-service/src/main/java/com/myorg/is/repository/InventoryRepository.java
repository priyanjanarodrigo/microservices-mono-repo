package com.myorg.is.repository;

import com.myorg.is.entity.model.Inventory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "inventoryRepository")
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

  @Query(value = "SELECT COUNT(inv) > 0 FROM Inventory inv WHERE inv.sku_code = :skuCode", nativeQuery = true)
  boolean existsBySkuCode(@Param("skuCode") String skuCode);
}
