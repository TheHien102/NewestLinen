package com.example.newestlinen.utils.projection.repository.Product;

import com.example.newestlinen.storage.model.ProductModel.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VariantRepository extends JpaRepository<Variant,Long> {
    void deleteAllByIdNotIn(Collection<Long> id);
}
