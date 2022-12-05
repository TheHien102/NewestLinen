package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.CartModel.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findByItemProductId(Long Id);
}
