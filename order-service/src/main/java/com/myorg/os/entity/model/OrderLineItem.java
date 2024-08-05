package com.myorg.os.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrderLineItem")
@Table(name = "ORDER_LINE_ITEM")
public class OrderLineItem implements Serializable, Cloneable {

  @Serial
  private static final long serialVersionUID = -7981542792921564864L;

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

  @Column(name = "UNIT_TOTAL")
  private BigDecimal unitTotal;

  @Column(name = "ORDER_ID")
  private UUID orderId;

  @Override
  public boolean equals(Object object) {
    return object == this || object instanceof OrderLineItem orderLineItem
        && Objects.equals(orderLineItemId, orderLineItem.getOrderLineItemId())
        && Objects.equals(skuCode, orderLineItem.getSkuCode())
        && Objects.equals(price, orderLineItem.getPrice())
        && Objects.equals(quantity, orderLineItem.getQuantity())
        && Objects.equals(unitTotal, orderLineItem.getUnitTotal())
        && Objects.equals(orderId, orderLineItem.getOrderId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderLineItemId, skuCode, price, quantity, unitTotal, orderId);
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
