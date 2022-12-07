package com.example.newestlinen.utils.projection.repository.Cart;

import com.example.newestlinen.storage.model.Address.AddressManagement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressManagementRepository extends JpaRepository<AddressManagement,Long> {
    AddressManagement findByParentId(Long parent_id);

    AddressManagement findByNameLike(String name);
}
