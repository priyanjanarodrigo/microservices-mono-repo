package com.myorg.os.repository;

import com.myorg.os.entity.model.OrderLineItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "orderLineItemRepository")
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, UUID> {

}
