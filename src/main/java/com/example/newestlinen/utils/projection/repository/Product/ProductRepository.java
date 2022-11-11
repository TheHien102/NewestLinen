package com.example.newestlinen.utils.projection.repository.Product;

import com.example.newestlinen.storage.model.ProductModel.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> getAllByOrderById(@Nullable Specification<Product> specification, Pageable pageable);

    Product findProductById(Long id);
}
