package com.example.newestlinen.utils.projection.repository.Order;

import com.example.newestlinen.storage.model.OrderModel.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
