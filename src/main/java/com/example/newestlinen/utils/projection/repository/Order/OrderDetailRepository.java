package com.example.newestlinen.utils.projection.repository.Order;

import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>, JpaSpecificationExecutor<OrderDetail> {
    List<OrderDetail> findAllByOrderId(Long order_id);
}
