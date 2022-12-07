package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.Address.ProvinceManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<ProvinceManagement, Long> {
    ProvinceManagement findByParentId(Long parent_id);

    Page<ProvinceManagement> findAllByParentId(Long id, Pageable pageable);

    ProvinceManagement findByNameLike(String name);
}
