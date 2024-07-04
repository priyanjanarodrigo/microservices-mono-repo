package com.myorg.ps.entity.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "product")
public class Product implements Serializable, Cloneable {

  @Serial
  private static final long serialVersionUID = 4370503636337834261L;

  @Id
  private String id;

  private String name;

  private String description;

  private BigDecimal price;

  private String skuCode;

  @Override
  public boolean equals(Object object) {
    return this == object || object instanceof Product product
        && Objects.equals(id, product.getId())
        && Objects.equals(name, product.getName())
        && Objects.equals(description, product.getDescription())
        && Objects.equals(price, product.getPrice())
        && Objects.equals(skuCode, product.getSkuCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, skuCode);
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
