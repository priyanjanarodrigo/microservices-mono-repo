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
@Table(name = "ORDER_TABLE")
@Entity(name = "OrderEntity")
@AllArgsConstructor
public class Order implements Serializable, Cloneable {

  @Serial
  private static final long serialVersionUID = 3705521753174784274L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "ORDER_NUMBER")
  private UUID orderNumber;

  @Column(name = "TOTAL")
  private BigDecimal total;

  @Override
  public boolean equals(Object object) {
    return this == object || object instanceof Order order
        && Objects.equals(id, order.getId())
        && Objects.equals(orderNumber, order.getOrderNumber())
        && Objects.equals(total, order.getTotal());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderNumber, total);
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
