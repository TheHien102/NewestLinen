package com.example.newestlinen.utils.projection.repository.Product;

import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant,Long> {
}
