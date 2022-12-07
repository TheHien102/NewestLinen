package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.Address.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findByParentId(Long parent_id);

    Page<Province> findAllByParentId(Long id, Pageable pageable);

    Province findByNameLike(String name);
}
