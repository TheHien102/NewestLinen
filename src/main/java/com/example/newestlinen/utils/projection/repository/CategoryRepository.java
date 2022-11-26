package com.example.newestlinen.utils.projection.repository;

import com.example.newestlinen.storage.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Category getById(Long Id);
    List<Category> findAllByParentCategoryIdIsNull();
}
