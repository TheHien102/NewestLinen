package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.CartModel.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartItemRepository extends JpaRepository<CartItem,Long>, JpaSpecificationExecutor<CartItem> {
}
