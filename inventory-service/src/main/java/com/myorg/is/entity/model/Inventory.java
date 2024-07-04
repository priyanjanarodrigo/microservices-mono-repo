package com.myorg.is.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@ToString
@AllArgsConstructor
@Entity(name = "Inventory")
@Table(name = "INVENTORY")
public class Inventory implements Serializable, Cloneable {

  @Serial
  private static final long serialVersionUID = 5840178066452112079L;

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

  @Override
  public boolean equals(Object object) {
    return this == object || object instanceof Inventory inventory
        && Objects.equals(id, inventory.getId())
        && Objects.equals(skuCode, inventory.getSkuCode())
        && Objects.equals(getReorderLevel(), inventory.getReorderLevel())
        && Objects.equals(getAvailableQuantity(), inventory.getAvailableQuantity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getSkuCode(), getReorderLevel(), getAvailableQuantity());
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
