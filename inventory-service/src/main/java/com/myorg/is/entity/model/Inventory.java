package com.myorg.is.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Inventory")
@Table(name = "INVENTORY")
public class Inventory {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "SKU_CODE")
  private String skuCode;

  @Column(name = "REORDER_LEVEL")
  private Integer reorderLevel;

  @Column(name = "AVAILABLE_QUANTITY")
  private Integer availableQuantity;
}
