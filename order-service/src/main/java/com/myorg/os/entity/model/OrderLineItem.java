package com.myorg.os.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "ORDER_LINE_ITEM")
@Table(name = "ORDER_LINE_ITEM")
public class OrderLineItem {

  @Id
  @Column(name = "ORDER_LINE_ITEM_ID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID orderLineItemId;

  @Column(name = "SKU_CODE")
  private String skuCode;

  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "QUANTITY")
  private Integer quantity;

  @Column(name = "ORDER_ID")
  private UUID orderId;
}

