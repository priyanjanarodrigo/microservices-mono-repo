package com.myorg.os.repository;

import com.myorg.os.entity.model.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "orderRepository")
public interface OrderRepository extends JpaRepository<Order, UUID> {

}
