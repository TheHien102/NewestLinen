package com.example.newestlinen.utils.projection.repository.Product;

import com.example.newestlinen.storage.model.ProductModel.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
