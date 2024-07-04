package com.myorg.is.repository;

import com.myorg.is.entity.model.Inventory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "inventoryRepository")
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

  @Query(value = "SELECT COUNT(inv) > 0 FROM Inventory inv WHERE inv.skuCode = :skuCode")
  boolean existsBySkuCode(@Param("skuCode") String skuCode);

  @Query(value = "SELECT inv FROM Inventory inv WHERE inv.skuCode = :skuCode")
  Optional<Inventory> findBySkuCode(@Param("skuCode") String skuCode);

  @Query(value = "SELECT inv FROM Inventory inv WHERE inv.skuCode IN :skuCodes")
  List<Inventory> findBySkuCodes(@Param("skuCodes") List<String> skuCodes);
}
