package com.example.newestlinen.utils.projection.repository;



import com.example.newestlinen.storage.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    public Permission findFirstByName(String name);
    void deleteAllByIdNotIn(Collection<Long> id);
}
