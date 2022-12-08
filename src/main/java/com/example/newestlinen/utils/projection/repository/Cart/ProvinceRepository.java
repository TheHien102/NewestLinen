package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.Address.Province;
import com.example.newestlinen.storage.model.ProductModel.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProvinceRepository extends JpaRepository<Province, Long>, JpaSpecificationExecutor<Province> {
    Province findByParentId(Long parent_id);

    Page<Province> findAllByParentId(Long id, Pageable pageable);

    Province findByNameLike(String name);
}
