package com.myorg.os.entity.model;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "ORDER_TABLE")
@Entity(name = "ORDER_TABLE")
public class Order {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "order_number")
  private UUID orderNumber;
}
