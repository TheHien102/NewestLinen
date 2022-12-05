package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.CartModel.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByAccountId(Long account_id);
}
