package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.CartModel.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

public interface CartItemRepository extends JpaRepository<CartItem,Long>, JpaSpecificationExecutor<CartItem> {
}
